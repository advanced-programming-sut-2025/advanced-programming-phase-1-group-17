package io.github.StardewValley.controllers;

import io.github.StardewValley.models.App;
import io.github.StardewValley.models.BackPackableType;
import io.github.StardewValley.models.Player;
import io.github.StardewValley.views.GiftMenu;

import javax.swing.plaf.SplitPaneUI;

public class GiftMenuController {
    private GiftMenu view;
    private Player targetPlayer;
    private Player currentPlayer;
    private int amount;
    public void setView(GiftMenu view, Player targetPlayer) {
        this.targetPlayer = targetPlayer;
        this.view = view;
        currentPlayer = App.getCurrentGame().getCurrentPlayingPlayer();


    }
    public void handleItemClick (BackPackableType backPackableType ) {



    }


}
