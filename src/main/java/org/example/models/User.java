package org.example.models;

import org.example.models.enums.Gender;

public class User {
    private String username;
    private String password;
    private String passwordHash; //TODO: encoding in signup menu

    private String email;
    private String nickName;
    private Gender gender;

    private String securityQuestion;
    private String securityAnswer;

    private int numOfPlay;
    private int theMostMoneyInGame;
    private Game activeGame;
    public User(){
        gender = Gender.Male;
    };

    //for signup
    public User(String username, String password, String email, String nickName, Gender gender) {
        this.username = username;
        this.password = password;
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
}