package io.github.StardewValley.server.model;

import io.github.StardewValley.shared.models.enums.Gender;

import java.util.Objects;

import io.github.StardewValley.shared.models.Game;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String passwordHash;
    private String email;
    private String nickName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String securityQuestion;
    private String securityAnswer;
    private int numOfPlay = 0;
    private double theMostMoneyInGame;
    private transient Game activeGame;
    private transient Game lastGame;
    private String avatar = "avatar/avatar7.jpg";

    public User() {
        if (this.username == null) {
            gender = Gender.Male;
        }
    }

    //for signup
    public User(String username, String hashedPassword, String email, String nickName, Gender gender) {
        this.username = username;
        this.passwordHash = hashedPassword;
        this.email = email;
        this.nickName = nickName;
        this.gender = gender;
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return Objects.equals(username, other.username);
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

    public String getUsername() {
        return username;
    }

    public String getNickName() {
        return nickName;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
