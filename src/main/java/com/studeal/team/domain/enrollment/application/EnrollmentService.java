package com.studeal.team.domain.enrollment.application;

import com.studeal.team.domain.enrollment.converter.EnrollmentConverter;
import com.studeal.team.domain.enrollment.dao.EnrollmentRepository;
import com.studeal.team.domain.enrollment.domain.Enrollment;
import com.studeal.team.domain.enrollment.domain.enums.EnrollmentStatus;
import com.studeal.team.domain.enrollment.dto.EnrollmentRequestDTO;
import com.studeal.team.domain.enrollment.dto.EnrollmentResponseDTO;
import com.studeal.team.domain.negotiation.dao.NegotiationRepository;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.negotiation.domain.enums.NegotiationStatus;
import com.studeal.team.domain.user.dao.StudentRepository;
import com.studeal.team.domain.user.domain.entity.Student;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.EnrollmentHandler;
import com.studeal.team.global.error.exception.handler.NegotiationHandler;
import com.studeal.team.global.error.exception.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final NegotiationRepository negotiationRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public EnrollmentResponseDTO createEnrollment(EnrollmentRequestDTO.CreateRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        // 협상(negotiation) 확인
        Negotiation negotiation = negotiationRepository.findById(request.getNegotiationId())
                .orElseThrow(() -> new NegotiationHandler(ErrorStatus.NEGOTIATION_NOT_FOUND));

        // 협상 상태 확인 (협상이 성공 상태인지)
        if (negotiation.getStatus() != NegotiationStatus.ACCEPTED) {
            throw new EnrollmentHandler(ErrorStatus.ENROLLMENT_INVALID_NEGOTIATION_STATUS);
        }

        // 이미 진행중인 수업 확정 데이터가 있는지 확인
        if (negotiation.getEnrollment() != null) {
            throw new EnrollmentHandler(ErrorStatus.ENROLLMENT_ALREADY_EXISTS);
        }

        // 수강 신청 생성
        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .negotiation(negotiation)
                .paidAmount(negotiation.getProposedPrice())
                .enrolledAt(LocalDateTime.now())
                .status(EnrollmentStatus.WAITING)
                .isActive(true)
                .build();

        // 수강 신청 저장
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // 협상과 수강 신청 연결
        negotiation.setEnrollment(savedEnrollment);
        negotiationRepository.save(negotiation);

        return EnrollmentConverter.toResponseDTO(savedEnrollment);
    }

    @Transactional
    public EnrollmentResponseDTO updateStatus(Long enrollmentId, EnrollmentRequestDTO.StatusUpdateRequest request) {
        // 수강 신청 확인
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new EnrollmentHandler(ErrorStatus.ENROLLMENT_NOT_FOUND));

        // 비활성화된 수강 신청인지 확인
        if (!enrollment.getIsActive()) {
            throw new EnrollmentHandler(ErrorStatus.ENROLLMENT_INVALID_REQUEST, "비활성화된 수강 신청은 상태를 변경할 수 없습니다.");
        }

        // 상태 전환 유효성 검증
        validateStatusTransition(enrollment.getStatus(), request.getStatus());

        // 상태 변경
        enrollment.setStatus(request.getStatus());

        // 수강 신청 저장
        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);

        return EnrollmentConverter.toResponseDTO(updatedEnrollment);
    }


    // TODO: 상태 전환 검증 로직을 별도의 유틸리티 클래스로 분리할 수 있음
    // TODO: 모든 상태 변환 API에 대해 상태변환 흐름 검즌 적용하기

    /**
     * 상태 전환이 유효한지 검증
     *
     * @param currentStatus 현재 상태
     * @param newStatus     새로운 상태
     */
    private void validateStatusTransition(EnrollmentStatus currentStatus, EnrollmentStatus newStatus) {
        if (currentStatus == newStatus) {
            return; // 같은 상태로 변경하는 것은 허용
        }

        switch (currentStatus) {
            case WAITING:
                // WAITING에서는 CONFIRMED, CANCELED로 변경 가능
                if (newStatus != EnrollmentStatus.CONFIRMED && newStatus != EnrollmentStatus.CANCELED) {
                    throw new EnrollmentHandler(ErrorStatus.ENROLLMENT_INVALID_REQUEST,
                            "대기 중인 수강 신청은 확정 또는 취소 상태로만 변경할 수 있습니다.");
                }
                break;
            case CONFIRMED:
                // CONFIRMED에서는 CANCELED로만 변경 가능
                if (newStatus != EnrollmentStatus.CANCELED) {
                    throw new EnrollmentHandler(ErrorStatus.ENROLLMENT_INVALID_REQUEST,
                            "확정된 수강 신청은 취소 상태로만 변경할 수 있습니다.");
                }
                break;
            case CANCELED:
                // CANCELED 상태는 다른 상태로 변경 불가
                throw new EnrollmentHandler(ErrorStatus.ENROLLMENT_INVALID_REQUEST,
                        "취소된 수강 신청은 상태를 변경할 수 없습니다.");

            default:
                throw new EnrollmentHandler(ErrorStatus.ENROLLMENT_INVALID_REQUEST,
                        "유효하지 않은 수강 신청 상태입니다.");
        }
    }
}
