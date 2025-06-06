package com.studeal.team.global.error.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.studeal.team.global.error.ApiResponse;
import com.studeal.team.global.error.code.ErrorReasonDTO;
import com.studeal.team.global.error.code.status.ErrorStatus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
@RequiredArgsConstructor
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    // 1) ConstraintViolationException 전용 처리
    @ExceptionHandler // Controller 내에서 특정 예외를 잡아 처리
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));

        ErrorStatus errorStatus = ErrorStatus.valueOf(errorMessage);
        return handleExceptionInternalConstraint(e, errorStatus, HttpHeaders.EMPTY, request);
    }

    // 2) MethodArgumentNotValidException 전용 처리
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors().stream()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });

        return handleExceptionInternalArgs(e,HttpHeaders.EMPTY,ErrorStatus.valueOf("_BAD_REQUEST"),request,errors);
    }

    // 3) GeneralException (Custom Exception) 전용 처리
    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity<Object> onThrowException(GeneralException generalException, HttpServletRequest request) {
        ErrorReasonDTO errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();
        return handleExceptionInternal(generalException,errorReasonHttpStatus,null,request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDTO reason,
                                                           HttpHeaders headers, HttpServletRequest request) {
        // 커스텀 코드로부터 적절한 HTTP 상태 코드 추출
        HttpStatus httpStatus = determineHttpStatus(reason.getCode(), reason.getHttpStatus());

        ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(), reason.getMessage(), null);
//        e.printStackTrace();

        WebRequest webRequest = new ServletWebRequest(request);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                httpStatus,
                webRequest
        );
    }

    // 4) 그 외 모든 Exception 처리 (최후의 보루)
    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        e.printStackTrace();

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternalFalse(e, ErrorStatus._INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY, httpStatus, request, e.getMessage());
    }

    private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, ErrorStatus errorCommonStatus,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request, String errorPoint) {
        // 커스텀 코드로부터 적절한 HTTP 상태 코드 추출
        HttpStatus httpStatus = determineHttpStatus(errorCommonStatus.getCode(), status);

        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), errorPoint);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                httpStatus,
                request
        );
    }

    private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers, ErrorStatus errorCommonStatus,
                                                               WebRequest request, Map<String, String> errorArgs) {
        // 커스텀 코드로부터 적절한 HTTP 상태 코드 추출
        HttpStatus httpStatus = determineHttpStatus(errorCommonStatus.getCode(), errorCommonStatus.getHttpStatus());

        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), errorArgs);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                httpStatus,
                request
        );
    }

    private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, ErrorStatus errorCommonStatus,
                                                                     HttpHeaders headers, WebRequest request) {
        // 커스텀 코드로부터 적절한 HTTP 상태 코드 추출
        HttpStatus httpStatus = determineHttpStatus(errorCommonStatus.getCode(), errorCommonStatus.getHttpStatus());

        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), null);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                httpStatus,
                request
        );
    }

    // 커스텀 코드를 기반으로 표준 HTTP 상태 코드 결정
    private HttpStatus determineHttpStatus(String customCode, HttpStatus defaultStatus) {
        if (customCode == null) {
            return defaultStatus;
        }

        if (customCode.startsWith("COMMON") || customCode.contains("200")) {
            return HttpStatus.OK;
        } else if (customCode.contains("400") ||
                  customCode.contains("4001") ||
                  customCode.contains("4002") ||
                  customCode.contains("4004") ||
                  customCode.contains("4005") ||
                  customCode.contains("4006")) {
            return HttpStatus.BAD_REQUEST;
        } else if (customCode.contains("401")) {
            return HttpStatus.UNAUTHORIZED;
        } else if (customCode.contains("403") ||
                  customCode.contains("4003")) {
            return HttpStatus.FORBIDDEN;
        } else if (customCode.contains("404")) {
            return HttpStatus.NOT_FOUND;
        } else if (customCode.contains("500")) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return defaultStatus;
        }
    }
}