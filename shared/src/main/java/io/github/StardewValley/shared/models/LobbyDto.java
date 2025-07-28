package io.github.StardewValley.shared.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LobbyDto {
    private Long id;
    private String name;
    private String inviteCode;
    private boolean isPrivate;
    private boolean isVisible;
    private LobbyStatus status;
    private String adminUsername;
    private List<String> playerUsernames;

    public LobbyDto() {}

    public LobbyDto(Long id, String name, String inviteCode, boolean isPrivate, boolean isVisible,
                    LobbyStatus status, String adminUsername, List<String> playerUsernames) {
        this.id = id;
        this.name = name;
        this.inviteCode = inviteCode;
        this.isPrivate = isPrivate;
        this.isVisible = isVisible;
        this.status = status;
        this.adminUsername = adminUsername;
        this.playerUsernames = playerUsernames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public LobbyStatus getStatus() {
        return status;
    }

    public void setStatus(LobbyStatus status) {
        this.status = status;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public List<String> getPlayerUsernames() {
        return playerUsernames;
    }

    public void setPlayerUsernames(List<String> playerUsernames) {
        this.playerUsernames = playerUsernames;
    }
    public void addPlayerUsername(String playerUsername) {
        this.playerUsernames.add(playerUsername);
    }
}



