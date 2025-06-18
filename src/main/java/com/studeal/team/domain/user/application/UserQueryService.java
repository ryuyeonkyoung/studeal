package com.studeal.team.domain.user.application;

import com.studeal.team.domain.board.dao.BoardRepository;
import com.studeal.team.domain.board.domain.AuctionBoard;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.enrollment.dao.EnrollmentRepository;
import com.studeal.team.domain.enrollment.domain.Enrollment;
import com.studeal.team.domain.lesson.dao.LessonRepository;
import com.studeal.team.domain.negotiation.dao.NegotiationRepository;
import com.studeal.team.domain.user.dao.StudentRepository;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.domain.user.dao.UserRepository;
import com.studeal.team.domain.user.domain.entity.Student;
import com.studeal.team.domain.user.domain.entity.Teacher;
import com.studeal.team.domain.user.domain.entity.User;
import com.studeal.team.domain.user.domain.entity.enums.UserRole;
import com.studeal.team.domain.user.dto.MyPageResponseDTO;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.UserHandler;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 조회 서비스 클래스 마이페이지 등 조회 작업만을 담당합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UserQueryService {

  private final UserRepository userRepository;
  private final TeacherRepository teacherRepository;
  private final StudentRepository studentRepository;
  private final LessonRepository lessonRepository;
  private final NegotiationRepository negotiationRepository;
  private final EnrollmentRepository enrollmentRepository;
  private final BoardRepository boardRepository;

  /**
   * 사용자 마이페이지 정보 조회 사용자의 역할에 따라 다른 응답 형식으로 반환
   *
   * @param userId 사용자 ID
   * @return 역할에 맞는 마이페이지 응답 DTO
   */
  public Object getMyPageInfo(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

    if (user.getRole() == UserRole.TEACHER) {
      return getTeacherMyPageInfo(userId);
    } else if (user.getRole() == UserRole.STUDENT) {
      return getStudentMyPageInfo(userId);
    } else {
      throw new UserHandler(ErrorStatus.USER_INVALID_ROLE);
    }
  }

  /**
   * 선생님 마이페이지 정보 조회
   *
   * @param teacherId 선생님 ID
   * @return 선생님 마이페이지 응답 DTO
   */
  @PreAuthorize("hasRole('TEACHER')")
  public MyPageResponseDTO.TeacherResponse getTeacherMyPageInfo(Long teacherId) {
    Teacher teacher = teacherRepository.findById(teacherId)
        .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

    // 기본 사용자 정보
    MyPageResponseDTO.UserInfo userInfo = MyPageResponseDTO.UserInfo.builder()
        .name(teacher.getName())
        .email(teacher.getEmail())
        .role(teacher.getRole().getKoreanName())
        .build();

    // 개설한 수업 목록 조회 (enrollment 정보를 통해 가져옴)
    List<Enrollment> enrollments = enrollmentRepository.findByTeacherUserId(teacherId);
    List<MyPageResponseDTO.OpenedLessonInfo> openedLessonInfos = enrollments.stream()
        .filter(enrollment -> enrollment.getNegotiation() != null
            && enrollment.getNegotiation().getAuctionBoard() != null) // null 체크 추가
        .map(enrollment -> MyPageResponseDTO.OpenedLessonInfo.builder()
            .enrollmentId(enrollment.getEnrollmentId())
            .boardTitle(enrollment.getNegotiation().getAuctionBoard().getTitle())
            .status(enrollment.getStatus().getKoreanName())
            .studentName(enrollment.getStudent().getName())  // 등록한 학생의 이름
            .build())
        .collect(Collectors.toList());

    // 협상 중인 수업 목록 조회 (교사가 생성한 게시글 정보)
    List<AuctionBoard> boards = boardRepository.findByTeacherUserId(teacherId);
    List<MyPageResponseDTO.TeacherNegotiatingLessonInfo> negotiatingLessonInfos = boards.stream()
        .map(board -> MyPageResponseDTO.TeacherNegotiatingLessonInfo.builder()
            .boardId(board.getBoardId())  // board ID 사용
            .title(board.getTitle())       // board 제목
            .build())
        .collect(Collectors.toList());

    log.info("선생님({})의 마이페이지 정보 조회 완료: 개설 수업 {}개, 협상 중 수업 {}개",
        teacherId, openedLessonInfos.size(), negotiatingLessonInfos.size());

    return MyPageResponseDTO.TeacherResponse.builder()
        .user(userInfo)
        .openedLessons(openedLessonInfos)
        .negotiatingLessons(negotiatingLessonInfos)
        .build();
  }

  /**
   * 학생 마이페이지 정보 조회
   *
   * @param studentId 학생 ID
   * @return 학생 마이페이지 응답 DTO
   */
  @PreAuthorize("hasRole('STUDENT')")
  public MyPageResponseDTO.StudentResponse getStudentMyPageInfo(Long studentId) {
    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

    // 기본 사용자 정보
    MyPageResponseDTO.UserInfo userInfo = MyPageResponseDTO.UserInfo.builder()
        .name(student.getName())
        .email(student.getEmail())
        .role(student.getRole().getKoreanName())
        .build();

    // 수강 중인 수업 목록 조회 (enrollment 정보를 통해 가져옴)
    List<Enrollment> enrollments = enrollmentRepository.findByStudentUserId(studentId);
    List<MyPageResponseDTO.EnrolledLessonInfo> enrolledLessonInfos = enrollments.stream()
        .map(enrollment -> MyPageResponseDTO.EnrolledLessonInfo.builder()
            .enrollmentId(enrollment.getEnrollmentId())
            .boardTitle(enrollment.getNegotiation().getAuctionBoard().getTitle()) // TODO: null 오류
            .status(enrollment.getStatus().getKoreanName())
            .build())
        .collect(Collectors.toList());

    // 협상 중인 수업 목록 조회 - 각 게시글당 최고 제안 가격만 포함 (Querydsl 최적화 버전 사용)
    List<BoardResponseDTO.BoardWithHighestPriceDTO> boardsWithPrice =
        negotiationRepository.findBoardsWithHighestPriceByStudentId(studentId);
    List<MyPageResponseDTO.StudentNegotiatingLessonInfo> negotiatingLessonInfos = boardsWithPrice.stream()
        .map(boardWithPrice -> MyPageResponseDTO.StudentNegotiatingLessonInfo.builder()
            .boardId(boardWithPrice.getBoard().getBoardId())
            .title(boardWithPrice.getBoard().getTitle())
            .highestPrice(boardWithPrice.getHighestPrice()) // 최고 제안 가격 추가
            .build())
        .collect(Collectors.toList());

    log.info("학생({})의 마이페이지 정보 조회 완료: 수강 수업 {}개, 협상 중 수업 {}개",
        studentId, enrolledLessonInfos.size(), negotiatingLessonInfos.size());

    return MyPageResponseDTO.StudentResponse.builder()
        .user(userInfo)
        .enrolledLessons(enrolledLessonInfos)
        .negotiatingLessons(negotiatingLessonInfos)
        .build();
  }
}