package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.NPCS.Quest;
import io.github.StardewValley.shared.models.UserDTO;

import java.util.ArrayList;

public class NPCSave {
    private String name;
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
        this.name = npc.getName();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setFatherUser(UserDTO fatherUser) {
        this.fatherUser = fatherUser;
    }

    public void setFatherPlayerUsername(String fatherPlayerUsername) {
        this.fatherPlayerUsername = fatherPlayerUsername;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    public void setNPC(boolean NPC) {
        isNPC = NPC;
    }

    public void setX_start(int x_start) {
        this.x_start = x_start;
    }

    public void setY_start(int y_start) {
        this.y_start = y_start;
    }

    public void setTile_x(int tile_x) {
        Tile_x = tile_x;
    }

    public void setTile_y(int tile_y) {
        Tile_y = tile_y;
    }

    public void setDialogueText(ArrayList<String> dialogueText) {
        this.dialogueText = dialogueText;
    }

    public void setRequests(ArrayList<Quest> requests) {
        this.requests = requests;
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
