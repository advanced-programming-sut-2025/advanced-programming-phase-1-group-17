package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.Message;

public class MessageSave {
    private String senderName;
    private String message;
    private String senderNPCName;

    public MessageSave() {
    }

    public MessageSave(Message message) {
        this.senderName = message.getSender().getUser().getUsername();
        this.message = message.getMessage();
        this.senderNPCName = message.getSenderNPC().getName();
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderNPCName() {
        return senderNPCName;
    }

    public void setSenderNPCName(String senderNPCName) {
        this.senderNPCName = senderNPCName;
    }
}
