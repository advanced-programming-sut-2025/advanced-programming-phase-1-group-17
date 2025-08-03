package io.github.StardewValley.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.MainMenuController;
import io.github.StardewValley.controllers.SignUpMenuController;

public class WelcomeMenu implements Screen {
    private Game game;
    private Texture welcomeImage;
    private SpriteBatch batch;
    private float timePassed = 0;
    private boolean isLoggedInUser = false;

    private OrthographicCamera camera;
    private Viewport viewport;

    public WelcomeMenu(Game game, boolean isLoggedIn) {
        this.game = game;
        this.isLoggedInUser = isLoggedIn;
        welcomeImage = new Texture("Logo.png");
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera); // Full-screen scaling
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        timePassed += delta;

        camera.update();
        batch.setProjectionMatrix(camera.combined); // ✅ Very important

        batch.begin();
        batch.draw(welcomeImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        if (timePassed > 3f) {
            if (!isLoggedInUser) {
                Main.getMain().setScreen(new SignUpMenu(new SignUpMenuController(), GameAssetManagerClient.getGameAssetManager().getSkin()));
            } else {
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManagerClient.getGameAssetManager().getSkin()));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        welcomeImage.dispose();
        batch.dispose();
    }
}
