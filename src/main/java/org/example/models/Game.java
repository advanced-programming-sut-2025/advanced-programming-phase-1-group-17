package org.example.models;


import org.example.models.map.GameMap;
import org.example.models.map.PlayerMap;
import org.example.models.map.Tile;

import java.util.ArrayList;

public class Game {
    private Player creator;
    private Player currentPlayingPlayer;
    private TimeAndDate date = new TimeAndDate();
    private int currentPlayingPlayerIndex = 0;
    private ArrayList<Player> players = new ArrayList<Player>();
    private GameMap gameMap;


    public Game(User user1, User user2, User user3) {
        players.add(creator = new Player(App.getLoggedInUser(), false));
        players.add(new Player(user1, user1.getUsername().startsWith("guest")));
        players.add(new Player(user2, user2.getUsername().startsWith("guest")));
        players.add(new Player(user3, user3.getUsername().startsWith("guest")));
        App.setCurrentGame(this);
        this.gameMap = new GameMap(players);
        App.getCurrentGame().setCurrentPlayingPlayer(creator);
    }

    public Player getPlayerByPlayerMap(PlayerMap playerMap) {
        for (Player player : players) {
            if (player.getPlayerMap().equals(playerMap)) {
                return player;
            }
        }
        return null;
    }

    public Player getCurrentPlayingPlayer() {
        return currentPlayingPlayer;
    }

    public void switchPlayer() {
        currentPlayingPlayer.setInitialEnergyForTomorrow(currentPlayingPlayer.isHasPassedOutToday());
        if (currentPlayingPlayer.getEnergy() == Double.POSITIVE_INFINITY)
            currentPlayingPlayer.setEnergy(currentPlayingPlayer.getMaxEnergy());
        if (currentPlayingPlayer.equals(players.get(3))) {
            date.increaseHour();
            currentPlayingPlayer = players.get(0);
            currentPlayingPlayerIndex = 0;
        } else {
            currentPlayingPlayer = players.get(++currentPlayingPlayerIndex);
        }
    }

    public void setCurrentPlayingPlayer(Player currentPlayingPlayer) {
        this.currentPlayingPlayer = currentPlayingPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public TimeAndDate getDate() {
        return date;
    }

    public void setDate(TimeAndDate date) {
        this.date = date;
    }

    public Player getCreator() {
        return creator;
    }

    public void setCreator(Player creator) {
        this.creator = creator;
    }

    public int getCurrentPlayingPlayerIndex() {
        return currentPlayingPlayerIndex;
    }

    public void setCurrentPlayingPlayerIndex(int currentPlayingPlayerIndex) {
        this.currentPlayingPlayerIndex = currentPlayingPlayerIndex;
    }

    public Tile getTileByIndex(int x , int y) {
        for (PlayerMap playerMap : gameMap.getPlayerMaps()) {
            for (Tile tile : playerMap.getTiles()) {
                if (tile.getX() == x && tile.getY() == y) {
                    return tile;
                }
            }
        }
        return null;
    }



}
