package io.github.StardewValley;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.StardewValley.controllers.GameMenuController;
import io.github.StardewValley.controllers.ProfileMenuController;
import io.github.StardewValley.controllers.SignUpMenuController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.User;
import io.github.StardewValley.models.enums.Menu;
import io.github.StardewValley.views.GameMenu;
import io.github.StardewValley.views.ProfileMenu;
import io.github.StardewValley.views.SignUpMenu;

import java.util.ArrayList;

public class Main extends Game {

    private static Main main;
    private static SpriteBatch batch;
    private static ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        ArrayList<User> users = LoadUser.loadUsers();
        if (users != null) {
            App.setUsers(users);
        }
        User user1 = LoadUser.loadStayLoggedInUser();
        if (user1 != null) {
            assert users != null;
            for (User user : users) {
                if (user.equals(user1)) {
                    App.setLoggedInUser(user);
                    App.setCurrentMenu(Menu.MainMenu);
                }
            }
        }
//        (new AppView()).run();
        Main.getMain().setScreen(new SignUpMenu(new SignUpMenuController(),GameAssetManager.getGameAssetManager().getSkin()));
        //Main.getMain().setScreen(new ProfileMenu(new ProfileMenuController(),GameAssetManager.getGameAssetManager().getSkin()));
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

}
