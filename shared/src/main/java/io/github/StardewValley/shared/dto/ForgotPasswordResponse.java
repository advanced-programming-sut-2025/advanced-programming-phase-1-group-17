package io.github.StardewValley.shared.dto;

public class ForgotPasswordResponse {
    private boolean match;
    private String message;

    public ForgotPasswordResponse() {}

    public ForgotPasswordResponse(boolean match, String message) {
        this.match = match;
        this.message = message;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
