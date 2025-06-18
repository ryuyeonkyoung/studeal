package com.studeal.team.global.validation.annotation;

import com.studeal.team.global.validation.validator.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {

  String message() default "비밀번호는 최소 8자 이상, 특수문자, 대소문자, 숫자를 포함해야 합니다";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
