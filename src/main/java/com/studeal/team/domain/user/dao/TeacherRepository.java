package com.studeal.team.domain.user.dao;

import com.studeal.team.domain.user.domain.entity.Teacher;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

  Optional<Teacher> findByEmail(String email);
}
