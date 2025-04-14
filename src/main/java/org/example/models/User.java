package org.example.models;

import org.example.models.Player;
import org.example.models.enums.Gender;

import java.util.ArrayList;

public class User {
    public static User userOfLogin;
    private String username;
    private String password;
    private String email;
    private String nickName;
    private Gender gender;
    private Player player;
    private int numOfPlay;
    private int theMostMoneyIngame;

    public int getNumOfPlay() {
        return numOfPlay;
    }

    public void setNumOfPlay(int numOfPlay) {
        this.numOfPlay = numOfPlay;
    }

    public int getTheMostMoneyIngame() {
        return theMostMoneyIngame;
    }

    public void setTheMostMoneyIngame(int theMostMoneyIngame) {
        this.theMostMoneyIngame = theMostMoneyIngame;
    }

    private static final ArrayList<User> users = new ArrayList<>();

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public static User getUserWithUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}