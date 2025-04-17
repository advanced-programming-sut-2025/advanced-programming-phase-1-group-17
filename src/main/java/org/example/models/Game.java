package org.example.models;


import org.example.models.map.GameMap;
import org.example.models.map.PlayerMap;
import org.example.models.map.Tile;

import java.util.ArrayList;

public class Game {
    private Player creator;
    private Player currentPlayer;
    private TimeAndDate date;
    private int currentPlayingPlayerIndex = 0;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private GameMap gameMap;


    public Game(User user1, User user2, User user3) {
        players.add(creator = new Player(App.getLoggedInUser(), false, this));
        players.add(new Player(user1, user1.getUsername().startsWith("guest"), this));
        players.add(new Player(user2, user2.getUsername().startsWith("guest"), this));
        players.add(new Player(user3, user3.getUsername().startsWith("guest"), this));
        this.gameMap = new GameMap(players);
    }

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

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }
}
