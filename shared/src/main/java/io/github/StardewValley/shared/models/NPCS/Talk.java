package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.Player;

public class Talk {
    private Player player;
    private String talk = "";

    public Talk() {}

    public Talk(Player player) {
        this.player = player;
    }

    public Talk(Player player, String talk) {
        this.player = player;
        this.talk = talk;
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }
}
