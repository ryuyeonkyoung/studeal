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
import com.studeal.team.global.error.exception.handler.StudentHandler;
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
        // 학생 확인
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentHandler(ErrorStatus.STUDENT_NOT_FOUND));

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

        // 상태 변경
        enrollment.setStatus(request.getStatus());

        // 수강 신청 저장
        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);

        return EnrollmentConverter.toResponseDTO(updatedEnrollment);
    }
}
