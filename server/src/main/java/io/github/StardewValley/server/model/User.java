package io.github.StardewValley.server.model;

import io.github.StardewValley.shared.enums.Gender;

import java.util.Objects;

import io.github.StardewValley.shared.model.Game;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    private String email;
    private String nickName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String securityQuestion;
    private String securityAnswer;
    private int numOfPlay;
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
}
