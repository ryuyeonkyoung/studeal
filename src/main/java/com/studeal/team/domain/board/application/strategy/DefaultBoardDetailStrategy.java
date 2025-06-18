package com.studeal.team.domain.board.application.strategy;

import com.studeal.team.domain.board.converter.BoardConverter;
import com.studeal.team.domain.board.dao.BoardRepository;
import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.BoardHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 기본 역할을 위한 게시글 상세 조회 전략 구현체
 * 비인증 사용자 또는 특별한 역할이 없는 사용자용
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultBoardDetailStrategy implements BoardDetailStrategy {

    private final BoardRepository boardRepository;

    /**
     * 기본 게시글 상세 정보 조회
     *
     * @param boardId 게시글 ID
     * @param userId  사용자 ID (무시될 수 있음)
     * @return 기본 게시글 상세 정보
     */
    @Override
    public Object getBoardDetail(Long boardId, Long userId) {
        AuctionBoard auctionBoard = boardRepository.findByIdWithTeacher(boardId)
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        log.info("기본 게시글 조회 완료. 게시글 ID: {}", boardId);

        return BoardConverter.toDetailResponse(auctionBoard);
    }
}
