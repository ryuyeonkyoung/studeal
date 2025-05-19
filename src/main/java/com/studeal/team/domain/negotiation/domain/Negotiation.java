package com.studeal.team.domain.negotiation.domain;

import com.studeal.team.global.common.domain.BaseEntity;
import com.studeal.team.domain.negotiation.enums.NegotiationStatus;
import com.studeal.team.domain.user.domain.Student;
import com.studeal.team.domain.user.domain.Teacher;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "NEGOTIATION")
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

    @Version
    private Integer version;

}