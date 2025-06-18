package com.studeal.team.global.validation.validator;

import com.studeal.team.global.validation.annotation.StrictEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 이메일 형식을 RFC 5322 기반으로 엄격하게 검증하는 Validator입니다.
 */
@Component
@RequiredArgsConstructor
public class StrictEmailValidator implements ConstraintValidator<StrictEmail, String> {

  /**
   * RFC 5322 기반 이메일 정규표현식
   */
  private static final String EMAIL_PATTERN =
      "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

  private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

  /**
   * 입력된 이메일이 유효한지 검사합니다.
   *
   * @param email   입력된 이메일
   * @param context 검증 컨텍스트
   * @return 유효하면 true, 그렇지 않으면 false
   */
  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (email == null || email.isEmpty()) {
      return true;
    }

    if (!pattern.matcher(email).matches()) {
      return false;
    }

    if (email.length() < 5) {
      return false;
    }

    String[] parts = email.split("@");
    if (parts.length != 2) {
      return false;
    }

    String domainPart = parts[1];
    if (!domainPart.contains(".")) {
      return false;
    }

    String[] domainParts = domainPart.split("\\.");
    String tld = domainParts[domainParts.length - 1];
    return tld.length() >= 2;
  }
}
