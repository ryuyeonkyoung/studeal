package com.studeal.team.domain.enrollment.dao;

import com.studeal.team.domain.enrollment.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    /**
     * 선생님 ID로 수업 등록 정보 조회
     */
    List<Enrollment> findByTeacherUserId(Long teacherId);

    /**
     * 학생 ID로 수업 등록 정보 조회
     */
    List<Enrollment> findByStudentUserId(Long studentId);
}
