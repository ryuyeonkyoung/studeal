package com.studeal.team.domain.enrollment.dto;

import com.studeal.team.domain.enrollment.domain.enums.EnrollmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDTO {
    private Long enrollmentId;
    private StudentInfo student;
    private NegotiationInfo negotiation;
    private Long paidAmount;
    private LocalDateTime enrolledAt;
    private EnrollmentStatus status;
    private Boolean isActive;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentInfo {
        private Long userId;
        private String name;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NegotiationInfo {
        private Long negotiationId;
        private Long proposedPrice;
    }
}
