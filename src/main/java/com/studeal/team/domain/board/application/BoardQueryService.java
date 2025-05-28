package com.studeal.team.domain.board.application;

import com.studeal.team.domain.board.converter.BoardConverter;
import com.studeal.team.domain.board.dao.BoardRepository;
import com.studeal.team.domain.board.domain.Board;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.BoardHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시판 조회 서비스 클래스
 * 조회 작업만을 담당합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardQueryService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 상세 조회
     * @param boardId 게시글 ID
     * @return 게시글 응답 DTO
     */
    public BoardResponseDTO.DetailResponse getBoard(Long boardId) {
        Board board = boardRepository.findByIdWithTeacher(boardId)
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        log.info("게시글 조회 완료. 게시글 ID: {}", boardId);

        return BoardConverter.toDetailResponse(board);
    }

    /**
     * 게시글 목록 조회 (페이징)
     * @param pageable 페이징 정보
     * @return 게시글 목록 페이징 응답 DTO
     */
    public BoardResponseDTO.PageResponse getBoardList(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);

        log.info("게시글 목록 조회 완료. 페이지: {}, 사이즈: {}", pageable.getPageNumber(), pageable.getPageSize());

        return BoardConverter.toPageResponse(boardPage);
    }

    /**
     * 선생님이 작성한 게시글 목록 조회 (페이징)
     * @param teacherId 선생님 ID
     * @param pageable 페이징 정보
     * @return 게시글 목록 페이징 응답 DTO
     */
    public BoardResponseDTO.PageResponse getBoardListByTeacher(Long teacherId, Pageable pageable) {
        Page<Board> boardPage = boardRepository.findByTeacherUserId(teacherId, pageable);

        log.info("선생님 ID로 게시글 목록 조회 완료. 선생님 ID: {}", teacherId);

        return BoardConverter.toPageResponse(boardPage);
    }

    /**
     * 제목으로 게시글 검색 (페이징)
     * @param keyword 검색어
     * @param pageable 페이징 정보
     * @return 게시글 목록 페이징 응답 DTO
     */
    public BoardResponseDTO.PageResponse searchBoardsByTitle(String keyword, Pageable pageable) {
        Page<Board> boardPage = boardRepository.findByTitleContaining(keyword, pageable);

        log.info("제목으로 게시글 검색 완료. 키워드: {}", keyword);

        return BoardConverter.toPageResponse(boardPage);
    }

    /**
     * 수업 주제로 게시글 검색 (페이징)
     * @param subject 수업 주제
     * @param pageable 페이징 정보
     * @return 게시글 목록 페이징 응답 DTO
     */
    public BoardResponseDTO.PageResponse searchBoardsBySubject(String subject, Pageable pageable) {
        Page<Board> boardPage = boardRepository.findBySpecMajorContaining(subject, pageable);

        log.info("수업 주제로 게시글 검색 완료. 주제: {}", subject);

        return BoardConverter.toPageResponse(boardPage);
    }
}
