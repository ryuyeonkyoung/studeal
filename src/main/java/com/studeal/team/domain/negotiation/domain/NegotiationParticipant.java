package com.studeal.team.domain.negotiation.domain;

import com.studeal.team.global.common.domain.BaseEntity;
import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import com.studeal.team.domain.negotiation.domain.enums.NegotiationParticipantStatus;
import com.studeal.team.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NEGOTIATION_PARTICIPANTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NegotiationParticipant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "negotiation_participants_seq_gen")
    @SequenceGenerator(name = "negotiation_participants_seq_gen", sequenceName = "NEGOTIATION_PARTICIPANTS_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negotiation_id")
    private Negotiation negotiation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NegotiationParticipantStatus status;
}