package com.minhhai.chat_bot_be;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GeminiRequest {
    private List<Content> contents;
    @JsonProperty("generationConfig")
    private GenerationConfig generationConfig;

    public static class Content {
        private List<Part> parts;

        public Content() {}

        public Content(List<Part> parts) {
            this.parts = parts;
        }

        // Getters and Setters
        public List<Part> getParts() { return parts; }
        public void setParts(List<Part> parts) { this.parts = parts; }
    }

    public static class Part {
        private String text;

        public Part() {}

        public Part(String text) {
            this.text = text;
        }

        // Getters and Setters
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }

    public static class GenerationConfig {
        private double temperature;
        @JsonProperty("maxOutputTokens")
        private int maxOutputTokens;

        public GenerationConfig() {}

        public GenerationConfig(double temperature, int maxOutputTokens) {
            this.temperature = temperature;
            this.maxOutputTokens = maxOutputTokens;
        }

        // Getters and Setters
        public double getTemperature() { return temperature; }
        public void setTemperature(double temperature) { this.temperature = temperature; }
        public int getMaxOutputTokens() { return maxOutputTokens; }
        public void setMaxOutputTokens(int maxOutputTokens) { this.maxOutputTokens = maxOutputTokens; }
    }

    // Getters and Setters
    public List<Content> getContents() { return contents; }
    public void setContents(List<Content> contents) { this.contents = contents; }

    public GenerationConfig getGenerationConfig() { return generationConfig; }
    public void setGenerationConfig(GenerationConfig generationConfig) { this.generationConfig = generationConfig; }
}