package com.studeal.team.domain.user.domain.validation;

import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidTeacherIdValidator implements ConstraintValidator<ValidTeacherId, Long> {
    private final TeacherRepository teacherRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) return true; // @NotNull로 체크
        return teacherRepository.findById(value)
                .map(teacher -> teacher.getRole() == UserRole.TEACHER)
                .orElse(false);
    }
}

