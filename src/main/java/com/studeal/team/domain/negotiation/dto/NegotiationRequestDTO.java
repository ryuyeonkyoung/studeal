package com.studeal.team.domain.negotiation.dto;

import com.studeal.team.domain.negotiation.domain.enums.NegotiationStatus;
import com.studeal.team.domain.user.domain.validation.ValidStudentId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "협상 요청 DTO")
public class NegotiationRequestDTO {

    @Getter
    @Setter
    @Schema(description = "협상 생성 요청")
    public static class CreateRequest {
        @NotNull
        @ValidStudentId
        @Schema(description = "학생 ID", example = "41", required = true)
        private Long studentId;

        @NotNull
        @Positive
        @Schema(description = "제안 가격", example = "50000", required = true)
        private Long proposedPrice;

        @NotNull
        @Schema(description = "경매 게시판 ID", example = "41", required = true)
        private Long boardId;
    }

    @Getter
    @Setter
    @Schema(description = "협상 상태 변경 요청")
    public static class UpdateStatusRequest {
        @NotNull
        @Schema(description = "변경할 협상 상태", example = "ACCEPTED", required = true)
        private NegotiationStatus status;
    }
}
