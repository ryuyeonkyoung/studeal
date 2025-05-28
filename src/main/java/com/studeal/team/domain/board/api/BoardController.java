package com.studeal.team.domain.board.api;

import com.studeal.team.domain.board.application.BoardCommandService;
import com.studeal.team.domain.board.application.BoardQueryService;
import com.studeal.team.domain.board.dto.BoardRequestDTO;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.global.error.ApiResponse;
import com.studeal.team.global.validation.annotation.ExistTeacher;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 게시판 API 컨트롤러
 * 과외 모집 게시글 관련 엔드포인트 제공
 */
@Tag(name = "게시판 API", description = "과외 모집 게시판 관련 API")
@RestController
@RequestMapping("/studeal/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardQueryService boardQueryService;
    private final BoardCommandService boardCommandService;

    @Operation(summary = "게시글 생성", description = "선생님이 과외 모집 게시글을 작성합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD4001", description = "게시글을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TEACHER4001", description = "선생님을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/teachers/{teacherId}")
    public ApiResponse<BoardResponseDTO.DetailResponse> createBoard(
            @Parameter(description = "선생님 ID") @PathVariable @ExistTeacher Long teacherId,
            @Valid @RequestBody BoardRequestDTO.CreateRequest request) {
        BoardResponseDTO.DetailResponse response = boardCommandService.createBoard(teacherId, request);
        return ApiResponse.onSuccess(response);
    }
}
