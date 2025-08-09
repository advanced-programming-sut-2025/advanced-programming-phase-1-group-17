package io.github.StardewValley.controllers.helperControllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.cooking.CookResponseDTO;
import io.github.StardewValley.shared.models.cooking.FoodType;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.dto.*;
import io.github.StardewValley.shared.models.PlayerDto;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.TileDTO;
import io.github.StardewValley.shared.models.UserDTO;
import io.github.StardewValley.shared.models.backpack.BackpackableTypeDTO;
import io.github.StardewValley.shared.models.game.GameState;
import io.github.StardewValley.shared.models.market.ShopItemDTO;
import io.github.StardewValley.shared.models.market.StoreType;
import okhttp3.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStateApiClient {
    private static final String BASE_URL = "http://%s:%d/api/gameState".formatted(Main.getServerIP(), Main.getServerPort());
    private static final String AnimalURL = "http://%s:%d/api/animals".formatted(Main.getServerIP(), Main.getServerPort());

    private String token;

    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

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

    public ArrayList<AnimalDTO> getAllAnimals() throws Exception {
        URL url = new URL(AnimalURL + "/allAnimals"); // آدرس Endpoint در سرور
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.connect();

        if (conn.getResponseCode() == 200) {
            try (InputStream inputStream = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                // Jackson برای تبدیل JSON به یک لیست از آبجکت‌های پیچیده (مثل AnimalDTO)
                // به TypeReference نیاز دارد تا نوع دقیق لیست را بداند.
                return mapper.readValue(inputStream, new TypeReference<ArrayList<AnimalDTO>>() {
                });
            }
        } else {
            // اگر سرور خطایی برگرداند (مثل 404 یا 500)، یک Exception پرتاب کن
            throw new RuntimeException("Failed to fetch animals data: " + conn.getResponseCode());
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
            System.out.println("Error: " + responseCode + "2");
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
            System.out.println("Error: " + responseCode + "3");
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
                this.token = token;
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
            System.out.println("Error: " + responseCode + "4");
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
                this.token = token;
            }
            System.out.println("Password changed successfully");
        } else {
            System.out.println("Failed to change password: " + code);
        }
    }

    public HudDataDTO getHudData() throws Exception {
        URL url = new URL(BASE_URL + "/hudData");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.connect();

        if (conn.getResponseCode() == 200) {
            try (InputStream inputStream = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(inputStream, HudDataDTO.class);
            }
        } else {
            throw new RuntimeException("Failed to fetch HUD data: " + conn.getResponseCode());
        }
    }

    public CraftResponseDTO attemptCraft(CraftingItemType type) throws Exception {
        String urlString = BASE_URL + "/craft?itemTypeName=" + URLEncoder.encode(type.name(), "UTF-8");
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            try (InputStream inputStream = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(inputStream, CraftResponseDTO.class);
            }
        } else {
            throw new RuntimeException("Failed to craft item. Response code: " + responseCode);
        }
    }

    public CookResponseDTO attemptCook(FoodType type) throws Exception {
        String urlString = BASE_URL + "/cook?itemTypeName=" + URLEncoder.encode(type.name(), "UTF-8");
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            try (InputStream inputStream = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(inputStream, CookResponseDTO.class);
            }
        } else {
            throw new RuntimeException("Failed to cook item. Response code: " + responseCode);
        }
    }

    public void setToken(String token) {
        this.token = token;
    }


    public HandleWorldClickResponse handleWorldClick(float x, float y, int button) throws Exception {
        URL url = new URL(BASE_URL + "/game/handleClick");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        String jsonInput = String.format("{\"x\":%f,\"y\":%f, \"button\":%d}", x, y, button);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        if (conn.getResponseCode() == 200) {
            try (InputStream inputStream = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(inputStream, HandleWorldClickResponse.class);
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


    public Result handleCheatCode(String command) throws Exception {
        URL url = new URL(BASE_URL + "/game/cheatCode/handleCheatCode");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInput = mapper.writeValueAsString(Map.of("input", command));


        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        if (conn.getResponseCode() == 200) {
            try (InputStream inputStream = conn.getInputStream()) {
                ObjectMapper mapper2 = new ObjectMapper();
                return mapper2.readValue(inputStream, Result.class);
            }
        } else {
            throw new RuntimeException("Failed to handle click: " + conn.getResponseCode());
        }
    }

    public Result purchase(ShopItemDTO item, int count, StoreType storeType) {
        try {
            // 1. Build PurchaseRequest DTO
            PurchaseRequest requestDTO = new PurchaseRequest();
            requestDTO.setShopItemDTO(item); // You must implement this method
            requestDTO.setCount(count);
            requestDTO.setStoreType(storeType);

            // 2. Serialize to JSON
            String json = objectMapper.writeValueAsString(requestDTO);

            // 3. Build HTTP request
            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

            Request request = new Request.Builder()
                .url(BASE_URL + "/game/market/purchase")
                .addHeader("Authorization", token)
                .post(body)
                .build();

            // 4. Execute
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    return new Result(false, "Server error: " + response.code());
                }

                // 5. Parse response
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, Result.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Failed to send purchase request: " + e.getMessage());
        }
    }

    public GetMarketInventoryResponse getInventory(StoreType storeType) {
        try {
            GetMarketInventoryRequest requestDTO = new GetMarketInventoryRequest(storeType);
            // 2. Serialize to JSON
            String json = objectMapper.writeValueAsString(requestDTO);

            // 3. Build HTTP request
            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

            Request request = new Request.Builder()
                .url(BASE_URL + "/game/market/getInventory")
                .addHeader("Authorization", token)
                .post(body)
                .build();

            // 4. Execute
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new Exception("Server Error: " + response.code());
                }

                // 5. Parse response
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, GetMarketInventoryResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetBackpackItemsResponse getBackpackItems() {
        try {
            Request request = new Request.Builder()
                .url(BASE_URL + "/game/backpack/getBackpackItems")
                .addHeader("Authorization", token)
                .post(RequestBody.create(new byte[0], null)) // Empty body
                .build();

            // 4. Execute
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new Exception("Server Error: " + response.code());
                }

                // 5. Parse response
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, GetBackpackItemsResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void equipItem(BackpackableTypeDTO backPackableTypeDTO) {
        try {
            // 3. Build HTTP request
            RequestBody body = RequestBody.create(backPackableTypeDTO.getName(), MediaType.get("application/json"));

            Request request = new Request.Builder()
                .url(BASE_URL + "/game/backpack/equipItem")
                .addHeader("Authorization", token)
                .post(body)
                .build();

            // 4. Execute
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new Exception("Server Error: " + response.code());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String trashItem() {
        try {
            Request request = new Request.Builder()
                .url(BASE_URL + "/game/backpack/trashItem")
                .addHeader("Authorization", token)
                .build();

            // 4. Execute
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new Exception("Server Error: " + response.code());
                }

                // 5. Parse response
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
                    return mapper.readValue(is, Result.class).message();
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
                    return mapper.readValue(is, Result.class).message();
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
            String params = "?giftNumber=" + URLEncoder.encode(giftNumber, "UTF-8")
                + "&rate=" + URLEncoder.encode(rate, "UTF-8");
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
                    return mapper.readValue(is, Result.class).message();
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
                    return mapper.readValue(is, Result.class).message();
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
            String baseUrl = BASE_URL + "/get/getNearbyNPC";
            URL url = new URL(baseUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, String.class);
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
            String url = BASE_URL + "/get/getNearbyPlayer";

            Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .post(RequestBody.create("", null))
                .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    return "Error: HTTP " + response.code();
                }

                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
    }


    public String getGender(String username) {
        try {
            String baseUrl = BASE_URL + "/getGender";
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
                    return mapper.readValue(is, Result.class).message();
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
            String params = "?Name=" + URLEncoder.encode(Name, "UTF-8");
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).message();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public PlayerDto getPlayerDTOByUserName(String username) {
        try {
            String baseUrl = BASE_URL + "/getPlayerDTOByUserName";
            String params = "?username=" + URLEncoder.encode(username, "UTF-8");
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setNewMessage(boolean newMessage) {
        try {
            String baseUrl = BASE_URL + "/setNewMessage";
            String params = "?newMessage=" + URLEncoder.encode(String.valueOf(newMessage), "UTF-8");
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

    public String tradeHistory() {
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
                    return mapper.readValue(is, Result.class).message();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }



    public String getQuestWithIndex(String NpcName, int index) {
        try {
            String baseUrl = BASE_URL + "/getQuestWithIndex";
            String params = "?NpcName=" + URLEncoder.encode(NpcName, "UTF-8")
                + "&index=" + index;
            URL url = new URL(baseUrl + params);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream is = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(is, Result.class).message();
                }
            } else {
                throw new RuntimeException("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    public Result buildGreenHouse() {
        try {
            Request request = new Request.Builder()
                .url(BASE_URL + "/game/greenhouse/buildGreenhouse")
                .addHeader("Authorization", token)
                .post(RequestBody.create(new byte[0], null)) // Empty body
                .build();

            // 4. Execute
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new Exception("Server Error: " + response.code());
                }

                // 5. Parse response
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, Result.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean sellItem(TileDTO shippingBinTile, BackpackableTypeDTO itemType, int quantity) {
        try {
            SellItemRequest requestDTO = new SellItemRequest(shippingBinTile, quantity, itemType);
            // 2. Serialize to JSON
            String json = objectMapper.writeValueAsString(requestDTO);

            // 3. Build HTTP request
            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

            Request request = new Request.Builder()
                .url(BASE_URL + "/game/shippingBin/sellItem")
                .addHeader("Authorization", token)
                .post(body)
                .build();

            // 4. Execute
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new Exception("Server Error: " + response.code());
                }

                // 5. Parse response
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, Boolean.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean takeArtisanProduct(CraftingItemDTO craftingItem) {
        try {
            // 2. Serialize to JSON
            String json = objectMapper.writeValueAsString(craftingItem);

            // 3. Build HTTP request
            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

            Request request = new Request.Builder()
                .url(BASE_URL + "/game/artisanProduct/takeProduct")
                .addHeader("Authorization", token)
                .post(body)
                .build();

            // 4. Execute
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new Exception("Server Error: " + response.code());
                }

                // 5. Parse response
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, Boolean.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cancelArtisanProduct(CraftingItemDTO craftingItem) {
        try {
            // 2. Serialize to JSON
            String json = objectMapper.writeValueAsString(craftingItem);

            // 3. Build HTTP request
            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

            Request request = new Request.Builder()
                .url(BASE_URL + "/game/artisanProduct/cancelProduction")
                .addHeader("Authorization", token)
                .post(body)
                .build();

            // 4. Execute
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new Exception("Server Error: " + response.code());
                }

                // 5. Parse response
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, Boolean.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Result craftArtisan(ArrayList<BackpackableTypeDTO> selectedItems, CraftingItemDTO craftingItemDTO) {
        try {
            CraftArtisanRequest craftArtisanRequest = new CraftArtisanRequest(selectedItems, craftingItemDTO);
            // 2. Serialize to JSON
            String json = objectMapper.writeValueAsString(craftArtisanRequest);

            // 3. Build HTTP request
            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

            Request request = new Request.Builder()
                .url(BASE_URL + "/game/artisanProduct/craftArtisan")
                .addHeader("Authorization", token)
                .post(body)
                .build();

            // 4. Execute
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new Exception("Server Error: " + response.code());
                }

                // 5. Parse response
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, Result.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GameState getGameState(int minTileX, int maxTileX, int minTileY, int maxTileY) {
        try {
            // Build URL with query params
            String url = String.format("%s/game/map?minX=%d&maxX=%d&minY=%d&maxY=%d",
                BASE_URL, minTileX, maxTileX, minTileY, maxTileY);

            Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", token)
                .post(RequestBody.create("", null))
                .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new Exception("Server Error: " + response.code());
                }

                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, GameState.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Integer, ArrayList<Integer>> getPlayerHutsLocationsFromServer() {
        try {
            String urlStr = BASE_URL + "/sendMap";
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            if (code == 200) {
                ObjectMapper mapper = new ObjectMapper();
                try (InputStream is = conn.getInputStream()) {
                    return mapper.readValue(is, new TypeReference<Map<Integer, ArrayList<Integer>>>() {
                    });
                }
            } else {
                throw new RuntimeException("HTTP error: " + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public Map<Integer, ArrayList<Integer>> getNPCSHutsLocationsFromServer() {
        try {
            String urlStr = BASE_URL + "/sendNPCMap";
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            if (code == 200) {
                ObjectMapper mapper = new ObjectMapper();
                try (InputStream is = conn.getInputStream()) {
                    return mapper.readValue(is, new TypeReference<Map<Integer, ArrayList<Integer>>>() {
                    });
                }
            } else {
                throw new RuntimeException("HTTP error: " + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public boolean isStarted() {
        try {
            String urlStr = BASE_URL + "/isStarted";
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            if (code == 200) {
                ObjectMapper mapper = new ObjectMapper();
                try (InputStream is = conn.getInputStream()) {
                    return mapper.readValue(is, boolean.class);
                }
            } else {
                throw new RuntimeException("HTTP error: " + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<Integer, ArrayList<Integer>> getGreenHouseLocationsFromServer() {
        try {
            Request request = new Request.Builder()
                .url(BASE_URL + "/game/greenhouse/getGreenHouseLocations")
                .addHeader("Authorization", token)
                .post(RequestBody.create(new byte[0], null)) // Empty body
                .build();

            // 4. Execute
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new Exception("Server Error: " + response.code());
                }

                // 5. Parse response
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, new TypeReference<Map<Integer, ArrayList<Integer>>>() {
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public NPCdto getNPCDtoByIndex(int index) {
        try {
            String baseUrl = BASE_URL + "/getNPCDtoByIndex";
            String params = "?index=" + index;
            URL url = new URL(baseUrl + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream inputStream = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(inputStream, NPCdto.class);
                }
            } else {
                throw new RuntimeException("Failed to update player state: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TileDTO> getAllTiles() {
        try {
            String baseUrl = BASE_URL + "/getAllTiles";
            URL url = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream inputStream = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(inputStream, new TypeReference<List<TileDTO>>() {
                    });
                }
            } else {
                throw new RuntimeException("Failed to update player state: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void tradeRequest(String username) {
        try {
            String baseUrl = BASE_URL + "/tradeRequest";
            String params = "?username=" + username;
            URL url = new URL(baseUrl + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Failed to update player state: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Result initAcceptTradeRequest(String username) {
        try {
            String baseUrl = BASE_URL + "/initAcceptTradeRequest";
            String params = "?username=" + username;
            URL url = new URL(baseUrl + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream inputStream = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(inputStream, Result.class);
                }
            } else {
                throw new RuntimeException("Failed to update player state: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "-");
        }
    }

    public void setTargetPlayerToTrade(String username, int i) {
        try {
            String baseUrl = BASE_URL + "/setTargetPlayerToTrade";
            String params = "?username=" + username + "&i=" + i;
            URL url = new URL(baseUrl + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Failed to update player state: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptTrade(HashMap<String, Integer> suggestions,
                            HashMap<String, Integer> requests,
                            String username) {
        try {
            String baseUrl = BASE_URL + "/acceptTrade";
            Map<String, Object> tradeData = new HashMap<>();
            tradeData.put("suggestions", suggestions);
            tradeData.put("requests", requests);
            tradeData.put("username", username);
            String json = new com.google.gson.Gson().toJson(tradeData);
            URL url = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Failed to send trade data: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateRequestAndSuggestions(HashMap<String, Integer> suggestions,
                                            HashMap<String, Integer> requests) {
        try {
            String baseUrl = BASE_URL + "/updateRequestAndSuggestions";
            Map<String, Object> tradeData = new HashMap<>();
            tradeData.put("suggestions", suggestions);
            tradeData.put("requests", requests);
            String json = new com.google.gson.Gson().toJson(tradeData);
            URL url = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Failed to send trade data: " + responseCode);
            } else {
                System.out.println("Trade data sent successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> getSuggestions(String username) {
        try {
            String baseUrl = BASE_URL + "/getSuggestions";
            String params = "?username=" + username;
            URL url = new URL(baseUrl + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream inputStream = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(inputStream, new TypeReference<HashMap<String, Integer>>() {
                    });
                }
            } else {
                throw new RuntimeException("Failed to update player state: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<String, Integer> getRequests(String username) {
        try {
            String baseUrl = BASE_URL + "/getRequests";
            String params = "?username=" + username;
            URL url = new URL(baseUrl + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (InputStream inputStream = conn.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(inputStream, new TypeReference<HashMap<String, Integer>>() {
                    });
                }
            } else {
                throw new RuntimeException("Failed to update player state: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
