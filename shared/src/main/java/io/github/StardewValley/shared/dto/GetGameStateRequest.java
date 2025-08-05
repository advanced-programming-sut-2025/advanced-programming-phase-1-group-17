package io.github.StardewValley.shared.dto;

public class GetGameStateRequest {
    private int minTileX;
    private int minTileY;
    private int maxTileX;
    private int maxTileY;

    public GetGameStateRequest() {
    }

    public GetGameStateRequest(int minTileX, int minTileY, int maxTileX, int maxTileY) {
        this.minTileY = minTileY;
        this.minTileX = minTileX;
        this.maxTileY = maxTileY;
        this.maxTileX = maxTileX;
    }

    public int getMinTileX() {
        return minTileX;
    }

    public void setMinTileX(int minTileX) {
        this.minTileX = minTileX;
    }

    public int getMinTileY() {
        return minTileY;
    }

    public void setMinTileY(int minTileY) {
        this.minTileY = minTileY;
    }

    public int getMaxTileX() {
        return maxTileX;
    }

    public void setMaxTileX(int maxTileX) {
        this.maxTileX = maxTileX;
    }

    public int getMaxTileY() {
        return maxTileY;
    }

    public void setMaxTileY(int maxTileY) {
        this.maxTileY = maxTileY;
    }
}
