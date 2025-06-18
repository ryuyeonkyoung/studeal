package com.studeal.team.domain.board.application;

import com.studeal.team.domain.board.application.strategy.BoardDetailStrategyFactory;
import com.studeal.team.domain.board.converter.BoardConverter;
import com.studeal.team.domain.board.dao.BoardRepository;
import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.negotiation.dao.NegotiationRepository;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.domain.user.dao.UserRepository;
import com.studeal.team.domain.user.domain.entity.Teacher;
import com.studeal.team.domain.user.domain.entity.User;
import com.studeal.team.domain.user.domain.entity.enums.MajorSubject;
import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.BoardHandler;
import com.studeal.team.global.error.exception.handler.UserHandler;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시판 조회 서비스 클래스 조회 작업만을 담당합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class BoardQueryService {

  private final BoardRepository boardRepository;
  private final NegotiationRepository negotiationRepository;
  private final TeacherRepository teacherRepository;
  private final UserRepository userRepository;
  private final BoardDetailStrategyFactory strategyFactory;

  /**
   * 게시글 상세 조회
   *
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
   *
   * @param pageable 페이징 정보
   * @return 게시글 목록 페이징 응답 DTO
   */
  public BoardResponseDTO.PageResponse getBoardList(Pageable pageable) {
    Page<AuctionBoard> boardPage = boardRepository.findAll(pageable);

    log.info("게시글 목록 조회 완료. 페이지: {}, 사이즈: {}", pageable.getPageNumber(), pageable.getPageSize());

    return BoardConverter.toPageResponse(boardPage);
  }

  /**
   * 게시글 상세 조회 - 선생님 용 게시글 정보와 입찰(협상) 정보를 함께 조회합니다.
   *
   * @param boardId   게시글 ID
   * @param teacherId 선생님 ID (인증 정보에서 추출)
   * @return 선생님용 게시글 상세 응답 DTO
   */
  @PreAuthorize("hasRole('TEACHER')")
  public BoardResponseDTO.DetailTeacherResponse getTeacherDetailBoard(Long boardId,
      Long teacherId) {

    Teacher teacher = teacherRepository.findById(teacherId)
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
        auctionBoard.getTeacher().getUserId().equals(teacherId);

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

    // Negotiation 상태 조회 (협상이 없는 경우 기본값 "OPEN" 사용)
    String status = "OPEN";
    if (!negotiations.isEmpty()) {
      status = negotiations.get(0).getStatus().name();
    }

    // 응답 DTO 생성 및 isAuthor 설정
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

  /**
   * 게시글 상세 조회 - 학생 용 게시글 정보와 선생님 정보를 함께 조회합니다.
   *
   * @param boardId   게시글 ID
   * @param studentId 학생 ID (인증 정보에서 추출)
   * @return 학생용 게시글 상세 응답 DTO
   */
  @PreAuthorize("hasRole('STUDENT')")
  public BoardResponseDTO.DetailStudentResponse getStudentDetailBoard(Long boardId,
      Long studentId) {
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

    log.info("학생({})이 게시글({}) 상세 조회 완료", studentId, boardId);

    // 응답 DTO 생성
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

  /**
   * 게시글 목록을 커서 기반 페이징으로 조회
   *
   * @param cursorId 커서 ID (이전 페이지의 마지막 게시글 ID, 첫 페이지는 null)
   * @param size     페이지 크기
   * @return 커서 페이징 결과 응답 DTO
   */
  public BoardResponseDTO.CursorPageResponse getBoardsByCursor(Long cursorId, Integer size) {
    // 페이지 크기 기본값 설정
    int pageSize = (size == null || size <= 0) ? 10 : size;

    // 커서 기반 페이징으로 게시글 조회
    Pageable pageable = PageRequest.of(0, pageSize + 1); // 다음 페이지 존재 여부 확인을 위해 +1
    List<AuctionBoard> boards = boardRepository.findBoardsByCursor(cursorId, pageable);

    boolean hasNext = false;
    Long nextCursor = null;

    // 요청한 페이지 크기보다 많이 조회되었다면 다음 페이지가 존재
    if (boards.size() > pageSize) {
      hasNext = true;
      // 마지막 항목은 다음 페이지 존재 여부 확인용이므로 제거
      boards.remove(boards.size() - 1);
    }

    // 게시글을 DTO로 변환
    List<BoardResponseDTO.CursorBoardItem> boardItems = boards.stream()
        .map(BoardConverter::toCursorBoardItem)
        .toList();

    // 다음 커서 설정
    if (!boards.isEmpty() && hasNext) {
      nextCursor = boards.get(boards.size() - 1).getBoardId();
    } else if (!boards.isEmpty()) {
      // 현재 페이지의 마지막 ID보다 작은 ID를 가진 게시글이 있는지 확인
      Long lastBoardId = boards.get(boards.size() - 1).getBoardId();
      hasNext = boardRepository.existsByBoardIdLessThan(lastBoardId);
      if (hasNext) {
        nextCursor = lastBoardId;
      }
    }

    log.info("커서 기반 게시글 목록 조회 완료. 커서 ID: {}, 조회된 게시글 수: {}, 다음 페이지 존재: {}",
        cursorId, boardItems.size(), hasNext);

    return BoardResponseDTO.CursorPageResponse.builder()
        .boards(boardItems)
        .hasNext(hasNext)
        .nextCursor(nextCursor)
        .count(boardItems.size())
        .build();
  }

  /**
   * 게시글 목록을 오프셋 기반 페이징으로 조회
   *
   * @param pageable 페이징 정보
   * @return 오프셋 페이징 결과 응답 DTO
   */
  public BoardResponseDTO.OffsetPageResponse getBoardsByOffset(Pageable pageable) {
    Page<AuctionBoard> boardPage = boardRepository.findAll(pageable);

    log.info("오프셋 기반 게시글 목록 조회 완료. 페이지: {}, 사이즈: {}",
        pageable.getPageNumber(), pageable.getPageSize());

    List<BoardResponseDTO.OffsetBoardItem> boardItems = boardPage.getContent().stream()
        .map(BoardConverter::toOffsetBoardItem)
        .toList();

    return BoardResponseDTO.OffsetPageResponse.builder()
        .content(boardItems)
        .pageNumber(boardPage.getNumber())
        .pageSize(boardPage.getSize())
        .totalElements(boardPage.getTotalElements())
        .totalPages(boardPage.getTotalPages())
        .first(boardPage.isFirst())
        .last(boardPage.isLast())
        .build();
  }

  /**
   * 게시글 검색 기능 검색 유형(major, teacherName, specMajor)에 따라 다른 검색 로직 적용
   *
   * @param searchType 검색 유형 (MAJOR, TEACHER_NAME, SPEC_MAJOR)
   * @param keyword    검색어
   * @param pageable   페이징 정보
   * @return 검색 결과 응답 DTO
   */
  public BoardResponseDTO.SearchPageResponse searchBoards(String searchType, String keyword,
      Pageable pageable) {
    if (searchType == null || keyword == null) {
      throw new BoardHandler(ErrorStatus.BOARD_SEARCH_INVALID_PARAMS);
    }

    Page<AuctionBoard> boardPage;

    // 검색 유형에 따른 다른 검색 로직 적용
    switch (searchType.toUpperCase()) {
      case "MAJOR":
        try {
          MajorSubject major = MajorSubject.fromKoreanName(keyword);
          boardPage = boardRepository.searchByMajor(major, pageable);
        } catch (IllegalArgumentException e) {
          throw new BoardHandler(ErrorStatus.BOARD_SEARCH_INVALID_MAJOR);
        }
        break;
      case "TEACHER_NAME":
        boardPage = boardRepository.searchByTeacherName(keyword, pageable);
        break;
      case "SPEC_MAJOR":
        boardPage = boardRepository.searchBySpecMajor(keyword, pageable);
        break;
      default:
        throw new BoardHandler(ErrorStatus.BOARD_SEARCH_INVALID_TYPE);
    }

    // 각 게시글의 최고 입찰가 조회
    List<Long> highestBids = new ArrayList<>();
    for (AuctionBoard board : boardPage.getContent()) {
      List<Negotiation> negotiations =
          negotiationRepository.findByBoardIdOrderByProposedPriceDesc(board.getBoardId());
      Long highestBid = negotiations.isEmpty() ? null : negotiations.get(0).getProposedPrice();
      highestBids.add(highestBid);
    }

    log.info("게시글 검색 완료. 검색 유형: {}, 검색어: {}, 결과 수: {}",
        searchType, keyword, boardPage.getContent().size());

    return BoardConverter.toSearchPageResponse(boardPage, highestBids);
  }

  /**
   * 게시글 상세 조회 - 역할 기반 사용자의 역할에 따라 다른 형태의 상세 정보를 반환합니다.
   *
   * @param boardId 게시글 ID
   * @param userId  사용자 ID
   * @return 역할에 맞는 게시글 상세 정보
   */
  public Object getBoardDetailByRole(Long boardId, Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

    // 역할에 따른 전략 패턴 적용
    return strategyFactory.getStrategy(user.getRole()).getBoardDetail(boardId, userId);
  }
}
