package com.studeal.team.domain.user.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 해당 ID의 선생님이 존재하는지 검증하는 어노테이션
 */
@Documented
@Constraint(validatedBy = ExistTeacherValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistTeacher {

  String message() default "존재하지 않는 선생님입니다";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
