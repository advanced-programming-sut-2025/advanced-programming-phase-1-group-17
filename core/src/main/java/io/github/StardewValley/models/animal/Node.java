package io.github.StardewValley.models.animal;

import io.github.StardewValley.models.map.Tile;

public class Node {
    public Tile tile;
    public Node parent;
    public float g, h;

    public Node(Tile tile) {
        this.tile = tile;
    }

    public float getF() {
        return g + h;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return other.tile.getX() == tile.getX() && other.tile.getY() == tile.getY();
    }

    @Override
    public int hashCode() {
        return tile.getX() * 1000 + tile.getY();
    }
}
