package com.studeal.team.domain.lesson.dto;

import com.studeal.team.global.validation.annotation.ExistStudents;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class LessonRequestDTO {

    @Getter
    public static class CreateRequest {
        @NotBlank
        @Size(max = 200)
        private String title;

        @NotBlank
        private Long teacherId;

        @ExistStudents
        private List<Long> studentIds;

        @NotBlank
        @Size(max = 1000)
        private String description;

        @NotBlank
        private LocalDate lessonDate;

        @NotNull
        @Positive
        private Long price;
    }
}
