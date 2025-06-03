package com.studeal.team.global.error.exception.handler;

import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.GeneralException;

public class EnrollmentHandler extends GeneralException {
    public EnrollmentHandler(ErrorStatus status) {
        super(status);
    }

    public EnrollmentHandler(ErrorStatus status, String message) {
        super(status);
        // 부모 클래스의 생성자를 호출하면서 상세 메시지 추가
        if (message != null && !message.isEmpty()) {
            RuntimeException runtimeException = new RuntimeException(message);
            this.initCause(runtimeException);
        }
    }
}
