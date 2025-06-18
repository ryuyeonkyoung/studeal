package com.studeal.team.domain.enrollment.converter;

import com.studeal.team.domain.enrollment.domain.Enrollment;
import com.studeal.team.domain.enrollment.dto.EnrollmentResponseDTO;

/**
 * Enrollment 엔티티와 DTO 간의 변환을 담당하는 유틸리티 클래스 이 클래스는 인스턴스화할 수 없습니다.
 */
public final class EnrollmentConverter {

  /**
   * 유틸리티 클래스의 인스턴스화를 방지하기 위한 private 생성자
   */
  private EnrollmentConverter() {
    throw new AssertionError("유틸리티 클래스는 인스턴스화할 수 없습니다.");
  }

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
