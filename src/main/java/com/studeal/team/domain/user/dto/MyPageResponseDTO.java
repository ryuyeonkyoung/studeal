package com.studeal.team.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 마이페이지 응답 DTO 클래스
 * 사용자 역할에 따라 다른 응답 형식을 제공합니다.
 */
public class MyPageResponseDTO {

    /**
     * 기본 사용자 정보 DTO
     */
    @Builder
    @Getter
    public static class UserInfo {
        private String name;
        private String email;
        private String role;
    }

    /**
     * 선생님이 개설한 강의 정보 DTO
     */
    @Builder
    @Getter
    public static class OpenedLessonInfo {
        private Long enrollmentId;
        private String boardTitle;
        private String studentName;
        private String status;
    }

    /**
     * 학생이 수강 중인 강의 정보 DTO
     */
    @Builder
    @Getter
    public static class EnrolledLessonInfo {
        private Long enrollmentId;
        private String boardTitle;
        private String status;
    }

    /**
     * 선생님 관점에서 협상 중인 강의 정보 DTO
     */
    @Builder
    @Getter
    public static class TeacherNegotiatingLessonInfo {
        private Long boardId;
        private String title;
    }

    /**
     * 학생 관점에서 협상 중인 강의 정보 DTO
     */
    @Builder
    @Getter
    public static class StudentNegotiatingLessonInfo {
        private Long boardId;
        private String title;
        private Long highestPrice; // 학생이 제안한 최고 가격
    }

    /**
     * 선생님 마이페이지 응답 DTO
     */
    @Builder
    @Getter
    public static class TeacherResponse {
        private UserInfo user;
        private List<OpenedLessonInfo> openedLessons;
        private List<TeacherNegotiatingLessonInfo> negotiatingLessons;
    }

    /**
     * 학생 마이페이지 응답 DTO
     */
    @Builder
    @Getter
    public static class StudentResponse {
        private UserInfo user;
        private List<EnrolledLessonInfo> enrolledLessons;
        private List<StudentNegotiatingLessonInfo> negotiatingLessons;
    }
}
