package com.studeal.team.domain.user.domain.entity;

import com.studeal.team.domain.lesson.domain.Lesson;
import com.studeal.team.domain.user.domain.entity.enums.MajorSubject;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue(value = "TEACHER")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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