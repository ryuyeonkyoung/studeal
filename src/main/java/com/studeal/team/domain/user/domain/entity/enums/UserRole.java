package com.studeal.team.domain.user.domain.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 사용자의 역할을 정의하는 열거형입니다.
 */
public enum UserRole {
  /**
   * 학생 역할입니다.
   */
  STUDENT("학생"),
  /**
   * 선생님 역할입니다.
   */
  TEACHER("선생님");

  /**
   * 한글명으로 enum을 조회하기 위한 맵입니다.
   */
  private static final Map<String, UserRole> BY_KOREAN_NAME =
      Arrays.stream(values())
          .collect(Collectors.toMap(UserRole::getKoreanName, Function.identity()));

  /**
   * 역할의 한글 이름입니다.
   */
  private final String koreanName;

  /**
   * UserRole 생성자입니다.
   *
   * @param koreanName 역할의 한글 이름
   */
  UserRole(String koreanName) {
    this.koreanName = koreanName;
  }

  /**
   * 한글명 또는 enum 이름으로 UserRole을 찾습니다.
   *
   * @param value 한글명 또는 enum 이름
   * @return 찾은 UserRole
   * @throws IllegalArgumentException 해당하는 역할을 찾을 수 없는 경우
   */
  @JsonCreator
  public static UserRole fromKoreanName(String value) {
    UserRole role = BY_KOREAN_NAME.get(value);

    if (role == null) {
      try {
        role = UserRole.valueOf(value);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("알 수 없는 역할입니다: " + value);
      }
    }

    return role;
  }

  /**
   * 역할의 한글 이름을 반환합니다.
   *
   * @return 역할의 한글 이름
   */
  @JsonValue
  public String getKoreanName() {
    return koreanName;
  }
}