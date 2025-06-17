package com.studeal.team.domain.board.api;

import com.studeal.team.domain.board.application.BoardCommandService;
import com.studeal.team.domain.board.application.BoardQueryService;
import com.studeal.team.domain.board.dto.BoardRequestDTO;
import com.studeal.team.domain.board.dto.BoardResponseDTO;
import com.studeal.team.global.common.util.SecurityUtils;
import com.studeal.team.global.error.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 게시판 API 컨트롤러
 * 과외 모집 게시글 관련 엔드포인트 제공
 */
@Tag(name = "게시판 API", description = "선생님의 과외 모집 게시판 관련 API")
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
@Slf4j
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
            @Valid @RequestBody BoardRequestDTO.CreateRequest request) {
        // SecurityContextHolder에서 현재 사용자 ID 추출
        Long teacherId = SecurityUtils.getCurrentUserId();
        log.info("게시글 생성 요청: 사용자 ID {}", teacherId);

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
            @Valid @RequestBody BoardRequestDTO.UpdateRequest request) {
        // SecurityContextHolder에서 현재 사용자 ID 추출
        Long teacherId = SecurityUtils.getCurrentUserId();
        log.info("게시글 수정 요청: 게시글 ID {}, 사용자 ID {}", boardId, teacherId);

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
            @Parameter(description = "게시글 ID") @PathVariable Long boardId) {
        // SecurityContextHolder에서 현재 사용자 ID 추출
        Long teacherId = SecurityUtils.getCurrentUserId();
        log.info("게시글 삭제 요청: 게시글 ID {}, 사용자 ID {}", boardId, teacherId);

        boardCommandService.deleteBoard(boardId, teacherId);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "게시글 상세 조회 (역할 기반)", description = "사용자의 역할(선생님/학생)에 따라 게시글 상세 정보를 다르게 제공합니다. 선생님은 입찰 정보를, 학생은 선생님 정보를 추가로 확인할 수 있습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_400_01", description = "게시글을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NEGOTIATION_400_01", description = "유효하지 않은 가격 제안 요청입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/{boardId}")
    public ApiResponse<?> getBoardDetail(
            @Parameter(description = "게시글 ID") @PathVariable Long boardId) {
        // SecurityContextHolder에서 현재 사용자 ID 추출
        Long userId = SecurityUtils.getCurrentUserId();
        log.info("게시글 상세 조회 요청: 게시글 ID {}, 사용자 ID {}", boardId, userId);

        // SecurityUtils를 사용하여 사용자 역할 확인
        boolean isTeacher = SecurityUtils.isTeacher();
        boolean isStudent = SecurityUtils.isStudent();

        // 역할에 따라 다른 응답 제공
        if (isTeacher) {
            BoardResponseDTO.DetailTeacherResponse response =
                    boardQueryService.getTeacherDetailBoard(boardId, userId);
            return ApiResponse.onSuccess(response);
        } else if (isStudent) {
            BoardResponseDTO.DetailStudentResponse response =
                    boardQueryService.getStudentDetailBoard(boardId, userId);
            return ApiResponse.onSuccess(response);
        } else {
            // 기본 응답 (역할이 없거나 알 수 없는 경우)
            BoardResponseDTO.DetailResponse response = boardQueryService.getBoard(boardId);
            return ApiResponse.onSuccess(response);
        }
    }

    @Operation(summary = "게시글 목록 조회 (커서 페이징)", description = "모든 과외 모집 게시글 목록을 커서 기반 페이징으로 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공")
    })
    @GetMapping("/cursor")
    public ApiResponse<BoardResponseDTO.CursorPageResponse> getBoardsByCursor(
            @Parameter(description = "커서 ID (이전 페이지의 마지막 게시글 ID, 첫 페이지는 null)")
            @RequestParam(required = false) Long cursorId,

            @Parameter(description = "페이지 크기 (기본값: 10)")
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        BoardResponseDTO.CursorPageResponse response = boardQueryService.getBoardsByCursor(cursorId, size);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "게시글 목록 조회 (오프셋 페이징)", description = "모든 과외 모집 게시글 목록을 오프셋 기반 페이징으로 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공")
    })
    @GetMapping("/offset")
    public ApiResponse<BoardResponseDTO.OffsetPageResponse> getBoardsByOffset(
            @Parameter(description = "페이지 번호 (0부터 시작)")
            @RequestParam(required = false, defaultValue = "0") Integer page,

            @Parameter(description = "페이지 크기 (기본값: 10)")
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        BoardResponseDTO.OffsetPageResponse response = boardQueryService.getBoardsByOffset(pageable);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "게시글 검색", description = "게시글을 검색 유형과 키워드로 검색합니다.\n\n" +
            "검색 유형(searchType) 옵션:\n" +
            "- MAJOR: 전공 과목으로 검색 (MATH, SCIENCE, ENGLISH, KOREAN, HISTORY, CODING)\n" +
            "- TEACHER_NAME: 선생님 이름으로 검색\n" +
            "- SPEC_MAJOR: 세부 전공으로 검색")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_400_02", description = "검색어나 검색 유형이 유효하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_400_03", description = "올바르지 않은 검색 유형입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_400_04", description = "올바르지 않은 전공 과목입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/search")
    public ApiResponse<BoardResponseDTO.SearchPageResponse> searchBoards(
            @Parameter(description = "검색 유형 (MAJOR, TEACHER_NAME, SPEC_MAJOR)")
            @RequestParam String searchType,

            @Parameter(description = "검색어")
            @RequestParam String keyword,

            @Parameter(description = "페이지 번호 (0부터 시작)")
            @RequestParam(required = false, defaultValue = "0") Integer page,

            @Parameter(description = "페이지 크기 (기본값: 10)")
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        log.info("게시글 검색 요청: 검색 유형={}, 검색어={}", searchType, keyword);

        Pageable pageable = PageRequest.of(page, size);
        BoardResponseDTO.SearchPageResponse response = boardQueryService.searchBoards(searchType, keyword, pageable);
        return ApiResponse.onSuccess(response);
    }
}