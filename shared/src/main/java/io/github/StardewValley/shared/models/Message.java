package io.github.StardewValley.shared.models;

import io.github.StardewValley.shared.models.NPCS.NPC;

public class Message {
    private Player sender;
    private String message;
    private NPC senderNPC;

    public Message(Player sender, String message) {
        this.sender = sender;
        this.message = message;
    }
    public Message(NPC sender, String message) {
        this.senderNPC = sender;
        this.message = message;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NPC getSenderNPC() {
        return senderNPC;
    }
}
