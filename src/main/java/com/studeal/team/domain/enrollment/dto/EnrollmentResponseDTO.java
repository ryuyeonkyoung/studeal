package com.studeal.team.domain.enrollment.dto;

import com.studeal.team.domain.enrollment.domain.enums.EnrollmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "수업 참여 확정 응답 DTO")
public class EnrollmentResponseDTO {

  @Schema(description = "수업 참여 확정 ID", example = "1")
  private Long enrollmentId;

  @Schema(description = "학생 정보")
  private StudentInfo student;

  @Schema(description = "협상 정보")
  private NegotiationInfo negotiation;

  @Schema(description = "지불 금액", example = "50000")
  private Long paidAmount;

  @Schema(description = "등록 시간", example = "2023-01-01T12:00:00")
  private LocalDateTime enrolledAt;

  @Schema(description = "상태", example = "확정",
      allowableValues = {"대기", "확정", "취소"})
  private EnrollmentStatus status;

  @Schema(description = "활성화 여부", example = "true")
  private Boolean isActive;

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(description = "학생 정보")
  public static class StudentInfo {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "이름", example = "홍길동")
    private String name;
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(description = "협상 정보")
  public static class NegotiationInfo {

    @Schema(description = "협상 ID", example = "1")
    private Long negotiationId;

    @Schema(description = "제안 가격", example = "50000")
    private Long proposedPrice;
  }
}
