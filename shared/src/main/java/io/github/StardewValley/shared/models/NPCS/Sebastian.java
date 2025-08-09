package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.foraging.Mineral;
import io.github.StardewValley.shared.models.foraging.MineralType;

import java.util.ArrayList;

public class Sebastian extends NPC implements Placeable {
    private int x;
    private int y;
    private String name = "Sebastian";
    private String job = "cook";
    private String texture1 = "Sebastian.png";
    private String texture2 = "hut2.png";

    public Sebastian(boolean isSebastian) {
        isNPC = isSebastian;
        this.dialogueText.add("hello i am sebastian");
        this.dialogueText.add("hi how are you?");
        this.dialogueText.add("what do you do on vacation?");
        this.dialogueText.add("I am a cook");
        this.dialogueText.add("this is a village in iran.");
        this.dialogueText.add("be careful,the weather is very hot.go home quickly.");
    }

    public Sebastian(boolean isHarvey, int x, int y) {
        isNPC = isHarvey;
        x_start = x;
        y_start = y;
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
            for (int i = 0; i < 2; i++) {
                Mineral m = new Mineral(MineralType.Diamond, false);
                player.getBackPack().addItemToInventory(m);
            }
        } else if (index == 1) {
            player.getBackPack().addcoin(5000);
        } else {
            for (int i = 0; i < 50; i++) {
                Mineral m = new Mineral(MineralType.Quartz, false);
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
