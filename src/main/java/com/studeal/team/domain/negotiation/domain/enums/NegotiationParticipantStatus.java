package com.studeal.team.domain.negotiation.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum NegotiationParticipantStatus {
    JOINED("참여중"),
    WITHDRAWN("철회함"),
    CONFIRMED("확정");

    private final String koreanName;

    // 한글명으로 enum을 조회하기 위한 맵
    private static final Map<String, NegotiationParticipantStatus> BY_KOREAN_NAME =
        Arrays.stream(values())
            .collect(Collectors.toMap(NegotiationParticipantStatus::getKoreanName, Function.identity()));

    NegotiationParticipantStatus(String koreanName) {
        this.koreanName = koreanName;
    }

    // JSON 직렬화 시 한글명을 사용
    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }

    // JSON 역직렬화 시 한글명 또는 enum 이름으로 enum 찾기
    @JsonCreator
    public static NegotiationParticipantStatus fromKoreanName(String value) {
        // 1. 한글명으로 찾기
        NegotiationParticipantStatus status = BY_KOREAN_NAME.get(value);

        // 2. 한글명으로 찾지 못한 경우, enum 이름으로 찾기
        if (status == null) {
            try {
                status = NegotiationParticipantStatus.valueOf(value);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown participation status: " + value);
            }
        }

        return status;
    }
}