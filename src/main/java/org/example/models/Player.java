package org.example.models;

import org.example.models.Crafting.CraftingItem;
import org.example.models.cooking.Food;
import org.example.models.cooking.Recipe;
import org.example.models.enums.BackPackType;
import org.example.models.enums.ToolMaterial;
import org.example.models.enums.ToolType;
import org.example.models.map.PlayerMap;
import org.example.models.tools.BackPack;
import org.example.models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private PlayerMap playerMap;
    private User user;
    private final boolean isGuest = false;
    private int x;
    private int y;

    //Coin and wood and stone
    private int wood;
    private int stone;
    private double coin;

    //For friendShip
    private final HashMap<Player, Integer> friendShips = new HashMap<Player, Integer>();
    private final HashMap<Player, Talk> talk = new HashMap<Player, Talk>();


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

    public double getCoin() {
        return coin;
    }

    public void addCoin(double coin) {
        this.coin += coin;
    }

    //For Energy
    private double energy;
    private double maxEnergy = 200;
    private boolean hasPassedOutToday = false;

    //For BackPack
    private BackPack backPack = new BackPack(BackPackType.PrimaryBackpack);

    //For TrashCan & WaterStorage
    private Tool trashCan = new Tool(ToolType.TrashCan, ToolMaterial.Basic);
    private Tool wateringCan = new Tool(ToolType.WateringCan, ToolMaterial.Basic);

    private Game activeGame;

    private Tool currentTool;
    private int vegetableFarmed = 0;
    private ArrayList<Food> foods = new ArrayList<>();
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<Friends> friends = new ArrayList<>();
    private ArrayList<Ability> abilitesLearned = new ArrayList<>();
    private ArrayList<CraftingItem> craftingItems = new ArrayList<>();

    private double balance;
    private Player partner = null;
    private int daysSinceBrakUp = 0;
    private boolean isbrokenUp = false;

    public ArrayList<CraftingItem> getCraftingRecipes() {
        return craftingItems;
    }

    public void setCraftingRecipes(ArrayList<CraftingItem> craftingItems) {
        this.craftingItems = craftingItems;
    }


    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Player(User user, boolean isGuest, Game activeGame) {
        this.user = user;
        this.isGuest = isGuest;
        this.activeGame = activeGame;
        this.energy = maxEnergy;
        backPack.addItemToInventory(new Tool(ToolType.WateringCan, ToolMaterial.Basic));
);
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
    public double getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(double maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public boolean isHasPassedOutToday() {
        return hasPassedOutToday;
    }

    public void setHasPassedOutToday(boolean hasPassedOutToday) {
        this.hasPassedOutToday = hasPassedOutToday;
    }

    //For BackPack
    public BackPack getBackPack() {
        return backPack;
    }

    public void setBackPack(BackPack backPack) {
        this.backPack = backPack;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }


    //For TrashCan
    public Tool getTrashCan() {
        return trashCan;
    }

    public void setTrashCan(Tool trashCan) {
        this.trashCan = trashCan;
    }

    public Tool getWateringCan() {
        return wateringCan;
    }

    public void setWateringCan(Tool wateringCan) {
        this.wateringCan = wateringCan;
    }

    public HashMap<Player, Integer> getFriendShips() {
        return friendShips;
    }

    public void addFriendShips(Player player, int friendShip) {
        this.friendShips.put(player, friendShip);
    }

    public HashMap<Player, Talk> getTalk() {
        return talk;
    }

    public void addTalk(Player player, Talk talk) {
        this.talk.put(player, talk);
    }
}
