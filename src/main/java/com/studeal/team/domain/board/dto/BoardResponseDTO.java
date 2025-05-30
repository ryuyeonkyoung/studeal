package com.studeal.team.domain.board.dto;

import com.studeal.team.domain.user.domain.entity.enums.MajorSubject;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시판 응답 DTO 클래스
 * 모든 게시판 관련 응답 DTO들의 컨테이너입니다.
 */
@Schema(description = "게시판 응답 DTO")
public class BoardResponseDTO {

    /**
     * 게시글 상세 응답 DTO
     * 게시글 조회 시 사용
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BoardDetailResponse", description = "게시글 상세 응답")
    public static class DetailResponse {
        @Schema(description = "게시글 ID", example = "1")
        private Long boardId;

        @Schema(description = "게시글 제목", example = "고등 수학 과외 모집합니다")
        private String title;

        @Schema(description = "게시글 내용", example = "수학 전공 대학생이 수학 과외를 모집합니다. 문제 풀이 및 개념 설명에 능숙합니다.")
        private String content;

        @Schema(description = "선생님 ID", example = "1")
        private Long teacherId;

        @Schema(description = "선생님 이름", example = "김선생")
        private String teacherName;

        @Schema(description = "게시글 유형", example = "MATH")
        private MajorSubject major;

        @Schema(description = "예상 수업 가격", example = "50000")
        private Long expectedPrice;

        @Schema(description = "구체적인 과외 주제", example = "고등 수학 - 미적분")
        private String specMajor;

        @Schema(description = "생성 일시", example = "2025-05-28T14:30:00")
        private LocalDateTime createdAt;

        @Schema(description = "수정 일시", example = "2025-05-28T15:45:00")
        private LocalDateTime updatedAt;

        @Schema(description = "썸네일 이미지 URL", example = "/images/thumbnails/math-123.jpg")
        private String thumbnailUrl;
    }

    /**
     * 게시글 목록 항목 응답 DTO
     * 게시글 목록 조회 시 사용
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BoardListItemResponse", description = "게시글 목록 항목 응답")
    public static class ListItemResponse {
        @Schema(description = "게시글 ID", example = "1")
        private Long boardId;

        @Schema(description = "게시글 제목", example = "고등 수학 과외 모집합니다")
        private String title;

        @Schema(description = "선생님 이름", example = "김선생")
        private String teacherName;

        @Schema(description = "게시글 유형", example = "MATH")
        private MajorSubject major;

        @Schema(description = "예상 수업 가격", example = "50000")
        private Long expectedPrice;

        @Schema(description = "구체적인 과외 주제", example = "고등 수학 - 미적분")
        private String specMajor;

        @Schema(description = "생성 일시", example = "2025-05-28T14:30:00")
        private LocalDateTime createdAt;

        @Schema(description = "썸네일 이미지 URL", example = "/images/thumbnails/math-123.jpg")
        private String thumbnailUrl;
    }

    /**
     * 게시글 페이지 응답 DTO
     * 페이지네이션 정보를 포함한 게시글 목록 조회 시 사용
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BoardPageResponse", description = "게시글 페이지 응답")
    public static class PageResponse {
        @ArraySchema(schema = @Schema(implementation = ListItemResponse.class))
        private List<ListItemResponse> content;

        @Schema(description = "현재 페이지 번호", example = "0")
        private int pageNumber;

        @Schema(description = "페이지 크기", example = "10")
        private int pageSize;

        @Schema(description = "전체 항목 수", example = "42")
        private long totalElements;

        @Schema(description = "전체 페이지 수", example = "5")
        private int totalPages;

        @Schema(description = "첫 페이지 여부", example = "false")
        private boolean first;

        @Schema(description = "마지막 페이지 여부", example = "false")
        private boolean last;
    }
}
