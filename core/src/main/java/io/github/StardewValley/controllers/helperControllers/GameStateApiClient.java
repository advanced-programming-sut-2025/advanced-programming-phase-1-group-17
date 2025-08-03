package io.github.StardewValley.controllers.helperControllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.PlayerDto;
import io.github.StardewValley.shared.models.TileDTO;
import io.github.StardewValley.shared.models.UserDTO;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStateApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/gameState";
    private String token;

    public GameStateApiClient(String jwtToken) {
        this.token = jwtToken;
    }

    public List<TileDTO> getMapTilesAroundPlayer(int minX, int maxX, int minY, int maxY) throws Exception {
        String path = String.format("/game/map?minX=%d&maxX=%d&minY=%d&maxY=%d", minX, maxX, minY, maxY);
        URL url = new URL(BASE_URL + path);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.connect();

        if (conn.getResponseCode() == 200) {
            try (InputStream inputStream = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(inputStream, new TypeReference<List<TileDTO>>() {
                });
            }
        } else {
            throw new RuntimeException("Could not fetch tiles: code " + conn.getResponseCode());
        }
    }
    public UserDTO getUserWithUserDTO() throws Exception {
        String urlString = "http://localhost:8080/api/auth/getUserByUsername";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            try (InputStream is = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
                return mapper.readValue(is, UserDTO.class);
            }
        } else {
            System.out.println("Error: " + responseCode);
        }
        return null;
    }

    public PlayerDto updateStateOfPlayer(float delta, boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed) throws Exception {
        URL url = new URL(BASE_URL + "/game/player/update");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        String jsonInput = String.format(
            "{\"delta\":%f,\"upPressed\":%b,\"downPressed\":%b,\"leftPressed\":%b,\"rightPressed\":%b}",
            delta, upPressed, downPressed, leftPressed, rightPressed
        );

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        if (conn.getResponseCode() == 200) {
            try (InputStream inputStream = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(inputStream, PlayerDto.class);
            }
        } else {
            throw new RuntimeException("Failed to update player state: " + conn.getResponseCode());
        }
    }

    public void selectMap(int type) throws Exception {
        URL url = new URL(BASE_URL + "/selectMap?type=" + type);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed to select map. Code: " + responseCode);
        }
    }
    public String getUserName() throws Exception {
        URL url = new URL(BASE_URL + "/getUserNameByToken");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            try (InputStream inputStream = conn.getInputStream()) {
                return new String(inputStream.readAllBytes(), "UTF-8");
            }
        } else {
            throw new RuntimeException("Failed to get username: " + responseCode);
        }
    }
    public boolean exitGame() throws Exception {
        String urlString = BASE_URL + "/exitGame";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            try (InputStream is = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(is, boolean.class);
            }
        } else {
            System.out.println("Error: " + responseCode);
            return false;
        }
    }
    public void updateUser(UserDTO dto) throws Exception {
        String urlStr = "http://localhost:8080/api/auth/user/update";
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        StringBuilder postData = new StringBuilder();
        postData.append("email=").append(URLEncoder.encode(dto.getEmail(), "UTF-8"));
        postData.append("&avatar=").append(URLEncoder.encode(dto.getAvatar(), "UTF-8"));
        postData.append("&username=").append(URLEncoder.encode(dto.getUsername(), "UTF-8"));
        postData.append("&nickname=").append(URLEncoder.encode(dto.getNickname(), "UTF-8"));
        postData.append("&theMostMoneyInGame=").append(dto.getTheMostMoneyInGame());
        postData.append("&securityQuestion=").append(URLEncoder.encode(dto.getSecurityQuestion(), "UTF-8"));
        postData.append("&securityAnswer=").append(URLEncoder.encode(dto.getSecurityAnswer(), "UTF-8"));
        postData.append("&numOfPlay=").append(dto.getNumOfPlay());

        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(postDataBytes);
        }


        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            try (InputStream is = conn.getInputStream()) {
                String token = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                setToken(token);
                Main.setJwt(token);
            }
        } else {
            throw new RuntimeException("Failed to update user: code " + responseCode);
        }
    }

    public boolean passWordCheck(String password) throws Exception {
        String urlString = "http://localhost:8080/api/auth/passWordCheck";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(password);
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            try (InputStream is = conn.getInputStream()) {
                ObjectMapper mapper2 = new ObjectMapper();
                return mapper2.readValue(is, boolean.class);
            }
        } else {
            System.out.println("Error: " + responseCode);
            return false;
        }
    }
    public void changePassword(String oldPassword, String newPassword) throws Exception {
        URL url = new URL("http://localhost:8080/api/auth/changePassword");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        Map<String, String> payload = new HashMap<>();
        payload.put("oldPassword", oldPassword);
        payload.put("newPassword", newPassword);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payload);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int code = conn.getResponseCode();
        if (code == 200) {
            try (InputStream is = conn.getInputStream()) {
                String token = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                setToken(token);
                Main.setJwt(token);
            }
            System.out.println("Password changed successfully");
        } else {
            System.out.println("Failed to change password: " + code);
        }
    }
    public void setToken(String token) {
        this.token = token;
    }


}
