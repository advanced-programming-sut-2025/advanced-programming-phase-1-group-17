package io.github.StardewValley.shared.models;


import java.util.List;
public class GameDTO {
    private String creatorUsername;
    private List<String> playerUsernames;
    private int currentPlayingPlayerIndex;
    private String currentPlayingUsername;
    private String currentDate;

    public GameDTO() {
    }

    public GameDTO(String creatorUsername, List<String> playerUsernames, int currentPlayingPlayerIndex, String currentPlayingUsername, String currentDate) {
        this.creatorUsername = creatorUsername;
        this.playerUsernames = playerUsernames;
        this.currentPlayingPlayerIndex = currentPlayingPlayerIndex;
        this.currentPlayingUsername = currentPlayingUsername;
        this.currentDate = currentDate;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public List<String> getPlayerUsernames() {
        return playerUsernames;
    }

    public void setPlayerUsernames(List<String> playerUsernames) {
        this.playerUsernames = playerUsernames;
    }

    public int getCurrentPlayingPlayerIndex() {
        return currentPlayingPlayerIndex;
    }

    public void setCurrentPlayingPlayerIndex(int currentPlayingPlayerIndex) {
        this.currentPlayingPlayerIndex = currentPlayingPlayerIndex;
    }

    public String getCurrentPlayingUsername() {
        return currentPlayingUsername;
    }

    public void setCurrentPlayingUsername(String currentPlayingUsername) {
        this.currentPlayingUsername = currentPlayingUsername;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}

