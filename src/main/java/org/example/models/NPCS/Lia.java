package org.example.models.NPCS;

import org.example.models.Placeable;
import org.example.models.Player;
import org.example.models.cooking.Food;
import org.example.models.cooking.FoodType;
import org.example.models.crafting.CraftingItem;
import org.example.models.crafting.CraftingItemType;

import java.util.ArrayList;
import java.util.HashMap;

public class Lia extends NPC implements Placeable {
    private int x;
    private int y;
    private String name = "Lia";
    private String job = "waiter";
    private final HashMap<String, String> dialogue = new HashMap<>();
    {
        dialogue.put("what's your name?", "Lia");
        dialogue.put("hello", "hi how are you?");
        dialogue.put("i'm fine how are you", "i'm fine");
        dialogue.put("where is this?", "this is a village in iran.");
        dialogue.put("goodbye", "bye , take care of yourself.");
        dialogue.put("how is the weather?", "excellent!");
        dialogue.put("what is your job?", "I am a waiter");
    }
    private ArrayList<String> favorites = new ArrayList<>();

    @Override
    public ArrayList<String> getFavorites() {
        return favorites;
    }
    {
        favorites.add("Grape");
        favorites.add("salad");
        favorites.add("Wine");
    }
    private ArrayList<Quest> requests= new ArrayList<>();
    public ArrayList<Quest> getRequests(){
        return requests;
    }
    {
        requests.add(new Quest("Delivery of 10 woods",0,false,"Wood",10));
        requests.add(new Quest("Delivery of a salmon fish",1,false,"salmon",1));
        requests.add(new Quest("Delivery of 200 woods",2,false,"Wood",200));
    }
    public void giveReward(Player player, int index) {
        if (index == 0) {
            player.getBackPack().addcoin(500);
        } else if (index == 1) {
            Food f = new Food();
            f.setFoodtype(FoodType.SalmonDinner);
            player.getBackPack().addItemToInventory(f);
        } else {
            for (int i =0 ; i < 3; i++) {
                CraftingItem c = new CraftingItem(CraftingItemType.DeluxeScarecrow);
                player.getBackPack().addItemToInventory(c);
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
