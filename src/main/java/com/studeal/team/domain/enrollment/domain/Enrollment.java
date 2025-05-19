package com.studeal.team.domain.enrollment.domain;

import com.studeal.team.domain.course.domain.Course;
import com.studeal.team.global.common.domain.BaseEntity;
import com.studeal.team.domain.enrollment.enums.EnrollmentStatus;
import com.studeal.team.global.common.converter.BooleanToYNConverter;
import com.studeal.team.domain.user.domain.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ENROLLMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private Course course;

    @Column(nullable = false)
    private Long paidAmount;

    @Column(nullable = false)
    private LocalDateTime enrolledAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('WAITING','CONFIRMED')", nullable = false)
    private EnrollmentStatus status;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(columnDefinition = "CHAR(1)", nullable = false)
    private Boolean isActive = false;

    @Version
    private Integer version;
}