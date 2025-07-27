package io.github.StardewValley.server.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lobbies")
public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String inviteCode;
    private boolean isPrivate;
    private boolean isVisible;

    @Enumerated(EnumType.STRING)
    private LobbyStatus status = LobbyStatus.WAITING;

    private String adminUsername;

    @ElementCollection
    private List<String> playerUsernames = new ArrayList<>();

    public Lobby() {}

    public Lobby(String name, String inviteCode, boolean isPrivate, boolean isVisible, String adminUsername) {
        this.name = name;
        this.inviteCode = inviteCode;
        this.isPrivate = isPrivate;
        this.isVisible = isVisible;
        this.adminUsername = adminUsername;
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
}


