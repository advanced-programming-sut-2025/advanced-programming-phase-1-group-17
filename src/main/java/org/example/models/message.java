package org.example.models;

public class message {
    private Player sender;
    private String message;
    public message(Player sender, String message) {
        this.sender = sender;
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
}
