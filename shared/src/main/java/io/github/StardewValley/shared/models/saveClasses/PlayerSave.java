package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.dto.AnimalDTO;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.cooking.Buff;
import io.github.StardewValley.shared.models.cooking.Recipe;
import io.github.StardewValley.shared.models.crafting.CraftingRecipe;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PlayerSave {
    private UserDTO user;
    private boolean isGuest;
    private boolean isPassedOut;
    private int x;
    private int y;
    private Buff buff;
    private float speed;
    private double coin;
    private float animationTimer;
    private float passOutTimer;
    private ArrayList<AnimalDTO> animals;
    private boolean moved;
    private Player.Direction lastDirection;
    private Player.Direction currentDirection;

    //For friendShip
    private HashMap<String, Integer> friendShips = new HashMap<>();
    private HashMap<String, TalkSave> talk = new HashMap<>();
    private boolean newMessage;

    private HashMap<String, ArrayList<GiftSaved>> gifts = new HashMap<>();
    private ArrayList<MessageSave> Messages = new ArrayList<>();
    private ArrayList<Trade> trades = new ArrayList<>();
    private String partnerUsername;
    private boolean interactionWithPartner;
    private int isbrokenUp;

    //For NPC
    private ArrayList<NPCSave> friendShipsWithNPCs = new ArrayList<>();
    private ArrayList<Integer> friendShipsWithNPCValues = new ArrayList<>();

    private ArrayList<NPCSave> talkedNPCToday = new ArrayList<>();
    private ArrayList<Boolean> talkedNPCTodayValues = new ArrayList<>();

    private ArrayList<NPCSave> giftNPCToday = new ArrayList<>();
    private ArrayList<Boolean> giftNPCTodayValue = new ArrayList<>();

    //For Energy
    private double energy;
    private double maxEnergy;
    private boolean isEnergyUnlimited;
    private boolean hasPassedOutToday;

    //For BackPack
    private BackPackSave backPack;

    //For TrashCan & WaterStorage
    private Tool trashCan;
    private Tool wateringCan;
    private Tool currentTool;
    private BackPackableSave equippedItem;

    private HashSet<Recipe> recipes = new HashSet<>();
    private AbilityDTO abilities;
    private HashSet<CraftingRecipe> craftingRecipes = new HashSet<>();

    private int daysSinceBrakeUp;

    public PlayerSave() {}

    public PlayerSave(Player player) {
        this.user = player.getUser();
        this.isGuest = player.isGuest();
        this.isPassedOut = player.isPassedOut();
        this.x = player.getX();
        this.y = player.getY();
        this.buff = player.getBuff();
        this.speed = player.getSpeed();
        this.coin = player.getCoin();
        this.animationTimer = player.getAnimationTimer();
        this.passOutTimer = player.getPassOutTimer();
        //todo
        this.animals = new ArrayList<>();
        this.moved = player.isMoved();
        this.lastDirection = player.getLastDirection();
        this.currentDirection = player.getCurrentDirection();

        // Friendships
        player.getFriendShips().forEach((player1, integer) -> {
            this.friendShips.put(player1.getUser().getUsername(), integer);
        });

        this.talk = new HashMap<>();
        for (var entry : player.getTalk().entrySet()) {
            this.talk.put(entry.getKey().getUser().getUsername(), new TalkSave(entry.getValue()));
        }
        this.newMessage = player.isNewMessage();

        // Gifts
        this.gifts = new HashMap<>();
        for (var entry : player.getGifts().entrySet()) {
            ArrayList<GiftSaved> giftList = new ArrayList<>();
            for (var g : entry.getValue()) {
                giftList.add(new GiftSaved(g));
            }
            this.gifts.put(entry.getKey().getUser().getUsername(), giftList);
        }

        // Messages
        this.Messages = new ArrayList<>();
        for (var m : player.getMessages()) {
            this.Messages.add(new MessageSave(m));
        }

        // Trades
        this.trades = player.getTrades();

        this.partnerUsername = player.getPartner().getUser().getUsername();
        this.interactionWithPartner = player.isInteractionWithPartner();
        this.isbrokenUp = player.getIsbrokenUp();

        // NPC friendships
        for (var entry : player.getFriendShipsWithNPCs().entrySet()) {
            this.friendShipsWithNPCs.add(new NPCSave(entry.getKey()));
            this.friendShipsWithNPCValues.add(entry.getValue());
        }

        for (var entry : player.getTalkedNPCToday().entrySet()) {
            this.talkedNPCToday.add(new NPCSave(entry.getKey()));
            this.talkedNPCTodayValues.add(entry.getValue());
        }

        for (var entry : player.getGiftNPCToday().entrySet()) {
            this.giftNPCToday.add(new NPCSave(entry.getKey()));
            this.giftNPCTodayValue.add(entry.getValue());
        }

        // Energy
        this.energy = player.getEnergy();
        this.maxEnergy = player.getMaxEnergy();
        this.isEnergyUnlimited = player.isEnergyUnlimited();
        this.hasPassedOutToday = player.isHasPassedOutToday();

        // BackPack
        this.backPack = new BackPackSave(player.getBackPack());

        // Tools & equipped item
        this.trashCan = player.getTrashCan();
        this.wateringCan = player.getWateringCan();
        this.currentTool = player.getCurrentTool();
        this.equippedItem = player.getEquippedItem().toBackpackableSave();

        // Recipes & crafting
        this.recipes = new HashSet<>(player.getRecipes());
        this.abilities = new AbilityDTO(
            player.getAbilities().getFarmingLevel(),
            player.getAbilities().getMiningLevel(),
            player.getAbilities().getForagingLevel(),
            player.getAbilities().getFishingLevel()
        );
        this.craftingRecipes = new HashSet<>(player.getCraftingRecipes());

        this.daysSinceBrakeUp = player.getDaysSinceBrakeUp();
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public boolean isPassedOut() {
        return isPassedOut;
    }

    public void setPassedOut(boolean passedOut) {
        isPassedOut = passedOut;
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

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }

    public float getAnimationTimer() {
        return animationTimer;
    }

    public void setAnimationTimer(float animationTimer) {
        this.animationTimer = animationTimer;
    }

    public float getPassOutTimer() {
        return passOutTimer;
    }

    public void setPassOutTimer(float passOutTimer) {
        this.passOutTimer = passOutTimer;
    }

    public ArrayList<AnimalDTO> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<AnimalDTO> animals) {
        this.animals = animals;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public Player.Direction getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(Player.Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    public Player.Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Player.Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public HashMap<String, Integer> getFriendShips() {
        return friendShips;
    }

    public void setFriendShips(HashMap<String, Integer> friendShips) {
        this.friendShips = friendShips;
    }

    public HashMap<String, TalkSave> getTalk() {
        return talk;
    }

    public void setTalk(HashMap<String, TalkSave> talk) {
        this.talk = talk;
    }

    public boolean isNewMessage() {
        return newMessage;
    }

    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }

    public HashMap<String, ArrayList<GiftSaved>> getGifts() {
        return gifts;
    }

    public void setGifts(HashMap<String, ArrayList<GiftSaved>> gifts) {
        this.gifts = gifts;
    }

    public ArrayList<MessageSave> getMessages() {
        return Messages;
    }

    public void setMessages(ArrayList<MessageSave> messages) {
        Messages = messages;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
    }

    public String getPartnerUsername() {
        return partnerUsername;
    }

    public void setPartnerUsername(String partnerUsername) {
        this.partnerUsername = partnerUsername;
    }

    public boolean isInteractionWithPartner() {
        return interactionWithPartner;
    }

    public void setInteractionWithPartner(boolean interactionWithPartner) {
        this.interactionWithPartner = interactionWithPartner;
    }

    public int getIsbrokenUp() {
        return isbrokenUp;
    }

    public void setIsbrokenUp(int isbrokenUp) {
        this.isbrokenUp = isbrokenUp;
    }

    public ArrayList<NPCSave> getFriendShipsWithNPCs() {
        return friendShipsWithNPCs;
    }

    public void setFriendShipsWithNPCs(ArrayList<NPCSave> friendShipsWithNPCs) {
        this.friendShipsWithNPCs = friendShipsWithNPCs;
    }

    public ArrayList<Integer> getFriendShipsWithNPCValues() {
        return friendShipsWithNPCValues;
    }

    public void setFriendShipsWithNPCValues(ArrayList<Integer> friendShipsWithNPCValues) {
        this.friendShipsWithNPCValues = friendShipsWithNPCValues;
    }

    public ArrayList<NPCSave> getTalkedNPCToday() {
        return talkedNPCToday;
    }

    public void setTalkedNPCToday(ArrayList<NPCSave> talkedNPCToday) {
        this.talkedNPCToday = talkedNPCToday;
    }

    public ArrayList<Boolean> getTalkedNPCTodayValues() {
        return talkedNPCTodayValues;
    }

    public void setTalkedNPCTodayValues(ArrayList<Boolean> talkedNPCTodayValues) {
        this.talkedNPCTodayValues = talkedNPCTodayValues;
    }

    public ArrayList<NPCSave> getGiftNPCToday() {
        return giftNPCToday;
    }

    public void setGiftNPCToday(ArrayList<NPCSave> giftNPCToday) {
        this.giftNPCToday = giftNPCToday;
    }

    public ArrayList<Boolean> getGiftNPCTodayValue() {
        return giftNPCTodayValue;
    }

    public void setGiftNPCTodayValue(ArrayList<Boolean> giftNPCTodayValue) {
        this.giftNPCTodayValue = giftNPCTodayValue;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(double maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public boolean isEnergyUnlimited() {
        return isEnergyUnlimited;
    }

    public void setEnergyUnlimited(boolean energyUnlimited) {
        isEnergyUnlimited = energyUnlimited;
    }

    public boolean isHasPassedOutToday() {
        return hasPassedOutToday;
    }

    public void setHasPassedOutToday(boolean hasPassedOutToday) {
        this.hasPassedOutToday = hasPassedOutToday;
    }

    public BackPackSave getBackPack() {
        return backPack;
    }

    public void setBackPack(BackPackSave backPack) {
        this.backPack = backPack;
    }

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

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public BackPackableSave getEquippedItem() {
        return equippedItem;
    }

    public void setEquippedItem(BackPackableSave equippedItem) {
        this.equippedItem = equippedItem;
    }

    public HashSet<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(HashSet<Recipe> recipes) {
        this.recipes = recipes;
    }

    public AbilityDTO getAbilities() {
        return abilities;
    }

    public void setAbilities(AbilityDTO abilities) {
        this.abilities = abilities;
    }

    public HashSet<CraftingRecipe> getCraftingRecipes() {
        return craftingRecipes;
    }

    public void setCraftingRecipes(HashSet<CraftingRecipe> craftingRecipes) {
        this.craftingRecipes = craftingRecipes;
    }

    public int getDaysSinceBrakeUp() {
        return daysSinceBrakeUp;
    }

    public void setDaysSinceBrakeUp(int daysSinceBrakeUp) {
        this.daysSinceBrakeUp = daysSinceBrakeUp;
    }
}
