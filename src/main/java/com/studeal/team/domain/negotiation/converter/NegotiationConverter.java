// NegotiationConverter.java
package com.studeal.team.domain.negotiation.converter;

import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.negotiation.dto.NegotiationRequestDTO;
import com.studeal.team.domain.negotiation.dto.NegotiationResponseDTO;

/**
 * Negotiation 엔티티와 DTO 간의 변환을 담당하는 유틸리티 클래스
 * 이 클래스는 인스턴스화할 수 없습니다.
 */
public final class NegotiationConverter {

    /**
     * 유틸리티 클래스의 인스턴스화를 방지하기 위한 private 생성자
     */
    private NegotiationConverter() {
        throw new AssertionError("유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    public static Negotiation toEntity(NegotiationRequestDTO.CreateRequest request) {
        return Negotiation.builder()
                .proposedPrice(request.getProposedPrice())
                .build();
    }

    public static NegotiationResponseDTO toResponseDTO(Negotiation negotiation) {
        return NegotiationResponseDTO.builder()
                .negotiationId(negotiation.getNegotiationId())
                .boardId(negotiation.getAuctionBoard().getBoardId())
                .proposedPrice(negotiation.getProposedPrice())
                .status(negotiation.getStatus())
                .build();
    }
}
