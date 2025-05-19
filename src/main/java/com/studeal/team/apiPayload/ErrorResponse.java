package com.studeal.team.apiPayload;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {

    private String code;            // 에러 코드 (예: "400", "NOT_FOUND")
    private String message;         // 에러 메시지
    private int status;             // HTTP 상태 코드 (예: 400, 404)
    private LocalDateTime timestamp;// 에러 발생 시각
    private List<FieldError> errors;// 필드별 상세 에러 (입력값 검증용)

    @Builder
    public ErrorResponse(String code, String message, int status, List<FieldError> errors) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.errors = errors != null ? errors : new ArrayList<>();
    }

    // 입력값 검증 실패용 생성 메서드
    public static ErrorResponse of(String code, String message, int status, List<FieldError> errors) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .status(status)
                .errors(errors)
                .build();
    }

    // 일반 예외용 생성 메서드
    public static ErrorResponse of(String code, String message, int status) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .status(status)
                .build();
    }

    // 필드 에러 내부 클래스
    @Getter
    public static class FieldError {
        private final String field;
        private final String value;
        private final String reason;

        @Builder
        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}
