package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.backpack.BackPack;
import io.github.StardewValley.shared.models.backpack.BackPackType;
import io.github.StardewValley.shared.models.backpack.BackPackableType;

import java.util.ArrayList;
import java.util.HashMap;

public class BackPackSave {
    private String playerUsername;
    private HashMap<BackPackableType, ArrayList<BackPackableSave>> backPackItems = new HashMap<>();
    private BackPackType type;

    public BackPackSave () {}

    public BackPackSave (BackPack backPack) {
        this.playerUsername = backPack.getPlayer().getUser().getUsername();
        backPack.getBackPackItems().forEach(((backPackableType, backPackables) -> {
            ArrayList<BackPackableSave> backPackableSaves = new ArrayList<>();
            backPackables.forEach((backPackable -> backPackableSaves.add(backPackable.toBackpackableSave())));
            this.backPackItems.put(
                backPackableType,
                backPackableSaves
            );
        }));
        this.type = backPack.getType();
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public HashMap<BackPackableType, ArrayList<BackPackableSave>> getBackPackItems() {
        return backPackItems;
    }

    public BackPackType getType() {
        return type;
    }
}
