package io.github.StardewValley.shared.dto;

public class DoesUserExistRequest {
    private String username;

    public DoesUserExistRequest() {}

    public DoesUserExistRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
