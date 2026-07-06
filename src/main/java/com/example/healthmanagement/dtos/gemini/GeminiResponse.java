package com.example.healthmanagement.dtos.gemini;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeminiResponse {

    private List<Candidate> candidates;

    @Getter
    @Setter
    public static class Candidate {

        private Content content;

        private String finishReason;
        private Integer index;
    }

    @Getter
    @Setter
    public static class Content {

        private List<Part> parts;

        private String role;
    }

    @Getter
    @Setter
    public static class Part {

        private String text;
    }
}