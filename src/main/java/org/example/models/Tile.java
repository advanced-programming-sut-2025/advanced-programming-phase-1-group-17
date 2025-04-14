package org.example.models;

import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private boolean isPlowed = false;

    public void plant(String plantName){
        isPlowed = false;
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
}
