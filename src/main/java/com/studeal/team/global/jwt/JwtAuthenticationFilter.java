package com.studeal.team.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    String jwt = resolveToken(request);

    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
      Authentication authentication = tokenProvider.getAuthentication(jwt);

      // 사용자 ID를 요청 속성으로 추가
      String userIdStr = tokenProvider.extractUserIdAsString(jwt);
      if (StringUtils.hasText(userIdStr)) {
        try {
          // userId 클레임에서 추출한 값을 Long으로 변환하여 설정
          Long userId = Long.parseLong(userIdStr);
          request.setAttribute("userId", userId);

          // Authentication 객체의 details 필드에 userId 설정 (SecurityUtils에서 사용)
          WebAuthenticationDetailsSource detailsSource = new WebAuthenticationDetailsSource();
          authentication = new JwtAuthenticationToken(
              authentication.getPrincipal(),
              authentication.getCredentials(),
              authentication.getAuthorities(),
              userId
          );

          log.debug("Request에 userId={} 속성 추가", userId);
        } catch (NumberFormatException e) {
          log.warn("JWT 토큰에서 userId를 추출하지 못했습니다.: {}", userIdStr);
        }
      }

      // 사용자 역할을 요청 속성으로 추가
      String role = tokenProvider.extractRole(jwt);
      if (StringUtils.hasText(role)) {
        request.setAttribute("userRole", role);
        log.debug("Request에 userRole={} 속성 추가", role);
      } else {
        log.warn("JWT 토큰에서 role을 추출하지 못했습니다.");
      }

      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.debug("Security Context에 '{}' 인증 정보를 저장했습니다", authentication.getName());
    } else {
      log.debug("유효한 JWT 토큰이 없습니다");
    }

    filterChain.doFilter(request, response);
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
