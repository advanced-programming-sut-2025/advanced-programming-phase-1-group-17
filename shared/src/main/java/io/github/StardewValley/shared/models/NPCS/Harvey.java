package io.github.StardewValley.shared.models.NPCS;

import com.badlogic.gdx.graphics.Texture;
import io.github.StardewValley.shared.models.Placeable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.cooking.Food;
import io.github.StardewValley.shared.models.cooking.FoodType;

import java.util.ArrayList;
import java.util.HashMap;

public class Harvey extends NPC implements Placeable {
    private int x;
    private int y;
    private String name = "Harvey";
    private String job = "teacher";
    private final HashMap<String, String> dialogue = new HashMap<>();
    private final HashMap<String, String> dialogue2 = new HashMap<>();
    private Texture texture1 = new Texture("Harvey.png");
    private Texture texture2 = new Texture("hut2.png");
    //TODO


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


    {
        dialogue.put("what's your name?", "Harvey");
        dialogue.put("hello", "hi how are you?what do you do on vacation");
        dialogue.put("i'm fine how are you", "i'm fine,");
        dialogue.put("where is this?", "this is a village in iran.");
        dialogue.put("goodbye", "bye , be careful,the weather is very hot.go home quickly.");
        dialogue.put("how is the weather?", "the weather is very hot!");
        dialogue.put("what is your job?", "I am a teacher");
        dialogue2.put("what's your name?", "Harvey");
        dialogue2.put("hello", "hi how are you?");
        dialogue2.put("i'm fine how are you", "i'm fine");
        dialogue2.put("where is this?", "this is a village in iran.");
        dialogue2.put("goodbye", "bye , take care of yourself.");
        dialogue2.put("how is the weather?", "the weather is very cold!");
        dialogue2.put("what is your job?", "I am a teacher");

    }

    private ArrayList<String> favorites = new ArrayList<>();

    @Override
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

    public HashMap<String, String> getDialogue() {
        return dialogue;
    }

    @Override
    public HashMap<String, String> getDialogue2() {
        return dialogue2;
    }

    @Override
    public Texture getTexture() {
        if (isNPC)
            return texture1;
        else
            return texture2;
    }

}
