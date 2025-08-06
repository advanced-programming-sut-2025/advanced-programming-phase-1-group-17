package io.github.StardewValley.shared.models.cooking;

public class CookResponseDTO {
    private boolean success;
    private String message;
    // می‌توانید اطلاعات بیشتری مثل موجودی جدید آیتم‌ها را هم اینجا اضافه کنید
    // اما برای شروع همین کافی است.

    // Constructor برای Jackson
    public CookResponseDTO() {}

    public CookResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
