package com.studeal.team.domain.user.domain.entity;

import com.studeal.team.domain.lesson.domain.Lesson;
import com.studeal.team.domain.user.domain.entity.enums.MajorSubject;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "TEACHER")
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Teacher extends User {

    @Enumerated(EnumType.STRING)
    private MajorSubject major;

    @Column
    private Float rating;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lesson> lessons = new HashSet<>();

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.setTeacher(this);
    }

    public void removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.setTeacher(null);
    }

}