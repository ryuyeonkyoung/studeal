package com.studeal.team.global.validation.annotation;

import com.studeal.team.global.validation.validator.StrictEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 이메일 형식의 유효성을 엄격하게 검증하는 어노테이션입니다.
 */
@Documented
@Constraint(validatedBy = StrictEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrictEmail {

  String message() default "유효하지 않은 이메일 형식입니다";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
