package io.github.StardewValley.shared.models.map;

import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.backpack.NormalItem;
import io.github.StardewValley.shared.models.backpack.NormalItemType;
import io.github.StardewValley.shared.models.foraging.Mineral;
import io.github.StardewValley.shared.models.foraging.MineralType;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.plant.Crop;
import io.github.StardewValley.shared.models.plant.Tree;
import io.github.StardewValley.shared.models.saveClasses.TileSave;

import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private Placeable placeable;
    private boolean isWalkAble = true;
    private boolean isPlowed = false;
    private Player owner;
    private NPC npcIsHere;
    private boolean crowImmunity = false;
    private static ArrayList<Tile> tiles = new ArrayList<Tile>();

    public Tile() {}

    public Tile(int x, int y, Player owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
        tiles.add(this);
    }

    public Tile(TileSave tileSave, Game game) {
        this.x = tileSave.getX();
        this.y = tileSave.getY();
        this.isWalkAble = tileSave.isWalkAble();
        this.isPlowed = tileSave.isPlowed();

        game.getPlaceableFromSave(this, tileSave);
        for (Player player : game.getPlayers()) {
            if (player.getUser().getUsername().equals(tileSave.getOwner())) {
                this.owner = player;
                break;
            }
        }
        for (NPC npc : game.getNPCs()) {
            if (npc.getName().equals(tileSave.getOwner())) {
                this.npcIsHere = npc;
                break;
            }
        }
        this.crowImmunity = tileSave.isCrowImmunity();
        tiles.add(this);
    }

    public static ArrayList<Tile> getTiles() {
        return tiles;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Placeable getPlaceable() {
        return placeable;
    }

    public void setPlaceable(Placeable placeable) {
        if (owner.getUser().getActiveGame() == null) {
            this.placeable = placeable;
            if (placeable instanceof Tree) {
                this.setWalkAble(false);
            }
        }
        else {
            this.placeable = placeable;
            if (placeable instanceof Tree) {
                this.setWalkAble(false);
            }
        }
    }

    public boolean isWalkAble() {
        return isWalkAble;
    }

    public void setWalkAble(boolean walkAble) {
        isWalkAble = walkAble;
    }

    public boolean isPlowed() {
        return isPlowed;
    }

    public void setPlowed(boolean plowed) {
        isPlowed = plowed;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isWater() {
        if (placeable instanceof Lake)
            return true;
        else if (placeable instanceof NormalItem normalItem){
            return normalItem.getType().equals(NormalItemType.Well);
        }
        return false;
    }

    public static Tile getTile(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        return null;
    }


    public NPC getNpcIsHere() {
        return npcIsHere;
    }

    public void setNpcIsHere(NPC npcIsHere) {
        this.npcIsHere = npcIsHere;
    }

    //public static boolean findAround(Animal animal){
        //TODO
        //        Player player= App.getCurrentGame().getCurrentPlayingPlayer();
//        int x=player.getTileX();
//        int y=player.getTileY();
//        for(int i=-1;i<2;i++){
//            for(int j=-1;j<2;j++){
//                Tile tile = Tile.getTile(x+i,y+j);
//
//                if(tile != null && tile.getPlaceable() != null && tile.getPlaceable().equals(animal)){
//                    return true;
//                }
//            }
//        }
  //      return false;
   // }

    public void lightningStrike() {
        //TODO
        //LightningController.getLightningController().triggerLightning();
        if (placeable instanceof Tree tree) {
            if (!tree.isInsideGreenhouse()) {
                placeable = new Mineral(MineralType.Coal, false);
//              TODO
//                Main.getGameView().showNotification("Tree (%s) in tile (%d, %d) was lightninged."
//                    .formatted(tree.getType().name(), tree.getTile().getX(), tree.getTile().getY()));
            }
        } else if (placeable instanceof Crop crop) {
            if (!crop.isInsideGreenhouse()) {
                crop.getTile().setPlaceable(null);
                //TODO
                //                Main.getGameView().showNotification("Tree (%s) in tile (%d, %d) was lightninged."
//                    .formatted(crop.getType().name(), crop.getTile().getX(), crop.getTile().getY()));
            }
        }
    }

    public boolean isCrowImmunity() {
        return crowImmunity;
    }

    public void setCrowImmunity(boolean crowImmunity) {
        this.crowImmunity = crowImmunity;
    }

    public static Tile getTileByClick(int x,int y){
        for(Tile tile : tiles){
            if(tile.getX() == x/120 && tile.getY() == y/120){
                return tile;
            }
        }
        return null;
    }
    public static Tile getTileFromPixel(int px, int py) {
        int tx = px / 120;
        int ty = py / 120;
        return getTile(tx, ty);
    }

    public static void setTiles(ArrayList<Tile> tiles) {
        Tile.tiles = tiles;
    }
}
