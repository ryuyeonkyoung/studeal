package com.studeal.team.global.common.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 모든 엔티티의 기본이 되는 추상 클래스입니다. 생성 시간과 수정 시간을 자동으로 관리합니다.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

  /**
   * 엔티티 생성 시간
   */
  @CreatedDate
  private LocalDateTime createdAt;

  /**
   * 엔티티 수정 시간
   */
  @LastModifiedDate
  private LocalDateTime updatedAt;
}