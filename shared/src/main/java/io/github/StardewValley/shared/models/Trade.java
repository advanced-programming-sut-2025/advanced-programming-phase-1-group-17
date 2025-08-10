package io.github.StardewValley.shared.models;

import java.util.HashMap;
import java.util.Map;


public class Trade {
    private String player1;
    private String player2;
    private HashMap<String, Integer> suggestions = new HashMap();
    private HashMap<String, Integer> requests = new HashMap();

    public Trade(String player1, Map<String, Integer> suggestions, String player2, Map<String, Integer> requests) {
        this.suggestions = new HashMap<>(suggestions);
        this.requests = new HashMap<>(requests);
        this.player1 = player1;
        this.player2 = player2;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public HashMap<String, Integer> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(HashMap<String, Integer> suggestions) {
        this.suggestions = suggestions;
    }

    public HashMap<String, Integer> getRequests() {
        return requests;
    }

    public void setRequests(HashMap<String, Integer> requests) {
        this.requests = requests;
    }
}
