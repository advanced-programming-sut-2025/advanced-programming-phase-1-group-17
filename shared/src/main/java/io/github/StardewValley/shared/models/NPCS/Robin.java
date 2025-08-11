package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.models.saveClasses.NPCSave;

import java.util.List;

public class Robin extends NPC implements Placeable {
    {
        name = "Robin";
        job = "architect";
    }

    private final String texture1 = "Robin.png";
    private final String texture2 =  "hut2.png";

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

    public Robin(NPCSave save, List<Player> playerList) {
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
        favorites.add("IronBar");
        favorites.add("Wood");
        favorites.add("Spaghetti");
    }

    {
        requests.add(new Quest("Delivery of 80 sticks", 0, false, "Wood", 80));
        requests.add(new Quest("Delivery of 10 iron ingots", 1, false, "IronBar", 10));
        requests.add(new Quest("Delivery of 1000 sticks", 2, false, "Wood", 1000));
    }

    public void giveReward(Player player, int index, Game game) {
        if (index == 0) {
            player.addcoin(1000);
        } else if (index == 1) {
            for (int i = 0; i < 3; i++) {
                CraftingItem c = new CraftingItem(CraftingItemType.BeeHouse, player, game);
                player.getBackPack().addItemToInventory(c);
            }
        } else {
            player.addcoin(25000);
        }
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String  getTexture() {
        if (isNPC)
            return texture1;
        else
            return texture2;
    }
}
