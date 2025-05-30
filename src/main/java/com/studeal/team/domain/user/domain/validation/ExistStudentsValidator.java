// ExistStudentsValidator.java
package com.studeal.team.domain.user.domain.validation;

import com.studeal.team.domain.user.dao.StudentRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExistStudentsValidator implements ConstraintValidator<ExistStudents, List<Long>> {

    private final StudentRepository studentRepository;

    @Override
    public boolean isValid(List<Long> studentIds, ConstraintValidatorContext context) {
        if (studentIds == null || studentIds.isEmpty()) {
            return false;
        }

        return studentIds.stream()
                .allMatch(studentRepository::existsById);
    }
}

