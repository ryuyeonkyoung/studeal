package com.studeal.team.domain.lesson.api;

import com.studeal.team.domain.lesson.application.LessonCommandService;
import com.studeal.team.domain.lesson.converter.LessonConverter;
import com.studeal.team.domain.lesson.domain.Lesson;
import com.studeal.team.domain.lesson.dto.LessonRequestDTO;
import com.studeal.team.domain.lesson.dto.LessonResponseDTO;
import com.studeal.team.global.common.util.SecurityUtils;
import com.studeal.team.global.error.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lessons")
@Tag(name = "수업", description = "수업 관련 API")
@Slf4j
public class LessonController {

  private final LessonCommandService lessonCommandService;

  public LessonController(LessonCommandService lessonCommandService) {
    this.lessonCommandService = lessonCommandService;
  }

  @Operation(summary = "수업 개설 API", description = "새로운 수업을 개설하는 API입니다. 강사와 학생 정보가 필요합니다.")
  @ApiResponses({
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSON_400_01", description = "수업이 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSON_400_02", description = "수업 제목은 필수입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSON_400_03", description = "강사 정보가 필요합니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSON_400_04", description = "최소 한 명 이상의 학생이 필요합니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSON_400_05", description = "유효한 수업 가격이 필요합니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TEACHER_400_01", description = "강사가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STUDENT_400_01", description = "학생이 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
  })
  @PostMapping
  public ApiResponse<LessonResponseDTO.CreateResponse> createLesson(
      @RequestBody @Valid LessonRequestDTO.CreateRequest request) {
    // SecurityContextHolder에서 현재 사용자 ID 조회
    Long currentUserId = SecurityUtils.getCurrentUserId();
    log.info("수업 생성 요청: 사용자 ID {}", currentUserId);

    Lesson createdLesson = lessonCommandService.createLesson(request);
    return ApiResponse.onSuccess(LessonConverter.toCreateResponseDTO(createdLesson));
  }
}
