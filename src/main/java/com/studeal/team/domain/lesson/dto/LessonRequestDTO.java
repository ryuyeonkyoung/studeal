package com.studeal.team.domain.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Schema(description = "수업 요청 DTO")
public class LessonRequestDTO {

    @Getter
    @Schema(description = "수업 생성 요청")
    public static class CreateRequest {
        @NotBlank
        @Size(max = 200)
        @Schema(description = "수업 제목", example = "초등학생을 위한 수학 특강", required = true)
        private String title;

        @NotNull
        @Schema(description = "협상 ID", example = "1", required = true)
        private Long negotiationId;

        @NotNull
        @Schema(description = "학생 ID", example = "1", required = true)
        private Long studentId; // 데이터 검증을 위해 필요

        @NotNull
        @Schema(description = "선생님 ID", example = "2", required = true)
        private Long teacherId; // 데이터 검증을 위해 필요

        @Size(max = 1000)
        @Schema(description = "수업 설명", example = "초등학생을 대상으로 한 수학 기초 개념 학습 및 문제 풀이 특강입니다.")
        private String description;

        @NotNull
        @Positive
        @Schema(description = "수업 가격", example = "50000", required = true)
        private Long price;
    }
}
