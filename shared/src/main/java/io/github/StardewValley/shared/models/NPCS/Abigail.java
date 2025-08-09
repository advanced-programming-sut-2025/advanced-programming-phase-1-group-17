package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.UserDTO;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.crafting.CraftingItem;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.shared.models.saveClasses.NPCSave;

import java.util.ArrayList;
import java.util.List;

public class Abigail extends NPC implements Placeable {
    {
        name = "Abigail";
        job = "Miner";
    }
    private final String texture1 = "Abigail.png";
    private final String texture2 = "hut2.png";

    public Abigail(boolean isAbigail) {
        this.dialogueText.add("hello i am abigail");
        this.dialogueText.add("hi how are you?");
        this.dialogueText.add("what do you do on vacation?");
        this.dialogueText.add("I am a miner");
        this.dialogueText.add("this is a village in iran.");
        this.dialogueText.add("be careful,the weather is very hot.go home quickly.");
        isNPC = isAbigail;
    }
    public Abigail(boolean isAbigail, int x, int y) {
        x_start = x;
        y_start = y;
        isNPC = isAbigail;
    }

    public Abigail(NPCSave save, List<Player> playerList) {
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
        favorites.add("Stone");
        favorites.add("IronOre");
        favorites.add("Coffee");
    }

    {
        requests.add(new Quest("Delivery of a gold bar", 0, false, "Gold Bar", 1));
        requests.add(new Quest("Delivery of a pumpkin", 1, false, "Pumpkin", 1));
        requests.add(new Quest("Delivery of 50 wheat", 2, false, "Wheat", 50));
    }

    public void giveReward(Player player, int index, Game game) {
        if (index == 0) {
            player.getFriendShipsWithNPCs().put(this, Math.min(799, player.getFriendShipsWithNPCs().get(this) + 200));
        } else if (index == 1) {
            player.addcoin(500);
        } else {
            CraftingItem c = new CraftingItem(CraftingItemType.IridiumSprinkler, player, game);
            player.getBackPack().addItemToInventory(c);
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
