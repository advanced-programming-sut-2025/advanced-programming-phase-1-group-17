package org.example.models.NPCS;

import org.example.models.Placeable;
import org.example.models.Player;
import org.example.models.crafting.CraftingItem;
import org.example.models.crafting.CraftingItemType;

import java.util.ArrayList;
import java.util.HashMap;

public class Abigail extends NPC implements Placeable {
    private int x;
    private int y;
    private String name = "Abigail";
    private String job = "Miner";
    private final HashMap<String, String> dialogue = new HashMap<>();

    {
        dialogue.put("what's your name?", "Abigail");
        dialogue.put("hello", "hi how are you?");
        dialogue.put("i'm fine how are you", "i'm fine");
        dialogue.put("where is this?", "this is a village in iran.");
        dialogue.put("goodbye", "bye , take care of yourself.");
        dialogue.put("how is the weather?", "excellent!");
        dialogue.put("what is your job?", "I am a miner");
    }

    private ArrayList<String> favorites = new ArrayList<>();

    @Override
    public ArrayList<String> getFavorites() {
        return favorites;
    }

    {
        favorites.add("Stone");
        favorites.add("IronOre");
        favorites.add("Coffee");
    }

    private ArrayList<Quest> requests = new ArrayList<>();

    public ArrayList<Quest> getRequests() {
        return requests;
    }

    {
        requests.add(new Quest("Delivery of a gold bar", 0, false, "GoldBar", 1));
        requests.add(new Quest("Delivery of a pumpkin", 1, false, "Pumpkin", 1));
        requests.add(new Quest("Delivery of 50 wheat", 2, false, "Wheat", 50));
    }

    public void giveReward(Player player, int index) {
        if (index == 0) {
            player.getFriendShipsWithNPCs().put(this, Math.min(799, player.getFriendShipsWithNPCs().get(this) + 200));
        } else if (index == 1) {
            player.getBackPack().addcoin(500);
        } else {
            CraftingItem c = new CraftingItem(CraftingItemType.IridiumSprinkler);
            player.getBackPack().addItemToInventory(c);
        }
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
}
