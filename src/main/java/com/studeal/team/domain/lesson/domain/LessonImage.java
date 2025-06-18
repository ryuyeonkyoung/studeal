package com.studeal.team.domain.lesson.domain;

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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LESSON_FILES")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LessonImage extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_files_seq_gen")
  @SequenceGenerator(name = "lesson_files_seq_gen", sequenceName = "LESSON_FILES_SEQ", allocationSize = 1)
  private Long fileId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id")
  private Lesson lesson;

  @Column(length = 255, nullable = false)
  private String fileName;

  @Column(length = 500, nullable = false)
  private String filePath;

  @Column(length = 50)
  private String fileType;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Boolean isThumbnail = false;

  public void setLesson(Lesson lesson) {
    if (this.lesson != null) {
      this.lesson.getLessonImages().remove(this);
    }
    this.lesson = lesson;
    if (lesson != null) {
      lesson.getLessonImages().add(this);
    }
  }
}