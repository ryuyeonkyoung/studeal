package com.studeal.team.domain.favorite.domain;

import com.studeal.team.global.common.domain.BaseEntity;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FAVORITES")
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Favorite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorites_seq_gen")
    @SequenceGenerator(name = "favorites_seq_gen", sequenceName = "FAVORITES_SEQ", allocationSize = 1)
    private Long favoriteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negotiation_id")
    private Negotiation negotiation;

    @Version
    private Integer version;
}