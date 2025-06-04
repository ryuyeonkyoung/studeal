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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(timeout = 5)
    public UserResponseDTO registerUser(UserRequestDTO.SignupRequest request) {
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

    @Transactional
    public TokenDTO login(UserRequestDTO.LoginRequest loginRequest) {
        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword());

        // 실제 검증 (사용자 비밀번호 체크)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(authentication);
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

        return TokenDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(3600000L)
                .build();
    }
}
