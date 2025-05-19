package com.studeal.team.apiPayload.exception;

import com.studeal.team.apiPayload.code.BaseErrorCode;
import com.studeal.team.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.studeal.team.apiPayload.code.BaseErrorCode;
import com.studeal.team.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
