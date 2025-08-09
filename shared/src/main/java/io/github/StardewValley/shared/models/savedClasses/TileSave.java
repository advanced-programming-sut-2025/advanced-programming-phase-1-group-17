package io.github.StardewValley.shared.models.savedClasses;

import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.Player;

public class TileSave {
    private int x;
    private int y;
    private PlaceableSave placeableSave;
    private boolean isWalkAble = true;
    private boolean isPlowed = false;
    private Player owner;
    private NPC npcIsHere;
    private boolean crowImmunity = false;
}
