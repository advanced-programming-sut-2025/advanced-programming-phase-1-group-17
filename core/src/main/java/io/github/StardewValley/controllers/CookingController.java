package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.cooking.CookResponseDTO;
import io.github.StardewValley.shared.models.cooking.FoodType;
import io.github.StardewValley.views.CookingShow;
import io.github.StardewValley.views.GameView;

import java.util.Map;

public class CookingController {
    private CookingShow view;
    private GameView gameView;
    public void setView(CookingShow view, GameView gameView) {
        this.view = view;
        this.gameView = gameView;
        handleButtons();

    }
    public void handleIngredients(FoodType item) {
        try {
            // 1. درخواست به سرور ارسال می‌شود
            CookResponseDTO response = GameClient.gameStateApiClient.attemptCook(item);

            // 2. نتیجه‌ای که از سرور آمده نمایش داده می‌شود
            view.getErrorMessage().setText(response.getMessage());

            // اگر موفق بود، می‌توانید صفحه را ببندید یا هر کار دیگری بکنید
            if (response.isSuccess()) {
                // مثلا می‌توانید inventory را رفرش کنید
            }

        } catch (Exception e) {
            e.printStackTrace();
            // نمایش یک خطای عمومی در صورت مشکل در ارتباط با سرور
            view.getErrorMessage().setText("Error connecting to the server.");
        }
    }
    public void handleIngredientsLabel(FoodType item) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<BackPackableType, Integer> entry : item.getIngredients().entrySet()) {
            sb.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append("\n");
        }
        view.getIngredients().setText(sb.toString());
    }
    public void handleButtons(){
        view.getBackButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }

        });
    }
}
