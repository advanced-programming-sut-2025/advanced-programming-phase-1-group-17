package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.NPCS.Talk;

public class TalkSave {
    private String playerUsername;
    private String talk;

    public TalkSave() {
    }

    public TalkSave(Talk talk) {
        this.playerUsername = talk.getPlayer().getUser().getUsername();
        this.talk = talk.getTalk();
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public String getTalk() {
        return talk;
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }
}
