package com.studeal.team.domain.user.domain.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum MajorSubject {
    MATH("수학"),
    SCIENCE("과학"),
    ENGLISH("영어"),
    KOREAN("국어"),
    HISTORY("역사"),
    CODING("코딩");

    private final String koreanName;

    // 한글명으로 enum을 조회하기 위한 맵
    private static final Map<String, MajorSubject> BY_KOREAN_NAME =
        Arrays.stream(values())
            .collect(Collectors.toMap(MajorSubject::getKoreanName, Function.identity()));

    MajorSubject(String koreanName) {
        this.koreanName = koreanName;
    }

    // JSON 직렬화 시 한글명을 사용
    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }

    // JSON 역직렬화 시 한글명 또는 enum 이름으로 enum 찾기
    @JsonCreator
    public static MajorSubject fromKoreanName(String value) {
        // 1. 한글명으로 찾기
        MajorSubject subject = BY_KOREAN_NAME.get(value);

        // 2. 한글명으로 찾지 못한 경우, enum 이름으로 찾기
        if (subject == null) {
            try {
                subject = MajorSubject.valueOf(value);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown subject: " + value);
            }
        }

        return subject;
    }
}