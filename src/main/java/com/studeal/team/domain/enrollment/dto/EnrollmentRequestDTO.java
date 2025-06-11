package com.studeal.team.domain.enrollment.dto;

import com.studeal.team.domain.enrollment.domain.enums.EnrollmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(description = "수업 참여 확정 요청 DTO")
public class EnrollmentRequestDTO {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "수업 참여 확정 생성 요청")
    public static class CreateRequest {
        @NotNull(message = "Negotiation ID는 필수입니다.")
        @Schema(description = "협상 ID", example = "1", required = true)
        private Long negotiationId;

        @NotNull(message = "학생 ID는 필수입니다.")
        @Schema(description = "학생 ID", example = "1", required = true)
        private Long studentId;

        @NotNull(message = "결제 금액은 필수입니다.")
        @Schema(description = "결제 금액", example = "10000", required = true)
        private Long paidAmount;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "수업 참여 확정 상태 변경 요청")
    public static class StatusUpdateRequest {
        @NotNull(message = "변경할 상태는 필수입니다.")
        @Schema(description = "변경할 상태", example = "확정",
                allowableValues = {"대기", "확정", "취소"}, required = true)
        private EnrollmentStatus status;
    }
}
