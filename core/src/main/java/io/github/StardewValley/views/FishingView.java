package io.github.StardewValley.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.animal.FishingController;
import io.github.StardewValley.shared.models.market.Fish;
import io.github.StardewValley.shared.models.tools.FishingPoleType;
import io.github.StardewValley.shared.models.tools.Tool;
import io.github.StardewValley.shared.models.tools.ToolMaterial;
import io.github.StardewValley.shared.models.tools.ToolType;

public class FishingView implements Screen {
    private FishingController controller;
    private TextureRegion barTexture;
    private TextureRegion targetTexture;
    private BitmapFont font;
    private Fish fish;
    public FishingView(FishingController controller, Skin skin) {
        //TODO
        //App.getCurrentGame().getCurrentPlayingPlayer().setCurrentTool(new Tool(ToolType.FishingPole, ToolMaterial.Basic,FishingPoleType.TrainingFishingPole));
        this.controller = controller;
        barTexture = new TextureRegion(new Texture("bar.png"));
        targetTexture = new TextureRegion(new Texture("target.png"));
        font = new BitmapFont();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        float baseX = 800;
        float baseY = 200;

        // target (ماهی)
        controller.update(v);
        Main.getBatch().draw(barTexture, baseX, baseY + controller.getBarY(), 20, controller.getBarHeight());
        Main.getBatch().draw(targetTexture, baseX, baseY + controller.getTargetY(), 20, 20);

        // مستطیل (نوار کنترل)
        font.draw(Main.getBatch(),String.valueOf(controller.getSuccess()),1700,700);
        font.draw(Main.getBatch(),controller.getFish().getName() + " :" + controller.getFishCount(),1500,500);
        if(controller.getSuccess()>=100){
            font.draw(Main.getBatch(),"you got " + controller.getFishCount()  + " " + controller.getFish().getName(),900,960);
            //TODo
//            App.getCurrentGame().getCurrentPlayingPlayer().setCurrentTool(controller.savedTool);
//            App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().addItemToInventory(controller.getFish());
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(Main.getGameView());

        }

        Main.getBatch().end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
