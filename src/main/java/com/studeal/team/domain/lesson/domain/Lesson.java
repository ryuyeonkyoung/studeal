package com.studeal.team.domain.lesson.domain;

import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.global.common.domain.BaseEntity;
import com.studeal.team.domain.enrollment.domain.Enrollment;
import com.studeal.team.domain.user.domain.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "LESSONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessons_seq_gen")
    @SequenceGenerator(name = "lessons_seq_gen", sequenceName = "LESSONS_SEQ", allocationSize = 1)
    private Long lessonId;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LessonPresence> lessonPresences = new HashSet<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Grade> grades = new HashSet<>();

    @OneToMany
    private Set<LessonImage> lessonImages = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negotiation_id")
    private Negotiation negotiation;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Long price;

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
        enrollment.setLesson(this);
    }

    public void removeEnrollment(Enrollment enrollment) {
        this.enrollments.remove(enrollment);
        enrollment.setLesson(null);
    }

    public void addLessonPresence(LessonPresence lessonPresence) {
        this.lessonPresences.add(lessonPresence);
        lessonPresence.setLesson(this);
    }

    public void removeLessonPresence(LessonPresence lessonPresence) {
        this.lessonPresences.remove(lessonPresence);
        lessonPresence.setLesson(null);
    }

    public void addGrade(Grade grade) {
        this.grades.add(grade);
        grade.setLesson(this);
    }

    public void removeGrade(Grade grade) {
        this.grades.remove(grade);
        grade.setLesson(null);
    }

    public void addLessonImage(LessonImage lessonImage) {
        this.lessonImages.add(lessonImage);
        lessonImage.setLesson(this);
    }

    public void removeLessonImage(LessonImage lessonImage) {
        this.lessonImages.remove(lessonImage);
        lessonImage.setLesson(null);
    }
}