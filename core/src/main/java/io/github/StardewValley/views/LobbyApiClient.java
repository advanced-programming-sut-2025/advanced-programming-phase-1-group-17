package io.github.StardewValley.views;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import io.github.StardewValley.shared.models.LobbyDto;

import java.io.InputStream;
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
            lobbies.add(new LobbyDto(
                lobbyJson.getLong("id"),
                lobbyJson.getString("name"),
                lobbyJson.getString("inviteCode"),
                lobbyJson.getBoolean("isPrivate"),
                lobbyJson.getBoolean("isVisible"), // status null
                lobbyJson.getString("adminUsername"),
                null // playerUsernames
            ));
        }
        return lobbies;
    }
    public LobbyDto createLobby(String name, boolean isPrivate, boolean isVisible) throws Exception {
        URL url = new URL(BASE_URL + "/create");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setDoOutput(true);

        String json = String.format("{\"name\":\"%s\", \"private\":%b, \"visible\":%b}", name, isPrivate, isVisible);
        conn.getOutputStream().write(json.getBytes());
        if (conn.getResponseCode() >= 400) {
            InputStream errorStream = conn.getErrorStream();
            Scanner scanner = new Scanner(errorStream).useDelimiter("\\A");
            String errorResponse = scanner.hasNext() ? scanner.next() : "";
            scanner.close();
            throw new RuntimeException("Server error: " + conn.getResponseCode() + "\n" + errorResponse);
        }
        Scanner scanner = new Scanner(conn.getInputStream());

        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        JsonValue lobbyJson = new JsonReader().parse(response);

        return new LobbyDto(
            lobbyJson.getLong("id"),
            lobbyJson.getString("name"),
            lobbyJson.getString("inviteCode"),
            lobbyJson.getBoolean("isPrivate"),
            lobbyJson.getBoolean("isVisible"),
            //status null
            lobbyJson.getString("adminUsername"),
            null
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

        return new LobbyDto(
            lobbyJson.getLong("id"),
            lobbyJson.getString("name"),
            lobbyJson.getString("inviteCode"),
            lobbyJson.getBoolean("isPrivate"),
            lobbyJson.getBoolean("isVisible"),//status null
            lobbyJson.getString("adminUsername"),
            null
        );
    }
    public void startGame(Long lobbyId) throws Exception {
        URL url = new URL(BASE_URL + "/start?lobbyId=" + lobbyId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.connect();

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed to start game. Code: " + conn.getResponseCode());
        }
    }

}
