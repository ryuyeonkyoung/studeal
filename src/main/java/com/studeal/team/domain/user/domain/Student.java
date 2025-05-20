package com.studeal.team.domain.user.domain;

import com.studeal.team.domain.lesson.domain.LessonPresence;
import com.studeal.team.domain.lesson.domain.Grade;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "STUDENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Student extends User {

    @Column(length = 100)
    private String major;

    private Integer gradeLevel;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LessonPresence> lessonPresences = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Grade> grades = new HashSet<>();

    public void addLessonPresence(LessonPresence lessonPresence) {
        this.lessonPresences.add(lessonPresence);
        lessonPresence.setStudent(this);
    }

    public void removeLessonPresence(LessonPresence lessonPresence) {
        this.lessonPresences.remove(lessonPresence);
        lessonPresence.setStudent(null);
    }

    public void addGrade(Grade grade) {
        this.grades.add(grade);
        grade.setStudent(this);
    }

    public void removeGrade(Grade grade) {
        this.grades.remove(grade);
        grade.setStudent(null);
    }
}