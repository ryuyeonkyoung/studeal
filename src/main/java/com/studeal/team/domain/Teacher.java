package com.studeal.team.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "TEACHER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Teacher extends User {

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String bio;

    @Column(columnDefinition = "FLOAT DEFAULT 0")
    private Float rating;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Course> courses = new HashSet<>();

}