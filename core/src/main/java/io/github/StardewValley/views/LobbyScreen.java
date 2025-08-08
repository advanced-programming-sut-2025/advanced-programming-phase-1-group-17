package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.StardewValley.GameAssetManagerClient;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.models.LobbyDto;

import java.util.List;

public class LobbyScreen implements Screen {
    private TextButton createLobbyBtn;
    private TextButton joinCodeBtn;
    private TextButton refreshButton;
    private TextField lobbyNameField = new TextField("", GameAssetManagerClient.getGameAssetManager().getSkin());
    private TextField codeField = new TextField("", GameAssetManagerClient.getGameAssetManager().getSkin());
    private CheckBox privateBox = new CheckBox("Private", GameAssetManagerClient.getGameAssetManager().getSkin());
    private CheckBox visibleBox = new CheckBox("Visible", GameAssetManagerClient.getGameAssetManager().getSkin());
    private Skin skin = GameAssetManagerClient.getGameAssetManager().getSkin();
    private Stage stage;
    private final LobbyApiClient apiClient;
    private final Table lobbyTable;
    private final Table ScrollPane2;
    private final TextField passwordField = new TextField("", GameAssetManagerClient.getGameAssetManager().getSkin());

    public LobbyScreen(String jwtToken) {
        this.apiClient = new LobbyApiClient(jwtToken);
        this.ScrollPane2 = new Table(skin);
        this.lobbyTable = new Table(skin);
        setListeners();
    }

    @Override
    public void show() {
        //this.stage = new Stage(new ScreenViewport());
        this.stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        lobbyTable.setFillParent(true);
        ScrollPane2.setFillParent(true);
        ScrollPane2.left();
        Table root = new Table(skin);
        root.setFillParent(true);
        root.center();
        root.add(new Label("Lobby System", skin)).colspan(2).center();
        root.row();
        root.add(refreshButton).colspan(2).center().padBottom(100);
        root.row();
        root.add(new Label("New Lobby", skin)).colspan(2).center();
        root.row();
        lobbyNameField.setMessageText("name");
        root.add(lobbyNameField).width(200).colspan(2).center().padBottom(20);
        root.row();
        passwordField.setMessageText("password");
        passwordField.setVisible(false);
        root.add(passwordField).width(200).colspan(2).center().padBottom(20);
        root.row();
        visibleBox.setChecked(true);
        root.add(privateBox).colspan(2).center();
        root.row();
        root.add(visibleBox).colspan(2).center();
        root.row();
        root.add(createLobbyBtn).colspan(2).center().padBottom(50);
        root.row();
        root.row();
        root.add(new Label("Join by Invite Code", skin)).colspan(2).center();
        root.row();
        codeField.setMessageText("code");
        root.add(codeField).width(200).colspan(2).center();
        root.row();
        root.add(joinCodeBtn).colspan(2).center();
        root.row();
        ScrollPane scrollPane = new ScrollPane(lobbyTable, skin);
        scrollPane.setFadeScrollBars(false);
        lobbyTable.add(new Label("Available Lobbies", skin)).colspan(2);
        ScrollPane2.add(scrollPane);
        stage.addActor(root);
        stage.addActor(ScrollPane2);
        refreshLobbyList(skin);
    }

    private void refreshLobbyList(Skin skin) {
        lobbyTable.clearChildren();
        lobbyTable.add(new Label("Available Lobbies", skin)).colspan(2);
        lobbyTable.row();

        try {
            List<LobbyDto> lobbies = apiClient.listLobbies();
            for (LobbyDto lobby : lobbies) {
                if (!lobby.isVisible()) continue;
                Label nameLabel = new Label("Lobby: " + lobby.getName(), skin);
                Label codeLabel = new Label(lobby.getPlayerUsernames().size() + "player", skin);
                Label playersLabel = new Label("", skin);
                StringBuilder s = new StringBuilder();
                s.append("Players: ");
                for (int i = 0; i < lobby.getPlayerUsernames().size(); i++) {
                    s.append("\"" + lobby.getPlayerUsernames().get(i) + "\"");
                }
                playersLabel.setText(s);
                TextButton joinButton = new TextButton("Join", skin);
                TextField pass = new TextField("", skin);
                pass.setMessageText("pass");
                pass.setWidth(100);

                joinButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        try {
                            if (lobby.isPrivate()) {
                                if (pass.getText().equals(lobby.getPassword())){
                                    LobbyDto joined = apiClient.joinLobbyByCode(lobby.getInviteCode());
                                    System.out.println("Joined lobby: " + joined.getName());
                                    Main.getMain().getScreen().dispose();
                                    Main.getMain().setScreen(new LobbyRoomScreen(joined, apiClient, GameClient.getLoggedInUser().getUsername()));
                                }
                            }
                            else {
                                LobbyDto joined = apiClient.joinLobbyByCode(lobby.getInviteCode());
                                System.out.println("Joined lobby: " + joined.getName());
                                Main.getMain().getScreen().dispose();
                                Main.getMain().setScreen(new LobbyRoomScreen(joined, apiClient, GameClient.getLoggedInUser().getUsername()));
                            }
                        } catch (Exception e) {
                            System.err.println("Error joining lobby:");
                            e.printStackTrace();
                        }
                    }
                });

                lobbyTable.add(nameLabel).left().pad(5);
                lobbyTable.add(codeLabel).left().pad(1);
                lobbyTable.row();
                lobbyTable.add(playersLabel).left().pad(5);
                lobbyTable.row();
                if (lobby.isPrivate()) {
                    lobbyTable.add(pass).left();
                }
                lobbyTable.add(joinButton);
                lobbyTable.row();
            }

        } catch (Exception e) {
            lobbyTable.add(new Label("Failed to load lobbies", skin)).row();
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (privateBox.isChecked()) {
            passwordField.setVisible(true);
        } else {
            passwordField.setVisible(false);
        }
        stage.act(delta);
        stage.draw();
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
        stage.dispose();
    }

    private void setListeners() {
        createLobbyBtn = new TextButton("➕ Create Lobby", GameAssetManagerClient.getGameAssetManager().getSkin());
        createLobbyBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                try {
                    LobbyDto newLobby = apiClient.createLobby(
                        lobbyNameField.getText(),
                        privateBox.isChecked(),
                        visibleBox.isChecked(),
                        passwordField.getText()
                    );
                    System.out.println("Lobby created: " + newLobby.getInviteCode());
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new LobbyRoomScreen(newLobby, apiClient, GameClient.getLoggedInUser().getUsername()));
                    refreshLobbyList(GameAssetManagerClient.getGameAssetManager().getSkin());
                } catch (Exception e) {
                    System.err.println("Error while creating lobby:");
                    e.printStackTrace();
                }
            }
        });
        joinCodeBtn = new TextButton("Join", GameAssetManagerClient.getGameAssetManager().getSkin());
        joinCodeBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (!codeField.getText().isEmpty()) {
                    try {
                        LobbyDto joined = apiClient.joinLobbyByCode(codeField.getText());
                        System.out.println("Joined lobby: " + joined.getName());
                        refreshLobbyList(GameAssetManagerClient.getGameAssetManager().getSkin());
                        Main.getMain().getScreen().dispose();
                        Main.getMain().setScreen(new LobbyRoomScreen(joined, apiClient, GameClient.getLoggedInUser().getUsername()));
                    } catch (Exception e) {
                        System.err.println("Error while joining lobby:");
                        e.printStackTrace();
                    }
                }
            }
        });
        refreshButton = new TextButton("Refresh", skin);
        refreshButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                refreshLobbyList(skin);
            }
        });
    }
}
