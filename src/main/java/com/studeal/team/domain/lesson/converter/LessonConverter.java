package com.studeal.team.domain.lesson.converter;

import com.studeal.team.domain.lesson.domain.Lesson;
import com.studeal.team.domain.lesson.dto.LessonRequestDTO;
import com.studeal.team.domain.lesson.dto.LessonResponseDTO;

import java.util.HashSet;

public class LessonConverter {

    public static LessonResponseDTO.CreateResponse toCreateResponseDTO(Lesson lesson) {
        return LessonResponseDTO.CreateResponse.builder()
                .lessonId(lesson.getLessonId())
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .price(lesson.getPrice())
                .build();
    }

    public static Lesson toEntity(LessonRequestDTO.CreateRequest request) {
        return Lesson.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .lessonPresences(new HashSet<>())
                .grades(new HashSet<>())
                .lessonImages(new HashSet<>())
                .build();
    }
}
