package com.studeal.team.domain.enrollment.domain;

import com.studeal.team.domain.course.domain.Course;
import com.studeal.team.global.common.domain.BaseEntity;
import com.studeal.team.domain.user.domain.Student;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GRADE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grade extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private Course course;

    private Float score;
}