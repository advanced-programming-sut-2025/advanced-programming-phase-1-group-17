package io.github.StardewValley.shared.dto;


public class TradeResponseDto {
    private String senderUsername;
    private String receiverUsername;
    private boolean accepted;

    public TradeResponseDto() {}
    public TradeResponseDto(String senderUsername, String receiverUsername, boolean accepted) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.accepted = accepted;
    }

    public String getSenderUsername() { return senderUsername; }
    public void setSenderUsername(String senderUsername) { this.senderUsername = senderUsername; }
    public String getReceiverUsername() { return receiverUsername; }
    public void setReceiverUsername(String receiverUsername) { this.receiverUsername = receiverUsername; }
    public boolean isAccepted() { return accepted; }
    public void setAccepted(boolean accepted) { this.accepted = accepted; }
}

