package com.studeal.team.domain.board.api;

import com.studeal.team.domain.board.application.BoardCommandService;
import com.studeal.team.domain.board.application.BoardQueryService;
import com.studeal.team.domain.board.dto.BoardRequestDTO;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.domain.user.domain.validation.ExistTeacher;
import com.studeal.team.global.error.ApiResponse;
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
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardQueryService boardQueryService;
    private final BoardCommandService boardCommandService;

    @Operation(summary = "게시글 생성", description = "선생님이 과외 모집 게시글을 작성합니다.\n\n" +
            "가능한 MajorSubject(전공 과목) 값:\n" +
            "- 수학 (MATH)\n" +
            "- 과학 (SCIENCE)\n" +
            "- 영어 (ENGLISH)\n" +
            "- 국어 (KOREAN)\n" +
            "- 역사 (HISTORY)\n" +
            "- 코딩 (CODING)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_400_01", description = "게시글을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TEACHER_400_01", description = "선생님을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping
    public ApiResponse<BoardResponseDTO.DetailResponse> createBoard(
            @Parameter(description = "선생님 ID (인증 정보에서 추출)", hidden = true) @RequestAttribute("userId") @ExistTeacher Long teacherId,
            @Valid @RequestBody BoardRequestDTO.CreateRequest request) {
        BoardResponseDTO.DetailResponse response = boardCommandService.createBoard(teacherId, request);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "게시글 수정", description = "과외 모집 게시글을 수정합니다. 본인이 작성한 게시글만 수정 가능합니다.\n\n" +
            "가능한 MajorSubject(전공 과목) 값:\n" +
            "- 수학 (MATH)\n" +
            "- 과학 (SCIENCE)\n" +
            "- 영어 (ENGLISH)\n" +
            "- 국어 (KOREAN)\n" +
            "- 역사 (HISTORY)\n" +
            "- 코딩 (CODING)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_400_01", description = "게시글을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_403_01", description = "게시글 권한이 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PutMapping("/{boardId}")
    public ApiResponse<BoardResponseDTO.DetailResponse> updateBoard(
            @Parameter(description = "게시글 ID") @PathVariable Long boardId,
            @Parameter(description = "선생님 ID (인증 정보에서 추출)", hidden = true) @RequestAttribute("userId") @ExistTeacher Long teacherId,
            @Valid @RequestBody BoardRequestDTO.UpdateRequest request) {
        BoardResponseDTO.DetailResponse response = boardCommandService.updateBoard(boardId, teacherId, request);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "게시글 삭제", description = "선생님이 작성한 과외 모집 게시글을 삭제합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_400_01", description = "게시글을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_403_01", description = "게시글 권한이 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @DeleteMapping("/{boardId}")
    public ApiResponse<?> deleteBoard(
            @Parameter(description = "게시글 ID") @PathVariable Long boardId,
            @Parameter(description = "선생님 ID (인증 정보에서 추출)", hidden = true) @RequestAttribute("userId") @ExistTeacher Long teacherId) {
        boardCommandService.deleteBoard(boardId, teacherId);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "게시글 상세 조회 - 선생님", description = "선생님이 조회하는 게시글 상세 정보 API. 게시글 정보와 입찰 순위 정보를 함께 제공합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_400_01", description = "게시글을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NEGOTIATION_400_01", description = "유효하지 않은 가격 제안 요청입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/{boardId}")
    public ApiResponse<BoardResponseDTO.DetailTeacherResponse> getTeacherDetail(
            @Parameter(description = "게시글 ID") @PathVariable Long boardId,
            @Parameter(description = "선생님 ID (인증 정보에서 추출)", hidden = true) @RequestAttribute("userId") @ExistTeacher Long teacherId) {
        BoardResponseDTO.DetailTeacherResponse response = boardQueryService.getTeacherDetailBoard(boardId, teacherId);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "게시글 목록 조회", description = "모든 과외 모집 게시글 목록을 커서 기반 페이징으로 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공")
    })
    @GetMapping
    public ApiResponse<BoardResponseDTO.CursorResponse> getBoardsWithCursor(
            @Parameter(description = "커서 ID (이전 페이지의 마지막 게시글 ID, 첫 페이지는 null)")
            @RequestParam(required = false) Long cursorId,

            @Parameter(description = "페이지 크기 (기본값: 10)")
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        BoardResponseDTO.CursorResponse response = boardQueryService.getBoardsWithCursor(cursorId, size);
        return ApiResponse.onSuccess(response);
    }
}
