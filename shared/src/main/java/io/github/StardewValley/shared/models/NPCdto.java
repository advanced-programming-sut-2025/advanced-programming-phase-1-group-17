package io.github.StardewValley.shared.models;

public class NPCdto {
    private int x;
    private int y;
    private String name;
    private String texture;
    public NPCdto(int x, int y, String name, String texture) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.texture = texture;
    }
    public NPCdto() {}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }
}
