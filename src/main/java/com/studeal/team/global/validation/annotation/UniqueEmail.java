package com.studeal.team.global.validation.annotation;

import com.studeal.team.global.validation.validator.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 이메일 중복 여부를 검증하는 어노테이션입니다.
 */
@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

  /**
   * 검증 실패 시 반환될 메시지
   */
  String message() default "이미 등록된 이메일입니다";

  /**
   * 그룹 지정용
   */
  Class<?>[] groups() default {};

  /**
   * 페이로드 지정용
   */
  Class<? extends Payload>[] payload() default {};
}
