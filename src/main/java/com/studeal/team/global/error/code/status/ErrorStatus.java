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
    USER_DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "USER4002", "이미 등록된 이메일입니다."),
    USER_INVALID_ROLE(HttpStatus.BAD_REQUEST, "USER4003", "유효하지 않은 사용자 역할입니다."),
    USER_INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "USER4004", "유효하지 않은 비밀번호 형식입니다."),
    USER_NAME_EMPTY(HttpStatus.BAD_REQUEST, "USER4005", "이름은 필수입니다."),
    USER_EMAIL_INVALID(HttpStatus.BAD_REQUEST, "USER4006", "유효한 이메일 형식이 아닙니다."),

    TEACHER_NOT_FOUND(HttpStatus.BAD_REQUEST, "TEACHER4001", "강사가 없습니다."),
    STUDENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "STUDENT4001", "학생이 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),

    // 게시판 관련 에러
    BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST, "BOARD4001", "게시글을 찾을 수 없습니다."),
    BOARD_TITLE_EMPTY(HttpStatus.BAD_REQUEST, "BOARD4002", "게시글 제목은 필수입니다."),
    BOARD_UNAUTHORIZED(HttpStatus.FORBIDDEN, "BOARD4003", "게시글 권한이 없습니다."),
    BOARD_INVALID_TYPE(HttpStatus.BAD_REQUEST, "BOARD4004", "유효하지 않은 게시글 유형입니다."),
    BOARD_SUBJECT_EMPTY(HttpStatus.BAD_REQUEST, "BOARD4005", "수업 주제는 필수입니다."),
    BOARD_STUDENT_FORBIDDEN(HttpStatus.FORBIDDEN, "BOARD4006", "학생은 게시글을 올릴 수 없습니다."),

    // Lesson 관련 에러
    LESSON_NOT_FOUND(HttpStatus.BAD_REQUEST, "LESSON4001", "수업이 존재하지 않습니다."),
    LESSON_TITLE_EMPTY(HttpStatus.BAD_REQUEST, "LESSON4002", "수업 제목은 필수입니다."),
    LESSON_TEACHER_NOT_FOUND(HttpStatus.BAD_REQUEST, "LESSON4003", "강사 정보가 필요합니다."),
    LESSON_STUDENTS_EMPTY(HttpStatus.BAD_REQUEST, "LESSON4004", "최소 한 명 이상의 학생이 필요합니다."),
    LESSON_PRICE_INVALID(HttpStatus.BAD_REQUEST, "LESSON4005", "유효한 수업 가격이 필요합니다."),

    COURSE_NOT_FOUND(HttpStatus.BAD_REQUEST, "COURSE4001", "코스가 없습니다."),
    COURSE_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "COURSE4002", "코스가 부족합니다."),
    COURSE_ALREADY_ENROLLED(HttpStatus.BAD_REQUEST, "COURSE4003", "이미 수강중인 코스입니다."),
    COURSE_ALREADY_ENROLLED_BY_ANOTHER_USER(HttpStatus.BAD_REQUEST, "COURSE4004", "다른 사용자가 수강중인 코스입니다."),

    // 협상 관련 에러
    NEGOTIATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "NEGOTIATION404", "존재하지 않는 협상입니다."),
    INVALID_NEGOTIATION_REQUEST(HttpStatus.BAD_REQUEST, "NEGOTIATION4001", "유효하지 않은 가격 제안 요청입니다."),

    // 수강 신청 관련 에러
    ENROLLMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "ENROLLMENT404", "존재하지 않는 수강 신청입니다."),
    ENROLLMENT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "ENROLLMENT4001", "이미 Enrollment(학생의 수업 참여 확정 현황)가 존재합니다."),
    ENROLLMENT_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "ENROLLMENT4002", "유효하지 않은 수강 신청 요청입니다."),
    ENROLLMENT_INVALID_NEGOTIATION_STATUS(HttpStatus.BAD_REQUEST, "ENROLLMENT4003", "협상이 성공 상태가 아닙니다.");

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

