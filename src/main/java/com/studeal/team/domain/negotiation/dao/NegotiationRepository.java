package com.studeal.team.domain.negotiation.dao;

import com.studeal.team.domain.negotiation.domain.Negotiation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NegotiationRepository extends JpaRepository<Negotiation, Long> {
    /**
     * 게시글 ID로 관련된 모든 협상 정보 조회
     * 학생 정보도 함께 조회 (Eager 로딩)
     * 제안된 가격 높은 순으로 정렬
     */
    @Query("SELECT n FROM Negotiation n JOIN FETCH n.student s WHERE n.auctionBoard.boardId = :boardId ORDER BY n.proposedPrice DESC")
    List<Negotiation> findByBoardIdOrderByProposedPriceDesc(Long boardId);
}