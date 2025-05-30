// NegotiationConverter.java
package com.studeal.team.domain.negotiation.converter;

import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.negotiation.api.dto.NegotiationRequestDTO;
import com.studeal.team.domain.negotiation.api.dto.NegotiationResponseDTO;

public class NegotiationConverter {

    public static Negotiation toEntity(NegotiationRequestDTO.CreateRequest request) {
        return Negotiation.builder()
                .proposedPrice(request.getProposedPrice())
                .build();
    }

    public static NegotiationResponseDTO toResponseDTO(Negotiation negotiation) {
        return NegotiationResponseDTO.builder()
                .negotiationId(negotiation.getNegotiationId())
                .studentId(negotiation.getStudent().getUserId())
                .teacherId(negotiation.getTeacher().getUserId())
                .proposedPrice(negotiation.getProposedPrice())
                .status(negotiation.getStatus())
                .build();
    }
}