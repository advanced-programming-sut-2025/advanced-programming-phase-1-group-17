package io.github.StardewValley.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.helperControllers.GameStateApiClient;
import io.github.StardewValley.shared.models.CraftResponseDTO;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.crafting.CraftingItemType;
import io.github.StardewValley.views.CraftingShow;
import io.github.StardewValley.views.GameView;

import java.util.Map;
public class CraftingController {
    private CraftingShow view;
    private GameView gameView;
    private GameStateApiClient apiClient; // اضافه کردن ApiClient

    public void setView(CraftingShow view, GameView gameView) {
        this.view = view;
        this.gameView = gameView;
        this.apiClient = GameClient.getGameStateApiClient(); // گرفتن ApiClient
        handleButtons();
    }

    // این متد کاملا بازنویسی می‌شود
    public void handleIngredients(CraftingItemType item) {
        try {
            // 1. درخواست به سرور ارسال می‌شود
            CraftResponseDTO response = apiClient.attemptCraft(item);

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
    public void handleIngredientsLabel(CraftingItemType item) {
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
