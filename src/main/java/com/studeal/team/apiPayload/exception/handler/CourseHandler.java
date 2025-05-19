package com.studeal.team.apiPayload.exception.handler;

import com.studeal.team.apiPayload.code.status.ErrorStatus;
import com.studeal.team.apiPayload.exception.GeneralException;

public class CourseHandler extends GeneralException {
    public CourseHandler(ErrorStatus status) {
        super( status);
    }
}
