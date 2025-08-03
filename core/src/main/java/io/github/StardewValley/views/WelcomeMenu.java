package io.github.StardewValley.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.TokenStorage;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.MainMenuController;
import io.github.StardewValley.controllers.SignUpMenuController;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WelcomeMenu implements Screen {
    private Game game;
    private Texture welcomeImage;
    private SpriteBatch batch;
    private float timePassed = 0;
    private boolean isLoggedInUser = false;

    public WelcomeMenu(Game game, boolean isLoggedIn) {
        this.game = game;
        this.isLoggedInUser = isLoggedIn;
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
            try {
                String savedToken = TokenStorage.loadToken();
                if (savedToken != null && tokenIsValid(savedToken)) {
                    Main.setJwt(savedToken);
                    Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManagerClient.getGameAssetManager().getSkin()));
                } else {
                    Main.getMain().setScreen(new SignUpMenu(new SignUpMenuController(), GameAssetManagerClient.getGameAssetManager().getSkin()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public boolean tokenIsValid(String token) throws Exception {
        URL url = new URL("http://localhost:8080/api/auth/tokenIsValid?token=" + URLEncoder.encode(token, "UTF-8"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            try (InputStream is = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(is, boolean.class);
            }
        } else {
            System.out.println("Error: " + responseCode);
            return false;
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


