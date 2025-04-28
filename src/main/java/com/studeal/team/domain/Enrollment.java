package com.studeal.team.domain;

import com.studeal.team.domain.common.BaseEntity;
import com.studeal.team.domain.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private Course course;

    @Version
    @Column(nullable = false)
    private Long paidAmount;

    @Column(nullable = false)
    private LocalDateTime enrolledAt;

    @Version
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('WAITING','CONFIRMED')", nullable = false)
    private EnrollmentStatus status;

    @Column(nullable = false)
    private Boolean isActive;
}