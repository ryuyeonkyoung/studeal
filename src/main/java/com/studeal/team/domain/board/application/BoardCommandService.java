package com.studeal.team.domain.board.application;

import com.studeal.team.domain.board.converter.BoardConverter;
import com.studeal.team.domain.board.dao.BoardRepository;
import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.dto.BoardRequestDTO;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.domain.user.domain.entity.Teacher;
import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.BoardHandler;
import com.studeal.team.global.error.exception.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 게시판 명령 서비스 클래스
 * 생성, 수정, 삭제 작업을 담당합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
//@Transactional
public class BoardCommandService {

    private final BoardRepository boardRepository;
    private final TeacherRepository teacherRepository;

    /**
     * 게시글 생성
     *
     * @param teacherId 선생님 ID
     * @param request   게시글 요청 DTO
     * @return 생성된 게시글 응답 DTO
     */
    // TODO: JPA 성능 최적화 필요 - N+1 문제 고려
    public BoardResponseDTO.DetailResponse createBoard(Long teacherId, BoardRequestDTO.CreateRequest request) {

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        // 교사 역할인지 검증
        if (teacher.getRole() != UserRole.TEACHER) {
            throw new UserHandler(ErrorStatus.USER_NOT_TEACHER);
        }

        // 게시글 생성 및 저장
        AuctionBoard auctionBoard = BoardConverter.toEntity(request, teacher);
        AuctionBoard savedAuctionBoard = boardRepository.save(auctionBoard);

        log.info("게시글 생성 완료. 게시글 ID: {}", savedAuctionBoard.getBoardId());

        return BoardConverter.toDetailResponse(savedAuctionBoard);
    }

    /**
     * 게시글 수정
     *
     * @param boardId   게시글 ID
     * @param teacherId 선생님 ID (작성자 확인용)
     * @param request   게시글 요청 DTO
     * @return 수정된 게시글 응답 DTO
     */
    public BoardResponseDTO.DetailResponse updateBoard(Long boardId, Long teacherId, BoardRequestDTO.UpdateRequest request) {
        // 게시글 조회
        AuctionBoard auctionBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        // 게시글 작성자 확인
        if (!auctionBoard.getTeacher().getUserId().equals(teacherId)) {
            throw new BoardHandler(ErrorStatus.BOARD_UNAUTHORIZED);
        }

        // 교사 역할인지 검증
        Teacher teacher = auctionBoard.getTeacher();
        if (teacher.getRole() != UserRole.TEACHER) {
            throw new UserHandler(ErrorStatus.USER_NOT_TEACHER);
        }

        // 게시글 정보 업데이트
        BoardConverter.updateEntity(auctionBoard, request);

        // 변경된 게시글 저장
        AuctionBoard updatedAuctionBoard = boardRepository.save(auctionBoard);

        log.info("게시글 수정 완료. 게시글 ID: {}", boardId);

        return BoardConverter.toDetailResponse(updatedAuctionBoard);
    }

    /**
     * 게시글 삭제
     *
     * @param boardId   게시글 ID
     * @param teacherId 선생님 ID (작성자 확인용)
     */
    public void deleteBoard(Long boardId, Long teacherId) {
        // 게시글 조회
        AuctionBoard auctionBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        // 게시글 작성자 확인
        if (!auctionBoard.getTeacher().getUserId().equals(teacherId)) {
            throw new BoardHandler(ErrorStatus.BOARD_UNAUTHORIZED);
        }

        // 교사 역할인지 검증
        Teacher teacher = auctionBoard.getTeacher();
        if (teacher.getRole() != UserRole.TEACHER) {
            throw new UserHandler(ErrorStatus.USER_NOT_TEACHER);
        }

        // 게시글 삭제
        boardRepository.delete(auctionBoard);
        log.info("게시글 삭제 완료. 게시글 ID: {}", boardId);
    }
}
