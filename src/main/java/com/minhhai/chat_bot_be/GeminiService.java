package com.minhhai.chat_bot_be;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class GeminiService {
    private static final Logger logger = LoggerFactory.getLogger(GeminiService.class);

    @Autowired
    private GeminiConfig geminiConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public String sendMessage(String message) {
        try {
            // Create Gemini request
            GeminiRequest request = new GeminiRequest();

            // Set content
            GeminiRequest.Part part = new GeminiRequest.Part(message);
            GeminiRequest.Content content = new GeminiRequest.Content(Arrays.asList(part));
            request.setContents(Arrays.asList(content));

            // Set generation config
            GeminiRequest.GenerationConfig config = new GeminiRequest.GenerationConfig(
                    geminiConfig.getTemperature(),
                    geminiConfig.getMaxTokens()
            );
            request.setGenerationConfig(config);

            // Convert to JSON
            String requestBody = objectMapper.writeValueAsString(request);
            logger.debug("Sending request to Gemini: {}", requestBody);

            // Create HTTP request
            String url = String.format("%s/v1beta/models/%s:generateContent?key=%s",
                    geminiConfig.getBaseUrl(),
                    geminiConfig.getModel(),
                    geminiConfig.getApiKey());

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

            // Execute request
            String responseBody = httpClient.execute(httpPost, response -> {
                int statusCode = response.getCode();
                logger.info("Gemini API status code: {}", statusCode);

                if (statusCode == 200) {
                    return new String(response.getEntity().getContent().readAllBytes());
                } else {
                    String errorBody = new String(response.getEntity().getContent().readAllBytes());
                    logger.error("Gemini API error {}: {}", statusCode, errorBody);
                    throw new RuntimeException("Gemini API error: " + statusCode + " - " + errorBody);
                }
            });

            logger.debug("Gemini API response: {}", responseBody);

            // Parse response
            GeminiResponse geminiResponse = objectMapper.readValue(responseBody, GeminiResponse.class);

            if (geminiResponse.getCandidates() != null && !geminiResponse.getCandidates().isEmpty()) {
                GeminiResponse.Candidate candidate = geminiResponse.getCandidates().get(0);
                if (candidate.getContent() != null &&
                        candidate.getContent().getParts() != null &&
                        !candidate.getContent().getParts().isEmpty()) {
                    return candidate.getContent().getParts().get(0).getText();
                }
            }

            throw new RuntimeException("No content in Gemini response");

        } catch (Exception e) {
            logger.error("Error calling Gemini API", e);
            return "Xin lỗi, tôi không thể trả lời câu hỏi của bạn lúc này. Vui lòng thử lại sau.";
        }
    }
}
