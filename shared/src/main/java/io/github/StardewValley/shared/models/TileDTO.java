package io.github.StardewValley.shared.models;

import io.github.StardewValley.shared.models.backpack.NormalItem;
import io.github.StardewValley.shared.models.backpack.NormalItemType;
import io.github.StardewValley.shared.models.map.Tile;
import io.github.StardewValley.shared.models.plant.Crop;
import io.github.StardewValley.shared.models.plant.Tree;
import io.github.StardewValley.shared.models.plant.TreeType;

public class TileDTO {
    private int x;
    private int y;
    private boolean crowImmunity = false;
    private String texturePath;
    private boolean isWalkAble = true;
    private String placeableType;
    private String ownerUsername;
    private boolean isPlowed = false;
    private int grassTextureID;
    private boolean isCropGiant = false;
    private boolean isLeftBottomCornerOfGiantCrop = false;
    private TreeType treeType;

    public TileDTO(){}

    public TileDTO(Tile tile) {
        this.x = tile.getX();
        this.y = tile.getY();
        this.isWalkAble = tile.isWalkAble();
        this.isPlowed = tile.isPlowed();
        if (tile.getPlaceable() != null) texturePath = tile.getPlaceable().getTexture();
        else texturePath = null;
        this.placeableType = tile.getPlaceable() != null ? tile.getPlaceable().getClass().getSimpleName() : null;
        this.ownerUsername = tile.getOwner() != null ? tile.getOwner().getUser().getUsername() : null;
        this.crowImmunity = tile.isCrowImmunity();
        if (tile.getPlaceable() instanceof NormalItem normalItem) {
            if (normalItem.getType().equals(NormalItemType.Grass))
                grassTextureID = normalItem.getGrassTextureID();
        }
        if (tile.getPlaceable() instanceof Crop crop) {
            isCropGiant = crop.isGiant();
            isLeftBottomCornerOfGiantCrop = crop.isLeftBottomTileOfGiant();
        }
        if (tile.getPlaceable() instanceof Tree tree) {
            treeType = tree.getType();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isCrowImmunity() {
        return crowImmunity;
    }

    public void setCrowImmunity(boolean crowImmunity) {
        this.crowImmunity = crowImmunity;
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

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public String getPlaceableType() {
        return placeableType;
    }

    public void setPlaceableType(String placeableType) {
        this.placeableType = placeableType;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public int getGrassTextureID() {
        return grassTextureID;
    }

    public boolean isCropGiant() {
        return isCropGiant;
    }

    public boolean isLeftBottomCornerOfGiantCrop() {
        return isLeftBottomCornerOfGiantCrop;
    }

    public TreeType getTreeType() {
        return treeType;
    }
}
