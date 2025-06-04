package com.studeal.team.domain.enrollment.domain;

import com.studeal.team.domain.enrollment.domain.enums.EnrollmentStatus;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.user.domain.entity.Student;
import com.studeal.team.global.common.converter.BooleanToYNConverter;
import com.studeal.team.global.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 수강 신청 도메인 엔티티
 * <p>
 * 프로세스 플로우: AuctionBoard (게시글) → Negotiation (협상) → 성공 → Enrollment (수강 신청, WAITING) → Lesson (수업 생성) → Enrollment 상태 변경 (CONFIRMED)
 */

/*
    * Enrollment (수강 신청) 엔티티는 학생이 협상에서 성공한 후, 수업 참여를 확정하는 상태를 나타냅니다.
    * 이 엔티티는 학생과 협상을 연결하며, 수업 참여 확정 상태(WAITING, CONFIRMED, CANCELED)를 관리합니다.
    * 또한, 수업 참여 확정 시 결제 금액(paidAmount)과 등록 시간(enrolledAt)을 기록합니다.
 */
@Entity
@Table(name = "ENROLLMENTS")
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
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