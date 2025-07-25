package io.github.StardewValley.shared.models.enums;

public enum NetworkRequests {
    Register("register"),
    SecurityQuestion("securityQuestion"),
    DoesUserExist("doesUserExist"),
    LoginRequest("login"),
    ForgotPasswordRequest("forgotPassword");

    private final String message;

    NetworkRequests(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
