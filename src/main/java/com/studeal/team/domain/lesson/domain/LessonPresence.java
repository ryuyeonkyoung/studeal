package com.studeal.team.domain.lesson.domain;

import com.studeal.team.global.common.domain.BaseEntity;
import com.studeal.team.domain.enrollment.domain.enums.AttendanceStatus;
import com.studeal.team.domain.user.domain.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "LESSON_PRESENCES")
@Getter
@Setter
@NoArgsConstructor
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
    private LocalDateTime attendanceDate;

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
        if (student != null && !student.getLessonPresences().contains(this)) {
            student.getLessonPresences().add(this);
        }
    }

    public void setLesson(Lesson lesson) {
        if (this.lesson != null) {
            this.lesson.getLessonPresences().remove(this);
        }
        this.lesson = lesson;
        if (lesson != null && !lesson.getLessonPresences().contains(this)) {
            lesson.getLessonPresences().add(this);
        }
    }

}