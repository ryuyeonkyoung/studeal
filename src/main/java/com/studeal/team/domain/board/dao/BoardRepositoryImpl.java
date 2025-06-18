package com.studeal.team.domain.board.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.domain.QAuctionBoard;
import com.studeal.team.domain.user.domain.entity.QTeacher;
import com.studeal.team.domain.user.domain.entity.enums.MajorSubject;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

/**
 * BoardRepositoryCustom 구현체 Querydsl을 활용한 복잡한 쿼리 구현
 */
public class BoardRepositoryImpl extends QuerydslRepositorySupport implements
    BoardRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public BoardRepositoryImpl(JPAQueryFactory queryFactory) {
    super(AuctionBoard.class);
    this.queryFactory = queryFactory;
  }

  /**
   * ID로 게시글 조회 (선생님 정보 포함) - 최적화
   */
  @Override
  public Optional<AuctionBoard> findByIdWithTeacher(Long boardId) {
    QAuctionBoard board = QAuctionBoard.auctionBoard;
    QTeacher teacher = QTeacher.teacher;

    AuctionBoard result = queryFactory
        .selectFrom(board)
        .leftJoin(board.teacher, teacher).fetchJoin()
        .where(board.boardId.eq(boardId))
        .fetchOne();

    return Optional.ofNullable(result);
  }

  /**
   * 모든 게시글 조회 (선생님 정보 포함) - 최적화
   */
  @Override
  public List<AuctionBoard> findAllWithTeacher() {
    QAuctionBoard board = QAuctionBoard.auctionBoard;
    QTeacher teacher = QTeacher.teacher;

    return queryFactory
        .selectFrom(board)
        .leftJoin(board.teacher, teacher).fetchJoin()
        .orderBy(board.createdAt.desc())
        .distinct()
        .fetch();
  }

  /**
   * 다중 조건으로 게시글 검색 (제목, 주제, 전공 과목)
   */
  @Override
  public Page<AuctionBoard> searchByKeyword(String keyword, MajorSubject majorSubject,
      Pageable pageable) {
    QAuctionBoard board = QAuctionBoard.auctionBoard;
    QTeacher teacher = QTeacher.teacher;

    // 동적 조건 생성
    BooleanBuilder builder = new BooleanBuilder();

    // 키워드 검색 (제목 또는 구체적인 주제)
    if (StringUtils.hasText(keyword)) {
      builder.and(board.title.containsIgnoreCase(keyword)
          .or(board.specMajor.containsIgnoreCase(keyword))
          .or(board.content.containsIgnoreCase(keyword)));
    }

    // 전공 과목 필터링
    if (majorSubject != null) {
      builder.and(board.major.eq(majorSubject));
    }

    // 총 개수 조회 쿼리
    long total = queryFactory
        .selectFrom(board)
        .where(builder)
        .fetchCount();

    // 페이징 적용 데이터 조회 쿼리
    List<AuctionBoard> content = queryFactory
        .selectFrom(board)
        .leftJoin(board.teacher, teacher).fetchJoin()
        .where(builder)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(board.createdAt.desc()) // 기본 정렬: 최신순
        .fetch();

    return new PageImpl<>(content, pageable, total);
  }
}
