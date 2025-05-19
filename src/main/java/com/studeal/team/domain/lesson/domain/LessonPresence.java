package com.studeal.team.domain.lesson.domain;

import com.studeal.team.global.common.domain.BaseEntity;
import com.studeal.team.domain.enrollment.enums.AttendanceStatus;
import com.studeal.team.domain.user.domain.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private LocalDate attendanceDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    @Version
    private Integer version;

}