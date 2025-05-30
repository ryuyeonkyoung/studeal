package com.studeal.team.domain.negotiation.dao;

import com.studeal.team.domain.negotiation.domain.Negotiation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegotiationRepository extends JpaRepository<Negotiation, Long> {
}