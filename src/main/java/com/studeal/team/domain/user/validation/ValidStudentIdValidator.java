package com.studeal.team.domain.user.validation;

import com.studeal.team.domain.user.dao.StudentRepository;
import com.studeal.team.domain.user.domain.enums.UserRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidStudentIdValidator implements ConstraintValidator<ValidStudentId, Long> {
    private final StudentRepository studentRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) return true; // @NotNull로 체크
        return studentRepository.findById(value)
                .map(student -> student.getRole() == UserRole.STUDENT)
                .orElse(false);
    }
}

