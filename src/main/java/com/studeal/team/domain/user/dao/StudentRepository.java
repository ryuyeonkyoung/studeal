package com.studeal.team.domain.user.dao;

import com.studeal.team.domain.user.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
