package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.LobbyDto;

public class CreateLobbyResponse {
    private boolean success;
    private String message;
    private LobbyDto lobby;

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

    public LobbyDto getLobby() {
        return lobby;
    }

    public void setLobby(LobbyDto lobby) {
        this.lobby = lobby;
    }
}

