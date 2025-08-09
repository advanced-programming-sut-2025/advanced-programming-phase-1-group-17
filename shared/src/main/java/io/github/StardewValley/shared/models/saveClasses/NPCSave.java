package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.NPCS.Quest;
import io.github.StardewValley.shared.models.UserDTO;

import java.util.ArrayList;

public class NPCSave {
    private int x;
    private int y;
    private UserDTO fatherUser;
    private String fatherPlayerUsername;
    private ArrayList<String> favorites;
    private boolean isNPC;
    private int x_start;
    private int y_start;
    private int Tile_x;
    private int Tile_y;
    private ArrayList<String> dialogueText = new ArrayList<>();
    private ArrayList<Quest> requests = new ArrayList<>();

    public NPCSave() {}

    public NPCSave(NPC npc) {
        this.x = npc.getX();
        this.y = npc.getY();
        this.fatherUser = NPC.getFatherUser();
        this.fatherPlayerUsername = NPC.getFatherUser().getUsername();
        this.favorites = npc.getFavorites();
        this.isNPC = npc.isNPC;
        this.x_start = npc.getX_start();
        this.y_start = npc.getY_start();
        this.Tile_x = npc.getTile_x();
        this.Tile_y = npc.getTile_y();
        this.dialogueText = npc.getDialogueTexts();
        this.requests = npc.getRequests();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public UserDTO getFatherUser() {
        return fatherUser;
    }

    public String getFatherPlayerUsername() {
        return fatherPlayerUsername;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public boolean isNPC() {
        return isNPC;
    }

    public int getX_start() {
        return x_start;
    }

    public int getY_start() {
        return y_start;
    }

    public int getTile_x() {
        return Tile_x;
    }

    public int getTile_y() {
        return Tile_y;
    }

    public ArrayList<String> getDialogueText() {
        return dialogueText;
    }

    public ArrayList<Quest> getRequests() {
        return requests;
    }
}
