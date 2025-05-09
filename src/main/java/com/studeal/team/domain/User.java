package com.studeal.team.domain;

import com.studeal.team.domain.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 50, nullable = false)
    private String name;

    @Email
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('STUDENT','TEACHER')", nullable = false)
    private UserRole role;

    @Version
    private Integer version;
}