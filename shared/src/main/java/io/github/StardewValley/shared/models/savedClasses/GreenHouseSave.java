package io.github.StardewValley.shared.models.savedClasses;

import io.github.StardewValley.shared.models.greenhouse.GreenHouse;

public class GreenHouseSave {
    private boolean isActive;
    private GreenHouseFenceSave fence;
    private GreenHouseLakeSave lake;
    private int width;
    private int height;
    private int starting_x;
    private int starting_y;
    private String ownerUsername;

    public GreenHouseSave() {}

    public GreenHouseSave(GreenHouse greenHouse) {
        this.isActive = greenHouse.isActive();
        this.fence = new GreenHouseFenceSave();
        this.lake = new GreenHouseLakeSave();
        this.width = greenHouse.getWidth();
        this.height = greenHouse.getHeight();
        this.starting_x = greenHouse.getStarting_x();
        this.starting_y = greenHouse.getStarting_y();
        this.ownerUsername = greenHouse.getOwner().getUser().getUsername();
    }

    public boolean isActive() {
        return isActive;
    }

    public GreenHouseFenceSave getFence() {
        return fence;
    }

    public GreenHouseLakeSave getLake() {
        return lake;
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
}
