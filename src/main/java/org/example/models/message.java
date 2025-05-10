package org.example.models;

import org.example.models.NPCS.NPC;

public class message {
    private Player sender;
    private String message;
    private NPC senderNPC;
    public message(Player sender, String message) {
        this.sender = sender;
        this.message = message;
    }
    public message(NPC sender, String message) {
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
