package com.studeal.team.global.validation.validator;

import com.studeal.team.global.validation.annotation.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 강력한 비밀번호 규칙을 검증하는 Validator입니다.
 */
@Component
@RequiredArgsConstructor
public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

  /**
   * 비밀번호 패턴: 최소 8자, 특수문자, 대소문자, 숫자 포함
   */
  private static final String PASSWORD_PATTERN =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

  private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

  /**
   * 입력된 비밀번호가 강력한 비밀번호 규칙에 부합하는지 검증합니다.
   *
   * @param password 검증할 비밀번호
   * @param context  제약 검증 컨텍스트
   * @return 유효하면 true, 그렇지 않으면 false
   */
  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    if (password == null) {
      return false;
    }
    return pattern.matcher(password).matches();
  }
}
