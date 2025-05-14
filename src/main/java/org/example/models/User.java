package org.example.models;

import org.example.models.enums.Gender;

import java.util.Objects;

public class User {
    private String username;
    private String rawPassword;
    private String passwordHash;
    private String email;
    private String nickName;
    private Gender gender;
    private String securityQuestion;
    private String securityAnswer;
    private int numOfPlay;
    private int theMostMoneyInGame;
    private transient Game activeGame;

    public User() {
        if (this.username == null) {
            gender = Gender.Male;
        }
    }



    //for signup
    public User(String username, String rawPassword, String hashedPassword, String email, String nickName, Gender gender) {
        this.username = username;
        this.rawPassword = rawPassword;
        this.passwordHash = hashedPassword;
        this.email = email;
        this.nickName = nickName;
        this.gender = gender;
    }

    public int getNumOfPlay() {
        return numOfPlay;
    }

    public void setNumOfPlay(int numOfPlay) {
        this.numOfPlay = numOfPlay;
    }

    public int getTheMostMoneyInGame() {
        return theMostMoneyInGame;
    }

    public void setTheMostMoneyInGame(int theMostMoneyInGame) {
        this.theMostMoneyInGame = theMostMoneyInGame;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public Game getActiveGame() {
        return activeGame;
    }

    public void setActiveGame(Game activeGame) {
        this.activeGame = activeGame;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return Objects.equals(username, other.username);
    }
}