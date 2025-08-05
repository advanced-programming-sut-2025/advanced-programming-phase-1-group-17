package io.github.StardewValley.controllers.helperControllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.NPCS.Quest;

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
    private static final String BASE_URL = "http://%s:%d/api/gameState".formatted(Main.getServerIP(), Main.getServerPort());
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
        String urlString = "http://%s:%d/api/auth/getUserByUsername".formatted(Main.getServerIP(), Main.getServerPort());
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


    public Result handleClick(int dx, int dy) throws Exception {
        URL url = new URL(BASE_URL + "/game/handleClick");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        String jsonInput = String.format("{\"dx\":%d,\"dy\":%d}", dx, dy);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        if (conn.getResponseCode() == 200) {
            try (InputStream inputStream = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(inputStream, Result.class);
            }
        } else {
            throw new RuntimeException("Failed to handle click: " + conn.getResponseCode());
        }
    }

    public void pickForaging(int dx, int dy) throws Exception {
        URL url = new URL(BASE_URL + "/game/Foraging/pickForaging");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        String jsonInput = String.format("{\"dx\":%d,\"dy\":%d}", dx, dy);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed to pick Foraging: " + conn.getResponseCode());
        }
    }

    public String friendship(String userNameOfPlayer) {
        try {
            String baseUrl = BASE_URL + "/friendship";
            String params = "?userNameOfPlayer=" + URLEncoder.encode(userNameOfPlayer, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).getMessage();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public Result talk(String username, String massage) {
        try {
            String baseUrl = BASE_URL + "/talk";
            String params = "?username=" + URLEncoder.encode(username, "UTF-8")
                + "&massage=" + URLEncoder.encode(massage, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }

    public String talkHistory(String username) {
        try {
            String baseUrl = BASE_URL + "/talkHistory";
            String params = "?username=" + URLEncoder.encode(username, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).getMessage();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public Result gift(String username, String item, String amount) {
        try {
            String baseUrl = BASE_URL + "/gift";
            String params = "?username=" + URLEncoder.encode(username, "UTF-8")
                +  "&massage=" + URLEncoder.encode(item, "UTF-8")
                + "&amount=" + URLEncoder.encode(amount, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }

    public Result giftList() {
        try {
            String baseUrl = BASE_URL + "/giftList";
            URL url = new URL(baseUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }

    public Result giftRate(String giftNumber, String rate) {
        try {
            String baseUrl = BASE_URL + "/giftRate";
            String params = "?username=" + URLEncoder.encode(giftNumber, "UTF-8")
                 + "&massage=" + URLEncoder.encode(rate, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }

    public String giftHistory(String username) {
        try {
            String baseUrl = BASE_URL + "/giftHistory";
            String params = "?username=" + URLEncoder.encode(username, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).getMessage();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public Result hug(String username) {
        try {
            String baseUrl = BASE_URL + "/hug";
            String params = "?username=" + URLEncoder.encode(username, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }

    public Result flower(String username) {
        try {
            String baseUrl = BASE_URL + "/flower";
            String params = "?username=" + URLEncoder.encode(username, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }

    public Result askMarriage(String username, String ring) {
        try {
            String baseUrl = BASE_URL + "/askMarriage";
            String params = "?username=" + URLEncoder.encode(username, "UTF-8")
                + "&ring=" + URLEncoder.encode(ring, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }

    public Result respond(String accept, String username) {
        try {
            String baseUrl = BASE_URL + "/respond";
            String params = "?accept=" + URLEncoder.encode(accept, "UTF-8")
                + "&username=" + URLEncoder.encode(username, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }

    public Result giftNPC(String npc1, String item, String amount) {
        try {
            String baseUrl = BASE_URL + "/giftNPC";
            String params = "?npc1=" + URLEncoder.encode(npc1, "UTF-8")
                + "&item=" + URLEncoder.encode(item, "UTF-8")
                + "&amount=" + URLEncoder.encode(amount, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }

    public String friendshipNPCList(String npc1) {
        try {
            String baseUrl = BASE_URL + "/friendshipNPCList";
            String params = "?npc1=" + URLEncoder.encode(npc1, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).getMessage();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public Result questsList() {
        try {
            String baseUrl = BASE_URL + "/questsList";
            URL url = new URL(baseUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }

    public Result questFinish(String index) {
        try {
            String baseUrl = BASE_URL + "/questFinish";
            String params = "?index=" + URLEncoder.encode(index, "UTF-8");
            URL url = new URL(baseUrl + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }

    public Result showMessage() {
        try {
            String baseUrl = BASE_URL + "/showMessage";
            URL url = new URL(baseUrl );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }

    public Result deleteMessage(int index) {
        try {
            String baseUrl = BASE_URL + "/deleteMessage";
            String params = "?index" + index;
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class);
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Exception: " + e.getMessage());
        }
    }
    public String getNearbyNPC() {
        try {
            String baseUrl = BASE_URL + "/getNearbyNPC";
            URL url = new URL(baseUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).getMessage();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public String getNearbyPlayer() {
        try {
            String baseUrl = BASE_URL + "/getNearbyPlayer";
            URL url = new URL(baseUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).getMessage();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public String getGender(String username) {
        try {
            String baseUrl = BASE_URL + "/getGender";
            String params = "?username" + URLEncoder.encode(username, "UTF-8");
            URL url = new URL(baseUrl + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).getMessage();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public String getDialogueTextNPCByName(String Name) {
        try {
            String baseUrl = BASE_URL + "/getDialogueTextNPCByName";
            String params = "?Name" + URLEncoder.encode(Name, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).getMessage();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public PlayerDto getPlayerDTOByUserName(String username){
        try {
            String baseUrl = BASE_URL + "/getPlayerDTOByUserName";
            String params = "?username" + URLEncoder.encode(username, "UTF-8");
            URL url = new URL(baseUrl + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream inputStream = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(inputStream, PlayerDto.class);
                }
            } else {
                throw new RuntimeException("Failed to update player state: " + conn.getResponseCode());
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public void setNewMessage(boolean newMessage) {
        try {
            String baseUrl = BASE_URL + "/setNewMessage";
            String params = "?newMessage" + URLEncoder.encode(String.valueOf(newMessage), "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String tradeHistory(){
        try {
            String baseUrl = BASE_URL + "/tradeHistory";
            URL url = new URL(baseUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).getMessage();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public String tradeList(){
        try {
            String baseUrl = BASE_URL + "/tradeList";
            URL url = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).getMessage();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public String getQuestWithIndex(String NpcName, int index){
        try {
            String baseUrl = BASE_URL + "/getQuestWithIndex";
            String params = "?NpcName" + URLEncoder.encode(NpcName, "UTF-8")
                + "&index" + index;
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).getMessage();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }






}
