package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.backpack.BackPackableType;
import io.github.StardewValley.shared.models.cooking.CookResponseDTO;
import io.github.StardewValley.shared.models.cooking.Food;
import io.github.StardewValley.shared.models.cooking.FoodType;

import java.util.ArrayList;
import java.util.Map;

public class Refrigerator implements Screen {
    private Stage stage;
    private Skin skin;
    private Table table;
    private Window infoWindow;
    private GameView gameView;
    private Label ingredients;
    private Label errorMessage;
    private TextButton backButton;
    private ArrayList<Food>foods = new ArrayList<>();


    public Refrigerator(Skin skin, GameView gameView) throws Exception {
        foods = GameClient.gameStateApiClient.updateRef();
        this.skin = skin;
        this.gameView = gameView;
        this.stage = new Stage();
        this.table = new Table();
        this.errorMessage = new Label("", skin);
        this.backButton = new TextButton("back",skin);
        handleButtons();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);

        for (Food food : foods) {


            Texture texture = new Texture(food.getType().getInventoryTexturePath());
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));

            ImageButton btn = new ImageButton(style);

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        GameClient.gameStateApiClient.addToBackPack(food);
                        foods = GameClient.gameStateApiClient.updateRef();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            table.add(btn).size(64, 64).pad(5);
        }

        stage.addActor(table);
        backButton.setPosition(Gdx.graphics.getWidth()-backButton.getWidth(),Gdx.graphics.getHeight()-backButton.getHeight());
        stage.addActor(backButton);
    }



    private void showDetails(FoodType type, float x, float y) {
        if (infoWindow != null && infoWindow.hasParent()) {
            infoWindow.remove();
        }

        infoWindow = new Window("",skin);

        errorMessage.setText("");

        TextButton okButton = new TextButton("Cook", skin);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleIngredients(type);
            }
        });
        infoWindow.add(ingredients).pad(10);
        infoWindow.row();
        infoWindow.add(errorMessage);
        infoWindow.row();
        infoWindow.add(okButton);
        infoWindow.pack();
        infoWindow.setSize(600, 300);
        infoWindow.setPosition(x + 70, y);
        stage.addActor(infoWindow);
    }
    public void handleIngredients(FoodType item) {
        try {
            // 1. درخواست به سرور ارسال می‌شود
            CookResponseDTO response = GameClient.gameStateApiClient.attemptCook(item);

            // 2. نتیجه‌ای که از سرور آمده نمایش داده می‌شود
            errorMessage.setText(response.getMessage());

            // اگر موفق بود، می‌توانید صفحه را ببندید یا هر کار دیگری بکنید
            if (response.isSuccess()) {
                // مثلا می‌توانید inventory را رفرش کنید
            }

        } catch (Exception e) {
            e.printStackTrace();
            // نمایش یک خطای عمومی در صورت مشکل در ارتباط با سرور
            errorMessage.setText("Error connecting to the server.");
        }
    }
    public void handleIngredientsLabel(FoodType item) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<BackPackableType, Integer> entry : item.getIngredients().entrySet()) {
            sb.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append("\n");
        }
        ingredients.setText(sb.toString());
    }
    public void handleButtons(){
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }

        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
        try {
            foods = GameClient.gameStateApiClient.updateRef();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

    public Label getIngredients() {
        return ingredients;
    }

    public void setIngredients(Label ingredients) {
        this.ingredients = ingredients;
    }

    public Label getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Label errorMessage) {
        this.errorMessage = errorMessage;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setBackButton(TextButton backButton) {
        this.backButton = backButton;
    }
}
