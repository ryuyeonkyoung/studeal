package com.studeal.team.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-validity}") long accessTokenValidity,
            @Value("${jwt.refresh-token-validity}") long refreshTokenValidity) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    // 액세스 토큰 생성
    public String createAccessToken(Authentication authentication, Long userId) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 권한 정보에서 역할(ROLE_) 추출
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .findFirst()
                .map(auth -> auth.replace("ROLE_", ""))
                .orElse("UNKNOWN");

        long now = (new Date()).getTime();
        Date validity = new Date(now + accessTokenValidity);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("userId", userId)
                .claim("auth", authorities)
                .claim("role", role)  // role 클레임 추가
                .claim("type", "access")
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(new Date(now))
                .setExpiration(validity)
                .compact();
    }

    // 리프레시 토큰 생성
    public String createRefreshToken(Authentication authentication) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + refreshTokenValidity);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("type", "refresh")
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(new Date(now))
                .setExpiration(validity)
                .compact();
    }

    // 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    // 토큰에서 사용자 ID 추출
    public String extractUserIdAsString(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // userId 클레임에서 사용자 ID 추출
            if (claims.get("userId") != null) {
                return String.valueOf(claims.get("userId"));
            }

            return null;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Cannot extract user ID from JWT token: {}", e.getMessage());
            return null;
        }
    }

    // 토큰에서 사용자 역할 추출
    public String extractRole(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // role 클레임에서 사용자 역할 추출
            if (claims.get("role") != null) {
                return String.valueOf(claims.get("role"));
            }

            return null;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Cannot extract role from JWT token: {}", e.getMessage());
            return null;
        }
    }
}
