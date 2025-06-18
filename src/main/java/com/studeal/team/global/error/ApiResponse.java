package com.studeal.team.global.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.studeal.team.global.error.code.BaseCode;
import com.studeal.team.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 응답을 위한 공통 응답 객체입니다.
 *
 * @param <T> 응답 데이터의 타입
 */
@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "httpStatusCode", "code", "message", "result"})
@Schema(description = "API 응답 형식")
public class ApiResponse<T> {

  /*
   * 성공 여부
   */
  @JsonProperty("isSuccess")
  @Schema(description = "성공 여부", example = "true")
  private final Boolean isSuccess;

  /*
   * HTTP 상태 코드
   */
  @Schema(description = "HTTP 상태 코드", example = "200")
  private final int httpStatusCode;

  /*
   * 커스텀 응답 코드
   */
  @Schema(description = "커스텀 응답 코드", example = "COMMON200")
  private final String code;

  /*
   * 응답 메시지
   */
  @Schema(description = "응답 메시지", example = "성공입니다.")
  private final String message;

  /*
   * 응답 데이터
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "응답 데이터")
  private T result;

  /**
   * 성공 응답을 생성합니다.
   *
   * @param result 응답 데이터
   * @param <T>    응답 데이터의 타입
   * @return ApiResponse 객체
   */
  public static <T> ApiResponse<T> onSuccess(T result) {
    return new ApiResponse<>(
        true,
        HttpStatus.OK.value(),
        SuccessStatus._OK.getCode(),
        SuccessStatus._OK.getMessage(),
        result);
  }

  /**
   * 주어진 BaseCode와 결과로 응답을 생성합니다.
   *
   * @param code   BaseCode
   * @param result 응답 데이터
   * @param <T>    응답 데이터의 타입
   * @return ApiResponse 객체
   */
  public static <T> ApiResponse<T> of(BaseCode code, T result) {
    return new ApiResponse<>(
        true,
        code.getReasonHttpStatus().getHttpStatus().value(),
        code.getReasonHttpStatus().getCode(),
        code.getReasonHttpStatus().getMessage(),
        result);
  }

  /**
   * 실패 응답을 생성합니다.
   *
   * @param code    에러 코드
   * @param message 에러 메시지
   * @param data    추가 데이터
   * @param <T>     응답 데이터의 타입
   * @return ApiResponse 객체
   */
  public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
    return new ApiResponse<>(false, extractHttpStatusCode(code), code, message, data);
  }

  /**
   * 커스텀 코드에서 HTTP 상태 코드를 추출합니다.
   *
   * @param customCode 커스텀 코드
   * @return HTTP 상태 코드
   */
  private static int extractHttpStatusCode(String customCode) {
    if (customCode.startsWith("COMMON") || customCode.contains("200")) {
      return HttpStatus.OK.value();
    } else if (customCode.contains("400")
        || customCode.contains("4001")
        || customCode.contains("4002")
        || customCode.contains("4004")
        || customCode.contains("4005")
        || customCode.contains("4006")) {
      return HttpStatus.BAD_REQUEST.value();
    } else if (customCode.contains("401")) {
      return HttpStatus.UNAUTHORIZED.value();
    } else if (customCode.contains("403") || customCode.contains("4003")) {
      return HttpStatus.FORBIDDEN.value();
    } else if (customCode.contains("404")) {
      return HttpStatus.NOT_FOUND.value();
    } else if (customCode.contains("500")) {
      return HttpStatus.INTERNAL_SERVER_ERROR.value();
    } else {
      return HttpStatus.OK.value();
    }
  }
}
