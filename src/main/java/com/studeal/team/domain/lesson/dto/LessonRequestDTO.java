package com.studeal.team.domain.lesson.dto;

import com.studeal.team.global.validation.annotation.ExistStudents;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

public class LessonRequestDTO {

    @Getter
    public static class CreateRequest {
        @NotBlank
        @Size(max = 200)
        private String title;

        @NotNull
        private Long teacherId;

        @NotNull
        private Long negotiationId;

        @ExistStudents
        private List<Long> studentIds;

        @Size(max = 1000)
        private String description;

        @NotNull
        @Positive
        private Long price;
    }
}
