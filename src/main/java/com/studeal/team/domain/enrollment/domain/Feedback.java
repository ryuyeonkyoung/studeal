package com.studeal.team.domain.enrollment.domain;

import com.studeal.team.domain.course.domain.Course;
import com.studeal.team.global.common.domain.BaseEntity;
import com.studeal.team.domain.user.domain.Student;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FEEDBACK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private Course course;

    @Column(length = 1000)
    private String feedbackText;
}