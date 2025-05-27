package com.studeal.team.domain.user.dto;

import com.studeal.team.domain.user.domain.enums.MajorSubject;
import com.studeal.team.domain.user.domain.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDTO {
    private Long userId;
    private String name;
    private String email;
    private UserRole role;
    private String bio;
    private MajorSubject major;
}

