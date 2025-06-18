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
     * 게시글 목록 항목 DTO
     * 게시글 목록 조회 시 사용되는 개별 게시글 정보
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BoardListItemResponse", description = "게시글 목록 항목 응답")
    public static class BoardListItem {
        @Schema(description = "게시글 ID", example = "1")
        private Long id;

        @Schema(description = "과목 분류", example = "수학")
        private String major;

        @Schema(description = "구체적인 과외 주제", example = "고등 수학 - 미적분")
        private String specMajor;

        @Schema(description = "게시글 제목", example = "제목ㅋㅋㅋㅋㅋ")
        private String title;

        @Schema(description = "선생님 이름", example = "김선생")
        private String teacher;

        @Schema(description = "가격 정보", example = "200,000~")
        private String price;
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

    /**
     * 선생님 상세 조회 응답 DTO
     * 게시글 상세 조회 시 선생님 정보 포함하여 조회
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "DetailTeacherResponse", description = "선생님 상세 조회 응답")
    public static class DetailTeacherResponse {
        @Schema(description = "협상 ID", example = "1")
        private Long negotiationId;

        @Schema(description = "게시글 제목", example = "고등 수학 과외 모집합니다")
        private String title;

        @Schema(description = "과목", example = "MATH")
        private MajorSubject major;

        @Schema(description = "구체적인 과외 주제", example = "고등 수학 - 미적분")
        private String specMajor;

        @Schema(description = "상세 설명", example = "수학 전공 대학생이 고등 수학 과외를 모집합니다.")
        private String description;

        @Schema(description = "가격 범위", example = "50000-70000")
        private String priceRange;

        @Schema(description = "입찰 목록", implementation = BidResponse.class)
        private List<BidResponse> bids;

        @Schema(description = "상태", example = "ACTIVE")
        private String status;

        @Schema(description = "현재 로그인한 사용자가 게시글 작성자인지 여부", example = "true")
        private Boolean isAuthor;
    }


    /**
     * 학생 상세 조회 응답 DTO
     * 게시글 상세 조회 시 학생에게 필요한 정보 포함
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "DetailStudentResponse", description = "학생 상세 조회 응답")
    public static class DetailStudentResponse {
        @Schema(description = "게시글 제목", example = "고등 수학 과외 모집합니다")
        private String title;

        @Schema(description = "과목", example = "MATH")
        private MajorSubject major;

        @Schema(description = "구체적인 과외 주제", example = "고등 수학 - 미적분")
        private String specMajor;

        @Schema(description = "상세 설명", example = "수학 전공 대학생이 고등 수학 과외를 모집합니다.")
        private String description;

        @Schema(description = "가격 범위", example = "50000-70000")
        private String priceRange;

        @Schema(description = "선생님 이름", example = "김선생")
        private String teacherName;

        @Schema(description = "선생님 이메일", example = "teacher@studeal.com")
        private String teacherEmail;

        @Schema(description = "상태", example = "OPEN")
        private String status;

        @Schema(description = "입찰 목록", implementation = BidResponse.class)
        private List<BidResponse> bids;
    }

    /**
     * 입찰 응답 DTO
     * 선생님 상세 조회 시 입찰 정보 포함
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BidResponse", description = "입찰 응답")
    public static class BidResponse {
        @Schema(description = "순위", example = "1")
        private int rank;

        @Schema(description = "가격", example = "60000")
        private Long price;

        @Schema(description = "입찰자", example = "김입찰자")
        private String bidder;
    }

    /**
     * 커서 기반 페이징용 게시글 목록 항목 DTO
     * 커서 기반 게시글 목록 조회 시 사용되는 개별 게시글 정보
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BoardCursorItemResponse", description = "커서 페이징 게시글 목록 항목")
    public static class CursorBoardItem {
        @Schema(description = "게시글 ID", example = "1")
        private Long id;

        @Schema(description = "과목 분류", example = "수학")
        private MajorSubject major;

        @Schema(description = "구체적인 과외 주제", example = "고등 수학 - 미적분")
        private String specMajor;

        @Schema(description = "게시글 제목", example = "제목ㅋㅋㅋㅋㅋ")
        private String title;

        @Schema(description = "선생님 이름", example = "김선생")
        private String teacher;

        @Schema(description = "가격 정보", example = "200,000~")
        private String price;
    }

    /**
     * 오프셋 기반 페이징용 게시글 목록 항목 DTO
     * 오프셋 기반 게시글 목록 조회 시 사용
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BoardOffsetItemResponse", description = "오프셋 페이징 게시글 목록 항목")
    public static class OffsetBoardItem {
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
     * 오프셋 기반 페이지 응답 DTO
     * 오프셋 페이지네이션 정보를 포함한 게시글 목록 조회 시 사용
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BoardOffsetPageResponse", description = "오프셋 페이징 게시글 목록 응답")
    public static class OffsetPageResponse {
        @ArraySchema(schema = @Schema(implementation = OffsetBoardItem.class))
        private List<OffsetBoardItem> content;

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

    /**
     * 커서 기반 페이징 응답 DTO
     * 커서 기반 페이징을 위한 응답
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BoardCursorPageResponse", description = "커서 페이징 게시글 목록 응답")
    public static class CursorPageResponse {
        @ArraySchema(schema = @Schema(implementation = CursorBoardItem.class))
        private List<CursorBoardItem> boards;

        @Schema(description = "다음 페이지 커서 값", example = "1654321")
        private Long nextCursor;

        @Schema(description = "다음 페이지 존재 여부", example = "true")
        private boolean hasNext;

        @Schema(description = "조회된 게시글 수", example = "10")
        private int count;
    }

    /**
     * 게시글 검색 결과 응답 DTO
     * 검색 결과는 페이징 처리되어 반환됩니다.
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BoardSearchPageResponse", description = "게시글 검색 결과 페이징 응답")
    public static class SearchPageResponse {
        @Schema(description = "검색 결과 게시글 목록")
        private List<SearchItemResponse> content;

        @Schema(description = "현재 페이지 번호", example = "0")
        private int pageNumber;

        @Schema(description = "페이지 크기", example = "10")
        private int pageSize;

        @Schema(description = "총 요소 수", example = "42")
        private long totalElements;

        @Schema(description = "총 페이지 수", example = "5")
        private int totalPages;

        @Schema(description = "첫 페이지 여부", example = "true")
        private boolean first;

        @Schema(description = "마지막 페이지 여부", example = "false")
        private boolean last;
    }

    /**
     * 게시글 검색 결과 아이템 DTO
     * 검색 결과의 각 게시글 항목을 나타냅니다.
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "BoardSearchItemResponse", description = "게시글 검색 결과 항목")
    public static class SearchItemResponse {
        @Schema(description = "게시글 ID", example = "1")
        private Long boardId;

        @Schema(description = "게시글 제목", example = "고등 수학 과외 모집합니다")
        private String title;

        @Schema(description = "전공 과목", example = "MATH")
        private MajorSubject major;

        @Schema(description = "세부 전공", example = "미적분학")
        private String specMajor;

        @Schema(description = "선생님 이름", example = "김선생")
        private String teacherName;

        @Schema(description = "현재 최고 입찰가", example = "60000")
        private Long highestBid;
    }
}
