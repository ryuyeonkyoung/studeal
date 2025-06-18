package com.studeal.team.domain.negotiation.dao;

import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import java.util.List;

/**
 * 협상 정보 Repository Custom 인터페이스 Querydsl을 활용한 복잡한 쿼리를 위한 인터페이스
 */
public interface NegotiationRepositoryCustom {

  /**
   * 게시글 ID로 관련된 모든 협상 정보 조회 (학생 정보 포함) 제안된 가격 높은 순으로 정렬 Querydsl 최적화 버전
   */
  List<Negotiation> findByBoardIdOrderByProposedPriceDesc(Long boardId);

  /**
   * 특정 학생이 참여한 협상 중인 게시글 목록 조회 Querydsl 최적화 버전
   */
  List<AuctionBoard> findBoardsByStudentId(Long studentId);

  /**
   * 특정 학생이 참여한 협상 중인 게시글 목록과 각 게시글별 최고 제안 가격을 함께 조회 게시글당 가장 높은 가격만 포함
   *
   * @param studentId 학생 ID
   * @return 게시글과 최고 제안 가격 정보가 담긴 DTO 리스트
   */
  List<BoardResponseDTO.BoardWithHighestPriceDTO> findBoardsWithHighestPriceByStudentId(
      Long studentId);

  /**
   * 협상 통계 정보 조회 (미사용) 게시글별 평균 제안 가격, 최고/최저 제안 가격 등 통계 정보
   */
  NegotiationStats getNegotiationStats(Long boardId);

  /**
   * 통계 정보 클래스
   */
  class NegotiationStats {

    private final Long avgPrice;
    private final Long maxPrice;
    private final Long minPrice;
    private final Long count;

    public NegotiationStats(Long avgPrice, Long maxPrice, Long minPrice, Long count) {
      this.avgPrice = avgPrice;
      this.maxPrice = maxPrice;
      this.minPrice = minPrice;
      this.count = count;
    }

    public Long getAvgPrice() {
      return avgPrice;
    }

    public Long getMaxPrice() {
      return maxPrice;
    }

    public Long getMinPrice() {
      return minPrice;
    }

    public Long getCount() {
      return count;
    }
  }
}
