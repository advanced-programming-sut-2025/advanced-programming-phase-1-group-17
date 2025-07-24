package io.github.StardewValley.shared.dto;

public class SecurityQuestionResponse {
    private boolean success;
    private String message;

    public SecurityQuestionResponse() {}

    public SecurityQuestionResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
