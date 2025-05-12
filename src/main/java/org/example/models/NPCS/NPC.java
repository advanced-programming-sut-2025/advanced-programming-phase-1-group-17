package org.example.models.NPCS;

import org.example.models.BackPackable;
import org.example.models.Player;
import org.example.models.Product;
import org.example.models.User;
import org.example.models.map.Tile;

import java.util.ArrayList;
import java.util.HashMap;

abstract public class NPC {
    private int x;
    private int y;
    private static User fatherUser;
    private static Player fatherPlayer;
    private String name;
    private String job;
    private ArrayList<Quest> quests =new ArrayList<>();
    private ArrayList<String> favorites;

    private HashMap<String, String> dialogue = new HashMap<>();
    private ArrayList<Quest> requests= new ArrayList<>();
    public ArrayList<Quest> getRequests(){
        return requests;
    }

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

    public ArrayList<String> getFavorites() {
        return favorites;
    }
    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }
    public void giveReward(Player player, int index){};


    public ArrayList<Product> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<Product> rewards) {
        this.rewards = rewards;
    }

    public void sendGift() {
    }

    private ArrayList<Product> rewards = new ArrayList<>();

    public static User getFatherUser() {
        return fatherUser;
    }

    public static void setFatherUser(User fatherUser) {
        NPC.fatherUser = fatherUser;
    }

    public static Player getFatherPlayer() {
        return fatherPlayer;
    }

    public static void setFatherPlayer(Player fatherPlayer) {
        NPC.fatherPlayer = fatherPlayer;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public HashMap<String, String> getDialogue() {
        return dialogue;
    }

}
