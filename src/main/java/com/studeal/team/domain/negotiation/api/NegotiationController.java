package com.studeal.team.domain.negotiation.api;

import com.studeal.team.domain.negotiation.dto.NegotiationRequestDTO;
import com.studeal.team.domain.negotiation.dto.NegotiationResponseDTO;
import com.studeal.team.domain.negotiation.application.NegotiationService;
import com.studeal.team.global.error.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/negotiations")
@Tag(name = "가격 제안(협상)", description = "학생의 가격 제안(협상) 관련 API")
public class NegotiationController {

    private final NegotiationService negotiationService;

    @Operation(summary = "학생 가격 제안(협상) 생성 API", description = "학생이 강사에게 가격 제안을 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공", content = @Content(schema = @Schema(implementation = NegotiationResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "존재하지 않는 학생 또는 강사입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NEGOTIATION4001", description = "유효하지 않은 가격 제안 요청입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping
    public ApiResponse<NegotiationResponseDTO> createNegotiation(
            @Valid @RequestBody NegotiationRequestDTO.CreateRequest request) {
        return ApiResponse.onSuccess(negotiationService.initiateNegotiation(request));
    }

    @Operation(summary = "학생 가격 제안(협상) 삭제 API", description = "협상 ID로 학생의 가격 제안(협상)을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NEGOTIATION404", description = "존재하지 않는 협상입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @DeleteMapping("/{negotiationId}")
    public ApiResponse<Void> deleteNegotiation(@PathVariable Long negotiationId) {
        negotiationService.deleteNegotiation(negotiationId);
        return ApiResponse.onSuccess(null);
    }
}

