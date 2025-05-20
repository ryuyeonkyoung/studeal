package com.studeal.team.domain.lesson.api;

import com.studeal.team.domain.lesson.applicationn.LessonService;
import com.studeal.team.domain.lesson.domain.Lesson;
import com.studeal.team.domain.lesson.dto.LessonRequestDTO;
import com.studeal.team.domain.lesson.dto.LessonResponseDTO;
import com.studeal.team.global.error.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.studeal.team.domain.lesson.converter.LessonConverter;

@RestController
@RequestMapping("/studeal/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public ApiResponse<LessonResponseDTO.CreateResponse> createLesson(@RequestBody @Valid LessonRequestDTO.CreateRequest request) {
        Lesson createdLesson = lessonService.createLesson(request);
        return ApiResponse.onSuccess(LessonConverter.toCreateResponseDTO(createdLesson));
    }
}