package com.studeal.team.domain.user.domain.entity;

import com.studeal.team.domain.lesson.domain.Grade;
import com.studeal.team.domain.lesson.domain.LessonPresence;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
@DiscriminatorValue(value = "STUDENT")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Student extends User {

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

