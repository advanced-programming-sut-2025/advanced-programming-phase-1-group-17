package io.github.StardewValley.shared.dto;

public class TradeRequestDto {
    private String senderUsername;
    private String receiverUsername;

    public TradeRequestDto() {}
    public TradeRequestDto(String senderUsername, String receiverUsername) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
    }

    public String getSenderUsername() { return senderUsername; }
    public void setSenderUsername(String senderUsername) { this.senderUsername = senderUsername; }
    public String getReceiverUsername() { return receiverUsername; }
    public void setReceiverUsername(String receiverUsername) { this.receiverUsername = receiverUsername; }
}

