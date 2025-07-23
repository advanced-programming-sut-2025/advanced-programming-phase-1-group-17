package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.enums.Gender;

public class RegisterRequest {
    private final String username;
    private final String password;
    private final String nickname;
    private final String email;
    private final Gender gender;

    public RegisterRequest(String username, String password, String nickname, String email, Gender gender) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }
}
