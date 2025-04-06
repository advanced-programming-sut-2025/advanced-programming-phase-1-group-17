package models;

import models.enums.WeatherType;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    private Player currentPlayer;
    private TimeAndDate date;
    private int currentPlayingPlayerIndex = 0;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private static ArrayList<PlayerMap> playerMaps = new ArrayList<>();

    public Player getPlayerByPlayerMap(PlayerMap playerMap) {
        for (Player player : players) {
            if (player.getPlayerMap().equals(playerMap)) {
                return player;
            }
        }
        return null;
    }

    public void SwitchPlayer() {
        if (currentPlayer.equals(players.get(3))) {
            date.increaseHour();
            currentPlayer = players.get(0);
            currentPlayingPlayerIndex = 0;
        } else {
            currentPlayer = players.get(++currentPlayingPlayerIndex);
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public static ArrayList<PlayerMap> getPlayerMaps() {
        return playerMaps;
    }

    public static void setPlayerMaps(ArrayList<PlayerMap> playerMaps) {
        Game.playerMaps = playerMaps;
    }
}
