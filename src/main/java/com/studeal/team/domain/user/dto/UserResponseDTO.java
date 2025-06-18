package com.studeal.team.domain.user.dto;

import com.studeal.team.domain.user.domain.entity.enums.MajorSubject;
import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "사용자 응답 DTO")
public class UserResponseDTO {

  @Schema(description = "사용자 ID", example = "1")
  private Long userId;

  @Schema(description = "사용자 이름", example = "홍길동")
  private String name;

  @Schema(description = "이메일", example = "user@example.com")
  private String email;

  @Schema(description = "사용자 역할", example = "학생",
      allowableValues = {"학생", "선생님"})
  private UserRole role;

  @Schema(description = "자기소개", example = "안녕하세요, 대학생 홍길동입니다.")
  private String bio;

  @Schema(description = "전공 과목", example = "수학",
      allowableValues = {"수학", "과학", "영어", "국어", "역사", "코딩"})
  private MajorSubject major;
}
