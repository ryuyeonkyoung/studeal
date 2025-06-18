package com.studeal.team.domain.negotiation.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.domain.QAuctionBoard;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.negotiation.domain.QNegotiation;
import com.studeal.team.domain.user.domain.entity.QStudent;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

/**
 * NegotiationRepositoryCustom 인터페이스 구현체
 * Querydsl을 활용한 쿼리 최적화
 */
public class NegotiationRepositoryImpl extends QuerydslRepositorySupport implements NegotiationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public NegotiationRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Negotiation.class);
        this.queryFactory = queryFactory;
    }

    /**
     * 게시글 ID로 관련된 모든 협상 정보 조회 (학생 정보 포함) - 최적화
     * 제안된 가격 높은 순으로 정렬
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
