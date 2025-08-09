package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.cooking.Food;
import io.github.StardewValley.shared.models.cooking.FoodType;
import io.github.StardewValley.shared.models.saveClasses.NPCSave;

import java.util.List;

public class Harvey extends NPC implements Placeable {
    private final String  texture1 = "Harvey.png";
    private final String  texture2 = "hut2.png";

    public Harvey(boolean isHarvey) {
        this.name = "Harvey";
        this.job = "teacher";
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

    public Harvey(NPCSave save, List<Player> playerList) {
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
        favorites.add("Wine");
        favorites.add("Pickles");
        favorites.add("Coffee");
    }

    {
        requests.add(new Quest("Delivery of 12 pieces of a desired CarrotSeed", 0, false, "CarrotSeeds", 12));
        requests.add(new Quest("Delivery of a salmon fish", 1, false, "Salmon", 1));
        requests.add(new Quest("Delivery of a bottle of wine", 2, false, "Wine", 1));
    }

    public void giveReward(Player player, int index) {
        if (index == 0) {
            player.addcoin(750);
        } else if (index == 1) {
            player.getFriendShipsWithNPCs().put(this, Math.min(799, player.getFriendShipsWithNPCs().get(this) + 200));
        } else {
            for (int i = 0; i < 5; i++) {
                Food f = new Food(FoodType.Pizza);
                f.setFoodtype(FoodType.Salad);
                player.getBackPack().addItemToInventory(f);
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
