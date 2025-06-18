package com.studeal.team.domain.lesson.application;

import com.studeal.team.domain.enrollment.domain.Enrollment;
import com.studeal.team.domain.enrollment.domain.enums.AttendanceStatus;
import com.studeal.team.domain.enrollment.domain.enums.EnrollmentStatus;
import com.studeal.team.domain.lesson.converter.LessonConverter;
import com.studeal.team.domain.lesson.dao.LessonRepository;
import com.studeal.team.domain.lesson.domain.Lesson;
import com.studeal.team.domain.lesson.domain.LessonPresence;
import com.studeal.team.domain.lesson.dto.LessonRequestDTO;
import com.studeal.team.domain.negotiation.dao.NegotiationRepository;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.user.dao.StudentRepository;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.domain.user.domain.entity.Student;
import com.studeal.team.domain.user.domain.entity.Teacher;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.NegotiationHandler;
import com.studeal.team.global.error.exception.handler.UserHandler;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class LessonCommandService {

  private final LessonRepository lessonRepository;
  private final TeacherRepository teacherRepository;
  private final StudentRepository studentRepository;
  private final NegotiationRepository negotiationRepository;
  private final EntityManager entityManager;

  public Lesson createLesson(LessonRequestDTO.CreateRequest request) {
    // 배타적 락을 통해 협상 정보 조회
    Negotiation negotiation = entityManager.createQuery(
            "SELECT n FROM Negotiation n WHERE n.negotiationId = :id",
            Negotiation.class)
        .setParameter("id", request.getNegotiationId())
        .setLockMode(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
        .getSingleResult();

    if (negotiation == null) {
      throw new NegotiationHandler(ErrorStatus.NEGOTIATION_NOT_FOUND);
    }

    Lesson newLesson = LessonConverter.toEntity(request);

    // 선생님 연관관계 매핑
    Teacher teacher = teacherRepository.findById(request.getTeacherId())
        .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    newLesson.setTeacher(teacher);

    // 협상 정보 연관관계 매핑 - 이미 배타적 락이 걸린 객체 사용
    newLesson.setNegotiation(negotiation);

    // 현재 시간 설정
    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDate currentDate = currentDateTime.toLocalDate();

    // 학생 조회
    Student student = studentRepository.findById(request.getStudentId())
        .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

    // 수강신청 연관관계 매핑 (1:1 관계)
    Enrollment enrollment = Enrollment.builder()
        .student(student)
        .negotiation(negotiation)
        .paidAmount(request.getPrice())
        .status(EnrollmentStatus.WAITING)
        .isActive(true)
        .enrolledAt(currentDateTime)
        .build();

    // 출석 정보 연관관계 매핑
    LessonPresence presence = LessonPresence.builder()
        .student(student)
        .lesson(newLesson)
        .status(AttendanceStatus.ABSENT)
        .attendanceDate(currentDate)
        .build();

    newLesson.addLessonPresence(presence);

    return lessonRepository.save(newLesson);
  }
}
