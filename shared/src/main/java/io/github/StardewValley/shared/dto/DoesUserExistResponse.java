package io.github.StardewValley.shared.dto;

public class DoesUserExistResponse {
    private boolean result;
    private String message;

    public DoesUserExistResponse(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public DoesUserExistResponse() {}

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
