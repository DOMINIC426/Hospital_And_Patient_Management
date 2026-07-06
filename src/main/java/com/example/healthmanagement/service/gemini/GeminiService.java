package com.example.healthmanagement.service.gemini;

import com.example.healthmanagement.dtos.gemini.AiResponse;
import com.example.healthmanagement.dtos.gemini.GeminiApiRequest;
import com.example.healthmanagement.dtos.gemini.GeminiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeminiService {

    private final RestTemplate restTemplate;

    @Value("${gemini.api.key}")
    private String apiKey;

    public AiResponse generateContent(String prompt) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        GeminiApiRequest.Part part = new GeminiApiRequest.Part(prompt);
        GeminiApiRequest.Content content = new GeminiApiRequest.Content(List.of(part));
        GeminiApiRequest requestBody = new GeminiApiRequest(List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GeminiApiRequest> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<GeminiResponse> response = restTemplate.postForEntity(
                url,
                entity,
                GeminiResponse.class
        );

        // extract just the text from the nested response
        String text = response.getBody()
                .getCandidates()
                .get(0)              // first candidate
                .getContent()
                .getParts()
                .get(0)              // first part
                .getText();          // the actual text

        return new AiResponse(text);
    }
}