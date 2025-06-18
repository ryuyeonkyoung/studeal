package com.studeal.team.domain.board.application;

import com.studeal.team.domain.board.converter.BoardConverter;
import com.studeal.team.domain.board.dao.BoardRepository;
import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.dto.BoardRequestDTO;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.domain.user.domain.entity.Teacher;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.BoardHandler;
import com.studeal.team.global.error.exception.handler.UserHandler;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시판 명령 서비스 클래스입니다. 게시글의 생성, 수정, 삭제 작업을 담당합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class BoardCommandService {

  /**
   * 게시판 저장소
   */
  private final BoardRepository boardRepository;

  /**
   * 선생님 정보 저장소
   */
  private final TeacherRepository teacherRepository;

  /**
   * 엔티티 관리자
   */
  private final EntityManager entityManager;

  /**
   * 새로운 게시글을 생성합니다.
   *
   * @param teacherId 선생님 ID
   * @param request   게시글 생성 요청 DTO
   * @return 생성된 게시글 상세 정보
   */
  // TODO: JPA 성능 최적화 필요 - N+1 문제 고려
  @PreAuthorize("hasRole('TEACHER')")
  public BoardResponseDTO.DetailResponse createBoard(
      Long teacherId,
      BoardRequestDTO.CreateRequest request) {
    // 배타적 락을 통해 선생님 정보 조회
    Teacher teacher = entityManager.createQuery(
            "SELECT t FROM Teacher t WHERE t.userId = :id",
            Teacher.class)
        .setParameter("id", teacherId)
        .setLockMode(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
        .getSingleResult();

    if (teacher == null) {
      throw new UserHandler(ErrorStatus.USER_NOT_FOUND);
    }

    // 게시글 생성 및 저장
    AuctionBoard auctionBoard = BoardConverter.toEntity(request, teacher);
    AuctionBoard savedAuctionBoard = boardRepository.save(auctionBoard);

    log.info("게시글 생성 완료. 게시글 ID: {}", savedAuctionBoard.getBoardId());

    return BoardConverter.toDetailResponse(savedAuctionBoard);
  }

  /**
   * 기존 게시글을 수정합니다.
   *
   * @param boardId   게시글 ID
   * @param teacherId 선생님 ID (작성자 확인용)
   * @param request   게시글 수정 요청 DTO
   * @return 수정된 게시글 상세 정보
   */
  @PreAuthorize("hasRole('TEACHER')")
  public BoardResponseDTO.DetailResponse updateBoard(
      Long boardId,
      Long teacherId,
      BoardRequestDTO.UpdateRequest request) {
    // 게시글 조회
    AuctionBoard auctionBoard = boardRepository.findById(boardId)
        .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

    // 게시글 작성자 확인
    if (!auctionBoard.getTeacher().getUserId().equals(teacherId)) {
      throw new BoardHandler(ErrorStatus.BOARD_UNAUTHORIZED);
    }

    // 게시글 정보 업데이트
    BoardConverter.updateEntity(auctionBoard, request);

    // 변경된 게시글 저장
    AuctionBoard updatedAuctionBoard = boardRepository.save(auctionBoard);

    log.info("게시글 수정 완료. 게시글 ID: {}", boardId);

    return BoardConverter.toDetailResponse(updatedAuctionBoard);
  }

  /**
   * 게시글을 삭제합니다.
   *
   * @param boardId   게시글 ID
   * @param teacherId 선생님 ID (작성자 확인용)
   */
  @PreAuthorize("hasRole('TEACHER')")
  public void deleteBoard(Long boardId, Long teacherId) {
    // 게시글 조회
    AuctionBoard auctionBoard = boardRepository.findById(boardId)
        .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

    // 게시글 작성자 확인
    if (!auctionBoard.getTeacher().getUserId().equals(teacherId)) {
      throw new BoardHandler(ErrorStatus.BOARD_UNAUTHORIZED);
    }

    // 게시글 삭제
    boardRepository.delete(auctionBoard);
    log.info("게시글 삭제 완료. 게시글 ID: {}", boardId);
  }
}
