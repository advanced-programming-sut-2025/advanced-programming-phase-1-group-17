package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.cooking.Food;
import io.github.StardewValley.shared.models.cooking.FoodType;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.models.saveClasses.NPCSave;

import java.util.List;

public class Lia extends NPC implements Placeable {
    {
        name = "Lia";
        job = "waiter";
    }
    private final String texture1 = "Leo.png";
    private final String texture2 =  "hut2.png";


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

    public Lia(NPCSave save, List<Player> playerList) {
        this.x = save.getX();
        this.y = save.getY();
        if (NPC.getFatherUser() != null)
            NPC.setFatherUser(save.getFatherUser());
        if (NPC.getFatherPlayer() != null) {
            for (Player player : playerList) {
                if (player.getUser().getUsername().equals(save.getFatherPlayerUsername()))
                    NPC.setFatherPlayer(player);
            }
        }
        this.favorites =  save.getFavorites();
        this.isNPC = save.isNPC();
        this.x_start = save.getX_start();
        this.y_start = save.getY_start();
        this.Tile_x = save.getTile_x();
        this.Tile_y = save.getTile_y();
        this.dialogueText = save.getDialogueText();
        this.requests = save.getRequests();
    }

    {
        favorites.add("Grape");
        favorites.add("Salad");
        favorites.add("Wine");
    }

    {
        requests.add(new Quest("Delivery of 10 woods",0,false,"Wood",10));
        requests.add(new Quest("Delivery of a salmon fish",1,false,"salmon",1));
        requests.add(new Quest("Delivery of 200 woods",2,false,"Wood",200));
    }
    public void giveReward(Player player, int index, Game game) {
        if (index == 0) {
            player.addcoin(500);
        } else if (index == 1) {
            Food f = new Food(FoodType.BakedFish);
            f.setType(FoodType.SalmonDinner);
            player.getBackPack().addItemToInventory(f);
        } else {
            for (int i =0 ; i < 3; i++) {
                CraftingItem c = new CraftingItem(CraftingItemType.DeluxeScarecrow, player, game);
                player.getBackPack().addItemToInventory(c);
            }
        }
    }
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String getTexture() {
        if (isNPC)
            return texture1;
        else
            return texture2;
    }
}
