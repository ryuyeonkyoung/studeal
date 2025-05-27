package com.studeal.team.global.validation.validator;

import com.studeal.team.global.validation.annotation.StrictEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class StrictEmailValidator implements ConstraintValidator<StrictEmail, String> {

    // RFC 5322 기반 이메일 정규표현식
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true; // null 검사는 @NotNull이나 @NotBlank가 담당
        }

        // 기본 형식 검증
        if (!pattern.matcher(email).matches()) {
            return false;
        }

        // 추가 검증: 최소 길이
        if (email.length() < 5) { // a@b.c의 최소 길이
            return false;
        }

        // 도메인 부분 검증
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }

        String domainPart = parts[1];
        // 도메인에 최소 하나의 점이 있어야 함
        if (!domainPart.contains(".")) {
            return false;
        }

        // 최상위 도메인 검증(TLD는 최소 2자 이상)
        String[] domainParts = domainPart.split("\\.");
        String tld = domainParts[domainParts.length - 1];
        if (tld.length() < 2) {
            return false;
        }

        return true;
    }
}
