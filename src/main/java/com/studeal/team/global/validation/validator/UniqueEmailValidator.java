package com.studeal.team.global.validation.validator;

import com.studeal.team.domain.user.dao.StudentRepository;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.global.validation.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            // null 체크는 @NotBlank 또는 @NotNull이 처리하도록 함
            return true;
        }

        // 학생 중에서 이메일 중복 확인
        boolean studentExists = studentRepository.findByEmail(email).isPresent();
        if (studentExists) {
            return false;
        }

        // 강사 중에서 이메일 중복 확인
        boolean teacherExists = teacherRepository.findByEmail(email).isPresent();
        return !teacherExists;
    }
}
