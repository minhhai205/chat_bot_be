package com.minhhai.chat_bot_be;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        try {
            logger.info("Received chat message: {}", request.getMessage());

            String response = geminiService.sendMessage(request.getMessage());
            return ResponseEntity.ok(new ChatResponse(response));

        } catch (Exception e) {
            logger.error("Error processing chat message", e);
            return ResponseEntity.ok(new ChatResponse("Có lỗi xảy ra khi xử lý tin nhắn. Vui lòng thử lại.", false));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}