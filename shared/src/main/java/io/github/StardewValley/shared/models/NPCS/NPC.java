package io.github.StardewValley.shared.models.NPCS;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

abstract public class NPC {
    private int x;
    private int y;
    private static UserDTO fatherUser;
    private static Player fatherPlayer;
    private String name;
    private String job;
    private ArrayList<Quest> quests =new ArrayList<>();
    private ArrayList<String> favorites;
    public boolean isNPC;
    public int x_start;
    public int y_start;
    private int Tile_x;
    private int Tile_y;
    public ArrayList<String> dialogueText = new ArrayList<>();

    private HashMap<String, String> dialogue = new HashMap<>();
    private HashMap<String, String> dialogue2 = new HashMap<>();

    private ArrayList<Quest> requests= new ArrayList<>();
    public ArrayList<Quest> getRequests(){
        return requests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }
    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }
    public void giveReward(Player player, int index){};

    public void sendGift() {
    }

    public static UserDTO getFatherUser() {
        return fatherUser;
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
    public HashMap<String, String> getDialogue() {
        return dialogue;
    }

    public HashMap<String, String> getDialogue2() {
        return dialogue2;
    }
    public boolean isNPC(){
        return isNPC;
    }

    public int getX_start() {
        return x_start;
    }

    public int getY_start() {
        return y_start;
    }
    public String getTexture(){
        return null;
    }
    public String getDialogueText(){
        Random rand = new Random();
        int min = 0;
        int max = dialogueText.size() - 1;
        int randomInt = rand.nextInt(max - min + 1) + min;
        return dialogueText.get(randomInt);
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

}
