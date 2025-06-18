package com.studeal.team.domain.user.dao;

import com.studeal.team.domain.user.domain.entity.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

  Optional<Student> findByEmail(String email);
}
