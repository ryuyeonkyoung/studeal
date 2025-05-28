package com.studeal.team.domain.board.converter;

import com.studeal.team.domain.board.domain.Board;
import com.studeal.team.domain.board.domain.BoardFile;
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
     * Board 엔티티를 DetailResponse DTO로 변환
     */
    public static BoardResponseDTO.DetailResponse toDetailResponse(Board board) {
        BoardResponseDTO.DetailResponse.DetailResponseBuilder builder = BoardResponseDTO.DetailResponse.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .major(board.getMajor())
                .expectedPrice(board.getExpectedPrice())
                .specMajor(board.getSpecMajor())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt());

        // 선생님 정보 세팅 - User 클래스에서 상속받은 필드를 사용
        if (board.getTeacher() != null) {
            builder.teacherId(board.getTeacher().getUserId())
                   .teacherName(board.getTeacher().getName());
        }

        // 썸네일 이미지가 있는 경우 URL 세팅
        board.getFiles().stream()
            .filter(BoardFile::getIsThumbnail)
            .findFirst()
            .ifPresent(file -> builder.thumbnailUrl(file.getFilePath()));

        return builder.build();
    }

    /**
     * Board 엔티티를 ListItemResponse DTO로 변환
     */
    public static BoardResponseDTO.ListItemResponse toListItemResponse(Board board) {
        BoardResponseDTO.ListItemResponse.ListItemResponseBuilder builder = BoardResponseDTO.ListItemResponse.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .major(board.getMajor())
                .expectedPrice(board.getExpectedPrice())
                .specMajor(board.getSpecMajor())
                .createdAt(board.getCreatedAt());

        // 선생님 정보 세팅 - User 클래스에서 상속받은 필드를 사용
        if (board.getTeacher() != null) {
            builder.teacherName(board.getTeacher().getName());
        }

        // 썸네일 이미지가 있는 경우 URL 세팅
        board.getFiles().stream()
            .filter(BoardFile::getIsThumbnail)
            .findFirst()
            .ifPresent(file -> builder.thumbnailUrl(file.getFilePath()));

        return builder.build();
    }

    /**
     * Board Page 객체를 PageResponse DTO로 변환
     */
    public static BoardResponseDTO.PageResponse toPageResponse(Page<Board> boardPage) {
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
     * CreateRequest DTO와 Teacher 엔티티로 Board 엔티티 생성
     */
    public static Board toEntity(BoardRequestDTO.CreateRequest request, Teacher teacher) {
        return Board.builder()
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
     * UpdateRequest DTO로 Board 엔티티 업데이트
     */
    public static void updateEntity(Board board, BoardRequestDTO.UpdateRequest request) {
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        board.setMajor(request.getMajor());
        board.setExpectedPrice(request.getExpectedPrice());
        board.setSpecMajor(request.getSubject());

        if (board.getFiles() == null) {
            board.setFiles(new ArrayList<>());
        }
    }
}
