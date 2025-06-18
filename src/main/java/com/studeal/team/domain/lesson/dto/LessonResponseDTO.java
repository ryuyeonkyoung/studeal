package com.studeal.team.domain.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "수업 응답 DTO")
public class LessonResponseDTO {

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(description = "수업 생성 응답")
  public static class CreateResponse {

    @Schema(description = "수업 ID", example = "1")
    private Long lessonId;

    @Schema(description = "수업 제목", example = "초등학생을 위한 수학 특강")
    private String title;

    @Schema(description = "수업 설명", example = "초등학생을 대상으로 한 수학 기초 개념 학습 및 문제 풀이 특강입니다.")
    private String description;

    @Schema(description = "수업 가격", example = "50000")
    private Long price;
  }
}
