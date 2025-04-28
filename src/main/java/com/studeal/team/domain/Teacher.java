package com.studeal.team.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher extends User {
    @Id
    private Long teacherId;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Version
    @Column(columnDefinition = "FLOAT DEFAULT 0")
    private Float rating;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Course> courses = new HashSet<>();
}