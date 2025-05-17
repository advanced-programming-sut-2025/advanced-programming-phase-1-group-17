package org.example.models.NPCS;

import org.example.models.Placeable;
import org.example.models.Player;
import org.example.models.foraging.Mineral;
import org.example.models.foraging.MineralType;
import java.util.ArrayList;
import java.util.HashMap;

public class Sebastian extends NPC implements Placeable {
    private int x;
    private int y;
    private String name = "Sebastian";
    private String job = "cook";
    private final HashMap<String, String> dialogue = new HashMap<>();
    private final HashMap<String, String> dialogue2 = new HashMap<>();


    {
        dialogue.put("what's your name?", "Sebastian");
        dialogue.put("hello", "hi how are you?what do you do on vacation");
        dialogue.put("i'm fine how are you", "i'm fine,");
        dialogue.put("where is this?", "this is a village in iran.");
        dialogue.put("goodbye", "bye , be careful,the weather is very hot.go home quickly.");
        dialogue.put("how is the weather?", "the weather is very hot!");
        dialogue.put("what is your job?", "I am a cook");
        dialogue2.put("what's your name?", "Sebastian");
        dialogue2.put("hello", "hi how are you?");
        dialogue2.put("i'm fine how are you", "i'm fine");
        dialogue2.put("where is this?", "this is a village in iran.");
        dialogue2.put("goodbye", "bye , take care of yourself.");
        dialogue2.put("how is the weather?", "the weather is very cold!");
        dialogue2.put("what is your job?", "I am a cook");
    }

    private ArrayList<String> favorites = new ArrayList<>();

    @Override
    public ArrayList<String> getFavorites() {
        return favorites;
    }

    {
        favorites.add("Wool");
        favorites.add("PumpkinPie");
        favorites.add("Pizza");
    }

    private ArrayList<Quest> requests = new ArrayList<>();

    public ArrayList<Quest> getRequests() {
        return requests;
    }

    {
        requests.add(new Quest("Delivery of 50 units of iron", 0, false, "Iron", 50));
        requests.add(new Quest("Delivery of pumpkin pie", 1, false, "pumpkinPie", 1));
        requests.add(new Quest("Delivery of 150 units of stone", 2, false, "Stone", 150));
    }

    public void giveReward(Player player, int index) {
        if (index == 0) {
            for (int i =0 ; i < 2; i++) {
                Mineral m = new Mineral(MineralType.Diamond,false);
                player.getBackPack().addItemToInventory(m);
            }
        } else if (index == 1) {
            player.getBackPack().addcoin(5000);
        } else {
            for (int i =0 ; i < 50; i++) {
                Mineral m = new Mineral(MineralType.Quartz,false);
                player.getBackPack().addItemToInventory(m);
            }
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

    public HashMap<String, String> getDialogue() {
        return dialogue;
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

    @Override
    public HashMap<String, String> getDialogue2() {
        return dialogue2;
    }
}
