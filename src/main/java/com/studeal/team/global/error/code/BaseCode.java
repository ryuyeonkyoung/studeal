package com.studeal.team.global.error.code;

/**
 * API 응답을 위한 기본 코드 인터페이스입니다. 모든 응답 코드는 이 인터페이스를 구현해야 합니다.
 */
public interface BaseCode {

  /**
   * 응답 코드의 이유를 반환합니다.
   *
   * @return ReasonDTO 객체
   */
  ReasonDTO getReason();

  /**
   * HTTP 상태 코드가 포함된 이유를 반환합니다.
   *
   * @return ReasonDTO 객체
   */
  ReasonDTO getReasonHttpStatus();
}
