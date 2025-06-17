package com.studeal.team.global.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * JWT 토큰 인증을 위한 Authentication 구현체
 * 사용자 ID를 details 필드에 저장하기 위해 UsernamePasswordAuthenticationToken을 확장
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final Long userId;

    public JwtAuthenticationToken(Object principal, Object credentials,
                                  Collection<? extends GrantedAuthority> authorities,
                                  Long userId) {
        super(principal, credentials, authorities);
        this.userId = userId;
        super.setDetails(userId);
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public void setDetails(Object details) {
        // 이미 userId가 details로 설정되어 있으므로 아무 작업도 하지 않음
        // 혹은 예외를 던질 수도 있음
    }
}
