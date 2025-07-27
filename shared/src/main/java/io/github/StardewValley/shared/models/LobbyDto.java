package io.github.StardewValley.shared.models;


import java.util.List;

public class LobbyDto {
    private Long id;
    private String name;
    private String inviteCode;
    private boolean isPrivate;
    private boolean isVisible;
//    private LobbyStatus status;
    private String adminUsername;
    private List<String> playerUsernames;

    public LobbyDto(Long id, String name, String inviteCode, boolean isPrivate, boolean isVisible, String adminUsername, List<String> playerUsernames) {
        this.id = id;
        this.name = name;
        this.inviteCode = inviteCode;
        this.isPrivate = isPrivate;
        this.isVisible = isVisible;
//        this.status = status;
        this.adminUsername = adminUsername;
        this.playerUsernames = playerUsernames;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getInviteCode() { return inviteCode; }
    public boolean isPrivate() { return isPrivate; }
    public boolean isVisible() { return isVisible; }
//    public LobbyStatus getStatus() { return status; }
    public String getAdminUsername() { return adminUsername; }
    public List<String> getPlayerUsernames() { return playerUsernames; }
}


