package com.studeal.team.domain.user.api;

import com.studeal.team.domain.user.application.UserQueryService;
import com.studeal.team.global.common.util.SecurityUtils;
import com.studeal.team.global.error.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 정보를 관리하는 컨트롤러입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "사용자 정보", description = "사용자 정보 조회 및 관리 API")
@Slf4j
public class UserController {

  /**
   * 사용자 조회 서비스입니다.
   */
  private final UserQueryService userQueryService;

  /**
   * 로그인한 사용자의 마이페이지 정보를 조회합니다.
   *
   * @return 사용자 역할에 따른 마이페이지 정보를 담은 응답
   */
  @Operation(summary = "마이페이지 조회 API", description = "로그인한 사용자의 마이페이지 정보를 조회합니다.")
  @ApiResponses({
      @io.swagger.v3.oas.annotations.responses.ApiResponse(
          responseCode = "COMMON_200",
          description = "OK, 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(
          responseCode = "USER_400_01",
          description = "사용자가 없습니다.",
          content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(
          responseCode = "USER_403_01",
          description = "유효하지 않은 사용자 역할입니다.",
          content = @Content(schema = @Schema(implementation = ApiResponse.class)))
  })
  @GetMapping("/mypage")
  public ApiResponse<?> getMyPageInfo() {
    Long currentUserId = SecurityUtils.getCurrentUserId();
    log.info("마이페이지 조회 요청: 사용자 ID {}", currentUserId);

    return ApiResponse.onSuccess(userQueryService.getMyPageInfo(currentUserId));
  }
}
