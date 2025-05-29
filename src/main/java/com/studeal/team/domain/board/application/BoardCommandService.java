package com.studeal.team.domain.board.application;

import com.studeal.team.domain.board.converter.BoardConverter;
import com.studeal.team.domain.board.dao.BoardRepository;
import com.studeal.team.domain.board.domain.Board;
import com.studeal.team.domain.board.dto.BoardRequestDTO;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.domain.user.domain.Teacher;
import com.studeal.team.domain.user.domain.enums.UserRole;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.BoardHandler;
import com.studeal.team.global.error.exception.handler.TeacherHandler;
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
     * @param teacherId 선생님 ID
     * @param request 게시글 요청 DTO
     * @return 생성된 게시글 응답 DTO
     */
    public BoardResponseDTO.DetailResponse createBoard(Long teacherId, BoardRequestDTO.CreateRequest request) {
        // 선생님 정보 조회
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherHandler(ErrorStatus.TEACHER_NOT_FOUND));

        // 학생인 경우 게시글 작성 불가 검증
        if (teacher.getRole() == UserRole.STUDENT) {
            throw new BoardHandler(ErrorStatus.BOARD_STUDENT_FORBIDDEN);
        }

        // 게시글 생성 및 저장
        Board board = BoardConverter.toEntity(request, teacher);
        Board savedBoard = boardRepository.save(board);

        log.info("게시글 생성 완료. 게시글 ID: {}", savedBoard.getBoardId());

        return BoardConverter.toDetailResponse(savedBoard);
    }

    /**
     * 게시글 삭제
     * @param boardId 게시글 ID
     * @param teacherId 선생님 ID (작성자 확인용)
     */
    public void deleteBoard(Long boardId, Long teacherId) {
        // 게시글 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        // 게시글 작성자 확인
        if (!board.getTeacher().getUserId().equals(teacherId)) {
            throw new BoardHandler(ErrorStatus.BOARD_UNAUTHORIZED);
        }

        // 게시글 삭제 (연관된 파일도 함께 삭제됨 - cascade 설정)
        boardRepository.delete(board);

        log.info("게시글 삭제 완료. 게시글 ID: {}", boardId);
    }
}
