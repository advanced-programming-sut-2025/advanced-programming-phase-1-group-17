package org.example.models;

import org.example.models.NPCS.NPC;
import org.example.models.artisan.ArtisanProduct;
import org.example.models.crafting.CraftingItem;
import org.example.models.cooking.Food;
import org.example.models.cooking.Recipe;
import org.example.models.enums.BackPackType;
import org.example.models.tools.ToolMaterial;
import org.example.models.tools.ToolType;
import org.example.models.map.PlayerMap;
import org.example.models.tools.BackPack;
import org.example.models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private PlayerMap playerMap;
    private User user;
    private boolean isGuest = false;
    private int x;
    private int y;

    //For friendShip
    private final HashMap<Player, Integer> friendShips = new HashMap<Player, Integer>();
    private final HashMap<Player, Talk> talk = new HashMap<Player, Talk>();


    private ArrayList<ArtisanProduct> artisanProductsInProgress = new ArrayList();
    private HashMap<Player, ArrayList<Gift>> gifts = new HashMap<Player, ArrayList<Gift>>();
    private ArrayList<message> messages = new ArrayList<>();
    private ArrayList<Trade> trades = new ArrayList<>();
    private ArrayList<Trade> tradeHistory = new ArrayList<>();
    private Player partner = this;
    private boolean interactionWithPartner = false;
    private int isbrokenUp = 0;
    //For NPC
    private HashMap<NPC, Integer> friendShipsWithNPCs = new HashMap<>();
    private HashMap<NPC, Boolean> talkedNPCToday = new HashMap<>();
    private HashMap<NPC, Boolean> giftNPCToday = new HashMap<>();


    //For Energy
    private double energy;
    private double maxEnergy = 200;
    private boolean hasPassedOutToday = false;

    //For BackPack
    private BackPack backPack = new BackPack(BackPackType.PrimaryBackpack);

    //For TrashCan & WaterStorage
    private Tool trashCan = new Tool(ToolType.TrashCan, ToolMaterial.Basic);
    private Tool wateringCan = new Tool(ToolType.WateringCan, ToolMaterial.Basic);
    private Tool currentTool;

    private int vegetableFarmed = 0;
    private ArrayList<Food> foods = new ArrayList<>();
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<Friends> friends = new ArrayList<>();
    private Ability abilities = new Ability();
    private ArrayList<CraftingItem> craftingItems = new ArrayList<>();

    private double balance;
    private int daysSinceBrakUp = 0;

    public ArrayList<CraftingItem> getCraftingRecipes() {
        return craftingItems;
    }

    public void setCraftingRecipes(ArrayList<CraftingItem> craftingItems) {
        this.craftingItems = craftingItems;
    }

    public int getVegetableFarmed() {
        return vegetableFarmed;
    }

    public void setVegetableFarmed(int vegetableFarmed) {
        this.vegetableFarmed = vegetableFarmed;
    }

    public Ability getAbilities() {
        return abilities;
    }

    public void setAbilities(Ability abilities) {
        this.abilities = abilities;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Player(User user, boolean isGuest) {
        this.user = user;
        this.isGuest = isGuest;
        this.energy = maxEnergy;
        Tool wateringCan = new Tool(ToolType.WateringCan, ToolMaterial.Basic);
        backPack.addItemToInventory(wateringCan);
        backPack.addItemToInventory(new Tool(ToolType.Scythe, null));
        backPack.addItemToInventory(new Tool(ToolType.Hoe, ToolMaterial.Basic));
        backPack.addItemToInventory(new Tool(ToolType.Pickaxe, ToolMaterial.Basic));
        backPack.addItemToInventory(new Tool(ToolType.Axe, ToolMaterial.Basic));
        backPack.setPlayer(this);
        this.currentTool = wateringCan;
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

    public ArrayList<ArtisanProduct> getArtisanProductsInProgress() {
        return artisanProductsInProgress;
    }

    public void setArtisanProductsInProgress(ArrayList<ArtisanProduct> artisanProductsInProgress) {
        this.artisanProductsInProgress = artisanProductsInProgress;
    }

    public void addGift(Player player) {
        this.gifts.put(player, new ArrayList<>());
    }

    public HashMap<Player, ArrayList<Gift>> getGifts() {
        return gifts;
    }

    public int getIsbrokenUp() {
        return isbrokenUp;
    }

    public void setIsbrokenUp(int isbrokenUp) {
        this.isbrokenUp = isbrokenUp;
    }

    public void setGifts(HashMap<Player, ArrayList<Gift>> gifts) {
        this.gifts = gifts;
    }

    public void addMessage(message message) {
        this.messages.add(message);
    }

    public ArrayList<message> getMessage() {
        return this.messages;
    }

    public String getStringMessage() {
        String message = "";
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getSender() != null) {
                message += (i + "- " + "SENDER" + " : " + messages.get(i).getSender().getUser().getUsername()
                        + "\n" + "message : " + messages.get(i).getMessage() + "\n");
            }
            else {
                message += (i + "- " + "SENDER(NPC)" + " : " + messages.get(i).getSenderNPC().getName()
                        + "\n" + "message : " + messages.get(i).getMessage() + "\n");
            }
        }
        return message;
    }

    public Player getPartner() {
        return partner;
    }

    public void setPartner(Player partner) {
        this.partner = partner;
    }

    public boolean isInteractionWithPartner() {
        return interactionWithPartner;
    }

    public void setInteractionWithPartner(boolean interactionWithPartner) {
        this.interactionWithPartner = interactionWithPartner;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void addTrades(Trade trade) {
        this.trades.add(trade);
    }

    public ArrayList<Trade> getTradeHistory() {
        return tradeHistory;
    }

    public void addTradeHistory(Trade trade) {
        this.tradeHistory.add(trade);
    }

    public HashMap<NPC, Integer> getFriendShipsWithNPCs() {
        return friendShipsWithNPCs;
    }
    public void setFriendShipsWithNPCs(NPC npc) {
        this.friendShipsWithNPCs.put(npc, 0);
    }

    public HashMap<NPC, Boolean> getTalkedNPCToday() {
        return talkedNPCToday;
    }
    public void setTalkedNPCToday(NPC npc) {
        this.talkedNPCToday.put(npc, false);
    }

    public HashMap<NPC, Boolean> getGiftNPCToday() {
        return giftNPCToday;
    }

    public void setGiftNPCToday(NPC npc) {
        this.giftNPCToday.put(npc,false);
    }
}
