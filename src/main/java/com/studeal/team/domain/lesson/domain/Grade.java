package com.studeal.team.domain.lesson.domain;

import com.studeal.team.domain.user.domain.entity.Student;
import com.studeal.team.global.common.domain.BaseEntity;
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
@Table(name = "GRADES")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Grade extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grades_seq_gen")
  @SequenceGenerator(name = "grades_seq_gen", sequenceName = "GRADES_SEQ", allocationSize = 1)
  private Long gradeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id")
  private Student student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id")
  private Lesson lesson;

  private Float score;

  public void setStudent(Student student) {
    if (this.student != null) {
      this.student.getGrades().remove(this);
    }
    this.student = student;
    if (student != null) {
      student.getGrades().add(this);
    }
  }

  public void setLesson(Lesson lesson) {
    if (this.lesson != null) {
      this.lesson.getGrades().remove(this);
    }
    this.lesson = lesson;
    if (lesson != null) {
      lesson.getGrades().add(this);
    }
  }
}