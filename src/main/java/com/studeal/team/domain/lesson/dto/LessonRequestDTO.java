package com.studeal.team.domain.lesson.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class LessonRequestDTO {

    @Getter
    public static class CreateRequest {
        @NotBlank
        @Size(max = 200)
        private String title;

        @NotNull
        private Long negotiationId;

        @NotNull
        private Long studentId; // 데이터 검증을 위해 필요

        @NotNull
        private Long teacherId; // 데이터 검증을 위해 필요

        @Size(max = 1000)
        private String description;

        @NotNull
        @Positive
        private Long price;
    }
}
