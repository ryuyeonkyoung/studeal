package com.studeal.team.global.error.exception.handler;

import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.GeneralException;

/**
 * 사용자 관련 예외를 처리하는 핸들러 클래스입니다.
 */
public class UserHandler extends GeneralException {

  /**
   * 사용자 관련 예외를 생성합니다.
   *
   * @param status 에러 상태 정보
   */
  public UserHandler(ErrorStatus status) {
    super(status);
  }
}
