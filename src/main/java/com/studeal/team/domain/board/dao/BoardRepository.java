package com.studeal.team.domain.board.dao;

import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.user.domain.entity.enums.MajorSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<AuctionBoard, Long>, BoardRepositoryCustom {

    /**
     * 선생님 ID로 게시글 목록 조회 (페이징)
     */
    Page<AuctionBoard> findByTeacherUserId(Long teacherId, Pageable pageable);

    /**
     * 선생님 ID로 게시글 목록 조회
     */
    List<AuctionBoard> findByTeacherUserId(Long teacherId);

    /**
     * 제목에 검색어 포함된 게시글 목록 조회 (페이징)
     */
    Page<AuctionBoard> findByTitleContaining(String keyword, Pageable pageable);

    /**
     * 수업 주제에 검색어 포함된 게시글 목록 조회 (페이징)
     * subject 필드가 아닌 specMajor 필드를 사용해야 함
     */
    Page<AuctionBoard> findBySpecMajorContaining(String keyword, Pageable pageable);

    /**
     * ID로 게시글 조회 (선생님 정보 포함) - Eager 로딩 사용
     */
    @Query("SELECT b FROM AuctionBoard b LEFT JOIN FETCH b.teacher WHERE b.boardId = :boardId")
    Optional<AuctionBoard> findByIdWithTeacher(Long boardId);

    /**
     * 모든 게시글 조회 (선생님 정보 포함) - Eager 로딩 사용
     */
    @Query("SELECT DISTINCT b FROM AuctionBoard b LEFT JOIN FETCH b.teacher ORDER BY b.createdAt DESC")
    List<AuctionBoard> findAllWithTeacher();

    /**
     * 커서 기반 페이징으로 게시글 조회 (선생님 및 파일 정보 포함)
     *
     * @param cursorId 마지막으로 조회한 게시글 ID (첫 페이지는 null)
     * @param pageable 페이징 정보
     * @return 게시글 목록
     */
    @Query("SELECT DISTINCT b FROM AuctionBoard b " +
            "LEFT JOIN FETCH b.teacher " +
            "LEFT JOIN FETCH b.files " +
            "WHERE (:cursorId IS NULL OR b.boardId < :cursorId) " +
            "ORDER BY b.boardId DESC")
    List<AuctionBoard> findBoardsByCursor(@Param("cursorId") Long cursorId, Pageable pageable);

    /**
     * 특정 ID보다 작은 게시글이 존재하는지 확인 (커서 페이징 다음 페이지 확인용)
     */
    boolean existsByBoardIdLessThan(Long boardId);

    /**
     * 오프셋 기반 페이징용 게시글 목록 조회 (선생님 및 파일 정보 포함)
     *
     * @param pageable 페이징 정보
     * @return 게시글 페이지
     */
    @Query(value = "SELECT DISTINCT b FROM AuctionBoard b " +
            "LEFT JOIN FETCH b.teacher " +
            "LEFT JOIN FETCH b.files",
            countQuery = "SELECT COUNT(b) FROM AuctionBoard b")
    Page<AuctionBoard> findBoardsByOffset(Pageable pageable);

    /**
     * 전공(Major)으로 게시글 검색 (페이징)
     */
    @Query("SELECT DISTINCT b FROM AuctionBoard b LEFT JOIN FETCH b.teacher WHERE b.major = :major")
    Page<AuctionBoard> searchByMajor(@Param("major") MajorSubject major, Pageable pageable);

    /**
     * 선생님 이름으로 게시글 검색 (페이징)
     */
    @Query("SELECT DISTINCT b FROM AuctionBoard b JOIN FETCH b.teacher t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :teacherName, '%'))")
    Page<AuctionBoard> searchByTeacherName(@Param("teacherName") String teacherName, Pageable pageable);

    /**
     * 과외 주제(SpecMajor)로 게시글 검색 (페이징)
     */
    @Query("SELECT DISTINCT b FROM AuctionBoard b LEFT JOIN FETCH b.teacher WHERE LOWER(b.specMajor) LIKE LOWER(CONCAT('%', :specMajor, '%'))")
    Page<AuctionBoard> searchBySpecMajor(@Param("specMajor") String specMajor, Pageable pageable);
}
