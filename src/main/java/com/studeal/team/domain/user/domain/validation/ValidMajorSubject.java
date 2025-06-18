package com.studeal.team.domain.user.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 선생님의 전공 과목 유효성을 검증하는 어노테이션입니다. 선생님 역할일 경우 반드시 전공 과목이 지정되어야 합니다.
 */
@Documented
@Constraint(validatedBy = ValidMajorSubjectValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMajorSubject {

  /**
   * 유효성 검증 실패 시 반환할 메시지입니다.
   *
   * @return 오류 메시지
   */
  String message() default "교사 역할일 경우 유효한 전공 과목이 필요합니다";

  /**
   * 검증 그룹을 지정합니다.
   *
   * @return 검증 그룹 클래스 배열
   */
  Class<?>[] groups() default {};

  /**
   * 페이로드를 지정합니다.
   *
   * @return 페이로드 클래스 배열
   */
  Class<? extends Payload>[] payload() default {};
}
