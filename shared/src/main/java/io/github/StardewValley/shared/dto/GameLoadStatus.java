package io.github.StardewValley.shared.dto;

public class GameLoadStatus {
    private boolean allReady;

    public GameLoadStatus() {}
    public GameLoadStatus(boolean allReady) {
        this.allReady = allReady;
    }

    public boolean isAllReady() {
        return allReady;
    }
    public void setAllReady(boolean allReady) {
        this.allReady = allReady;
    }
}

