package com.studeal.team.global.error.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import com.studeal.team.global.error.code.BaseErrorCode;
import com.studeal.team.global.error.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    // 도메인 관련 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "사용자가 없습니다."),
    TEACHER_NOT_FOUND(HttpStatus.BAD_REQUEST, "TEACHER4001", "강사가 없습니다."),
    STUDENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "STUDENT4001", "학생이 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),

    COURSE_NOT_FOUND(HttpStatus.BAD_REQUEST, "COURSE4001", "코스가 없습니다."),
    COURSE_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "COURSE4002", "코스가 부족합니다."),
    COURSE_ALREADY_ENROLLED(HttpStatus.BAD_REQUEST, "COURSE4003", "이미 수강중인 코스입니다."),
    COURSE_ALREADY_ENROLLED_BY_ANOTHER_USER(HttpStatus.BAD_REQUEST, "COURSE4004", "다른 사용자가 수강중인 코스입니다."),

    NEGOTIATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "NEGOTIATION4001", "협상 정보가 없습니다."),
    NEGOTIATION_ALREADY_ENROLLED(HttpStatus.BAD_REQUEST, "NEGOTIATION4002", "이미 수강중인 협상입니다."),
    NEGOTIATION_ALREADY_ENROLLED_BY_ANOTHER_USER(HttpStatus.BAD_REQUEST, "NEGOTIATION4003", "다른 사용자가 수강중인 협상입니다."),
    // 예시,,,
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),
    // For test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
