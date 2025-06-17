package com.studeal.team.global.error.code.status;

import com.studeal.team.global.error.code.BaseErrorCode;
import com.studeal.team.global.error.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500_01", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400_01", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_401_01", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403_01", "금지된 요청입니다."),


    // USER(사용자) 관련 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_400_01", "사용자가 없습니다."),
    USER_DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "USER_400_02", "이미 등록된 이메일입니다."),
    USER_INVALID_ROLE(HttpStatus.FORBIDDEN, "USER_403_01", "유효하지 않은 사용자 역할입니다."),
    USER_INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "USER_400_03", "유효하지 않은 비밀번호 형식입니다."),
    USER_NAME_EMPTY(HttpStatus.BAD_REQUEST, "USER_400_04", "이름은 필수입니다."),
    USER_EMAIL_INVALID(HttpStatus.BAD_REQUEST, "USER_400_05", "유효한 이메일 형식이 아닙니다."),
    USER_NOT_STUDENT(HttpStatus.FORBIDDEN, "USER_403_02", "해당 사용자는 학생이 아닙니다."),
    USER_NOT_TEACHER(HttpStatus.FORBIDDEN, "USER_403_03", "해당 사용자는 교사가 아닙니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "USER_400_06", "닉네임은 필수 입니다."),

    // BOARD(경매 게시판) 관련 에러
    BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST, "BOARD_400_01", "게시글을 찾을 수 없습니다."),
    BOARD_TITLE_EMPTY(HttpStatus.BAD_REQUEST, "BOARD_400_02", "게시글 제목은 필수입니다."),
    BOARD_UNAUTHORIZED(HttpStatus.FORBIDDEN, "BOARD_403_01", "게시글 권한이 없습니다."),
    BOARD_INVALID_TYPE(HttpStatus.BAD_REQUEST, "BOARD_400_03", "유효하지 않은 게시글 유형입니다."),
    BOARD_SUBJECT_EMPTY(HttpStatus.BAD_REQUEST, "BOARD_400_04", "수업 주제는 필수입니다."),

    // Lesson(수업) 관련 에러
    LESSON_NOT_FOUND(HttpStatus.BAD_REQUEST, "LESSON_400_01", "수업이 존재하지 않습니다."),
    LESSON_TITLE_EMPTY(HttpStatus.BAD_REQUEST, "LESSON_400_02", "수업 제목은 필수입니다."),
    LESSON_TEACHER_NOT_FOUND(HttpStatus.BAD_REQUEST, "LESSON_400_03", "강사 정보가 필요합니다."),
    LESSON_STUDENTS_EMPTY(HttpStatus.BAD_REQUEST, "LESSON_400_04", "최소 한 명 이상의 학생이 필요합니다."),
    LESSON_PRICE_INVALID(HttpStatus.BAD_REQUEST, "LESSON_400_05", "유효한 수업 가격이 필요합니다."),

    COURSE_NOT_FOUND(HttpStatus.BAD_REQUEST, "COURSE_400_01", "코스가 없습니다."),
    COURSE_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "COURSE_400_02", "코스가 부족합니다."),
    COURSE_ALREADY_ENROLLED(HttpStatus.BAD_REQUEST, "COURSE_400_03", "이미 수강중인 코스입니다."),
    COURSE_ALREADY_ENROLLED_BY_ANOTHER_USER(HttpStatus.BAD_REQUEST, "COURSE_400_04", "다른 사용자가 수강중인 코스입니다."),

    // Negotiation(협상) 관련 에러
    NEGOTIATION_NOT_FOUND(HttpStatus.NOT_FOUND, "NEGOTIATION_404_01", "존재하지 않는 협상입니다."),
    INVALID_NEGOTIATION_REQUEST(HttpStatus.BAD_REQUEST, "NEGOTIATION_400_01", "유효하지 않은 가격 제안 요청입니다."),

    // Enrollment(수강신청) 관련 에러
    ENROLLMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ENROLLMENT_404_01", "존재하지 않는 수강 신청입니다."),
    ENROLLMENT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "ENROLLMENT_400_01", "이미 수강신청 현황이 존재합니다."),
    ENROLLMENT_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "ENROLLMENT_400_02", "유효하지 않은 수강 신청 요청입니다."),
    ENROLLMENT_INVALID_NEGOTIATION_STATUS(HttpStatus.BAD_REQUEST, "ENROLLMENT_400_03", "협상이 성공 상태가 아닙니다.");

    // 이전 코드들은 기록용으로만 남겨두고 실제 사용하지 않습니다.
    // STUDENT_NOT_FOUND → USER_NOT_FOUND
    // TEACHER_NOT_FOUND → USER_NOT_FOUND

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
