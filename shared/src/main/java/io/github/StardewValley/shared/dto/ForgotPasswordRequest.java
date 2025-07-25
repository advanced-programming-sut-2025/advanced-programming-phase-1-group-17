package io.github.StardewValley.shared.dto;

public class ForgotPasswordRequest {
    private String securityAnswer;
    private String username;

    public ForgotPasswordRequest() {}

    public ForgotPasswordRequest(String securityAnswer, String username) {
        this.securityAnswer = securityAnswer;
        this.username = username;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
