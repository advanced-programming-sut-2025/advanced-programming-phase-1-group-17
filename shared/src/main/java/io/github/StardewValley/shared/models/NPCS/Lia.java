package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.Game;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.cooking.Food;
import io.github.StardewValley.shared.models.cooking.FoodType;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;

import java.util.ArrayList;

public class Lia extends NPC implements Placeable {
    private int x;
    private int y;
    private String name = "Lia";
    private String job = "waiter";
    private String texture1 = "Leo.png";
    private String  texture2 =  "hut2.png";


    public Lia(boolean isLia){
        isNPC = isLia;
        this.dialogueText.add("hello i am lia");
        this.dialogueText.add("hi how are you?");
        this.dialogueText.add("what do you do on vacation?");
        this.dialogueText.add("I am a waiter");
        this.dialogueText.add("this is a village in iran.");
        this.dialogueText.add("be careful,the weather is very hot.go home quickly.");
    }
    public Lia(boolean isHarvey, int x, int y){
        isNPC = isHarvey;
        x_start = x;
        y_start = y;
    }

    private ArrayList<String> favorites = new ArrayList<>();


    public ArrayList<String> getFavorites() {
        return favorites;
    }
    {
        favorites.add("Grape");
        favorites.add("Salad");
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
    public void giveReward(Player player, int index, Game game) {
        if (index == 0) {
            player.getBackPack().addcoin(500);
        } else if (index == 1) {
            Food f = new Food(FoodType.BakedFish);
            f.setFoodtype(FoodType.SalmonDinner);
            player.getBackPack().addItemToInventory(f);
        } else {
            for (int i =0 ; i < 3; i++) {
                CraftingItem c = new CraftingItem(CraftingItemType.DeluxeScarecrow, player, game);
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

    @Override
    public String getTexture() {
        if (isNPC)
            return texture1;
        else
            return texture2;
    }

}
