package com.studeal.team.domain.board.dao;

import com.studeal.team.domain.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    /**
     * 선생님 ID로 게시글 목록 조회 (페이징)
     */
    Page<Board> findByTeacherUserId(Long teacherId, Pageable pageable);

    /**
     * 선생님 ID로 게시글 목록 조회 (모든 글)
     */
    List<Board> findByTeacherUserId(Long teacherId);

    /**
     * 제목에 검색어 포함된 게시글 목록 조회 (페이징)
     */
    Page<Board> findByTitleContaining(String keyword, Pageable pageable);

    /**
     * 수업 주제에 검색어 포함된 게시글 목록 조회 (페이징)
     * subject 필드가 아닌 specMajor 필드를 사용해야 함
     */
    Page<Board> findBySpecMajorContaining(String keyword, Pageable pageable);

    /**
     * ID로 게시글 조회 (선생님 정보 포함) - Eager 로딩 사용
     */
    @Query("SELECT b FROM Board b LEFT JOIN FETCH b.teacher WHERE b.boardId = :boardId")
    Optional<Board> findByIdWithTeacher(Long boardId);
}
