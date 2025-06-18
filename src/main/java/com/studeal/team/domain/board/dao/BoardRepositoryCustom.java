package com.studeal.team.domain.board.dao;

import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.user.domain.entity.enums.MajorSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 게시판 Repository Custom 인터페이스
 * Querydsl을 활용한 복잡한 쿼리를 위한 인터페이스
 */
public interface BoardRepositoryCustom {

    /**
     * ID로 게시글 조회 (선생님 정보 포함) - Querydsl 최적화 버전
     */
    Optional<AuctionBoard> findByIdWithTeacher(Long boardId);

    /**
     * 모든 게시글 조회 (선생님 정보 포함) - Querydsl 최적화 버전
     */
    List<AuctionBoard> findAllWithTeacher();

    /**
     * 다중 조건으로 게시글 검색 (제목, 주제, 전공 과목)
     */
    Page<AuctionBoard> searchByKeyword(String keyword, MajorSubject majorSubject, Pageable pageable);
}
