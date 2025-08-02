package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.UserDTO;

public class LoginResponse {
    private String token;
    private UserDTO userDTO;
    private String message;

    public LoginResponse() {
    }

    public LoginResponse(String token, UserDTO userDTO, String message) {
        this.token = token;
        this.userDTO = userDTO;
        this.message = message;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
