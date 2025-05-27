package com.studeal.team.domain.user.dto;

import com.studeal.team.domain.user.domain.enums.MajorSubject;
import com.studeal.team.domain.user.domain.enums.UserRole;
import com.studeal.team.global.validation.annotation.StrictEmail;
import com.studeal.team.global.validation.annotation.StrongPassword;
import com.studeal.team.global.validation.annotation.UniqueEmail;
import com.studeal.team.global.validation.annotation.ValidMajorSubject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class UserRequestDTO {

    @Getter
    @Setter
    @ValidMajorSubject
    public static class SignupRequest {
        @NotBlank
        @Size(max = 50)
        private String name;

        @StrictEmail
        @NotBlank
        @UniqueEmail
        @Size(max = 100)
        private String email;

        @NotBlank
        @StrongPassword
        @Size(max = 255)
        private String password;

        @NotNull
        private UserRole role;

        @Size(max = 1000)
        private String bio;

        // Student 필드
        private MajorSubject major;
    }
}

