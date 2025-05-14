package org.example.models;


import org.example.models.NPCS.*;
import org.example.models.cooking.FoodType;
import org.example.models.cooking.Recipe;
import org.example.models.crafting.CraftingItemType;
import org.example.models.crafting.CraftingRecipe;
import org.example.models.map.GameMap;
import org.example.models.map.GreenHouse;
import org.example.models.map.PlayerMap;
import org.example.models.map.Tile;
import org.example.models.market.StoreManager;

import java.util.ArrayList;

public class Game {
    private Player creator;
    private Player currentPlayingPlayer;
    private TimeAndDate date = new TimeAndDate();
    private int currentPlayingPlayerIndex = 0;
    private ArrayList<Player> players = new ArrayList<Player>();
    private GameMap gameMap;
    private ArrayList<NPC> NPCs = new ArrayList<>();
    private StoreManager storeManager = new StoreManager();
    private ArrayList<GreenHouse>GreenHouses = new ArrayList<>();


    public Game(User user1, User user2, User user3) {
        App.getLoggedInUser().setActiveGame(this);
        user1.setActiveGame(this);
        user2.setActiveGame(this);
        user3.setActiveGame(this);
        players.add(creator = new Player(App.getLoggedInUser(), false));
        players.add(new Player(user1, user1.getUsername().startsWith("guest")));
        players.add(new Player(user2, user2.getUsername().startsWith("guest")));
        players.add(new Player(user3, user3.getUsername().startsWith("guest")));
        addNPCs(new Abigail());
        addNPCs(new Harvey());
        addNPCs(new Lia());
        addNPCs(new Robin());
        addNPCs(new Sebastian());
        for (Player player : players) {
            for (NPC npc : NPCs) {
                player.setFriendShipsWithNPCs(npc);
                player.setTalkedNPCToday(npc);
                player.setGiftNPCToday(npc);
            }
        }
        for (Player p : players) {
            p.getUser().setNumOfPlay(p.getUser().getNumOfPlay() + 1);
        }
        for (int i = 0; i < players.size(); i++) {
            if (i != 0) {
                players.get(0).addGift(players.get(i));
                players.get(0).addTalk(players.get(i),new Talk(players.get(i)));
                players.get(0).addFriendShips(players.get(i), 0);
            }
        }
        for (int i = 0; i < players.size(); i++) {
            if (i != 1) {
                players.get(1).addGift(players.get(i));
                players.get(1).addTalk(players.get(i),new Talk(players.get(i)));
                players.get(1).addFriendShips(players.get(i), 0);
            }
        }
        for (int i = 0; i < players.size(); i++) {
            if (i != 2) {
                players.get(2).addGift(players.get(i));
                players.get(2).addTalk(players.get(i),new Talk(players.get(i)));
                players.get(2).addFriendShips(players.get(i), 0);
            }
        }
        for (int i = 0; i < players.size(); i++) {
            if (i != 3) {
                players.get(3).addGift(players.get(i));
                players.get(3).addTalk(players.get(i),new Talk(players.get(i)));
                players.get(3).addFriendShips(players.get(i), 0);
            }
        }
        App.setCurrentGame(this);
        this.gameMap = new GameMap(players);
        App.getCurrentGame().setCurrentPlayingPlayer(creator);
        this.storeManager.initializeStores();
        //cooking recipes
        for(Player player : App.getCurrentGame().getPlayers()){
            player.getRecipes().add(new Recipe(FoodType.FriedEgg));
            player.getRecipes().add(new Recipe(FoodType.BakedFish));
            player.getRecipes().add(new Recipe(FoodType.Salad));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.Furnace));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.Scarecrow));
            player.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.MayonnaiseMachine));
        }
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

    public StoreManager getStoreManager() {
        return storeManager;
    }
    public ArrayList<NPC> getNPCs() {
        return NPCs;
    }

    public void addNPCs(NPC npc) {
        this.NPCs.add(npc);
    }
    public NPC getNPC(String npcName) {
        for (NPC npc : NPCs) {
            if (npc.getName().equals(npcName)) {
                return npc;
            }
        }
        return null;
    }

    public ArrayList<GreenHouse> getGreenHouses() {
        return GreenHouses;
    }

    public void addGreenHouses(GreenHouse greenHouse) {
        GreenHouses.add(greenHouse);
    }
}
