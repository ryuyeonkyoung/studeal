// NegotiationResponseDTO.java
package com.studeal.team.domain.negotiation.api.dto;

import com.studeal.team.domain.negotiation.domain.enums.NegotiationStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NegotiationResponseDTO {
    private Long negotiationId;
    private Long studentId;
    private Long teacherId;
    private Long proposedPrice;
    private NegotiationStatus status;
}