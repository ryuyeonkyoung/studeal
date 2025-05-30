package com.studeal.team.domain.user.domain.validation;

import com.studeal.team.domain.user.dao.TeacherRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 선생님 ID 유효성 검증 클래스
 */
@Component
@RequiredArgsConstructor
public class ExistTeacherValidator implements ConstraintValidator<ExistTeacher, Long> {

    private final TeacherRepository teacherRepository;

    @Override
    public boolean isValid(Long teacherId, ConstraintValidatorContext context) {
        if (teacherId == null) {
            return false;
        }

        return teacherRepository.existsById(teacherId);
    }
}
