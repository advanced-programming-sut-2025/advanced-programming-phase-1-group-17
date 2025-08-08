package io.github.StardewValley;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.StardewValley.shared.models.UserDTO;
import io.github.StardewValley.views.*;

public class Main extends Game {

    private static Main main;
    private static SpriteBatch batch;
    private static ShapeRenderer shapeRenderer;
    private static boolean isLoggedInUser = false;
    private static GameView gameView;
    private static final String serverIP = "localhost";
    private static final int serverPort = 8080;

    private static String jwtToken;


    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        Main.getMain().setScreen(new WelcomeMenu(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

    public static ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public static GameView getGameView() {
        return gameView;
    }

    public static void setGameView(GameView gameView) {
        Main.gameView = gameView;
    }

    public static String getServerIP() {
        return serverIP;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static String getJwtToken() {
        return jwtToken;
    }
    public static void setJwt(String jwtToken) {
        Main.jwtToken = jwtToken;
    }
}
