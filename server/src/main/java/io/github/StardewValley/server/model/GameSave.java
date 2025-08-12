package io.github.StardewValley.server.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class GameSave {
    @Id
    private UUID id;

    private String creatorUsername;
    private LocalDateTime lastSaved;
    private String playerUsernamesCSV;

    @Lob
    private String serializedState; // JSON or binary


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public LocalDateTime getLastSaved() {
        return lastSaved;
    }

    public void setLastSaved(LocalDateTime lastSaved) {
        this.lastSaved = lastSaved;
    }

    public String getSerializedState() {
        return serializedState;
    }

    public void setSerializedState(String serializedState) {
        this.serializedState = serializedState;
    }

    public String getPlayerUsernamesCSV() {
        return playerUsernamesCSV;
    }

    public void setPlayerUsernamesCSV(String playerUsernamesCSV) {
        this.playerUsernamesCSV = playerUsernamesCSV;
    }
}

