package com.studeal.team.domain.enrollment.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum EnrollmentStatus {
  WAITING("대기"),
  CONFIRMED("확정"),
  CANCELED("취소");

  // 한글명으로 enum을 조회하기 위한 맵
  private static final Map<String, EnrollmentStatus> BY_KOREAN_NAME =
      Arrays.stream(values())
          .collect(Collectors.toMap(EnrollmentStatus::getKoreanName, Function.identity()));
  private final String koreanName;

  EnrollmentStatus(String koreanName) {
    this.koreanName = koreanName;
  }

  // JSON 역직렬화 시 한글명 또는 enum 이름으로 enum 찾기
  @JsonCreator
  public static EnrollmentStatus fromKoreanName(String value) {
    // 1. 한글명으로 찾기
    EnrollmentStatus status = BY_KOREAN_NAME.get(value);

    // 2. 한글명으로 찾지 못한 경우, enum 이름으로 찾기
    if (status == null) {
      try {
        status = EnrollmentStatus.valueOf(value);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Unknown status: " + value);
      }
    }

    return status;
  }

  // JSON 직렬화 시 한글명을 사용
  @JsonValue
  public String getKoreanName() {
    return koreanName;
  }
}