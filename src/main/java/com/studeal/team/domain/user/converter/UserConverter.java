package com.studeal.team.domain.user.converter;

import com.studeal.team.domain.user.domain.entity.Student;
import com.studeal.team.domain.user.domain.entity.Teacher;
import com.studeal.team.domain.user.domain.entity.User;
import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import com.studeal.team.domain.user.dto.UserRequestDTO;
import com.studeal.team.domain.user.dto.UserResponseDTO;

/**
 * User 엔티티와 DTO 간의 변환을 담당하는 유틸리티 클래스입니다. 이 클래스는 인스턴스화할 수 없습니다.
 */
public final class UserConverter {

  /**
   * 유틸리티 클래스의 인스턴스화를 방지하기 위한 private 생성자입니다.
   */
  private UserConverter() {
    throw new AssertionError("유틸리티 클래스는 인스턴스화할 수 없습니다.");
  }

  /**
   * 회원가입 요청 DTO를 Student 엔티티로 변환합니다.
   *
   * @param request 회원가입 요청 DTO
   * @return 생성된 Student 엔티티
   */
  public static Student toStudentEntity(UserRequestDTO.SignupRequest request) {
    return Student.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(request.getPassword())
        .bio(request.getBio())
        .role(UserRole.STUDENT)
        .build();
  }

  /**
   * 회원가입 요청 DTO를 Teacher 엔티티로 변환합니다.
   *
   * @param request 회원가입 요청 DTO
   * @return 생성된 Teacher 엔티티
   */
  public static Teacher toTeacherEntity(UserRequestDTO.SignupRequest request) {
    return Teacher.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(request.getPassword())
        .bio(request.getBio())
        .major(request.getMajor())
        .role(UserRole.TEACHER)
        .rating(0.0f)
        .build();
  }

  /**
   * User 엔티티를 응답 DTO로 변환합니다.
   *
   * @param user 변환할 User 엔티티
   * @return 생성된 응답 DTO
   */
  public static UserResponseDTO toResponseDTO(User user) {
    return UserResponseDTO.builder()
        .userId(user.getUserId())
        .name(user.getName())
        .email(user.getEmail())
        .role(user.getRole())
        .bio(user.getBio())
        .major(user instanceof Teacher ? ((Teacher) user).getMajor() : null)
        .build();
  }
}
