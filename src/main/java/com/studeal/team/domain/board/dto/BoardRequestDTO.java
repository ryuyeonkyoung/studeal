package com.studeal.team.domain.board.dto;

import com.studeal.team.domain.user.domain.enums.MajorSubject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시판 요청 DTO 클래스
 * 모든 게시판 관련 요청 DTO들의 컨테이너입니다.
 */
@Schema(description = "게시판 요청 DTO")
public class BoardRequestDTO {

    /**
     * 게시글 생성 요청 DTO
     * 선생님이 과외 모집 글을 올릴 때 사용
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "게시글 생성 요청")
    public static class CreateRequest {
        @NotBlank(message = "제목은 필수 입력값입니다")
        @Size(max = 200, message = "제목은 최대 200자까지 가능합니다")
        @Schema(description = "게시글 제목", example = "고등 수학 과외 모집합니다", required = true)
        private String title;

        @Size(max = 2000, message = "내용은 최대 2000자까지 가능합니다")
        @Schema(description = "게시글 내용", example = "수학 전공 대학생이 수학 과외를 모집합니다. 문제 풀이 및 개념 설명에 능숙합니다.")
        private String content;

        @NotNull(message = "과외 과목은 필수 입력값입니다.")
        @Schema(description = "과외 과목", example = "MATH", required = true)
        private MajorSubject major;

        @NotNull(message = "시작 수업 가격은 필수 입력값입니다")
        @Schema(description = "시작 수업 가격", example = "50000", required = true)
        private Long startPrice;

        @NotBlank(message = "수업 주제는 필수 입력값입니다")
        @Size(max = 100, message = "수업 주제는 최대 100자까지 가능합니다")
        @Schema(description = "수업 주제", example = "고등 수학 - 미적분", required = true)
        private String subject;
    }

    /**
     * 게시글 수정 요청 DTO
     * 게시글 정보를 수정할 때 사용
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BoardUpdateRequest", description = "게시글 수정 요청")
    public static class UpdateRequest {
        @NotBlank(message = "제목은 필수 입력값입니다")
        @Size(max = 200, message = "제목은 최대 200자까지 가능합니다")
        @Schema(description = "게시글 제목", example = "고등 수학 과외 모집합니다 (수정)", required = true)
        private String title;

        @Size(max = 2000, message = "내용은 최대 2000자까지 가능합니다")
        @Schema(description = "게시글 내용", example = "수학 전공 대학생이 수학 과외를 모집합니다. 문제 풀이 및 개념 설명에 능숙합니다. (내용 수정)")
        private String content;

        @NotNull(message = "게시글 유형은 필수 입력값입니다")
        @Schema(description = "게시글 유형", example = "MATH", required = true)
        private MajorSubject major;

        @NotNull(message = "예상 수업 가격은 필수 입력값입니다")
        @Schema(description = "예상 수업 가격", example = "55000", required = true)
        private Long expectedPrice;

        @NotBlank(message = "수업 주제는 필수 입력값입니다")
        @Size(max = 100, message = "수업 주제는 최대 100자까지 가능합니다")
        @Schema(description = "수업 주제", example = "고등 수학 - 미적분과 통계", required = true)
        private String subject;
    }

    /**
     * 게시글 검색 요청 DTO
     * 게시글을 검색할 때 사용
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BoardSearchRequest", description = "게시글 검색 요청")
    public static class SearchRequest {
        @Schema(description = "검색 키워드", example = "수학")
        private String keyword;

        @Schema(description = "게시글 유형", example = "MATH")
        private MajorSubject major;

        @Schema(description = "최소 가격", example = "30000")
        private Long minPrice;

        @Schema(description = "최대 가격", example = "100000")
        private Long maxPrice;

        @Schema(description = "수업 주제", example = "미적분")
        private String subject;

        @Schema(description = "선생님 ID", example = "1")
        private Long teacherId;
    }
}
