package com.studeal.team.global.error.exception.handler;

import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.GeneralException;

public class BoardHandler extends GeneralException {

  public BoardHandler(ErrorStatus status) {
    super(status);
  }
}
