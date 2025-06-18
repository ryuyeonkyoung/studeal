package com.studeal.team.global.error;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * API 오류 응답을 위한 공통 응답 객체입니다.
 */
@Getter
public class ErrorResponse {

  /**
   * 에러 코드 (예: "400", "NOT_FOUND")
   */
  private final String code;
  /**
   * 에러 메시지
   */
  private final String message;
  /**
   * HTTP 상태 코드 (예: 400, 404)
   */
  private final int status;
  /**
   * 에러 발생 시각
   */
  private final LocalDateTime timestamp;
  /**
   * 필드별 상세 에러 (입력값 검증용)
   */
  private final List<FieldError> errors;

  /**
   * ErrorResponse 생성자입니다.
   *
   * @param code    에러 코드
   * @param message 에러 메시지
   * @param status  HTTP 상태 코드
   * @param errors  필드 에러 목록
   */
  @Builder
  public ErrorResponse(String code, String message, int status, List<FieldError> errors) {
    this.code = code;
    this.message = message;
    this.status = status;
    this.timestamp = LocalDateTime.now();
    this.errors = errors != null ? errors : new ArrayList<>();
  }

  /**
   * 입력값 검증 실패용 ErrorResponse를 생성합니다.
   *
   * @param code    에러 코드
   * @param message 에러 메시지
   * @param status  HTTP 상태 코드
   * @param errors  필드별 상세 에러 목록
   * @return ErrorResponse 객체
   */
  public static ErrorResponse of(String code, String message, int status, List<FieldError> errors) {
    return ErrorResponse.builder()
        .code(code)
        .message(message)
        .status(status)
        .errors(errors)
        .build();
  }

  /**
   * 일반 예외용 ErrorResponse를 생성합니다.
   *
   * @param code    에러 코드
   * @param message 에러 메시지
   * @param status  HTTP 상태 코드
   * @return ErrorResponse 객체
   */
  public static ErrorResponse of(String code, String message, int status) {
    return ErrorResponse.builder()
        .code(code)
        .message(message)
        .status(status)
        .build();
  }

  /**
   * 필드별 에러 정보를 담는 내부 클래스입니다.
   */
  @Getter
  public static class FieldError {

    /**
     * 필드명
     */
    private final String field;
    /**
     * 잘못된 값
     */
    private final String value;
    /**
     * 오류 이유
     */
    private final String reason;

    /**
     * FieldError 생성자입니다.
     *
     * @param field  필드명
     * @param value  잘못된 값
     * @param reason 오류 이유
     */
    @Builder
    public FieldError(String field, String value, String reason) {
      this.field = field;
      this.value = value;
      this.reason = reason;
    }
  }
}
