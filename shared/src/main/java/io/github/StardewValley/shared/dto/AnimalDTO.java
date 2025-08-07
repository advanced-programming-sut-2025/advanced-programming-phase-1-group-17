package io.github.StardewValley.shared.dto;

import io.github.StardewValley.shared.models.animal.AnimalType;
import io.github.StardewValley.shared.models.enums.Direction;

// این کلاس فقط داده حمل می‌کند. هیچ منطقی ندارد.
public class AnimalDTO {
    private String id;
    private AnimalType animalType;
    private float x;
    private float y;
    private Direction direction;
    private boolean isEating;
    private boolean showPetHeart; // افکت نوازش را نشان بده
    private boolean showAlreadyPettedHeart; // افکت "قبلا نوازش شده" را نشان بده

    // کانستراکتور خالی برای Jackson
    public AnimalDTO() {}

    // Getters and Setters for all fields...
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public AnimalType getAnimalType() { return animalType; }
    public void setAnimalType(AnimalType animalType) { this.animalType = animalType; }
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public Direction getDirection() { return direction; }
    public void setDirection(Direction direction) { this.direction = direction; }
    public boolean isEating() { return isEating; }
    public void setEating(boolean eating) { this.isEating = eating; }
    public boolean isShowPetHeart() { return showPetHeart; }
    public void setShowPetHeart(boolean showPetHeart) { this.showPetHeart = showPetHeart; }
    public boolean isShowAlreadyPettedHeart() { return showAlreadyPettedHeart; }
    public void setShowAlreadyPettedHeart(boolean showAlreadyPettedHeart) { this.showAlreadyPettedHeart = showAlreadyPettedHeart; }
}
