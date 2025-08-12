package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.greenhouse.GreenHouse;
import io.github.StardewValley.shared.models.greenhouse.GreenHouseFence;
import io.github.StardewValley.shared.models.greenhouse.GreenHouseLake;

public class GreenHouseSave {
    private boolean isActive;
    private GreenHouseFence fence;
    private GreenHouseLake lake;
    private int width;
    private int height;
    private int starting_x;
    private int starting_y;
    private String ownerUsername;

    public GreenHouseSave() {}

    public GreenHouseSave(GreenHouse greenHouse) {
        this.isActive = greenHouse.isActive();
        this.fence = greenHouse.getFence();
        this.lake = greenHouse.getLake();
        this.width = greenHouse.getWidth();
        this.height = greenHouse.getHeight();
        this.starting_x = greenHouse.getStarting_x();
        this.starting_y = greenHouse.getStarting_y();
        this.ownerUsername = greenHouse.getOwner().getUser().getUsername();
    }

    public boolean isActive() {
        return isActive;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStarting_x() {
        return starting_x;
    }

    public int getStarting_y() {
        return starting_y;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public GreenHouseFence getFence() {
        return fence;
    }

    public void setFence(GreenHouseFence fence) {
        this.fence = fence;
    }

    public GreenHouseLake getLake() {
        return lake;
    }

    public void setLake(GreenHouseLake lake) {
        this.lake = lake;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setStarting_x(int starting_x) {
        this.starting_x = starting_x;
    }

    public void setStarting_y(int starting_y) {
        this.starting_y = starting_y;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}
