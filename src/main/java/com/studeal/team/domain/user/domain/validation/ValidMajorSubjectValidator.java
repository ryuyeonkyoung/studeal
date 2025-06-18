package com.studeal.team.domain.user.domain.validation;

import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import com.studeal.team.domain.user.dto.UserRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 선생님의 전공 과목 유효성을 검증하는 Validator 클래스입니다. 선생님 역할로 가입 시 전공 과목이 필수적으로 입력되어야 합니다.
 */
@Component
@RequiredArgsConstructor
public class ValidMajorSubjectValidator
    implements ConstraintValidator<ValidMajorSubject, UserRequestDTO.SignupRequest> {

  /**
   * 회원가입 요청의 전공 과목 유효성을 검증합니다.
   *
   * @param request 회원가입 요청 DTO
   * @param context 제약 조건 컨텍스트
   * @return 유효한 경우 true, 그렇지 않으면 false
   */
  @Override
  public boolean isValid(UserRequestDTO.SignupRequest request, ConstraintValidatorContext context) {
    // 역할이 지정되지 않은 경우
    if (request.getRole() == null) {
      return true; // 다른 검증기에서 처리
    }

    // Teacher 역할일 경우 major 필드 필수
    if (request.getRole() == UserRole.TEACHER) {
      if (request.getMajor() == null) {
        // 오류 메시지 사용자 지정
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("교사 역할은 전공 과목이 필수입니다")
            .addConstraintViolation();
        return false;
      }
      return true;
    }

    return true;
  }
}
