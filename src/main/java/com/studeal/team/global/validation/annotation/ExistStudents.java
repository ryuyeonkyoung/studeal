// ExistStudents.java
package com.studeal.team.global.validation.annotation;

import com.studeal.team.global.validation.validator.ExistStudentsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistStudentsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistStudents {
    String message() default "존재하지 않는 학생이 포함되어 있습니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}