package models;

import models.cooking.Food;
import models.cooking.Recipe;
import models.enums.BackPackType;
import models.map.PlayerMap;
import models.tools.BackPack;
import models.tools.Tool;

import java.util.ArrayList;

public class Player {
    private PlayerMap playerMap;
    private User user;
    private boolean isGuest;
    private int x;
    private int y;

    //For Energy
    private double energy;
    private double maxEnergy = 200;

    //For BackPack
    private BackPack backPack = new BackPack(BackPackType.PrimaryBackpack);

    private int farmingAbility;
    private int miningAbility;
    private int foragingAbility;
    private int fishingAbility;
    private int farmingLevel;
    private int miningLevel;
    private int foragingLevel;
    private int fishingLevel;

    private Game activeGame;

    private Tool currentTool;
    private int vegetableFarmed = 0;
    private ArrayList<Food> foods = new ArrayList<>();
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<Friends> friends = new ArrayList<>();
    private ArrayList<Ability> abilitesLearned = new ArrayList<>();

    int[] levels = {50, 150, 250, 350, 450};
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

    public int getFarmingLevel() {
        return farmingLevel;
    }

    public void setFarmingLevel(int farmingLevel) {
        this.farmingLevel = farmingLevel;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public void setMiningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
    }

    public int getForagingLevel() {
        return foragingLevel;
    }

    public void setForagingLevel(int foragingLevel) {
        this.foragingLevel = foragingLevel;
    }

    public int getFishingLevel() {
        return fishingLevel;
    }

    public void setFishingLevel(int fishingLevel) {
        this.fishingLevel = fishingLevel;
    }

    public int getFarming() {
        return farmingAbility;
    }

    public void addFarming(int farming) {
        this.farmingAbility += farming;
    }

    public int getMining() {
        return miningAbility;
    }

    public void addMining(int mining) {
        this.miningAbility += mining;
    }

    public int getForaging() {
        return foragingAbility;
    }

    public void addForaging(int foraging) {
        this.foragingAbility += foraging;
    }

    public int getFishing() {
        return fishingAbility;
    }

    public void addFishing(int fishing) {
        this.fishingAbility += fishing;
    }


    public void passOut() {

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



}
