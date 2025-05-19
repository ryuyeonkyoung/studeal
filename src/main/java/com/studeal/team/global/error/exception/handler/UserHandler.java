package com.studeal.team.global.error.exception.handler;

import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.GeneralException;

public class UserHandler extends GeneralException {
    public UserHandler(ErrorStatus status) {
        super(status);
    }
}
