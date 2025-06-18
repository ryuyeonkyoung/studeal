package com.studeal.team.domain.enrollment.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 수업 출결 상태를 나타내는 열거형입니다.
 */
public enum AttendanceStatus {
  PRESENT("출석"),
  ABSENT("결석");

  /*
   * 한글명으로 enum을 조회하기 위한 맵
   */
  private static final Map<String, AttendanceStatus> BY_KOREAN_NAME =
      Arrays.stream(values())
          .collect(
              Collectors.toMap(
                  AttendanceStatus::getKoreanName,
                  Function.identity()
              )
          );

  private final String koreanName;

  AttendanceStatus(String koreanName) {
    this.koreanName = koreanName;
  }

  /**
   * JSON 역직렬화 시 한글명 또는 enum 이름으로 AttendanceStatus를 조회합니다.
   *
   * @param value 한글명 또는 enum 이름
   * @return 조회된 AttendanceStatus
   * @throws IllegalArgumentException 유효하지 않은 값의 경우
   */
  @JsonCreator
  public static AttendanceStatus fromKoreanName(String value) {
    AttendanceStatus status = BY_KOREAN_NAME.get(value);
    if (status == null) {
      try {
        status = valueOf(value);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("알 수 없는 출결 상태입니다: " + value);
      }
    }
    return status;
  }

  /**
   * JSON 직렬화 시 한글명을 반환합니다.
   *
   * @return 한글명
   */
  @JsonValue
  public String getKoreanName() {
    return koreanName;
  }
}