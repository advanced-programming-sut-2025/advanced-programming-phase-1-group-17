package org.example.models;

import org.example.models.NPCS.NPC;
import org.example.models.artisan.ArtisanProduct;
import org.example.models.cooking.*;
import org.example.models.crafting.CraftingItemType;
import org.example.models.crafting.CraftingRecipe;
import org.example.models.enums.BackPackType;
import org.example.models.tools.*;
import org.example.models.map.PlayerMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Player {
    private PlayerMap playerMap;
    private User user;
    private boolean isGuest = false;
    private int x;
    private int y;
    private Buff buff;

    //For friendShip
    private final HashMap<Player, Integer> friendShips = new HashMap<Player, Integer>();
    private final HashMap<Player, Talk> talk = new HashMap<Player, Talk>();


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
    private BackPack backPack = new BackPack(BackPackType.PrimaryBackpack, this);

    //For TrashCan & WaterStorage
    private Tool trashCan = new Tool(ToolType.TrashCan, ToolMaterial.Basic, null);
    private Tool wateringCan = new Tool(ToolType.WateringCan, ToolMaterial.Basic, null);
    private Tool currentTool;

    private int vegetableFarmed = 0;
    private ArrayList<Food> foods = new ArrayList<>();
    private HashSet<Recipe> recipes = new HashSet<>();
    private ArrayList<Friends> friends = new ArrayList<>();
    private Ability abilities = new Ability(this);
    private HashSet<CraftingRecipe> craftingRecipes = new HashSet<>();

    private double balance;
    private int daysSinceBrakUp = 0;



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

    public HashSet<CraftingRecipe> getCraftingRecipes() {
        return craftingRecipes;
    }

    public void setCraftingRecipes(HashSet<CraftingRecipe> craftingRecipes) {
        this.craftingRecipes = craftingRecipes;
    }

    public HashSet<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(HashSet<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Player(User user, boolean isGuest) {
        this.user = user;
        this.isGuest = isGuest;
        this.energy = maxEnergy;
        Tool wateringCan = new Tool(ToolType.WateringCan, ToolMaterial.Basic, null);
        backPack.addItemToInventory(wateringCan);
        backPack.addItemToInventory(new Tool(ToolType.Scythe, null, null));
        backPack.addItemToInventory(new Tool(ToolType.Hoe, ToolMaterial.Basic, null));
        backPack.addItemToInventory(new Tool(ToolType.Pickaxe, ToolMaterial.Basic, null));
        backPack.addItemToInventory(new Tool(ToolType.Axe, ToolMaterial.Basic, null));
        this.currentTool = wateringCan;
        this.getCraftingRecipes().add(new CraftingRecipe(CraftingItemType.MegaBomb));
        backPack.addItemToInventory(new Tool(ToolType.FishingPole, null, FishingPoleType.IridiumFishingPole));
        this.getRecipes().add(new Recipe(FoodType.MakiRoll));
        this.getRecipes().add(new Recipe(FoodType.FarmersLunch));
        this.buff = new Buff(BuffType.None,0);
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
    private int temporaryMaxEnergyBoost = 0;
    private int temporaryBoostRemainingHours = 0;

    public double getMaxEnergy() {
        return maxEnergy + temporaryMaxEnergyBoost;
    }

    public void applyTemporaryMaxEnergyBoost(int boostAmount, int hours) {
        this.temporaryMaxEnergyBoost = boostAmount;
        this.temporaryBoostRemainingHours = hours ;
    }

    public void updateTemporaryBoostTimer() {
        if (temporaryBoostRemainingHours > 0) {
            temporaryBoostRemainingHours--;
            if (temporaryBoostRemainingHours == 0) {
                temporaryMaxEnergyBoost = 0;

            }
        }
    }


    public void setMaxEnergy(double maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double amount) {
        if(amount < this.energy) {
            this.energy = amount;
        }
        if(amount > this.getMaxEnergy()){
            return;
        }
        amount = Math.min(amount, this.getMaxEnergy());
        this.energy = amount;
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

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }
    public void updateBuff() {
        if (buff != null && buff.getBuffType() != BuffType.None) {
            if(buff.getDuration()>0) buff.setDuration(buff.getDuration() -1 );
            if (buff.getDuration() <= 0) {
                buff = new Buff(BuffType.None, 0);
            }
        }
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void upgradeTrashCan() {
        ToolMaterial material = trashCan.getMaterial();
        if (material.equals(ToolMaterial.Basic))
            trashCan = new Tool(ToolType.TrashCan, ToolMaterial.Copper, null);
        else if (material.equals(ToolMaterial.Copper))
            trashCan = new Tool(ToolType.TrashCan, ToolMaterial.Iron, null);
        else if (material.equals(ToolMaterial.Iron))
            trashCan = new Tool(ToolType.TrashCan, ToolMaterial.Gold, null);
        else if (material.equals(ToolMaterial.Gold))
            trashCan = new Tool(ToolType.TrashCan, ToolMaterial.Iridium, null);
    }
}
