package io.github.StardewValley.views;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import io.github.StardewValley.shared.models.game.GameDTO;
import io.github.StardewValley.shared.models.LobbyDto;
import io.github.StardewValley.shared.models.LobbyStatus;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LobbyApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/lobbies";
    private final String token;

    public LobbyApiClient(String jwtToken) {
        this.token = jwtToken;
    }

    public List<LobbyDto> listLobbies() throws Exception {
        URL url = new URL(BASE_URL + "/list");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        Scanner scanner = new Scanner(conn.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        JsonReader reader = new JsonReader();
        JsonValue json = reader.parse(response);


        List<LobbyDto> lobbies = new ArrayList<>();
        for (JsonValue lobbyJson : json) {
            List<String> players = new ArrayList<>();
            JsonValue playersJson = lobbyJson.get("playerUsernames");
            if (playersJson != null) {
                for (JsonValue playerJson : playersJson) {
                    players.add(playerJson.asString());
                }
            }
            LobbyStatus status = LobbyStatus.valueOf(lobbyJson.getString("status"));
            lobbies.add(new LobbyDto(
                lobbyJson.getLong("id"),
                lobbyJson.getString("name"),
                lobbyJson.getString("inviteCode"),
                lobbyJson.getBoolean("private"),
                lobbyJson.getBoolean("visible"), status,
                lobbyJson.getString("adminUsername"),
                players,
                lobbyJson.getString("password")
            ));

        }
        return lobbies;
    }

    public LobbyDto createLobby(String name, boolean isPrivate, boolean isVisible,String password) throws Exception {
        URL url = new URL(BASE_URL + "/create");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setDoOutput(true);

        String json = String.format("{\"name\":\"%s\", \"private\":%b, \"visible\":%b, \"password\":\"%s\"}", name, isPrivate, isVisible,password);
        conn.getOutputStream().write(json.getBytes());
        Scanner scanner = new Scanner(conn.getInputStream());

        String response = scanner.useDelimiter("\\A").next();
        scanner.close();
        JsonValue lobbyJson = new JsonReader().parse(response);
        List<String> players = new ArrayList<>();
        JsonValue playersJson = lobbyJson.get("playerUsernames");
        if (playersJson != null) {
            for (JsonValue playerJson : playersJson) {
                players.add(playerJson.asString());
            }
        }


        return new LobbyDto(
            lobbyJson.getLong("id"),
            lobbyJson.getString("name"),
            lobbyJson.getString("inviteCode"),
            lobbyJson.getBoolean("private"),
            lobbyJson.getBoolean("visible"),
            LobbyStatus.WAITING,
            lobbyJson.getString("adminUsername"),
            players,
            lobbyJson.getString("password")
        );
    }

    public LobbyDto joinLobbyByCode(String code) throws Exception {
        URL url = new URL(BASE_URL + "/join?inviteCode=" + code);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        Scanner scanner = new Scanner(conn.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        JsonValue lobbyJson = new JsonReader().parse(response);
        List<String> players = new ArrayList<>();
        JsonValue playersJson = lobbyJson.get("playerUsernames");
        if (playersJson != null) {
            for (JsonValue playerJson : playersJson) {
                players.add(playerJson.asString());
            }
        }

        LobbyStatus status = LobbyStatus.valueOf(lobbyJson.getString("status"));
        return new LobbyDto(
            lobbyJson.getLong("id"),
            lobbyJson.getString("name"),
            lobbyJson.getString("inviteCode"),
            lobbyJson.getBoolean("private"),
            lobbyJson.getBoolean("visible"), status,
            lobbyJson.getString("adminUsername"),
            players,
            lobbyJson.getString("password")
        );
    }

    public GameDTO startGame(Long lobbyId) throws Exception {
        URL url = new URL(BASE_URL + "/start?lobbyId=" + lobbyId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setDoInput(true);
        conn.connect();

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed to start game. Code: " + conn.getResponseCode());
        }

        Scanner scanner = new Scanner(conn.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        JsonValue json = new JsonReader().parse(response);

        GameDTO game = parseGameDto(json);
        return game;
    }
    private GameDTO parseGameDto(JsonValue json) {
        GameDTO dto = new GameDTO();
        return dto;
    }
    public LobbyDto getLobbyByInviteCode(Long Id) throws Exception {
        URL url = new URL(BASE_URL + "/code/" + Id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        Scanner scanner = new Scanner(conn.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        JsonValue lobbyJson = new JsonReader().parse(response);

        List<String> players = new ArrayList<>();
        JsonValue playersJson = lobbyJson.get("playerUsernames");
        if (playersJson != null) {
            for (JsonValue playerJson : playersJson) {
                players.add(playerJson.asString());
            }
        }
        LobbyStatus status = LobbyStatus.valueOf(lobbyJson.getString("status"));
        return new LobbyDto(
            lobbyJson.getLong("id"),
            lobbyJson.getString("name"),
            lobbyJson.getString("inviteCode"),
            lobbyJson.getBoolean("private"),
            lobbyJson.getBoolean("visible"),
            status,
            lobbyJson.getString("adminUsername"),
            players,
            lobbyJson.getString("password")
        );
    }
    public void leaveLobby(Long lobbyId) throws Exception {
        String urlStr = BASE_URL + "/leave?lobbyId=" + lobbyId;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setDoOutput(true);
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed to leave lobby. HTTP code: " + responseCode);
        }

        conn.disconnect();
    }
    public void changeAdmin(Long lobbyId) throws Exception {
        String urlStr = BASE_URL + "/changeAdmin?lobbyId=" + lobbyId;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setDoOutput(true);
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed to leave lobby. HTTP code: " + responseCode);
        }

        conn.disconnect();
    }

}
