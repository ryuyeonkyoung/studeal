// NegotiationService.java
package com.studeal.team.domain.negotiation.application;

import com.studeal.team.domain.board.dao.BoardRepository;
import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.enrollment.application.EnrollmentService;
import com.studeal.team.domain.enrollment.dto.EnrollmentRequestDTO;
import com.studeal.team.domain.negotiation.converter.NegotiationConverter;
import com.studeal.team.domain.negotiation.dao.NegotiationRepository;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.negotiation.domain.enums.NegotiationStatus;
import com.studeal.team.domain.negotiation.dto.NegotiationRequestDTO;
import com.studeal.team.domain.negotiation.dto.NegotiationResponseDTO;
import com.studeal.team.domain.user.dao.StudentRepository;
import com.studeal.team.domain.user.domain.entity.Student;
import com.studeal.team.domain.user.domain.entity.Teacher;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.BoardHandler;
import com.studeal.team.global.error.exception.handler.NegotiationHandler;
import com.studeal.team.global.error.exception.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NegotiationService {

    private final NegotiationRepository negotiationRepository;
    private final StudentRepository studentRepository;
    private final BoardRepository boardRepository;
    private final EnrollmentService enrollmentService;

    @PreAuthorize("hasRole('STUDENT')")
    @Transactional
    public NegotiationResponseDTO initiateNegotiation(NegotiationRequestDTO.CreateRequest request, Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        AuctionBoard board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        Teacher teacher = board.getTeacher(); // 게시판에서 선생님 정보 가져오기

        Negotiation negotiation = NegotiationConverter.toEntity(request);
        negotiation.setStudent(student);
        negotiation.setTeacher(teacher);
        negotiation.setAuctionBoard(board);
        negotiation.setStatus(NegotiationStatus.PENDING);

        // 저장 전에 board의 negotiations 컬렉션에 추가
        board.getNegotiations().add(negotiation);

        return NegotiationConverter.toResponseDTO(negotiationRepository.save(negotiation));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @Transactional
    public NegotiationResponseDTO updateNegotiationStatus(Long negotiationId, NegotiationStatus newStatus) {
        Negotiation negotiation = negotiationRepository.findById(negotiationId)
                .orElseThrow(() -> new NegotiationHandler(ErrorStatus.NEGOTIATION_NOT_FOUND));

        log.info("Negotiation status updated: {} -> {}", negotiation.getNegotiationId(), newStatus);

        // 상태 먼저 변경
        negotiation.setStatus(newStatus);
        Negotiation savedNegotiation = negotiationRepository.save(negotiation);


        // 상태가 ACCEPTED로 변경된 후에 Enrollment 생성
        if (newStatus == NegotiationStatus.ACCEPTED) {
            createEnrollmentForAcceptedNegotiation(savedNegotiation);
        }

        return NegotiationConverter.toResponseDTO(savedNegotiation);
    }

    // TODO: 이벤트 핸들러 적용 검토

    /**
     * 수락된 협상에 대해 자동으로 Enrollment를 생성하는 메서드
     */
    private void createEnrollmentForAcceptedNegotiation(Negotiation negotiation) {
        // 이미 Enrollment가 생성되어 있는 경우 스킵
        if (negotiation.getEnrollment() != null) {
            log.error("Enrollment already exists for negotiation ID: {}", negotiation.getNegotiationId());
            return;
        }

        EnrollmentRequestDTO.CreateRequest enrollmentRequest = EnrollmentRequestDTO.CreateRequest.builder()
                .studentId(negotiation.getStudent().getUserId())
                .negotiationId(negotiation.getNegotiationId())
                .paidAmount(negotiation.getProposedPrice())  // 협상된 가격으로 설정
                .build();

        enrollmentService.createEnrollment(enrollmentRequest);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @Transactional
    public void deleteNegotiation(Long negotiationId) {
        Negotiation negotiation = negotiationRepository.findById(negotiationId)
                .orElseThrow(() -> new NegotiationHandler(ErrorStatus.NEGOTIATION_NOT_FOUND));
        negotiationRepository.delete(negotiation);
    }
}