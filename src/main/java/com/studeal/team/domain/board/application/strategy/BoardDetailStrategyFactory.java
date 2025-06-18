package com.studeal.team.domain.board.application.strategy;

import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * 게시글 상세 조회 전략 팩토리
 * 사용자 역할에 따라 적절한 조회 전략을 제공
 */
@Component
@RequiredArgsConstructor
public class BoardDetailStrategyFactory {

    private final TeacherBoardDetailStrategy teacherStrategy;
    private final StudentBoardDetailStrategy studentStrategy;
    private final DefaultBoardDetailStrategy defaultStrategy;

    private final Map<UserRole, BoardDetailStrategy> strategies = new EnumMap<>(UserRole.class);

    /**
     * 해당하는 역할에 맞는 전략 반환
     *
     * @param role 사용자 역할
     * @return 역할에 맞는 게시글 상세 조회 전략
     */
    public BoardDetailStrategy getStrategy(UserRole role) {
        // 최초 호출 시 전략 맵 초기화
        if (strategies.isEmpty()) {
            initializeStrategies();
        }

        // 역할에 해당하는 전략이 없으면 기본 전략 반환
        return strategies.getOrDefault(role, defaultStrategy);
    }

    /**
     * 역할별 전략 초기화
     */
    private void initializeStrategies() {
        strategies.put(UserRole.TEACHER, teacherStrategy);
        strategies.put(UserRole.STUDENT, studentStrategy);
    }
}
