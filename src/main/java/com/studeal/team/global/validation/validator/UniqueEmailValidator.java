package com.studeal.team.global.validation.validator;

import com.studeal.team.domain.user.dao.StudentRepository;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.global.validation.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 이메일의 중복 여부를 검증하는 Validator입니다.
 */
@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  /*
   * 학생 저장소
   */
  private final StudentRepository studentRepository;

  /*
   * 강사 저장소
   */
  private final TeacherRepository teacherRepository;

  /**
   * 입력된 이메일이 학생 또는 강사에 이미 등록되어 있는지 검사합니다.
   *
   * @param email   입력된 이메일
   * @param context 검증 컨텍스트
   * @return 중복이 없으면 true, 중복이 있으면 false
   */
  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (email == null || email.isEmpty()) {
      // null 체크는 @NotBlank 또는 @NotNull이 처리하도록 함
      return true;
    }

    // 학생 중에서 이메일 중복 확인
    boolean studentExists = studentRepository.findByEmail(email).isPresent();
    if (studentExists) {
      return false;
    }

    // 강사 중에서 이메일 중복 확인
    boolean teacherExists = teacherRepository.findByEmail(email).isPresent();
    return !teacherExists;
  }
}
