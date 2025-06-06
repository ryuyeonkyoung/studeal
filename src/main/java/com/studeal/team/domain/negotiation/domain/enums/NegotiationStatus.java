package com.studeal.team.domain.negotiation.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum NegotiationStatus {
    PENDING("대기"),
    ACCEPTED("수락됨"),
    REJECTED("거절됨");

    private final String koreanName;

    // 한글명으로 enum을 조회하기 위한 맵
    private static final Map<String, NegotiationStatus> BY_KOREAN_NAME =
        Arrays.stream(values())
            .collect(Collectors.toMap(NegotiationStatus::getKoreanName, Function.identity()));

    NegotiationStatus(String koreanName) {
        this.koreanName = koreanName;
    }

    // JSON 직렬화 시 한글명을 사용
    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }

    // JSON 역직렬화 시 한글명 또는 enum 이름으로 enum 찾기
    @JsonCreator
    public static NegotiationStatus fromKoreanName(String value) {
        // 1. 한글명으로 찾기
        NegotiationStatus status = BY_KOREAN_NAME.get(value);

        // 2. 한글명으로 찾지 못한 경우, enum 이름으로 찾기
        if (status == null) {
            try {
                status = NegotiationStatus.valueOf(value);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown negotiation status: " + value);
            }
        }

        return status;
    }
}