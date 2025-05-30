package com.studeal.team.domain.negotiation.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import com.studeal.team.domain.user.validation.ValidStudentId;
import com.studeal.team.domain.user.validation.ValidTeacherId;

public class NegotiationRequestDTO {

    @Getter
    @Setter
    public static class CreateRequest {
        @NotNull
        @ValidStudentId
        private Long studentId;

        @NotNull
        @ValidTeacherId
        private Long teacherId;

        @NotNull
        @Positive
        private Long proposedPrice;
    }
}

