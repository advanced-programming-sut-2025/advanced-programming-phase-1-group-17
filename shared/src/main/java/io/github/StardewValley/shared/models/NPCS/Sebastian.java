package io.github.StardewValley.shared.models.NPCS;

import io.github.StardewValley.shared.models.map.Placeable;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.foraging.Mineral;
import io.github.StardewValley.shared.models.foraging.MineralType;
import io.github.StardewValley.shared.models.saveClasses.NPCSave;

import java.util.List;

public class Sebastian extends NPC implements Placeable {
    {
        name = "Sebastian";
        job = "cook";
    }
    private final String texture1 = "Sebastian.png";
    private final String texture2 = "hut2.png";

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

    public Sebastian(NPCSave save, List<Player> playerList) {
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
        favorites.add("Wool");
        favorites.add("PumpkinPie");
        favorites.add("Pizza");
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
            player.addcoin(5000);
        } else {
            for (int i = 0; i < 50; i++) {
                Mineral m = new Mineral(MineralType.Quartz, false);
                player.getBackPack().addItemToInventory(m);
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
