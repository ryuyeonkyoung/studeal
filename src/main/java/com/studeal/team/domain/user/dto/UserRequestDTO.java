package com.studeal.team.domain.user.dto;

import com.studeal.team.domain.user.domain.entity.enums.MajorSubject;
import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import com.studeal.team.domain.user.domain.validation.ValidMajorSubject;
import com.studeal.team.global.validation.annotation.StrictEmail;
import com.studeal.team.global.validation.annotation.StrongPassword;
import com.studeal.team.global.validation.annotation.UniqueEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 요청과 관련된 DTO들을 포함하는 클래스입니다. 모든 사용자 관련 요청 DTO들의 컨테이너 클래스입니다.
 */
@Schema(description = "사용자 요청 DTO")
public class UserRequestDTO {

  /**
   * 회원가입 요청을 위한 DTO 클래스입니다.
   */
  @Getter
  @Setter
  @ValidMajorSubject
  @Schema(description = "회원가입 요청")
  public static class SignupRequest {

    /**
     * 사용자의 이름입니다.
     */
    @NotBlank
    @Size(max = 50)
    @Schema(description = "사용자 이름", example = "홍길동", required = true)
    private String name;

    /**
     * 사용자의 이메일 주소입니다.
     */
    @StrictEmail
    @NotBlank
    @UniqueEmail
    @Size(max = 100)
    @Schema(description = "이메일", example = "user@example.com", required = true)
    private String email;

    /**
     * 사용자의 비밀번호입니다.
     */
    @NotBlank
    @StrongPassword
    @Size(max = 255)
    @Schema(description = "비밀번호", example = "StrongPassword1!", required = true)
    private String password;

    /**
     * 사용자의 역할입니다.
     */
    @NotNull
    @Schema(description = "사용자 역할", example = "학생",
        allowableValues = {"학생", "선생님"}, required = true)
    private UserRole role;

    /**
     * 사용자의 자기소개입니다.
     */
    @Size(max = 1000)
    @Schema(description = "자기소개", example = "안녕하세요, 대학생 홍길동입니다.")
    private String bio;

    /**
     * 선생님의 전공 과목입니다.
     */
    @Schema(description = "전공 과목", example = "수학",
        allowableValues = {"수학", "과학", "영어", "국어", "역사", "코딩"})
    private MajorSubject major;
  }

  /**
   * 로그인 요청을 위한 DTO 클래스입니다.
   */
  @Getter
  @Setter
  @Schema(name = "LoginRequest", description = "로그인 요청")
  public static class LoginRequest {

    /**
     * 로그인할 사용자의 이메일입니다.
     */
    @NotBlank(message = "이메일은 필수입니다")
    @Schema(description = "이메일", example = "teacher@example.com", required = true)
    private String email;

    /**
     * 로그인할 사용자의 비밀번호입니다.
     */
    @NotBlank(message = "비밀번호는 필수입니다")
    @Schema(description = "비밀번호", example = "StrongPassword1!", required = true)
    private String password;
  }
}
