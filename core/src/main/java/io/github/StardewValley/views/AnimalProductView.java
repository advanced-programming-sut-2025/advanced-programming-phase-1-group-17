package io.github.StardewValley.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.shared.dto.AnimalProductDTO;

public class AnimalProductView {

    public AnimalProductView() {
        // AssetManager مسئول لود کردن تکسچرهاست، پس کانستراکتور خالی است.
    }

    /**
     * یک محصول را بر اساس DTO دریافتی رسم می‌کند.
     */
    public void render(SpriteBatch batch, AnimalProductDTO dto) {
        if (dto == null) return;

        Texture texture = GameAssetManagerClient.getGameAssetManager()
            .getTexture(dto.getType().getInventoryTexturePath());

        if (texture != null) {
            batch.draw(texture, dto.getX(), dto.getY());
        }
    }

    /**
     * یک hitbox برای تشخیص کلیک در کلاینت می‌سازد.
     */

}
