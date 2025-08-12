package io.github.StardewValley.shared.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SavedGameInfo {
    private UUID id;
    private LocalDateTime dateSaved;
    private List<String> participants;
    private String creatorUsername;

    public SavedGameInfo() {
    }

    public SavedGameInfo(UUID id, LocalDateTime dateSaved, List<String> participants, String creatorUsername) {
        this.id = id;
        this.dateSaved = dateSaved;
        this.participants = participants;
        this.creatorUsername = creatorUsername;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDateSaved() {
        return dateSaved;
    }

    public void setDateSaved(LocalDateTime dateSaved) {
        this.dateSaved = dateSaved;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }
}
