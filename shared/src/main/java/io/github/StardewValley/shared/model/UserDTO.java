package io.github.StardewValley.shared.model;

import io.github.StardewValley.shared.enums.Gender;

public class UserDTO {
    /*
    This class is a simpler version of user, that stores only unimportant data like username and nickname
    it can store email.
    it must not store the password (raw or encrypted)
     */

    private String username;
    private String nickname;
    private Gender gender;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
