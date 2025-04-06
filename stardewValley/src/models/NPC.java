package models;

import java.util.ArrayList;
import java.util.HashMap;

abstract public class NPC {
    private String name;
    private HashMap<Player,Integer> friendshipLevel = new HashMap<>();
    private HashMap<Player,Integer> friendshipPoint;
    private ArrayList<Tile> tiles;
    private ArrayList<Quest> quests=new ArrayList<>();

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    private ArrayList<Product> favorites;
    private ArrayList<Product> requests = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public ArrayList<Product> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<Product> favorites) {
        this.favorites = favorites;
    }

    public ArrayList<Product> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Product> requests) {
        this.requests = requests;
    }

    public ArrayList<Product> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<Product> rewards) {
        this.rewards = rewards;
    }

    public void sendGift() {
    }

    private ArrayList<Product> rewards = new ArrayList<>();


}
