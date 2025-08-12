package io.github.StardewValley.shared.dto;

public class ChatMessageDTO {
    public enum MessageType {
        PUBLIC,
        TAGGED
    }

    private MessageType type = MessageType.PUBLIC;
    private String senderUsername;
    private String recipientUsername; // <<-- فیلد جدید اضافه شد
    private String content;
    private long timestamp;
    private boolean isPrivate = false;

    public ChatMessageDTO() {}

    // Getters and Setters
    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }
    public String getSenderUsername() { return senderUsername; }
    public void setSenderUsername(String senderUsername) { this.senderUsername = senderUsername; }

    // --- گتر و ستر جدید برای گیرنده ---
    public String getRecipientUsername() { return recipientUsername; }
    public void setRecipientUsername(String recipientUsername) { this.recipientUsername = recipientUsername; }
    // ------------------------------------

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public boolean isPrivate() { return isPrivate; }
    public void setPrivate(boolean aPrivate) { isPrivate = aPrivate; }
}
