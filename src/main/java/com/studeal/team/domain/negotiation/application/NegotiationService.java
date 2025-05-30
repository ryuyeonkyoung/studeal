// NegotiationService.java
package com.studeal.team.domain.negotiation.application;

import com.studeal.team.domain.negotiation.converter.NegotiationConverter;
import com.studeal.team.domain.negotiation.dao.NegotiationRepository;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.negotiation.api.dto.NegotiationRequestDTO;
import com.studeal.team.domain.negotiation.api.dto.NegotiationResponseDTO;
import com.studeal.team.domain.negotiation.domain.enums.NegotiationStatus;
import com.studeal.team.domain.user.dao.StudentRepository;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.domain.user.domain.Student;
import com.studeal.team.domain.user.domain.Teacher;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.TeacherHandler;
import com.studeal.team.global.error.exception.handler.StudentHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NegotiationService {

    private final NegotiationRepository negotiationRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Transactional
    public NegotiationResponseDTO initiateNegotiation(NegotiationRequestDTO.CreateRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentHandler(ErrorStatus.USER_NOT_FOUND));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new TeacherHandler(ErrorStatus.USER_NOT_FOUND));

        Negotiation negotiation = NegotiationConverter.toEntity(request);
        negotiation.setStudent(student);
        negotiation.setTeacher(teacher);
        negotiation.setStatus(NegotiationStatus.PENDING);

        return NegotiationConverter.toResponseDTO(negotiationRepository.save(negotiation));
    }

    @Transactional
    public void deleteNegotiation(Long negotiationId) {
        Negotiation negotiation = negotiationRepository.findById(negotiationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 협상입니다."));
        negotiationRepository.delete(negotiation);
    }
}
