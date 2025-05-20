package com.studeal.team.domain.lesson.applicationn;

import com.studeal.team.domain.enrollment.domain.enums.AttendanceStatus;
import com.studeal.team.domain.enrollment.domain.enums.EnrollmentStatus;
import com.studeal.team.domain.lesson.converter.LessonConverter;
import com.studeal.team.domain.lesson.dto.LessonRequestDTO;
import com.studeal.team.domain.lesson.domain.Lesson;
import com.studeal.team.domain.lesson.domain.LessonPresence;
import com.studeal.team.domain.enrollment.domain.Enrollment;
import com.studeal.team.domain.lesson.dao.LessonRepository;
import com.studeal.team.domain.user.dao.TeacherRepository;
import com.studeal.team.domain.user.dao.StudentRepository;
import com.studeal.team.domain.user.domain.Teacher;
import com.studeal.team.domain.user.domain.Student;
import com.studeal.team.global.error.code.status.ErrorStatus;
import com.studeal.team.global.error.exception.handler.TeacherHandler;
import com.studeal.team.global.error.exception.handler.StudentHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public Lesson createLesson(LessonRequestDTO.CreateRequest request) {
        Lesson newLesson = LessonConverter.toEntity(request);

        // 선생님 연관관계 매핑
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new TeacherHandler(ErrorStatus.TEACHER_NOT_FOUND));
        newLesson.setTeacher(teacher);

        // 수강신청 연관관계 매핑
        List<Enrollment> enrollments = request.getStudentIds().stream()
                .map(studentId -> {
                    Student student = studentRepository.findById(studentId)
                            .orElseThrow(() -> new StudentHandler(ErrorStatus.STUDENT_NOT_FOUND));

                    return Enrollment.builder()
                            .student(student)
                            .lesson(newLesson)
                            .paidAmount(request.getPrice())
                            .status(EnrollmentStatus.WAITING)
                            .isActive(true)
                            .build();
                }).toList();

        // 출석 정보 연관관계 매핑
        List<LessonPresence> presences = request.getStudentIds().stream()
                .map(studentId -> {
                    Student student = studentRepository.findById(studentId)
                            .orElseThrow(() -> new StudentHandler(ErrorStatus.STUDENT_NOT_FOUND));

                    return LessonPresence.builder()
                            .student(student)
                            .lesson(newLesson)
                            .status(AttendanceStatus.ABSENT)
                            .attendanceDate(request.getLessonDate())
                            .build();
                }).toList();

        enrollments.forEach(newLesson::addEnrollment);
        presences.forEach(newLesson::addLessonPresence);

        return lessonRepository.save(newLesson);
    }
}