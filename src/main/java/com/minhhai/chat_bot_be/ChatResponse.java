package com.minhhai.chat_bot_be;

public class ChatResponse {
    private String response;
    private boolean success;
    private String error;

    public ChatResponse() {}

    public ChatResponse(String response) {
        this.response = response;
        this.success = true;
    }

    public ChatResponse(String error, boolean success) {
        this.error = error;
        this.success = success;
    }

    // Getters and Setters
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
