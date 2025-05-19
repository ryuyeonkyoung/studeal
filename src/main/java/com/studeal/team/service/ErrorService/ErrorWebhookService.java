package com.studeal.team.service.ErrorService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class ErrorWebhookService {

    public void sendToDiscord(String webhookUrl, Map<String, Object> errorDetails) {
        sendErrorToWebhook(webhookUrl, errorDetails);
    }

    private void sendErrorToWebhook(String webhookUrl, Map<String, Object> errorDetails) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> payload = Map.of("content", "에러 발생: " + errorDetails.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {
            restTemplate.postForEntity(webhookUrl, request, String.class);
        } catch (Exception ex) {
            log.error("Webhook 전송 실패: " + webhookUrl, ex);
        }
    }
}