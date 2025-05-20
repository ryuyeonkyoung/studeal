package com.studeal.team.domain.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LessonResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResponse {
        private Long lessonId;
        private String title;
        private String description;
        private Long price;
    }
}
