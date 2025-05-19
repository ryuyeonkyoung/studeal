package com.studeal.team.apiPayload.exception.handler;

import com.studeal.team.apiPayload.code.status.ErrorStatus;
import com.studeal.team.apiPayload.exception.GeneralException;

public class UserHandler extends GeneralException {
    public UserHandler(ErrorStatus status) {
        super(status);
    }
}
