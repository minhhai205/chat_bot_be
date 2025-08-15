package com.minhhai.chat_bot_be;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChatRequest {
    @NotBlank(message = "Message cannot be empty")
    @Size(max = 4000, message = "Message too long")
    private String message;

    public ChatRequest() {}

    public ChatRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
