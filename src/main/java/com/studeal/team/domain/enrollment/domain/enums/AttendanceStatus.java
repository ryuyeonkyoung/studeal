package com.studeal.team.domain.enrollment.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum AttendanceStatus {
  PRESENT("출석"),
  ABSENT("결석");

  // 한글명으로 enum을 조회하기 위한 맵
  private static final Map<String, AttendanceStatus> BY_KOREAN_NAME =
      Arrays.stream(values())
          .collect(Collectors.toMap(AttendanceStatus::getKoreanName, Function.identity()));
  private final String koreanName;

  AttendanceStatus(String koreanName) {
    this.koreanName = koreanName;
  }

  // JSON 역직렬화 시 한글명 또는 enum 이름으로 enum 찾기
  @JsonCreator
  public static AttendanceStatus fromKoreanName(String value) {
    // 1. 한글명으로 찾기
    AttendanceStatus status = BY_KOREAN_NAME.get(value);

    // 2. 한글명으로 찾지 못한 경우, enum 이름으로 찾기
    if (status == null) {
      try {
        status = AttendanceStatus.valueOf(value);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Unknown attendance status: " + value);
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