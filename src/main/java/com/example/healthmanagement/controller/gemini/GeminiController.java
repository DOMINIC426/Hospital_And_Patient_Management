package com.example.healthmanagement.controller.gemini;

import com.example.healthmanagement.dtos.gemini.AiResponse;
import com.example.healthmanagement.dtos.gemini.PromptRequest;
import com.example.healthmanagement.service.gemini.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gemini")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("/ask")
    public ResponseEntity<AiResponse> ask(@RequestBody PromptRequest request) {
        return ResponseEntity.ok(geminiService.generateContent(request.getMessage()));
    }
}