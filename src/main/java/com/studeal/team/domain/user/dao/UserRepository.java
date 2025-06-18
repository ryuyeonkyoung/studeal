package com.studeal.team.domain.user.dao;

import com.studeal.team.domain.user.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 사용자 정보에 대한 데이터 접근을 제공하는 저장소입니다.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * 이메일로 사용자를 조회합니다.
   *
   * @param email 조회할 이메일
   * @return 조회된 사용자 정보
   */
  Optional<User> findByEmail(String email);

  /**
   * 이메일의 존재 여부를 확인합니다.
   *
   * @param email 확인할 이메일
   * @return 이메일이 존재하면 true, 없으면 false
   */
  boolean existsByEmail(String email);
}
