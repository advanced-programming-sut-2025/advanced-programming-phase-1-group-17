package io.github.StardewValley.shared.dto;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.animal.AnimalProductType;
import io.github.StardewValley.shared.models.backpack.BackPackable;
import io.github.StardewValley.shared.models.market.ItemQuality;
import io.github.StardewValley.shared.models.saveClasses.BackPackableSave;

public class AnimalProductDTO implements BackPackable {
    private String id; // شناسه منحصر به فرد برای هر محصول روی زمین
    private AnimalProductType type;
    private ItemQuality quality;
    private float x;
    private float y;
    private AnimalDTO animalDTO;

    // کانستراکتور خالی برای Jackson
    public AnimalProductDTO() {}

    // Getters and Setters for all fields...
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @Override
    @JsonIgnore
    public String getName() {
        return type.name();
    }

    @Override
    @JsonIgnore
    public double getPrice() {
        return this.type.getPrice();
    }


    @Override
    public BackPackableSave toBackpackableSave() {
        return null;
    }
    @Override
    public AnimalProductType getType() {
        return type;
    }

    public void setType(AnimalProductType type) { this.type = type; }
    public ItemQuality getQuality() { return quality; }
    public void setQuality(ItemQuality quality) { this.quality = quality; }
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public AnimalDTO getAnimalDTO() {
        return animalDTO;
    }

    public void setAnimalDTO(AnimalDTO animalDTO) {
        this.animalDTO = animalDTO;
    }

    @JsonIgnore
    public Rectangle getHitBox() {
        return new Rectangle(x,y, new Texture(this.type.getInventoryTexturePath()).getWidth(),new Texture(this.type.getInventoryTexturePath()).getHeight());
    }

}
