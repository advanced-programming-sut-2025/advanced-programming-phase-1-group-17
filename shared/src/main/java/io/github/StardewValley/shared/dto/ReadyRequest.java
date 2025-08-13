package io.github.StardewValley.shared.dto;

import java.util.UUID;

public class ReadyRequest {
    private UUID gameId;

    public ReadyRequest() {
    }

    public ReadyRequest(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }
    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
}
