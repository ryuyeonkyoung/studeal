package com.studeal.team.domain.lesson.domain;

import com.studeal.team.domain.enrollment.domain.enums.AttendanceStatus;
import com.studeal.team.domain.user.domain.entity.Student;
import com.studeal.team.global.common.domain.BaseEntity;
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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LESSON_PRESENCES")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LessonPresence extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_presences_seq_gen")
  @SequenceGenerator(name = "lesson_presences_seq_gen", sequenceName = "LESSON_PRESENCES_SEQ", allocationSize = 1)
  private Long attendanceId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id")
  private Student student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id")
  private Lesson lesson;

  @Column(nullable = false)
  private LocalDate attendanceDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AttendanceStatus status;

  @Version
  private Integer version;

  public void setStudent(Student student) {
    if (this.student != null) {
      this.student.getLessonPresences().remove(this);
    }
    this.student = student;
    if (student != null) {
      student.getLessonPresences().add(this);
    }
  }

  public void setLesson(Lesson lesson) {
    if (this.lesson != null) {
      this.lesson.getLessonPresences().remove(this);
    }
    this.lesson = lesson;
    if (lesson != null) {
      lesson.getLessonPresences().add(this);
    }
  }

}

