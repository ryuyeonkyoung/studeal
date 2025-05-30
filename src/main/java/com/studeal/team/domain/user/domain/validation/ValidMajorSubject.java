package com.studeal.team.domain.user.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidMajorSubjectValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMajorSubject {
    String message() default "교사 역할일 경우 유효한 전공 과목이 필요합니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
