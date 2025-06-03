package com.studeal.team.domain.enrollment.converter;

import com.studeal.team.domain.enrollment.domain.Enrollment;
import com.studeal.team.domain.enrollment.dto.EnrollmentResponseDTO;

public class EnrollmentConverter {

    public static EnrollmentResponseDTO toResponseDTO(Enrollment enrollment) {
        return EnrollmentResponseDTO.builder()
                .enrollmentId(enrollment.getEnrollmentId())
                .student(
                        EnrollmentResponseDTO.StudentInfo.builder()
                                .userId(enrollment.getStudent().getUserId())
                                .name(enrollment.getStudent().getName())
                                .build()
                )
                .negotiation(
                        EnrollmentResponseDTO.NegotiationInfo.builder()
                                .negotiationId(enrollment.getNegotiation().getNegotiationId())
                                .proposedPrice(enrollment.getNegotiation().getProposedPrice())
                                .build()
                )
                .paidAmount(enrollment.getPaidAmount())
                .enrolledAt(enrollment.getEnrolledAt())
                .status(enrollment.getStatus())
                .isActive(enrollment.getIsActive())
                .build();
    }
}
