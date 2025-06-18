package com.studeal.team.domain.negotiation.dao;

import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NegotiationRepository extends JpaRepository<Negotiation, Long>,
    NegotiationRepositoryCustom {

  /**
   * 게시글 ID로 관련된 모든 협상 정보 조회 학생 정보도 함께 조회 (Eager 로딩) 제안된 가격 높은 순으로 정렬
   */
  @Query("SELECT n FROM Negotiation n JOIN FETCH n.student s WHERE n.auctionBoard.boardId = :boardId ORDER BY n.proposedPrice DESC")
  List<Negotiation> findByBoardIdOrderByProposedPriceDesc(Long boardId);

  /**
   * 특정 학생이 참여한 협상 중인 게시글 목록 조회 학생 ID로 연결된 AuctionBoard 목록을 조회
   */
  @Query("SELECT DISTINCT n.auctionBoard FROM Negotiation n WHERE n.student.userId = :studentId")
  List<AuctionBoard> findBoardsByStudentId(Long studentId);

  /**
   * 게시글 ID로 최근 협상 정보 하나를 조회 선생님의 협상 중인 수업 목록 조회 시 사용
   */
  @Query("SELECT n FROM Negotiation n WHERE n.auctionBoard.boardId = :boardId ORDER BY n.createdAt DESC")
  Optional<Negotiation> findTopByBoardIdOrderByCreatedAtDesc(Long boardId);

  /**
   * 학생 ID로 협상 정보 목록 조회
   */
  List<Negotiation> findByStudentUserId(Long studentId);
}