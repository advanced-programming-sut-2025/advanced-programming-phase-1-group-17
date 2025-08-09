package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.Game;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;

import java.util.ArrayList;

public class Robin extends NPC implements Placeable {
    private int x;
    private int y;
    private String name = "Robin";
    private String job = "architect";

    private String texture1 = "Robin.png";
    private String texture2 =  "hut2.png";

    public Robin(boolean isRobin) {
        isNPC = isRobin;
        this.dialogueText.add("hello i am robin");
        this.dialogueText.add("hi how are you?");
        this.dialogueText.add("what do you do on vacation?");
        this.dialogueText.add("I am a architect");
        this.dialogueText.add("this is a village in iran.");
        this.dialogueText.add("be careful,the weather is very hot.go home quickly.");
    }
    public Robin(boolean isHarvey, int x, int y){
        isNPC = isHarvey;
        x_start = x;
        y_start = y;
    }


    private ArrayList<String> favorites = new ArrayList<>();


    public ArrayList<String> getFavorites() {
        return favorites;
    }

    {
        favorites.add("IronBar");
        favorites.add("Wood");
        favorites.add("Spaghetti");
    }

    private ArrayList<Quest> requests = new ArrayList<>();

    public ArrayList<Quest> getRequests() {
        return requests;
    }

    {
        requests.add(new Quest("Delivery of 80 sticks", 0, false, "Wood", 80));
        requests.add(new Quest("Delivery of 10 iron ingots", 1, false, "IronBar", 10));
        requests.add(new Quest("Delivery of 1000 sticks", 2, false, "Wood", 1000));
    }

    public void giveReward(Player player, int index, Game game) {
        if (index == 0) {
            player.getBackPack().addcoin(1000);
        } else if (index == 1) {
            for (int i = 0; i < 3; i++) {
                CraftingItem c = new CraftingItem(CraftingItemType.BeeHouse, player, game);
                player.getBackPack().addItemToInventory(c);
            }
        } else {
            player.getBackPack().addcoin(25000);
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
    public String  getTexture() {
        if (isNPC)
            return texture1;
        else
            return texture2;
    }

}
