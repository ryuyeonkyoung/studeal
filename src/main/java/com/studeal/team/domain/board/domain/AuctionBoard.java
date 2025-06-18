package com.studeal.team.domain.board.domain;

import com.studeal.team.domain.lesson.domain.Lesson;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.user.domain.entity.Teacher;
import com.studeal.team.domain.user.domain.entity.enums.MajorSubject;
import com.studeal.team.global.common.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 경매 게시판 엔티티 클래스입니다. 선생님이 작성한 수업 관련 게시글로서 학생과의 협상을 포함합니다.
 */
@Entity
@Table(name = "BOARDS")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AuctionBoard extends BaseEntity {

  /**
   * 게시판 ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boards_seq_gen")
  @SequenceGenerator(name = "boards_seq_gen", sequenceName = "BOARDS_SEQ", allocationSize = 1)
  private Long boardId;

  /**
   * 게시글 제목
   */
  @Column(length = 200, nullable = false)
  private String title;

  /**
   * 게시글 내용
   */
  @Column(length = 2000)
  private String content;

  /**
   * 연결된 수업 정보
   */
  @OneToOne(fetch = FetchType.LAZY)
  private Lesson lesson; // 추가

  /**
   * 게시글 작성 선생님
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;  // 선생님만 작성 가능

  /**
   * 협상 정보 목록
   */
  @OneToMany(mappedBy = "auctionBoard", cascade = CascadeType.ALL)
  private Set<Negotiation> negotiations = new HashSet<>(); // 협상 정보

  /**
   * 예상 수업 가격
   */
  @Column(nullable = false)
  private Long expectedPrice;  // 예상 수업 가격

  /**
   * 과외 주요 과목
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "major", nullable = false)
  private MajorSubject major;  // 과외 과목

  /**
   * 구체적인 과외 주제
   */
  @Column(length = 200)
  private String specMajor;  // 구체적인 과외 주제

  /**
   * 게시판 첨부 파일 목록
   */
  @OneToMany(mappedBy = "auctionBoard", cascade = CascadeType.ALL)
  private List<AuctionBoardFile> files = new ArrayList<>();

  /**
   * 낙관적 락을 위한 버전
   */
  @Version
  private Integer version;
}
