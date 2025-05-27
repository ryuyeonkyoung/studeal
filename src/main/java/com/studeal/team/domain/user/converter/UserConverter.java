package com.studeal.team.domain.user.converter;

import com.studeal.team.domain.user.domain.Student;
import com.studeal.team.domain.user.domain.Teacher;
import com.studeal.team.domain.user.domain.User;
import com.studeal.team.domain.user.domain.enums.UserRole;
import com.studeal.team.domain.user.dto.UserRequestDTO;
import com.studeal.team.domain.user.dto.UserResponseDTO;

public class UserConverter {

    public static Student toStudentEntity(UserRequestDTO.SignupRequest request) {
        return Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .bio(request.getBio())
                .role(UserRole.STUDENT)
                .build();
    }

    public static Teacher toTeacherEntity(UserRequestDTO.SignupRequest request) {
        return Teacher.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .bio(request.getBio())
                .major(request.getMajor())
                .role(UserRole.TEACHER)
                .rating(0.0f)
                .build();
    }

    public static UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .bio(user.getBio())
                .major(user instanceof Teacher ? ((Teacher) user).getMajor() : null)
                .build();
    }
}

