package com.studeal.team.global.error.code;

/**
 * 에러 코드 정의를 위한 기본 인터페이스입니다. 모든 에러 코드는 이 인터페이스를 구현해야 합니다.
 */
public interface BaseErrorCode {

  /**
   * 에러의 이유를 반환합니다.
   *
   * @return ErrorReasonDTO 객체
   */
  ErrorReasonDTO getReason();

  /**
   * HTTP 상태 코드가 포함된 에러 이유를 반환합니다.
   *
   * @return ErrorReasonDTO 객체
   */
  ErrorReasonDTO getReasonHttpStatus();
}
