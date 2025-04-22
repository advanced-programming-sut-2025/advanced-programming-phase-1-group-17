package org.example.models;

import org.example.models.cooking.Food;
import org.example.models.cooking.Recipe;
import org.example.models.enums.BackPackType;
import org.example.models.map.PlayerMap;
import org.example.models.tools.BackPack;
import org.example.models.tools.Tool;

import java.util.ArrayList;

public class Player {
    private PlayerMap playerMap;
    private User user;
    private final boolean isGuest;
    private int x;
    private int y;
    //Coin and wood and stone
    private int wood;
    private int stone;
    private int coin;

    public int getWood() {
        return wood;
    }

    public void addWood(int wood) {
        this.wood += wood;
    }

    public int getStone() {
        return stone;
    }

    public void addStone(int stone) {
        this.stone += stone;
    }

    public int getCoin() {
        return coin;
    }

    public void addCoin(int coin) {
        this.coin += coin;
    }

    //For Energy
    private double energy;
    private double maxEnergy = 200;
    public boolean hasPassedOutToday = false;

    //For BackPack
    private BackPack backPack = new BackPack(BackPackType.PrimaryBackpack);

    private Game activeGame;

    private Tool currentTool;
    private int vegetableFarmed = 0;
    private ArrayList<Food> foods = new ArrayList<>();
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<Friends> friends = new ArrayList<>();
    private ArrayList<Ability> abilitesLearned = new ArrayList<>();

    private double balance;
    private Player partner = null;
    private int daysSinceBrakUp = 0;
    private boolean isbrokenUp = false;

    public Player(User user, boolean isGuest, Game activeGame) {
        this.user = user;
        this.isGuest = isGuest;
        this.activeGame = activeGame;
        this.energy = maxEnergy;
    }

    public void setInitialEnergyForTomorrow(boolean isPassedOut) {
        if (isPassedOut) {
            energy = maxEnergy * 0.75;
        } else {
            energy = maxEnergy;
        }
    }

    public void passOut() {
        hasPassedOutToday = true;
        App.getCurrentGame().switchPlayer();
        hasPassedOutToday = false;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public PlayerMap getPlayerMap() {
        return playerMap;
    }
    public void setPlayerMap(PlayerMap playerMap) {
        this.playerMap = playerMap;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //For Energy
    public double getMaxEnergy() { return maxEnergy; }

    public void setMaxEnergy(double maxEnergy) { this.maxEnergy = maxEnergy; }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }


    //For BackPack
    public BackPack getBackPack() { return backPack; }

    public void setBackPack(BackPack backPack) { this.backPack = backPack; }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }


}
