package com.studeal.team.domain.negotiation.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

public class NegotiationRequestDTO {

    @Getter
    @Setter
    public static class CreateRequest {
        @NotNull
        private Long studentId;

        @NotNull
        private Long teacherId;

        @NotNull
        @Positive
        private Long proposedPrice;
    }
}