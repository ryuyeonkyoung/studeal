package com.studeal.team.domain.user.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValidTeacherIdValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ValidTeacherId {
    String message() default "해당 ID는 TEACHER 역할이 아닙니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

