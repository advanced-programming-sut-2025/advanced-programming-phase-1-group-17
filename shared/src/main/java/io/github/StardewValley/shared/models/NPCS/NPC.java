package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.UserDTO;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.saveClasses.NPCSave;
import io.github.StardewValley.shared.models.saveClasses.PlaceableSave;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract public class NPC implements Placeable {
    protected int x;
    protected int y;
    private static UserDTO fatherUser;
    private static Player fatherPlayer;
    protected String name;
    protected String job;
    protected ArrayList<String> favorites;
    public boolean isNPC;
    public int x_start;
    public int y_start;
    protected int Tile_x;
    protected int Tile_y;
    public ArrayList<String> dialogueText = new ArrayList<>();
    protected ArrayList<Quest> requests = new ArrayList<>();

    public ArrayList<Quest> getRequests() {
        return requests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public void giveReward(Player player, int index) {
    }
    public static void setFatherUser(UserDTO fatherUser) {
        NPC.fatherUser = fatherUser;
    }

    public static Player getFatherPlayer() {
        return fatherPlayer;
    }

    public static void setFatherPlayer(Player fatherPlayer) {
        NPC.fatherPlayer = fatherPlayer;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isNPC() {
        return isNPC;
    }

    public String getTexture() {
        return null;
    }

    public String getDialogueText() {
        Random rand = new Random();
        int min = 0;
        int max = dialogueText.size() - 1;
        int randomInt = rand.nextInt(max - min + 1) + min;
        return dialogueText.get(randomInt);
    }

    public ArrayList<String> getDialogueTexts() {
        return dialogueText;
    }

    public int getTile_x() {
        return Tile_x;
    }

    public void setTile_x(int tile_x) {
        Tile_x = tile_x;
    }

    public int getTile_y() {
        return Tile_y;
    }

    public void setTile_y(int tile_y) {
        Tile_y = tile_y;
    }

    public static UserDTO getFatherUser() {
        return fatherUser;
    }

    public String getJob() {
        return job;
    }

    public int getX_start() {
        return x_start;
    }

    public int getY_start() {
        return y_start;
    }

    @Override
    public PlaceableSave toDTO() {
        PlaceableSave placeableSave = new PlaceableSave(this.name);
        placeableSave.setNPCSave(new NPCSave(this));
        return placeableSave;
    }

    @Override
    public Placeable loadFromDTO(PlaceableSave dto, List<Player> playerList) {
        NPCSave save = dto.getNPCSave();
        return switch (dto.getType()) {
            case "Abigail" -> new Abigail(save, playerList);
            case "Sebastian" -> new Sebastian(save, playerList);
            case "Lia" -> new Lia(save, playerList);
            case "Harvey" -> new Harvey(save, playerList);
            case "Robin" -> new Robin(save, playerList);
            default -> null;
        };
    }
}
