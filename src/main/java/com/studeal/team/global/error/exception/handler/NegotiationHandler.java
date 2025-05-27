package com.studeal.team.global.error.exception.handler;

import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.GeneralException;

public class NegotiationHandler extends GeneralException {
    public NegotiationHandler(ErrorStatus status) {
        super( status);
    }
}
