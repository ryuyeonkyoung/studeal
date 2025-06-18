package com.studeal.team.global.validation.annotation;

import com.studeal.team.global.validation.validator.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 강력한 비밀번호 규칙을 검증하는 어노테이션입니다.
 */
@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {

  /**
   * 검증 실패 시 반환될 메시지
   */
  String message() default "비밀번호는 최소 8자 이상, 특수문자, 대소문자, 숫자를 포함해야 합니다";

  /**
   * 그룹 지정용
   */
  Class<?>[] groups() default {};

  /**
   * 페이로드 지정용
   */
  Class<? extends Payload>[] payload() default {};
}
