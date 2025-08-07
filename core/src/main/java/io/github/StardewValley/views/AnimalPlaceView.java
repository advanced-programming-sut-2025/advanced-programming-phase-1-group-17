package io.github.StardewValley.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.shared.dto.AnimalPlaceDTO;
import io.github.StardewValley.shared.models.animal.AnimalPlaceType;

import java.util.HashMap;
import java.util.Map;

public class AnimalPlaceView {


    public AnimalPlaceView() {

    }

    public void render(SpriteBatch batch, AnimalPlaceDTO dto) {
        if (dto == null) return;

        Texture texture = GameAssetManagerClient.getGameAssetManager().getTexture(dto.getAnimalPlaceType().getInventoryTexturePath());
        if (texture != null) {
            batch.draw(texture, dto.getX(), dto.getY());
        }
    }

    public Rectangle getHitBox(AnimalPlaceDTO dto) {
        // برای هماهنگی، از همان AssetManager استفاده کن
        Texture texture = GameAssetManagerClient.getGameAssetManager().getTexture(dto.getAnimalPlaceType().getInventoryTexturePath());
        if (texture != null) {
            return new Rectangle(dto.getX(), dto.getY(), texture.getWidth(), texture.getHeight());
        }
        return new Rectangle();
    }

    public void dispose() {
        // Dispose of all loaded textures to prevent memory leaks

    }
}
