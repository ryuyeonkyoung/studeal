package com.studeal.team.domain.user.dao;

import com.studeal.team.domain.user.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
