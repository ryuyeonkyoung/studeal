package com.studeal.team.domain.board.application.strategy;

import com.studeal.team.domain.board.dao.BoardRepository;
import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.negotiation.dao.NegotiationRepository;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.domain.user.domain.entity.Teacher;
import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.BoardHandler;
import com.studeal.team.global.error.exception.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 선생님 역할을 위한 게시글 상세 조회 전략 구현체
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherBoardDetailStrategy implements BoardDetailStrategy {

    private final BoardRepository boardRepository;
    private final NegotiationRepository negotiationRepository;
    private final TeacherRepository teacherRepository;

    /**
     * 선생님 역할을 위한 게시글 상세 정보 조회
     *
     * @param boardId 게시글 ID
     * @param userId  사용자 ID (선생님)
     * @return 선생님용 게시글 상세 정보
     */
    @Override
    @PreAuthorize("hasRole('TEACHER')")
    public Object getBoardDetail(Long boardId, Long userId) {
        Teacher teacher = teacherRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        // 교사 역할인지 검증
        if (teacher.getRole() != UserRole.TEACHER) {
            throw new UserHandler(ErrorStatus.USER_NOT_TEACHER);
        }

        // 게시글 조회
        AuctionBoard auctionBoard = boardRepository.findByIdWithTeacher(boardId)
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        // 현재 로그인한 사용자가 게시글 작성자인지 확인
        boolean isAuthor = auctionBoard.getTeacher() != null &&
                auctionBoard.getTeacher().getUserId().equals(userId);

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

        // Negotiation 상태 조회 (협상이 없는 경우 기본값 "PENDING" 사용)
        String status = "PENDING";
        if (!negotiations.isEmpty()) {
            status = negotiations.get(0).getStatus().name();
        }

        log.info("선생님({})이 게시글({}) 상세 조회 완료", userId, boardId);

        return BoardResponseDTO.DetailTeacherResponse.builder()
                .negotiationId(negotiations.isEmpty() ? null : negotiations.get(0).getNegotiationId())
                .title(auctionBoard.getTitle())
                .major(auctionBoard.getMajor())
                .specMajor(auctionBoard.getSpecMajor())
                .description(auctionBoard.getContent())
                .priceRange(priceRange)
                .bids(bids)
                .status(status)
                .isAuthor(isAuthor) // 작성자 여부 추가
                .build();
    }
}
