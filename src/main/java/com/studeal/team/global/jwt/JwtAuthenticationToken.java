package com.studeal.team.global.jwt;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * JWT 토큰 인증을 위한 Authentication 구현체입니다. 사용자 ID를 details 필드에 저장하기 위해
 * UsernamePasswordAuthenticationToken을 확장합니다.
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

  private final Long userId;

  /**
   * JwtAuthenticationToken 생성자입니다.
   *
   * @param principal   사용자 정보
   * @param credentials 자격 증명 정보
   * @param authorities 권한 목록
   * @param userId      사용자 ID
   */
  public JwtAuthenticationToken(
      Object principal,
      Object credentials,
      Collection<? extends GrantedAuthority> authorities,
      Long userId) {
    super(principal, credentials, authorities);
    this.userId = userId;
    super.setDetails(userId);
  }

  /**
   * 사용자 ID를 반환합니다.
   *
   * @return 사용자 ID
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * details 설정은 무시됩니다. userId가 이미 설정되어 있기 때문입니다.
   *
   * @param details 설정할 details 객체
   */
  @Override
  public void setDetails(Object details) {
    // 이미 userId가 details로 설정되어 있으므로 아무 작업도 하지 않음
  }
}
