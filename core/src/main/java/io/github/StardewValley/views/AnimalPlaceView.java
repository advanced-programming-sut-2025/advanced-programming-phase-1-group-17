package io.github.StardewValley.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.shared.dto.AnimalPlaceDTO;

public class AnimalPlaceView {

    public AnimalPlaceView() {

    }


    public void render(SpriteBatch batch, AnimalPlaceDTO dto) {
        if (dto == null) return;

        Texture texture = GameAssetManagerClient.getGameAssetManager()
            .getTexture(dto.getAnimalPlaceType().getInventoryTexturePath());

        if (texture != null) {
            batch.draw(texture, dto.getX(), dto.getY());
        }
    }



    public void dispose() {
        // چون AssetManager تکسچرها را مدیریت می‌کند، اینجا کاری برای انجام دادن نداریم.
    }
}
