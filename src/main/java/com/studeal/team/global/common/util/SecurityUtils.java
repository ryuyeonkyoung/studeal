package com.studeal.team.global.common.util;

import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Spring Security 컨텍스트에서 현재 인증된 사용자 정보를 조회하는 유틸리티 클래스
 */
@Slf4j
@Component
public class SecurityUtils {

  private SecurityUtils() {
    // 유틸리티 클래스는 인스턴스화 방지
  }

  /**
   * 현재 인증된 사용자의 ID를 반환합니다. 인증 컨텍스트에서 직접 가져오므로 컨트롤러에서 매번 토큰을 추출할 필요가 없습니다.
   *
   * @return 현재 사용자 ID 또는 예외 발생
   */
  public static Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      log.error("인증된 사용자 정보를 찾을 수 없습니다");
      throw new IllegalStateException("인증된 사용자 정보를 찾을 수 없습니다");
    }

    // Principal 객체는 UserDetails를 구현한 클래스이거나, 아이디 문자열일 수 있음
    Object principal = authentication.getPrincipal();

    // 원래 서블릿 속성에 저장해 둔 userId를 반환
    // 이 방법은 JwtAuthenticationFilter에서 설정한 userId를 활용함
    Object userId = authentication.getDetails();
    if (userId instanceof Long) {
      return (Long) userId;
    }

    // 직접 JWT 클레임에서 추출한 userId를 반환하도록 구현 필요
    // 현재는 getName()이 일반적으로 사용자 아이디(이메일 등)를 반환
    // 실제 구현에서는 JwtTokenProvider에서 authenticaton.getDetails()에 userId를 설정해야 함
    log.error("사용자 ID를 찾을 수 없습니다. 인증 객체: {}", authentication);
    throw new IllegalStateException("사용자 ID를 찾을 수 없습니다");
  }

  /**
   * 현재 사용자가 STUDENT 역할을 가지고 있는지 확인합니다.
   *
   * @return STUDENT 역할인 경우 true, 아니면 false
   */
  public static boolean isStudent() {
    return hasRole(UserRole.STUDENT.name());
  }

  /**
   * 현재 사용자가 특정 역할을 가지고 있는지 확인합니다.
   *
   * @param role 확인할 역할
   * @return 해당 역할을 가진 경우 true, 아니면 false
   */
  private static boolean hasRole(String role) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return false;
    }

    // "ROLE_" 접두사가 있는지 확인
    String roleToCheck = role.startsWith("ROLE_") ? role : "ROLE_" + role;

    for (GrantedAuthority authority : authentication.getAuthorities()) {
      if (authority.getAuthority().equals(roleToCheck)) {
        return true;
      }
    }

    return false;
  }

  /**
   * 현재 사용자가 TEACHER 역할을 가지고 있는지 확인합니다.
   *
   * @return TEACHER 역할인 경우 true, 아니면 false
   */
  public static boolean isTeacher() {
    return hasRole(UserRole.TEACHER.name());
  }
}
