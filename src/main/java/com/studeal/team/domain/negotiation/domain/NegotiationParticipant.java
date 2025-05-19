package com.studeal.team.domain.negotiation.domain;

import com.studeal.team.global.common.domain.BaseEntity;
import com.studeal.team.domain.user.enums.UserRole;
import com.studeal.team.domain.negotiation.enums.NegotiationParticipantStatus;
import com.studeal.team.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NEGOTIATION_PARTICIPANT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NegotiationParticipant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negotiation_id")
    private Negotiation negotiation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('STUDENT','TEACHER')", nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('JOINED','WITHDRAWN','CONFIRMED')", nullable = false)
    private NegotiationParticipantStatus status;
}