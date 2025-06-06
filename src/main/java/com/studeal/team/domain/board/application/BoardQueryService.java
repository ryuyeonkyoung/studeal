package com.studeal.team.domain.board.application;

import com.studeal.team.domain.board.converter.BoardConverter;
import com.studeal.team.domain.board.dao.BoardRepository;
import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.negotiation.dao.NegotiationRepository;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.BoardHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    private final NegotiationRepository negotiationRepository;

    /**
     * 게시글 상세 조회
     * @param boardId 게시글 ID
     * @return 게시글 응답 DTO
     */
    public BoardResponseDTO.DetailResponse getBoard(Long boardId) {
        AuctionBoard auctionBoard = boardRepository.findByIdWithTeacher(boardId)
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        log.info("게시글 조회 완료. 게시글 ID: {}", boardId);

        return BoardConverter.toDetailResponse(auctionBoard);
    }

    /**
     * 게시글 목록 조회 (페이징)
     * @param pageable 페이징 정보
     * @return 게시글 목록 페이징 응답 DTO
     */
    public BoardResponseDTO.PageResponse getBoardList(Pageable pageable) {
        Page<AuctionBoard> boardPage = boardRepository.findAll(pageable);

        log.info("게시글 목록 조회 완료. 페이지: {}, 사이즈: {}", pageable.getPageNumber(), pageable.getPageSize());

        return BoardConverter.toPageResponse(boardPage);
    }

    /**
     * 게시글 상세 조회 - 선생님 용
     * 게시글 정보와 입찰(협상) 정보를 함께 조회합니다.
     * @param boardId 게시글 ID
     * @param teacherId 선생님 ID (인증 정보에서 추출)
     * @return 선생님용 게시글 상세 응답 DTO
     */
    public BoardResponseDTO.DetailTeacherResponse getTeacherDetailBoard(Long boardId, Long teacherId) {
        // 게시글 조회
        AuctionBoard auctionBoard = boardRepository.findByIdWithTeacher(boardId)
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        // 게시글에 관련된 모든 협상(입찰) 정보를 가격 높은 순으로 조회
        List<Negotiation> negotiations = negotiationRepository.findByBoardIdOrderByProposedPriceDesc(boardId);

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

        // 응답 DTO 생성
        return BoardResponseDTO.DetailTeacherResponse.builder()
                .negotiationId(negotiations.isEmpty() ? null : negotiations.get(0).getNegotiationId())
                .title(auctionBoard.getTitle())
                .subject(auctionBoard.getTeacher().getMajor()) // Teacher의 major 필드 사용
                .description(auctionBoard.getContent())
                .priceRange(priceRange)
                .bids(bids)
                .status("OPEN") // 현재는 고정값으로 설정, 필요시 상태 관리 로직 추가
                .build();
    }
}
