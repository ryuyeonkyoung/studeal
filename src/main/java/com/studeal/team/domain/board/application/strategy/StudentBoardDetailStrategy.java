package com.studeal.team.domain.board.application.strategy;

import com.studeal.team.domain.board.dao.BoardRepository;
import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.negotiation.dao.NegotiationRepository;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.BoardHandler;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 학생 역할을 위한 게시글 상세 조회 전략 구현체
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentBoardDetailStrategy implements BoardDetailStrategy {

  private final BoardRepository boardRepository;
  private final NegotiationRepository negotiationRepository;

  /**
   * 학생 역할을 위한 게시글 상세 정보 조회
   *
   * @param boardId 게시글 ID
   * @param userId  사용자 ID (학생)
   * @return 학생용 게시글 상세 정보
   */
  @Override
  @PreAuthorize("hasRole('STUDENT')")
  public Object getBoardDetail(Long boardId, Long userId) {
    // 게시글 조회
    AuctionBoard auctionBoard = boardRepository.findByIdWithTeacher(boardId)
        .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

    // 게시글에 관련된 모든 협상(입찰) 정보를 가격 높은 순으로 조회
    List<Negotiation> negotiations = negotiationRepository.findByBoardIdOrderByProposedPriceDesc(
        boardId);

    // 입찰 정보 변환 및 순위 부여
    List<BoardResponseDTO.BidResponse> bids = new ArrayList<>();
    int rank = 1;
    for (Negotiation negotiation : negotiations) {
      BoardResponseDTO.BidResponse bid = BoardResponseDTO.BidResponse.builder()
          .rank(rank++)
          .price(negotiation.getProposedPrice())
          .bidder(negotiation.getStudent().getName())
          .build();
      bids.add(bid);
    }

    // 가격 범위 문자열 생성 (시작 가격 + '~')
    String priceRange = auctionBoard.getExpectedPrice() + "~";

    // 선생님 정보 조회
    String teacherName = auctionBoard.getTeacher().getName();
    String teacherEmail = auctionBoard.getTeacher().getEmail();

    // Negotiation 상태 조회 (협상이 없는 경우 기본값 "OPEN" 사용)
    String status = "OPEN";
    if (!negotiations.isEmpty()) {
      status = negotiations.get(0).getStatus().name();
    }

    log.info("학생({})이 게시글({}) 상세 조회 완료", userId, boardId);

    return BoardResponseDTO.DetailStudentResponse.builder()
        .title(auctionBoard.getTitle())
        .major(auctionBoard.getMajor())
        .specMajor(auctionBoard.getSpecMajor())
        .description(auctionBoard.getContent())
        .priceRange(priceRange)
        .teacherName(teacherName)
        .teacherEmail(teacherEmail)
        .bids(bids)
        .status(status)
        .build();
  }
}
