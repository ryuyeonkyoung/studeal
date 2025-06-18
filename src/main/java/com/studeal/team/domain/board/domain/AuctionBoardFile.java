package com.studeal.team.domain.board.domain;

import com.studeal.team.global.common.converter.BooleanToYNConverter;
import com.studeal.team.global.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 경매 게시판의 첨부 파일 정보를 관리하는 엔티티입니다.
 */
@Entity
@Table(name = "BOARD_FILES")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AuctionBoardFile extends BaseEntity {

  /**
   * 파일 ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_files_seq_gen")
  @SequenceGenerator(
      name = "board_files_seq_gen",
      sequenceName = "BOARD_FILES_SEQ",
      allocationSize = 1)
  private Long fileId;

  /**
   * 연결된 경매 게시판
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private AuctionBoard auctionBoard;

  /**
   * 파일 이름
   */
  @Column(length = 255, nullable = false)
  private String fileName;

  /**
   * 파일 저장 경로
   */
  @Column(length = 500, nullable = false)
  private String filePath;

  /**
   * 파일 타입
   */
  @Column(length = 50)
  private String fileType;

  /**
   * 파일 업로드 시간
   */
  @Column
  private LocalDateTime uploadedAt;

  /**
   * 썸네일 여부
   */
  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Boolean isThumbnail = false;
}