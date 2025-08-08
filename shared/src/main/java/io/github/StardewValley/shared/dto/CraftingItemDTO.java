package io.github.StardewValley.shared.dto;

public class CraftingItemDTO {
    private boolean isInProgress;
    private boolean isArtisanProductReady;
    private String type;
    private String artisanProductType;

    private int hoursInProgress;
    private int daysInProgress;
    private int artisanProductionHours;
    private int artisanProductionDays;
    private String artisanProductTexturePath;

    private int tileX;
    private int tileY;

    public CraftingItemDTO() {
    }

    public CraftingItemDTO(boolean isInProgress, boolean isArtisanProductReady,
                           String type, String artisanProductType, String artisanProductTexturePath,
                           int tileX, int tileY,
                           int hoursInProgress, int daysInProgress, int artisanProductionHours, int artisanProductionDays) {
        this.isInProgress = isInProgress;
        this.isArtisanProductReady = isArtisanProductReady;
        this.type = type;
        this.artisanProductType = artisanProductType;
        this.artisanProductTexturePath = artisanProductTexturePath;
        this.tileX = tileX;
        this.tileY = tileY;
        this.hoursInProgress = hoursInProgress;
        this.daysInProgress = daysInProgress;
        this.artisanProductionHours = artisanProductionHours;
        this.artisanProductionDays = artisanProductionDays;
    }

    public boolean isInProgress() {
        return isInProgress;
    }

    public void setInProgress(boolean inProgress) {
        isInProgress = inProgress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public boolean isArtisanProductReady() {
        return isArtisanProductReady;
    }

    public void setArtisanProductReady(boolean artisanProductReady) {
        isArtisanProductReady = artisanProductReady;
    }

    public String getArtisanProductType() {
        return artisanProductType;
    }

    public void setArtisanProductType(String artisanProductType) {
        this.artisanProductType = artisanProductType;
    }

    public int getHoursInProgress() {
        return hoursInProgress;
    }

    public void setHoursInProgress(int hoursInProgress) {
        this.hoursInProgress = hoursInProgress;
    }

    public int getDaysInProgress() {
        return daysInProgress;
    }

    public void setDaysInProgress(int daysInProgress) {
        this.daysInProgress = daysInProgress;
    }

    public int getArtisanProductionHours() {
        return artisanProductionHours;
    }

    public void setArtisanProductionHours(int artisanProductionHours) {
        this.artisanProductionHours = artisanProductionHours;
    }

    public int getArtisanProductionDays() {
        return artisanProductionDays;
    }

    public void setArtisanProductionDays(int artisanProductionDays) {
        this.artisanProductionDays = artisanProductionDays;
    }

    public String getArtisanProductTexturePath() {
        return artisanProductTexturePath;
    }

    public void setArtisanProductTexturePath(String artisanProductTexturePath) {
        this.artisanProductTexturePath = artisanProductTexturePath;
    }
}
