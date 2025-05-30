package com.studeal.team.domain.user.domain.validation;

import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import com.studeal.team.domain.user.dto.UserRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidMajorSubjectValidator implements ConstraintValidator<ValidMajorSubject, UserRequestDTO.SignupRequest> {

    @Override
    public boolean isValid(UserRequestDTO.SignupRequest request, ConstraintValidatorContext context) {
        // 역할이 지정되지 않은 경우
        if (request.getRole() == null) {
            return true; // 다른 검증기에서 처리
        }

        // Teacher 역할일 경우 major 필드 필수
        if (request.getRole() == UserRole.TEACHER) {
            if (request.getMajor() == null) {
                // 오류 메시지 사용자 지정
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("교사 역할은 전공 과목이 필수입니다")
                       .addConstraintViolation();
                return false;
            }
            return true;
        }
        // Student 역할일 경우 major 필드가 null이어야 함 (교사 전용 필드)
        else if (request.getRole() == UserRole.STUDENT && request.getMajor() != null) {
            // 오류 메시지 사용자 지정
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("학생 역할에서는 전공 과목을 지정할 수 없습니다")
                   .addConstraintViolation();
            return false;
        }

        return true;
    }
}
