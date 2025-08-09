package io.github.StardewValley.shared.models.saveClasses;

import io.github.StardewValley.shared.models.backpack.BackPack;
import io.github.StardewValley.shared.models.backpack.BackPackType;

import java.util.ArrayList;
import java.util.HashMap;

public class BackPackSave {
    private String playerUsername;
    private HashMap<Pair<String, String>, ArrayList<BackPackableSave>> backPackItems;
    private BackPackType type;

    public BackPackSave () {}

    public BackPackSave (BackPack backPack) {
        this.playerUsername = backPack.getPlayer().getUser().getUsername();
        backPack.getBackPackItems().forEach(((backPackableType, backPackables) -> {
            ArrayList<BackPackableSave> backPackableSaves = new ArrayList<>();
            backPackables.forEach((backPackable -> backPackableSaves.add(backPackable.toBackpackableSave())));
            this.backPackItems.put(
                new Pair<String, String>(backPackableType.getName(), backPackableType.getClass().getSimpleName()),
                backPackableSaves
            );
        }));
        this.type = backPack.getType();
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public HashMap<Pair<String, String>, ArrayList<BackPackableSave>> getBackPackItems() {
        return backPackItems;
    }

    public BackPackType getType() {
        return type;
    }
}
