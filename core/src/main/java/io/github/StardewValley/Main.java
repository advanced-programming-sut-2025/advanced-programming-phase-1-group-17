package io.github.StardewValley;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.StardewValley.models.App;
import io.github.StardewValley.views.GameView;
import io.github.StardewValley.views.*;

public class Main extends Game {

    private static Main main;
    private static SpriteBatch batch;
    private static ShapeRenderer shapeRenderer;
    private static boolean isLoggedInUser = false;
    private static GameView gameView;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();


//        ArrayList<User> users = LoadUser.loadUsers();
//        if (users != null) {
//            App.setUsers(users);
//        }
//        User user1 = LoadUser.loadStayLoggedInUser();
        if (user1 != null) {
            assert users != null;
            for (User user : users) {
                if (user.equals(user1)) {
                    App.setLoggedInUser(user);
                    isLoggedInUser = true;
                    Main.getMain().setScreen(new WelcomeMenu(this,true));
                }
            }
        }
        if (!isLoggedInUser) {
            Main.getMain().setScreen(new WelcomeMenu(this,false));
        }
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
}
