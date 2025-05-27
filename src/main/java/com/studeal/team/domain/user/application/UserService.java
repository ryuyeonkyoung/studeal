package com.studeal.team.domain.user.application;

import com.studeal.team.domain.user.converter.UserConverter;
import com.studeal.team.domain.user.dao.StudentRepository;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.domain.user.dto.UserRequestDTO;
import com.studeal.team.domain.user.dto.UserResponseDTO;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Transactional(timeout = 5)
    public UserResponseDTO registerUser(UserRequestDTO.SignupRequest request) {
        switch (request.getRole()) {
            case STUDENT -> {
                return UserConverter.toResponseDTO(
                        studentRepository.save(UserConverter.toStudentEntity(request))
                );
            }
            case TEACHER -> {
                return UserConverter.toResponseDTO(
                        teacherRepository.save(UserConverter.toTeacherEntity(request))
                );
            }
            default -> throw new UserHandler(ErrorStatus.USER_INVALID_ROLE);
        }
    }
}
