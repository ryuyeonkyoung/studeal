package com.studeal.team.domain.enrollment.domain;

import com.studeal.team.domain.course.domain.Course;
import com.studeal.team.global.common.domain.BaseEntity;
import com.studeal.team.domain.enrollment.enums.AttendanceStatus;
import com.studeal.team.domain.user.domain.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ATTENDANCE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private Course course;

    @Column(nullable = false)
    private LocalDate attendanceDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PRESENT','ABSENT')", nullable = false)
    private AttendanceStatus status;

    @Version
    private Integer version;

}