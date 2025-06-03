package com.studeal.team.domain.lesson.converter;

import com.studeal.team.domain.lesson.domain.Lesson;
import com.studeal.team.domain.lesson.dto.LessonRequestDTO;
import com.studeal.team.domain.lesson.dto.LessonResponseDTO;

import java.util.HashSet;

/**
 * Lesson 엔티티와 DTO 간의 변환을 담당하는 유틸리티 클래스
 * 이 클래스는 인스턴스화할 수 없습니다.
 */
public final class LessonConverter {

    /**
     * 유틸리티 클래스의 인스턴스화를 방지하기 위한 private 생성자
     */
    private LessonConverter() {
        throw new AssertionError("유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

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
