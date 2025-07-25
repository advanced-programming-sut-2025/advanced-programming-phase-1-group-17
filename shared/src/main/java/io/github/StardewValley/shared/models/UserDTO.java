package io.github.StardewValley.shared.models;

import io.github.StardewValley.shared.models.enums.Gender;

public class UserDTO {
    /*
    This class is a simpler version of user, that stores only unimportant data like username and nickname
    it can store email.
    it must not store the password (raw or encrypted)
     */

    private String username;
    private String nickname;
    private Gender gender;
    private String securityQuestion;
    private String securityAnswer;
    private int numOfPlay = 0;
    private double theMostMoneyInGame;
    private transient Game activeGame;
    private transient Game lastGame;
    private String avatar = "avatar/avatar7.jpg";

    public UserDTO(String username, String nickname, Gender gender) {
        this.username = username;
        this.nickname = nickname;
        this.gender = gender;
    }

    public UserDTO(String username, String nickname, Gender gender, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.nickname = nickname;
        this.gender = gender;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

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

    public Game getActiveGame() {
        return activeGame;
    }

    public void setActiveGame(Game activeGame) {
        this.activeGame = activeGame;
    }

    public int getNumOfPlay() {
        return numOfPlay;
    }

    public void setNumOfPlay(int numOfPlay) {
        this.numOfPlay = numOfPlay;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getTheMostMoneyInGame() {
        return theMostMoneyInGame;
    }

    public void setTheMostMoneyInGame(double theMostMoneyInGame) {
        this.theMostMoneyInGame = theMostMoneyInGame;
    }

    public Game getLastGame() {
        return lastGame;
    }

    public void setLastGame(Game lastGame) {
        this.lastGame = lastGame;
    }
}
