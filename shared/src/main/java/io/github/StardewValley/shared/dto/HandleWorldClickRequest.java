package io.github.StardewValley.shared.dto;

public class HandleWorldClickRequest {
    private float x;
    private float y;
    private int button;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }
}
