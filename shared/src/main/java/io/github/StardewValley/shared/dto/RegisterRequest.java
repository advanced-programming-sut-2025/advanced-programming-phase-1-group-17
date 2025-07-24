package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.enums.Gender;

public class RegisterRequest {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Gender gender;

    public RegisterRequest() {
        // default no-arg constructor required by Jackson
    }

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
