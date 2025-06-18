package com.studeal.team.domain.user.application;

import com.studeal.team.domain.user.converter.UserConverter;
import com.studeal.team.domain.user.dao.StudentRepository;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.domain.user.dao.UserRepository;
import com.studeal.team.domain.user.dto.TokenDTO;
import com.studeal.team.domain.user.dto.UserRequestDTO;
import com.studeal.team.domain.user.dto.UserResponseDTO;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.UserHandler;
import com.studeal.team.global.jwt.JwtTokenProvider;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class UserCommandService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EntityManager entityManager;

    @Transactional(timeout = 2, propagation = Propagation.REQUIRED)
    public UserResponseDTO registerUser(UserRequestDTO.SignupRequest request) {
        // 배타적 락을 통해 이메일 중복 체크
        // 동일한 이메일로 다른 트랜잭션에서 동시에 사용자를 등록하려는 경우 방지
        boolean existsUser = userRepository.findByEmail(request.getEmail()).isPresent();

        if (existsUser) {
            throw new UserHandler(ErrorStatus.USER_DUPLICATE_EMAIL);
        }

        // SELECT FOR UPDATE 구문을 이용해 행 레벨 락을 걸어 동시 등록 방지
        entityManager.createNativeQuery("SELECT 1 FROM DUAL FOR UPDATE")
                .getResultList();

        // 비밀번호 암호화 추가
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        switch (request.getRole()) {
            case STUDENT -> {
                return UserConverter.toResponseDTO(
                        studentRepository.save(UserConverter.toStudentEntity(request))
                );
            }
            case TEACHER -> {
                return UserConverter.toResponseDTO(
                        teacherRepository.save(UserConverter.toTeacherEntity(request))
                );
            }
            default -> throw new UserHandler(ErrorStatus.USER_INVALID_ROLE);
        }
    }

    public TokenDTO login(UserRequestDTO.LoginRequest loginRequest) {
        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword());

        // 실제 검증 (사용자 비밀번호 체크)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증된 사용자의 ID 조회
        var userDetails = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        Long userId = userDetails.getUserId();

        // 사용자 역할 정보 가져오기
        String userRole = userDetails.getRole().getKoreanName();

        // JWT 토큰 생성 (userId 포함)
        String accessToken = jwtTokenProvider.createAccessToken(authentication, userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

        return TokenDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(3600000L)
                .role(userRole)  // 사용자 역할 정보 추가
                .build();
    }
}