package com.studeal.team.domain;

import com.studeal.team.domain.enums.UserRole;
import com.studeal.team.domain.enums.NegotiationParticipantStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "negotiation_participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NegotiationParticipant {
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