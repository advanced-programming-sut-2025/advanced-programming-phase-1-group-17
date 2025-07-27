package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.LobbyDto;

import java.util.List;

public class LobbyScreen implements Screen {

    private final Stage stage;
    private final LobbyApiClient apiClient;
    private final Table lobbyTable;

    public LobbyScreen(String jwtToken) {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.apiClient = new LobbyApiClient(jwtToken);
        this.lobbyTable = new Table();
        this.lobbyTable.top();
        lobbyTable.setFillParent(true);
    }

    @Override
    public void show() {
        Skin skin =  GameAssetManager.getGameAssetManager().getSkin();
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        // عنوان و دکمه رفرش
        Label title = new Label("Lobby System", skin);
        TextButton refreshButton = new TextButton("Refresh", skin);
        refreshButton.addListener(event -> {
            refreshLobbyList(skin);
            return true;
        });

        // 🟦 فرم ساخت لابی جدید
        TextField lobbyNameField = new TextField("", skin);
        CheckBox privateBox = new CheckBox("Private", skin);
        CheckBox visibleBox = new CheckBox("Visible", skin);
        visibleBox.setChecked(true);
        TextButton createLobbyBtn = new TextButton("Create Lobby", skin);

        createLobbyBtn.addListener(event -> {
            try {
                LobbyDto newLobby = apiClient.createLobby(
                    lobbyNameField.getText(),
                    privateBox.isChecked(),
                    visibleBox.isChecked()
                );
                System.out.println("Lobby created: " + newLobby.getInviteCode());
                refreshLobbyList(skin);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });

        // 🟩 فرم پیوستن به لابی با کد
        TextField codeField = new TextField("", skin);
        TextButton joinCodeBtn = new TextButton("Join by Code", skin);
        joinCodeBtn.addListener(event -> {
            try {
                LobbyDto joined = apiClient.joinLobbyByCode(codeField.getText());
                System.out.println("Joined lobby: " + joined.getName());
                refreshLobbyList(skin);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });

        // چیدمان UI
        root.add(title).colspan(2).pad(10);
        root.row();

        root.add(refreshButton).colspan(2).pad(10);
        root.row();

        root.add(new Label("New Lobby:", skin)).left();
        root.row();
        root.add(lobbyNameField).width(200).colspan(2);
        root.row();
        root.add(privateBox).left();
        root.add(visibleBox).left();
        root.row();
        root.add(createLobbyBtn).colspan(2).padBottom(20);
        root.row();

        root.add(new Label("Join by Invite Code:", skin)).colspan(2).left();
        root.row();
        root.add(codeField).width(200).colspan(2);
        root.row();
        root.add(joinCodeBtn).colspan(2);
        root.row();

        ScrollPane scrollPane = new ScrollPane(lobbyTable, skin);
        root.add(scrollPane).colspan(2).expand().fill().padTop(20);

        refreshLobbyList(skin);
    }


    private void refreshLobbyList(Skin skin) {
        lobbyTable.clearChildren();

        try {
            List<LobbyDto> lobbies = apiClient.listLobbies();
            for (LobbyDto lobby : lobbies) {
                Label nameLabel = new Label("Lobby: " + lobby.getName(), skin);
                Label codeLabel = new Label("Code: " + lobby.getInviteCode(), skin);
                TextButton joinButton = new TextButton("Join", skin);

                joinButton.addListener(event -> {
                    // TODO: ارسال درخواست به API /join
                    System.out.println("Joining lobby: " + lobby.getInviteCode());
                    return true;
                });

                lobbyTable.add(nameLabel).pad(10);
                lobbyTable.add(codeLabel).pad(10);
                lobbyTable.add(joinButton).pad(10);
                lobbyTable.row();
            }

        } catch (Exception e) {
            e.printStackTrace();
            lobbyTable.add(new Label("Failed to load lobbies", skin)).row();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        stage.dispose();
    }
}
