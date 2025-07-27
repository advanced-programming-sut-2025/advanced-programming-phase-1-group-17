package io.github.StardewValley.views;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Gdx;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.LobbyDto;


public class LobbyRoomScreen extends ScreenAdapter {
    private final Stage stage;
    private final LobbyDto lobby;
    private final LobbyApiClient apiClient;
    private final String currentUsername;

    public LobbyRoomScreen(LobbyDto lobby, LobbyApiClient apiClient, String currentUsername) {
        this.stage = new Stage(new ScreenViewport());
        this.lobby = lobby;
        this.apiClient = apiClient;
        this.currentUsername = currentUsername;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Label title = new Label("Lobby: " + lobby.getName(), skin);
        root.add(title).colspan(2).pad(10);
        root.row();

        root.add(new Label("Players:", skin)).left().padBottom(10).colspan(2);
        root.row();

        for (String username : lobby.getPlayerUsernames()) {
            root.add(new Label("- " + username, skin)).left().colspan(2);
            root.row();
        }

        // فقط اگر currentUser ادمینه، دکمه Start Game رو نشون بده
        if (lobby.getAdminUsername().equals(currentUsername)) {
            TextButton startGameBtn = new TextButton("Start Game", skin);
            startGameBtn.addListener(event -> {
                try {
                    apiClient.startGame(lobby.getId());
                    System.out.println("Game started!");
                    //TODO
                    // بعدش می‌تونه بره به صفحه بازی واقعی
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            });
            root.add(startGameBtn).colspan(2).padTop(20);
        }
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

