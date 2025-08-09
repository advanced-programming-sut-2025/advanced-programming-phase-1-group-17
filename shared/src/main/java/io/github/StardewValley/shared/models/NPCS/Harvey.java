package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.cooking.Food;
import io.github.StardewValley.shared.models.cooking.FoodType;

import java.util.ArrayList;

public class Harvey extends NPC implements Placeable {
    private int x;
    private int y;
    private String name = "Harvey";
    private String job = "teacher";
    private String  texture1 = "Harvey.png";
    private String  texture2 = "hut2.png";


    public Harvey(boolean isHarvey) {
        isNPC = isHarvey;
        this.dialogueText.add("hello i am harvey");
        this.dialogueText.add("hi how are you?");
        this.dialogueText.add("what do you do on vacation?");
        this.dialogueText.add("I am a teacher");
        this.dialogueText.add("this is a village in iran.");
        this.dialogueText.add("be careful,the weather is very hot.go home quickly.");
    }
    public Harvey(boolean isHarvey, int x, int y){
        isNPC = isHarvey;
        x_start = x;
        y_start = y;
    }



    private ArrayList<String> favorites = new ArrayList<>();


    public ArrayList<String> getFavorites() {
        return favorites;
    }

    {
        favorites.add("Wine");
        favorites.add("Pickles");
        favorites.add("Coffee");
    }

    private ArrayList<Quest> requests = new ArrayList<>();

    public ArrayList<Quest> getRequests() {
        return requests;
    }

    {
        requests.add(new Quest("Delivery of 12 pieces of a desired CarrotSeed", 0, false, "CarrotSeeds", 12));
        requests.add(new Quest("Delivery of a salmon fish", 1, false, "Salmon", 1));
        requests.add(new Quest("Delivery of a bottle of wine", 2, false, "Wine", 1));
    }

    public void giveReward(Player player, int index) {
        if (index == 0) {
            player.getBackPack().addcoin(750);
        } else if (index == 1) {
            player.getFriendShipsWithNPCs().put(this, Math.min(799, player.getFriendShipsWithNPCs().get(this) + 200));
        } else {
            for (int i = 0; i < 5; i++) {
                Food f = new Food(null);
                f.setFoodtype(FoodType.Salad);
                player.getBackPack().addItemToInventory(f);
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
