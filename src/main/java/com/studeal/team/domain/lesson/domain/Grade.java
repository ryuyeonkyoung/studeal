package com.studeal.team.domain.lesson.domain;

import com.studeal.team.global.common.domain.BaseEntity;
import com.studeal.team.domain.user.domain.entity.Student;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GRADES")
@Getter
@Setter
@NoArgsConstructor
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
        if (student != null && !student.getGrades().contains(this)) {
            student.getGrades().add(this);
        }
    }

    public void setLesson(Lesson lesson) {
        if (this.lesson != null) {
            this.lesson.getGrades().remove(this);
        }
        this.lesson = lesson;
        if (lesson != null && !lesson.getGrades().contains(this)) {
            lesson.getGrades().add(this);
        }
    }
}