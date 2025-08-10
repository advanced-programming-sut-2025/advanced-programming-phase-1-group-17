package io.github.StardewValley.shared.models;

import io.github.StardewValley.shared.models.tools.FishingPoleType;
import io.github.StardewValley.shared.models.tools.ToolMaterial;
import io.github.StardewValley.shared.models.tools.ToolType;

public class PlayerDto {
    private boolean isPassedOut = false;
    private double energy;
    private double maxEnergy = 200;
    private boolean isEnergyUnlimited = false;
    private boolean hasPassedOutToday = false;
    private int x;
    private int y;
    private int coin = 550;
    private float speed = 1000f;
    private Player.Direction lastDirection;
    private Player.Direction currentDirection;
    private float animationTimer = 0f;
    private float passOutTimer = 0f;
    private boolean isNewMessage;
    private String targetPlayerToTrade;

    private ToolType toolType;
    //private String toolTexturePath;
    private AbilityDTO ability;
    private ToolMaterial toolMaterial;
    private FishingPoleType fishingPoleType;
    private String gender;

    public PlayerDto() {
    }

    public PlayerDto(boolean isPassedOut
        , double energy
        , double maxEnergy
        , boolean isEnergyUnlimited
        , boolean hasPassedOutToday
        , int x, int y, Player.Direction currentDirection
        , float speed, Player.Direction lastDirection
        , int coin, float animationTimer
        , float passOutTimer
        , AbilityDTO abilityDTO
        , ToolType toolType
        , ToolMaterial toolMaterial
        , FishingPoleType fishingPoleType,
                     String targetPlayerToTrade) {
        this.isPassedOut = isPassedOut;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.isEnergyUnlimited = isEnergyUnlimited;
        this.hasPassedOutToday = hasPassedOutToday;
        this.x = x;
        this.y = y;
        this.currentDirection = currentDirection;
        this.speed = speed;
        this.lastDirection = lastDirection;
        this.coin = coin;
        this.animationTimer = animationTimer;
        this.passOutTimer = passOutTimer;
        this.ability = abilityDTO;
        this.toolType = toolType;
        this.toolMaterial = toolMaterial;
        this.fishingPoleType = fishingPoleType;
        this.targetPlayerToTrade = targetPlayerToTrade;
        //this.toolTexturePath = toolTexturePath;
    }


    public boolean isPassedOut() {
        return isPassedOut;
    }

    public void setPassedOut(boolean passedOut) {
        isPassedOut = passedOut;
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

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
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

    public boolean isNewMessage() {
        return isNewMessage;
    }

    public void setNewMessage(boolean newMessage) {
        isNewMessage = newMessage;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public AbilityDTO getAbility() {
        return ability;
    }

    public void setAbility(AbilityDTO ability) {
        this.ability = ability;
    }

    public ToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public void setToolMaterial(ToolMaterial toolMaterial) {
        this.toolMaterial = toolMaterial;
    }

    public FishingPoleType getFishingPoleType() {
        return fishingPoleType;
    }

    public void setFishingPoleType(FishingPoleType fishingPoleType) {
        this.fishingPoleType = fishingPoleType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTargetPlayerToTrade() {
        return targetPlayerToTrade;
    }

    public void setTargetPlayerToTrade(String targetPlayerToTrade) {
        this.targetPlayerToTrade = targetPlayerToTrade;
    }
}
