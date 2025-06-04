package com.studeal.team.domain.negotiation.domain;

import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.enrollment.domain.Enrollment;
import com.studeal.team.domain.negotiation.domain.enums.NegotiationStatus;
import com.studeal.team.domain.user.domain.entity.Student;
import com.studeal.team.domain.user.domain.entity.Teacher;
import com.studeal.team.global.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/*
 * Negotiation (협상) → 성공 → Enrollment (수강 신청, WAITING) → 충분한 학생 모집 → Lesson (수업 생성) → Enrollment 상태 변경 (CONFIRMED)
 */
@Entity
@Table(name = "NEGOTIATIONS")
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Negotiation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "negotiations_seq_gen")
    @SequenceGenerator(name = "negotiations_seq_gen", sequenceName = "NEGOTIATIONS_SEQ", allocationSize = 1)
    private Long negotiationId;

    @OneToOne(mappedBy = "negotiation", cascade = CascadeType.ALL)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private AuctionBoard auctionBoard;

    @Column(nullable = false)
    private Long proposedPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NegotiationStatus status;

    @Version
    private Integer version;

}