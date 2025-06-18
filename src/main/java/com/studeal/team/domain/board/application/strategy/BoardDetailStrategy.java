package com.studeal.team.domain.board.application.strategy;

/**
 * 게시글 상세 조회 전략 인터페이스 사용자 역할에 따른 다양한 게시글 상세 조회 전략 정의
 */
public interface BoardDetailStrategy {

  /**
   * 게시글 상세 정보 조회
   *
   * @param boardId 게시글 ID
   * @param userId  사용자 ID
   * @return 역할에 맞는 게시글 상세 정보 (DTO)
   */
  Object getBoardDetail(Long boardId, Long userId);
}
