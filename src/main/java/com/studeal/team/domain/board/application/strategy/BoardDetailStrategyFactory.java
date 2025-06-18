package com.studeal.team.domain.board.application.strategy;

import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * 게시글 상세 조회 전략 팩토리
 * 사용자 역할에 따라 적절한 조회 전략을 제공
 */
@Component
public class BoardDetailStrategyFactory {

    private final TeacherBoardDetailStrategy teacherStrategy;
    private final StudentBoardDetailStrategy studentStrategy;
    private final DefaultBoardDetailStrategy defaultStrategy;

    private final Map<UserRole, BoardDetailStrategy> strategies;

    /**
     * 생성자를 통한 전략 맵 초기화
     * 스레드 안전성 보장 및 코드 명확성 향상 목적
     */
    public BoardDetailStrategyFactory(
            TeacherBoardDetailStrategy teacherStrategy,
            StudentBoardDetailStrategy studentStrategy,
            DefaultBoardDetailStrategy defaultStrategy) {
        this.teacherStrategy = teacherStrategy;
        this.studentStrategy = studentStrategy;
        this.defaultStrategy = defaultStrategy;

        // 생성자에서 전략 맵 초기화
        this.strategies = new EnumMap<>(UserRole.class);
        initializeStrategies();
    }

    /**
     * 해당하는 역할에 맞는 전략 반환
     *
     * @param role 사용자 역할
     * @return 역할에 맞는 게시글 상세 조회 전략
     */
    public BoardDetailStrategy getStrategy(UserRole role) {
        // 역할에 해당하는 전략이 없으면 기본 전략 반환
        return strategies.getOrDefault(role, defaultStrategy);
    }

    /**
     * 역할별 전략 초기화
     */
    private void initializeStrategies() {
        strategies.put(UserRole.TEACHER, teacherStrategy);
        strategies.put(UserRole.STUDENT, studentStrategy);
        // 필요시 추가 역할에 대한 전략 매핑 추가
    }
}
