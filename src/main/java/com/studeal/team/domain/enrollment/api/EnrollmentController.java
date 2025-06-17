package com.studeal.team.domain.enrollment.api;

import com.studeal.team.domain.enrollment.application.EnrollmentService;
import com.studeal.team.domain.enrollment.dto.EnrollmentRequestDTO;
import com.studeal.team.domain.enrollment.dto.EnrollmentResponseDTO;
import com.studeal.team.global.error.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enrollments")
@Tag(name = "수업 참여 확정", description = "학생의 수업 참여 확정 관련 API")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Operation(
            summary = "(테스트용) 최고가를 부른 학생과 선생님을 연결하는 API",
            description = "(테스트용) 경매 종료 후, 최고가를 부른 학생과 선생님을 연결하는 API입니다.\n\n" +
                    "이를 통해 학생은 낙찰받은 수업을 결제하여 거래를 성사하거나, 거절하여 최종 낙찰을 포기할 수 있습니다.\n" +
                    "이 API는 테스트용으로, 실제 서비스에서는 경매 종료 후 자동으로 연결됩니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STUDENT_400_01", description = "존재하지 않는 학생입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NEGOTIATION_404_01", description = "존재하지 않는 협상입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ENROLLMENT_400_01", description = "이미 수강신청 현황이 존재합니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ENROLLMENT_400_03", description = "협상이 성공 상태가 아닙니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping
    public ApiResponse<EnrollmentResponseDTO> createEnrollment(@Valid @RequestBody EnrollmentRequestDTO.CreateRequest request) {
        return ApiResponse.onSuccess(enrollmentService.createEnrollment(request));
    }

    @Operation(
            summary = "수업 참여 확정 변경 API",
            description = "수업 참여 확정 상태를 변경하는 API입니다. (대기 → 확정/취소 변경 가능)" +
                    " 이 API를 통해 학생은 수업 참여 확정 상태를 업데이트할 수 있습니다.\n\n" +
                    "가능한 EnrollmentStatus(상태) 값:\n" +
                    "- 대기 (WAITING) : 학생이 수업 참여를 신청한 상태\n" +
                    "- 확정 (CONFIRMED) : 수업 참여가 확정된 상태\n" +
                    "- 취소 (CANCELED) : 수업 참여가 취소된 상태")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ENROLLMENT_404_01", description = "존재하지 않는 수강 신청입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ENROLLMENT_400_02", description = "유효하지 않은 수강 신청 요청입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PatchMapping("/{enrollmentId}/status")
    public ApiResponse<EnrollmentResponseDTO> updateStatus(
            @PathVariable Long enrollmentId,
            @Valid @RequestBody EnrollmentRequestDTO.StatusUpdateRequest request) {
        return ApiResponse.onSuccess(enrollmentService.updateStatus(enrollmentId, request));
    }
}
