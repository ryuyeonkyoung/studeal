package com.studeal.team.domain.board.converter;

import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.domain.AuctionBoardFile;
import com.studeal.team.domain.board.dto.BoardRequestDTO;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.user.domain.Teacher;
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
                .specMajor(request.getSubject())
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
        auctionBoard.setSpecMajor(request.getSubject());

        if (auctionBoard.getFiles() == null) {
            auctionBoard.setFiles(new ArrayList<>());
        }
    }
}
