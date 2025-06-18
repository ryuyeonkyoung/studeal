package com.studeal.team.domain.user.api;

import com.studeal.team.domain.user.application.UserCommandService;
import com.studeal.team.domain.user.application.UserQueryService;
import com.studeal.team.domain.user.dto.TokenDTO;
import com.studeal.team.domain.user.dto.UserRequestDTO;
import com.studeal.team.domain.user.dto.UserResponseDTO;
import com.studeal.team.global.error.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "사용자 인증", description = "회원가입 및 로그인 관련 API")
@Slf4j
public class UserAuthController {

  private final UserCommandService userCommandService;
  private final UserQueryService userQueryService;

  @Operation(summary = "회원가입 API", description = "사용자 회원가입을 처리하는 API입니다. 학생/강사 역할에 따라 처리됩니다.\n\n" +
      "가능한 UserRole(사용자 역할) 값:\n" +
      "- 학생 (STUDENT) : 학생 사용자 역할\n" +
      "- 선생님 (TEACHER) : 선생님 사용자 역할\n\n" +
      "가능한 MajorSubject(전공 과목) 값:\n" +
      "- 수학 (MATH)\n" +
      "- 과학 (SCIENCE)\n" +
      "- 영어 (ENGLISH)\n" +
      "- 국어 (KOREAN)\n" +
      "- 역사 (HISTORY)\n" +
      "- 코딩 (CODING)")
  @ApiResponses({
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_400_02", description = "이미 등록된 이메일입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_403_01", description = "유효하지 않은 사용자 역할입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_400_03", description = "유효하지 않은 비밀번호 형식입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_400_04", description = "이름은 필수입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_400_05", description = "유효한 이메일 형식이 아닙니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
  })
  @PostMapping("/signup")
  public ApiResponse<UserResponseDTO> signupUser(
      @Valid @RequestBody UserRequestDTO.SignupRequest request) {
    return ApiResponse.onSuccess(userCommandService.registerUser(request));
  }

  @Operation(summary = "로그인 API", description = "이메일과 비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
  @ApiResponses({
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_400_01", description = "사용자가 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
  })
  @PostMapping("/login")
  public ApiResponse<TokenDTO> login(@Valid @RequestBody UserRequestDTO.LoginRequest request) {
    return ApiResponse.onSuccess(userCommandService.login(request));
  }

  @Operation(summary = "로그아웃 API", description = "현재 인증된 사용자를 로그아웃 처리합니다.")
  @ApiResponses({
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공")
  })
  @PostMapping("/logout")
  public ApiResponse<String> logout(HttpServletRequest request) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, null, auth);
    }
    return ApiResponse.onSuccess("로그아웃 처리되었습니다.");
  }
}

