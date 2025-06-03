package com.studeal.team.domain.enrollment.dto;

import com.studeal.team.domain.enrollment.domain.enums.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class EnrollmentRequestDTO {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        @NotNull(message = "Negotiation ID는 필수입니다.")
        private Long negotiationId;

        @NotNull(message = "학생 ID는 필수입니다.")
        private Long studentId;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatusUpdateRequest {
        @NotNull(message = "변경할 상태는 필수입니다.")
        private EnrollmentStatus status;
    }
}
