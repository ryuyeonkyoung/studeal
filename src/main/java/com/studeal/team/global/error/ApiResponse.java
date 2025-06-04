package com.studeal.team.global.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.studeal.team.global.error.code.BaseCode;
import com.studeal.team.global.error.code.status.SuccessStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"}) // JSON 출력 시 필드 순서를 지정
@Schema(description = "API 응답 형식")
public class ApiResponse<T> {

    @JsonProperty("isSuccess") // 자바 필드명과 JSON키를 매핑
    @Schema(description = "성공 여부", example = "true")
    private final Boolean isSuccess;

    @Schema(description = "응답 코드", example = "COMMON200")
    private final String code;

    @Schema(description = "응답 메시지", example = "성공입니다.")
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL) // 필드를 null로 설정 시 JSON에서 제외
    @Schema(description = "응답 데이터")
    private T result;


    // 성공한 경우 응답 생성
    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<>(true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result);
    }

    public static <T> ApiResponse<T> of(BaseCode code, T result) {
        return new ApiResponse<>(true, code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(), result);
    }


    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
        return new ApiResponse<>(false, code, message, data);
    }
}
