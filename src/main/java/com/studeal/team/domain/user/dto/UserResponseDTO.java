package com.studeal.team.domain.user.dto;

import com.studeal.team.domain.user.domain.entity.enums.MajorSubject;
import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 사용자 정보에 대한 응답 DTO 클래스입니다.
 */
@Getter
@Builder
@Schema(description = "사용자 응답 DTO")
public class UserResponseDTO {

  /**
   * 사용자의 고유 식별자입니다.
   */
  @Schema(description = "사용자 ID", example = "1")
  private Long userId;

  /**
   * 사용자의 이름입니다.
   */
  @Schema(description = "사용자 이름", example = "홍길동")
  private String name;

  /**
   * 사용자의 이메일 주소입니다.
   */
  @Schema(description = "이메일", example = "user@example.com")
  private String email;

  /**
   * 사용자의 역할 정보입니다.
   */
  @Schema(description = "사용자 역할", example = "학생",
      allowableValues = {"학생", "선생님"})
  private UserRole role;

  /**
   * 사용자의 자기소개입니다.
   */
  @Schema(description = "자기소개", example = "안녕하세요, 대학생 홍길동입니다.")
  private String bio;

  /**
   * 선생님의 전공 과목입니다.
   */
  @Schema(description = "전공 과목", example = "수학",
      allowableValues = {"수학", "과학", "영어", "국어", "역사", "코딩"})
  private MajorSubject major;
}
