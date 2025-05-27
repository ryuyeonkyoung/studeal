package com.studeal.team.global.error.exception.handler;

import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.GeneralException;

public class EnrollmentHandler extends GeneralException {
    public EnrollmentHandler(ErrorStatus status) {
        super( status);
    }
}
