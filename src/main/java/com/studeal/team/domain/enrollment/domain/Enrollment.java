package com.studeal.team.domain.enrollment.domain;

import com.studeal.team.domain.enrollment.domain.enums.EnrollmentStatus;
import com.studeal.team.domain.lesson.domain.Lesson;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.user.domain.Student;
import com.studeal.team.global.common.converter.BooleanToYNConverter;
import com.studeal.team.global.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Negotiation (협상) → 성공 → Enrollment (수강 신청, WAITING) → 충분한 학생 모집 → Lesson (수업 생성) → Enrollment 상태 변경 (CONFIRMED) (협상) → 성공 → Lesson (수업 생성) → Enrollment (수강 등록)
 */
@Entity
@Table(name = "ENROLLMENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enrollments_seq_gen")
    @SequenceGenerator(name = "enrollments_seq_gen", sequenceName = "ENROLLMENTS_SEQ", allocationSize = 1)
    private Long enrollmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negotiation_id") // Negotiation중 일부만 Enrollment로 연결되므로 외래키가 Enrollment에 위치
    private Negotiation negotiation; // Negotiation (협상 성공 시)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(nullable = false)
    private Long paidAmount;

    @Column(nullable = false)
    private LocalDateTime enrolledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(nullable = false)
    private Boolean isActive = false;

    @Version
    private Integer version;
}