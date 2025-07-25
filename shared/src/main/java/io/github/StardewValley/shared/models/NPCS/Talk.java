package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.Player;

public class Talk {
    private Player player;
    private String talk = "";
    public Talk(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String getTalk() {
        return talk;
    }

    public void addTalk(String talk) {
        this.talk += talk;
    }
}
