package com.studeal.team.domain.negotiation.api;

import com.studeal.team.domain.negotiation.application.NegotiationCommandService;
import com.studeal.team.domain.negotiation.dto.NegotiationRequestDTO;
import com.studeal.team.domain.negotiation.dto.NegotiationResponseDTO;
import com.studeal.team.global.common.util.SecurityUtils;
import com.studeal.team.global.error.ApiResponse;
import com.studeal.team.global.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/negotiations")
@Tag(name = "가격 제안(협상)", description = "학생의 가격 제안(협상) 관련 API")
public class NegotiationController {

  private final NegotiationCommandService negotiationCommandService;
  private final JwtTokenProvider jwtTokenProvider;

  @Operation(summary = "학생 가격 제안(협상) 생성 API",
      description = "학생이 강사에게 가격 제안을 생성하는 API입니다.\n\n SecurityContext에서 현재 인증된 학생 ID를 자동으로 사용합니다.")
  @ApiResponses({
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공", content = @Content(schema = @Schema(implementation = NegotiationResponseDTO.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_400_01", description = "사용자가 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NEGOTIATION_400_01", description = "유효하지 않은 가격 제안 요청입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_403_02", description = "해당 사용자는 학생이 아닙니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
  })
  @PostMapping
  public ApiResponse<NegotiationResponseDTO> createNegotiation(
      @Valid @RequestBody NegotiationRequestDTO.CreateRequest request) {

    // SecurityContextHolder에서 현재 사용자 ID 조회
    Long currentUserId = SecurityUtils.getCurrentUserId();

    return ApiResponse.onSuccess(
        negotiationCommandService.initiateNegotiation(request, currentUserId));
  }

  @Operation(summary = "학생 가격 제안(협상) 상태 변경 API", description = "협상 ID로 학생의 가격 제안(협상) 상태를 변경하는 API입니다. 상태가 ACCEPTED로 변경될 경우 자동으로 Enrollment가 생성됩니다.")
  @ApiResponses({
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공", content = @Content(schema = @Schema(implementation = NegotiationResponseDTO.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NEGOTIATION_404_01", description = "존재하지 않는 협상입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
  })
  @PatchMapping("/{negotiationId}/status")
  public ApiResponse<NegotiationResponseDTO> updateNegotiationStatus(
      @PathVariable Long negotiationId,
      @Valid @RequestBody NegotiationRequestDTO.UpdateStatusRequest request) {
    return ApiResponse.onSuccess(
        negotiationCommandService.updateNegotiationStatus(negotiationId, request.getStatus()));
  }

  @Operation(summary = "학생 가격 제안(협상) 삭제 API", description = "협상 ID로 학생의 가격 제안(협상)을 삭제하는 API입니다.")
  @ApiResponses({
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NEGOTIATION_404_01", description = "존재하지 않는 협상입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
  })
  @DeleteMapping("/{negotiationId}")
  public ApiResponse<Void> deleteNegotiation(@PathVariable Long negotiationId) {
    negotiationCommandService.deleteNegotiation(negotiationId);
    return ApiResponse.onSuccess(null);
  }

  /**
   * HttpServletRequest에서 토큰을 추출합니다.
   */
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
