package com.studeal.team.domain.negotiation.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.domain.QAuctionBoard;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.negotiation.domain.QNegotiation;
import com.studeal.team.domain.user.domain.entity.QStudent;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * NegotiationRepositoryCustom 인터페이스 구현체 Querydsl을 활용한 쿼리 최적화
 */
public class NegotiationRepositoryImpl extends QuerydslRepositorySupport implements
    NegotiationRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public NegotiationRepositoryImpl(JPAQueryFactory queryFactory) {
    super(Negotiation.class);
    this.queryFactory = queryFactory;
  }

  /**
   * 게시글 ID로 관련된 모든 협상 정보 조회 (학생 정보 포함) - 최적화 제안된 가격 높은 순으로 정렬
   */
  @Override
  public List<Negotiation> findByBoardIdOrderByProposedPriceDesc(Long boardId) {
    QNegotiation negotiation = QNegotiation.negotiation;
    QStudent student = QStudent.student;
    QAuctionBoard board = QAuctionBoard.auctionBoard;

    return queryFactory
        .selectFrom(negotiation)
        .join(negotiation.student, student).fetchJoin()
        .join(negotiation.auctionBoard, board).fetchJoin()
        .where(negotiation.auctionBoard.boardId.eq(boardId))
        .orderBy(negotiation.proposedPrice.desc())
        .fetch();
  }

  /**
   * 특정 학생이 참여한 협상 중인 게시글 목록 조회 - 최적화
   */
  @Override
  public List<AuctionBoard> findBoardsByStudentId(Long studentId) {
    QNegotiation negotiation = QNegotiation.negotiation;
    QAuctionBoard board = QAuctionBoard.auctionBoard;

    return queryFactory
        .selectDistinct(negotiation.auctionBoard)
        .from(negotiation)
        .join(negotiation.auctionBoard, board).fetchJoin()
        .where(negotiation.student.userId.eq(studentId))
        .fetch();
  }

  /**
   * 특정 학생이 참여한 협상 중인 게시글 목록과 각 게시글별 최고 제안 가격을 함께 조회 각 게시글당 학생이 제안한 가장 높은 가격만 반환 오라클 GROUP BY 규칙을
   * 준수하는 구현 방식으로 변경
   */
  @Override
  public List<BoardResponseDTO.BoardWithHighestPriceDTO> findBoardsWithHighestPriceByStudentId(
      Long studentId) {
    QNegotiation negotiation = QNegotiation.negotiation;
    QAuctionBoard board = QAuctionBoard.auctionBoard;

    // 먼저 학생이 참여한 모든 게시글 조회
    List<AuctionBoard> boards = queryFactory
        .selectDistinct(board)
        .from(negotiation)
        .join(negotiation.auctionBoard, board)
        .where(negotiation.student.userId.eq(studentId))
        .fetch();

    // 각 게시글별 최고 제안 가격을 별도로 조회
    List<BoardResponseDTO.BoardWithHighestPriceDTO> result = new ArrayList<>();
    for (AuctionBoard auctionBoard : boards) {
      // 해당 게시글에 대한 학생의 최고 제안가 조회
      Long highestPrice = queryFactory
          .select(negotiation.proposedPrice.max())
          .from(negotiation)
          .where(negotiation.student.userId.eq(studentId)
              .and(negotiation.auctionBoard.boardId.eq(auctionBoard.getBoardId())))
          .fetchOne();

      // DTO 생성
      BoardResponseDTO.BoardWithHighestPriceDTO.AuctionBoardInfo boardInfo =
          BoardResponseDTO.BoardWithHighestPriceDTO.AuctionBoardInfo.builder()
              .boardId(auctionBoard.getBoardId())
              .title(auctionBoard.getTitle())
              .major(auctionBoard.getMajor())
              .build();

      BoardResponseDTO.BoardWithHighestPriceDTO dto =
          BoardResponseDTO.BoardWithHighestPriceDTO.builder()
              .board(boardInfo)
              .highestPrice(highestPrice)
              .build();

      result.add(dto);
    }

    return result;
  }

  /**
   * 협상 통계 정보 조회 (미사용)
   */
  @Override
  public NegotiationStats getNegotiationStats(Long boardId) {
    QNegotiation negotiation = QNegotiation.negotiation;

    return queryFactory
        .select(Projections.constructor(NegotiationStats.class,
            negotiation.proposedPrice.avg().coalesce((double) 0L),
            negotiation.proposedPrice.max().coalesce(0L),
            negotiation.proposedPrice.min().coalesce(0L),
            negotiation.count()))
        .from(negotiation)
        .where(negotiation.auctionBoard.boardId.eq(boardId))
        .fetchOne();
  }
}
