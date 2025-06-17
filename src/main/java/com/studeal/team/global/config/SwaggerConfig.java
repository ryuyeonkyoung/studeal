package com.studeal.team.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Studeal API",
                description = "Studeal의 API 명세서 🌱",
                version = "4.4.0",
                contact = @Contact(name = "Studeal Team")
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        description = "JWT 인증 토큰을 입력해주세요. 'Bearer [토큰]' 형식으로 입력"
)
public class SwaggerConfig {

    /**
     * OpenAPI 문서의 응답 코드를 커스텀 코드에서 표준 HTTP 코드로 변환하는 커스터마이저
     */
    @Bean
    public OpenApiCustomizer httpStatusCodeBasedOpenApiCustomizer() {
        return openApi -> {
            Paths paths = openApi.getPaths();

            if (paths == null) return;

            paths.forEach((path, pathItem) -> {
                Map<String, Operation> operations = new HashMap<>();

                if (pathItem.getGet() != null) operations.put("get", pathItem.getGet());
                if (pathItem.getPut() != null) operations.put("put", pathItem.getPut());
                if (pathItem.getPost() != null) operations.put("post", pathItem.getPost());
                if (pathItem.getDelete() != null) operations.put("delete", pathItem.getDelete());
                if (pathItem.getPatch() != null) operations.put("patch", pathItem.getPatch());
                if (pathItem.getHead() != null) operations.put("head", pathItem.getHead());
                if (pathItem.getOptions() != null) operations.put("options", pathItem.getOptions());
                if (pathItem.getTrace() != null) operations.put("trace", pathItem.getTrace());

                operations.forEach((method, operation) -> {
                    ApiResponses responses = operation.getResponses();
                    if (responses == null) return;

                    // 기존 응답 복사
                    Map<String, io.swagger.v3.oas.models.responses.ApiResponse> originalResponses = new LinkedHashMap<>(responses);

                    // 기존 응답 정보 초기화
                    responses.clear();

                    // 커스텀 코드를 표준 HTTP 상태 코드로 매핑
                    originalResponses.forEach((code, response) -> {
                        String httpStatusCode = mapCustomCodeToHttpCode(code);
                        responses.put(httpStatusCode, response);
                    });
                });
            });
        };
    }

    /**
     * 커스텀 코드를 표준 HTTP 상태 코드로 매핑
     */
    private String mapCustomCodeToHttpCode(String customCode) {
        // 커스텀 코드가 이미 HTTP 상태 코드인 경우
        if (customCode.matches("\\d{3}")) {
            return customCode;
        }

        // 커스텀 코드로부터 HTTP 상태 코드 추출
        if (customCode.startsWith("COMMON") || customCode.contains("200")) {
            return "200"; // OK
        } else if (customCode.contains("400") ||
                customCode.contains("4001") ||
                customCode.contains("4002") ||
                customCode.contains("4004") ||
                customCode.contains("4005") ||
                customCode.contains("4006")) {
            return "400"; // Bad Request
        } else if (customCode.contains("401")) {
            return "401"; // Unauthorized
        } else if (customCode.contains("403") ||
                customCode.contains("4003")) {
            return "403"; // Forbidden
        } else if (customCode.contains("404")) {
            return "404"; // Not Found
        } else if (customCode.contains("500")) {
            return "500"; // Internal Server Error
        } else {
            // 매핑되지 않은 상태 코드는 기본적으로 200으로 처리
            return "200";
        }
    }
}
