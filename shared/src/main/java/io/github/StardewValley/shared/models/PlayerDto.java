package io.github.StardewValley.shared.models;

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

    private ToolType toolType;
    private String toolTexturePath;

    public PlayerDto() {}
    public PlayerDto(boolean isPassedOut
        , double energy
        , double maxEnergy
        , boolean isEnergyUnlimited
        , boolean hasPassedOutToday
        , int x, int y, Player.Direction currentDirection
        , float speed, Player.Direction lastDirection
        , int coin, float animationTimer
        , float passOutTimer) {
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

    public String getToolTexturePath() {
        return toolTexturePath;
    }
}
