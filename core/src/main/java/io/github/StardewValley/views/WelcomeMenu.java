package io.github.StardewValley.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.StardewValley.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.SignUpMenuController;

public class WelcomeMenu implements Screen {
    private Game game;
    private Texture welcomeImage;
    private SpriteBatch batch;
    private float timePassed = 0;

    public WelcomeMenu(Game game) {
        this.game = game;
        welcomeImage = new Texture("Logo.png");
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        timePassed += delta;

        batch.begin();
        batch.draw(welcomeImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if (timePassed > 3f) {
            Main.getMain().setScreen(new SignUpMenu(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin()));

        }
    }

    @Override
    public void resize(int width, int height) {
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
        welcomeImage.dispose();
        batch.dispose();
    }
}


