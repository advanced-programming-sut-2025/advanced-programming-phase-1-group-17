package io.github.StardewValley.views;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Gdx;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.ChooseMapController;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.App;
import io.github.StardewValley.shared.models.Game;
import io.github.StardewValley.shared.models.GameDTO;
import io.github.StardewValley.shared.models.LobbyDto;
import io.github.StardewValley.shared.models.NPCS.NPC;


public class LobbyRoomScreen implements Screen {
    private final Stage stage;
    private final LobbyDto lobby;
    private final LobbyApiClient apiClient;
    private final String currentUsername;
    private final Table playersTable;
    private final TextButton left;
    private  TextButton startGameBtn;

    public LobbyRoomScreen(LobbyDto lobby, LobbyApiClient apiClient, String currentUsername) {
        this.stage = new Stage(new ScreenViewport());
        this.lobby = lobby;
        this.left = new TextButton("left", GameAssetManagerClient.getGameAssetManager().getSkin());
        this.apiClient = apiClient;
        this.currentUsername = currentUsername;
        this.playersTable = new Table(GameAssetManagerClient.getGameAssetManager().getSkin());
    }

    @Override
    public void show() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> refreshPlayerList());
            }
        }, 0, 2);
        Gdx.input.setInputProcessor(stage);
        Skin skin = GameAssetManagerClient.getGameAssetManager().getSkin();
        Table root = new Table(skin);
        root.setFillParent(true);
        playersTable.setFillParent(true);
        playersTable.center();

        root.left().top();
        Label title = new Label("Lobby: " + lobby.getName(), skin);
        Label id = new Label("ID: " + lobby.getId(), skin);
        Label inviteCode = new Label("Invite Code: " + lobby.getInviteCode(), skin);
        Label isPrivate = new Label((lobby.isPrivate() ? "private" : "public"), skin);
        Label isVisible = new Label(lobby.isVisible() ? "visible" : "invisible", skin);
        root.add(title).colspan(2).pad(10).center();
        root.row();
        root.add(id).colspan(2).pad(10).center();
        root.row();
        root.add(inviteCode).colspan(2).pad(10).center();
        root.row();
        root.add(isPrivate).colspan(2).pad(10).center();
        root.row();
        root.add(isVisible).colspan(2).pad(10).center();
        root.row();

        playersTable.add(new Label("Players:", skin)).left().padBottom(10).colspan(2);
        playersTable.row();

        for (String username : lobby.getPlayerUsernames()) {
            playersTable.add(new Label("- " + username, skin)).left().colspan(2);
            playersTable.row();
        }
        left.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (currentUsername.equals(lobby.getAdminUsername()) && lobby.getPlayerUsernames().size() > 1) {
                        apiClient.changeAdmin(lobby.getId());
                        LobbyDto updatedLobby = apiClient.getLobbyByInviteCode(lobby.getId());
                        lobby.setAdminUsername(updatedLobby.getAdminUsername());
                    }
                    apiClient.leaveLobby(lobby.getId());
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new LobbyScreen(Main.getJwtToken()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        root.row();
        root.add(left).padTop(20).colspan(2);
        root.row();

//        if (lobby.getAdminUsername().equals(currentUsername)) {
        startGameBtn = new TextButton("Start Game", skin);
        startGameBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (lobby.getPlayerUsernames().size() < 2) {
                    return;
                }
                try {
                    GameDTO gameDTO = apiClient.startGame(lobby.getId());
                    //TODO

                    System.out.println("Game started!");
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new chooseMap(new ChooseMapController(), GameAssetManagerClient.getGameAssetManager().getSkin()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        root.add(startGameBtn).colspan(2).padTop(20);
        if (!lobby.getAdminUsername().equals(currentUsername)) {
            startGameBtn.setVisible(false);
        }
        //}
        stage.addActor(playersTable);
        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        if (lobby.getAdminUsername().equals(currentUsername)) {
            startGameBtn.setVisible(true);
        }
        stage.act(delta);
        stage.draw();
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
        Timer.instance().clear();
        stage.dispose();
    }

    private void refreshPlayerList() {
        try {
            LobbyDto updatedLobby = apiClient.getLobbyByInviteCode(lobby.getId());
            lobby.setPlayerUsernames(updatedLobby.getPlayerUsernames());
            lobby.setAdminUsername(updatedLobby.getAdminUsername());

            playersTable.clearChildren();

            playersTable.add(new Label("Players:", GameAssetManagerClient.getGameAssetManager().getSkin())).left().padBottom(10).colspan(2);
            playersTable.row();

            for (String username : updatedLobby.getPlayerUsernames()) {
                playersTable.add(new Label("- " + username, GameAssetManagerClient.getGameAssetManager().getSkin())).left().colspan(2);
                playersTable.row();
            }
        } catch (Exception e) {
            System.err.println("Error refreshing player list:");
            e.printStackTrace();
        }
    }
}

