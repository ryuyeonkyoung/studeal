// NegotiationResponseDTO.java
package com.studeal.team.domain.negotiation.dto;

import com.studeal.team.domain.negotiation.domain.enums.NegotiationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "협상 응답 DTO")
public class NegotiationResponseDTO {
    @Schema(description = "협상 ID", example = "1")
    private Long negotiationId;

    @Schema(description = "학생 ID", example = "41")
    private Long studentId;

    @Schema(description = "경매 게시판 ID", example = "41")
    private Long boardId;

    @Schema(description = "제안 가격", example = "50000")
    private Long proposedPrice;

    @Schema(description = "협상 상태", example = "수락됨",
            allowableValues = {"대기", "수락됨", "거절됨"})
    private NegotiationStatus status;
}