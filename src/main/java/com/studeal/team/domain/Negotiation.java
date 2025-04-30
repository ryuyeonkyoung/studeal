package com.studeal.team.domain;

import com.studeal.team.domain.common.BaseEntity;
import com.studeal.team.domain.enums.NegotiationStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "negotiation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Negotiation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long negotiationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(nullable = false)
    private Long proposedPrice;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PENDING','ACCEPTED','REJECTED')", nullable = false)
    private NegotiationStatus status;
}