package com.studeal.team.global.validation.validator;

import com.studeal.team.domain.user.domain.enums.MajorSubject;
import com.studeal.team.domain.user.domain.enums.UserRole;
import com.studeal.team.domain.user.dto.UserRequestDTO;
import com.studeal.team.global.validation.annotation.ValidMajorSubject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidMajorSubjectValidator implements ConstraintValidator<ValidMajorSubject, UserRequestDTO.SignupRequest> {

    @Override
    public boolean isValid(UserRequestDTO.SignupRequest request, ConstraintValidatorContext context) {
        // Teacher 역할일 경우에만 major 필드가 필수
        if (request.getRole() == UserRole.TEACHER) {
            return request.getMajor() != null;
        }
        // 다른 역할(Student)일 경우 major 필드는 무시
        return true;
    }
}
