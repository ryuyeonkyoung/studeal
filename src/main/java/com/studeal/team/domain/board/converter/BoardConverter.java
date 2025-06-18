package com.studeal.team.domain.board.converter;

import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.domain.AuctionBoardFile;
import com.studeal.team.domain.board.dto.BoardRequestDTO;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.user.domain.entity.Teacher;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시판 데이터 변환 유틸리티 클래스
 */
public class BoardConverter {

    /**
     * AuctionBoard 엔티티를 DetailResponse DTO로 변환
     */
    public static BoardResponseDTO.DetailResponse toDetailResponse(AuctionBoard auctionBoard) {
        BoardResponseDTO.DetailResponse.DetailResponseBuilder builder = BoardResponseDTO.DetailResponse.builder()
                .boardId(auctionBoard.getBoardId())
                .title(auctionBoard.getTitle())
                .content(auctionBoard.getContent())
                .major(auctionBoard.getMajor())
                .expectedPrice(auctionBoard.getExpectedPrice())
                .specMajor(auctionBoard.getSpecMajor())
                .createdAt(auctionBoard.getCreatedAt())
                .updatedAt(auctionBoard.getUpdatedAt());

        // 선생님 정보 세팅 - User 클래스에서 상속받은 필드를 사용
        if (auctionBoard.getTeacher() != null) {
            builder.teacherId(auctionBoard.getTeacher().getUserId())
                    .teacherName(auctionBoard.getTeacher().getName());
        }

        // 썸네일 이미지가 있는 경우 URL 세팅
        auctionBoard.getFiles().stream()
                .filter(AuctionBoardFile::getIsThumbnail)
                .findFirst()
                .ifPresent(file -> builder.thumbnailUrl(file.getFilePath()));

        return builder.build();
    }

    /**
     * AuctionBoard 엔티티를 ListItemResponse DTO로 변환
     */
    public static BoardResponseDTO.ListItemResponse toListItemResponse(AuctionBoard auctionBoard) {
        BoardResponseDTO.ListItemResponse.ListItemResponseBuilder builder = BoardResponseDTO.ListItemResponse.builder()
                .boardId(auctionBoard.getBoardId())
                .title(auctionBoard.getTitle())
                .major(auctionBoard.getMajor())
                .expectedPrice(auctionBoard.getExpectedPrice())
                .specMajor(auctionBoard.getSpecMajor())
                .createdAt(auctionBoard.getCreatedAt());

        // 선생님 정보 세팅 - User 클래스에서 상속받은 필드를 사용
        if (auctionBoard.getTeacher() != null) {
            builder.teacherName(auctionBoard.getTeacher().getName());
        }

        // 썸네일 이미지가 있는 경우 URL 세팅
        auctionBoard.getFiles().stream()
                .filter(AuctionBoardFile::getIsThumbnail)
                .findFirst()
                .ifPresent(file -> builder.thumbnailUrl(file.getFilePath()));

        return builder.build();
    }

    /**
     * AuctionBoard Page 객체를 PageResponse DTO로 변환
     */
    public static BoardResponseDTO.PageResponse toPageResponse(Page<AuctionBoard> boardPage) {
        List<BoardResponseDTO.ListItemResponse> boardDtos = boardPage.getContent().stream()
                .map(BoardConverter::toListItemResponse)
                .collect(Collectors.toList());

        return BoardResponseDTO.PageResponse.builder()
                .content(boardDtos)
                .pageNumber(boardPage.getNumber())
                .pageSize(boardPage.getSize())
                .totalElements(boardPage.getTotalElements())
                .totalPages(boardPage.getTotalPages())
                .first(boardPage.isFirst())
                .last(boardPage.isLast())
                .build();
    }

    /**
     * CreateRequest DTO와 Teacher 엔티티로 AuctionBoard 엔티티 생성
     */
    public static AuctionBoard toEntity(BoardRequestDTO.CreateRequest request, Teacher teacher) {
        return AuctionBoard.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .teacher(teacher)
                .major(request.getMajor())
                .expectedPrice(request.getStartPrice())
                .specMajor(request.getSpecMajor())
                .files(new ArrayList<>())
                .build();
    }

    /**
     * UpdateRequest DTO로 AuctionBoard 엔티티 업데이트
     */
    public static void updateEntity(AuctionBoard auctionBoard, BoardRequestDTO.UpdateRequest request) {
        auctionBoard.setTitle(request.getTitle());
        auctionBoard.setContent(request.getContent());
        auctionBoard.setMajor(request.getMajor());
        auctionBoard.setExpectedPrice(request.getExpectedPrice());
        auctionBoard.setSpecMajor(request.getSpecMajor());

        if (auctionBoard.getFiles() == null) {
            auctionBoard.setFiles(new ArrayList<>());
        }
    }

    /**
     * AuctionBoard 엔티티를 BoardListItem DTO로 변환
     * 게시글 목록 조회 API용 간략한 정보만 포함
     */
    public static BoardResponseDTO.BoardListItem toBoardListItem(AuctionBoard auctionBoard) {
        String majorString = auctionBoard.getMajor() != null ?
                String.valueOf(auctionBoard.getMajor()) : "기타";

        String teacherName = auctionBoard.getTeacher() != null ?
                auctionBoard.getTeacher().getName() : "Unknown";

        String price = auctionBoard.getExpectedPrice() != null ?
                formatPrice(auctionBoard.getExpectedPrice()) + "~" : "가격 미정";

        return BoardResponseDTO.BoardListItem.builder()
                .id(auctionBoard.getBoardId())
                .major(majorString)
                .specMajor(auctionBoard.getSpecMajor())
                .title(auctionBoard.getTitle())
                .teacher(teacherName)
                .price(price)
                .build();
    }

    /**
     * 가격 포맷팅 메서드
     */
    private static String formatPrice(Long price) {
        if (price == null) return "";
        return String.format("%,d", price);
    }

    /**
     * AuctionBoard 엔티티를 CursorBoardItem DTO로 변환
     * 커서 기반 페이징 API용 게시글 항목
     */
    public static BoardResponseDTO.CursorBoardItem toCursorBoardItem(AuctionBoard auctionBoard) {
        // major 필드를 직접 전달하여 JSON 변환 시 한글명이 사용되도록 수정
        String teacherName = auctionBoard.getTeacher() != null ?
                auctionBoard.getTeacher().getName() : "Unknown";

        String price = auctionBoard.getExpectedPrice() != null ?
                formatPrice(auctionBoard.getExpectedPrice()) + "~" : "가격 미정";

        return BoardResponseDTO.CursorBoardItem.builder()
                .id(auctionBoard.getBoardId())
                .major(auctionBoard.getMajor()) // String.valueOf() 제거하여 Enum 객체를 그대로 전달
                .specMajor(auctionBoard.getSpecMajor())
                .title(auctionBoard.getTitle())
                .teacher(teacherName)
                .price(price)
                .build();
    }

    /**
     * AuctionBoard 엔티티를 OffsetBoardItem DTO로 변환
     * 오프셋 기반 페이징 API용 게시글 항목
     */
    public static BoardResponseDTO.OffsetBoardItem toOffsetBoardItem(AuctionBoard auctionBoard) {
        return BoardResponseDTO.OffsetBoardItem.builder()
                .boardId(auctionBoard.getBoardId())
                .title(auctionBoard.getTitle())
                .teacherName(auctionBoard.getTeacher() != null ? auctionBoard.getTeacher().getName() : "Unknown")
                .major(auctionBoard.getMajor())
                .expectedPrice(auctionBoard.getExpectedPrice())
                .specMajor(auctionBoard.getSpecMajor())
                .createdAt(auctionBoard.getCreatedAt())
                .thumbnailUrl(auctionBoard.getFiles().stream()
                        .filter(file -> file.getIsThumbnail())
                        .findFirst()
                        .map(file -> file.getFilePath())
                        .orElse(null))
                .build();
    }

    /**
     * AuctionBoard 엔티티와 최고 입찰가를 SearchItemResponse DTO로 변환
     */
    public static BoardResponseDTO.SearchItemResponse toSearchItemResponse(AuctionBoard auctionBoard, Long highestBid) {
        return BoardResponseDTO.SearchItemResponse.builder()
                .boardId(auctionBoard.getBoardId())
                .title(auctionBoard.getTitle())
                .major(auctionBoard.getMajor())
                .specMajor(auctionBoard.getSpecMajor())
                .teacherName(auctionBoard.getTeacher() != null ? auctionBoard.getTeacher().getName() : "Unknown")
                .highestBid(highestBid)
                .build();
    }

    /**
     * AuctionBoard Page 객체를 SearchPageResponse DTO로 변환
     * 각 게시글의 최고 입찰가를 포함하여 변환
     */
    public static BoardResponseDTO.SearchPageResponse toSearchPageResponse(Page<AuctionBoard> boardPage,
                                                                           List<Long> highestBids) {
        List<BoardResponseDTO.SearchItemResponse> items = new ArrayList<>();

        List<AuctionBoard> boards = boardPage.getContent();
        for (int i = 0; i < boards.size(); i++) {
            AuctionBoard board = boards.get(i);
            Long highestBid = (i < highestBids.size()) ? highestBids.get(i) : null;
            items.add(toSearchItemResponse(board, highestBid));
        }

        return BoardResponseDTO.SearchPageResponse.builder()
                .content(items)
                .pageNumber(boardPage.getNumber())
                .pageSize(boardPage.getSize())
                .totalElements(boardPage.getTotalElements())
                .totalPages(boardPage.getTotalPages())
                .first(boardPage.isFirst())
                .last(boardPage.isLast())
                .build();
    }
}
