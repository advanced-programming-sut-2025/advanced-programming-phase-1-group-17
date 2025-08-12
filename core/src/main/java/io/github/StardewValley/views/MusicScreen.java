package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.dto.MusicDTO;

import java.util.ArrayList;
import java.util.List;

public class MusicScreen implements Screen {

    private final Stage stage;
    private final Skin skin;
    private final GameView gameView;

    // این لیست‌ها حالا وضعیت فعلی UI را نگه می‌دارند
    private final ArrayList<Music> musicList = new ArrayList<>();
    private final ArrayList<String> musicNames = new ArrayList<>();

    private Table musicTable;
    private float musicUpdateTimer = 0f;

    public MusicScreen(GameView gameView, Skin skin) {
        this.stage = new Stage(new ScreenViewport());
        this.skin = skin;
        this.gameView = gameView;

        buildInitialUI();

        // در ابتدای کار، یک بار لیست را از سرور بگیر و UI را بساز
        try {
            updateMusicListFromServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildInitialUI() {
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        TextButton backButton = new TextButton("Back", skin);
        rootTable.add(backButton).top().right().pad(10).row();
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(gameView);
            }
        });

        musicTable = new Table();
        ScrollPane scrollPane = new ScrollPane(musicTable, skin);
        rootTable.add(scrollPane).grow();
    }

    private void updateMusicListFromServer() throws Exception {
        List<MusicDTO> dtoList = GameClient.getGameStateApiClient().getAllMusic();

        // چک کن آیا لیست سرور با لیست فعلی ما فرقی دارد یا نه
        if (dtoList.size() != musicNames.size()) { // یک چک ساده (می‌تواند هوشمندتر باشد)
            rebuildMusicUI(dtoList);
        }
    }

    // این متد مسئول بازسازی کامل UI و آبجکت‌های Music است
    private void rebuildMusicUI(List<MusicDTO> dtoList) {
        // ۱. اول منابع قبلی را آزاد کن تا memory leak رخ ندهد
        for (Music music : musicList) {
            music.dispose();
        }
        musicList.clear();
        musicNames.clear();
        musicTable.clearChildren();

        // ۲. حالا لیست جدید را بساز
        for (MusicDTO dto : dtoList) {
            Music music = Gdx.audio.newMusic(Gdx.files.absolute(dto.getUrl()));
            musicList.add(music);
            musicNames.add(dto.getName());

            TextButton btn = new TextButton(dto.getName(), skin);

            // مهم: از آبجکت music که در همان لحظه ساخته شده استفاده کن
            btn.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    playMusic(music);
                }
            });
            musicTable.add(btn).expandX().fillX().pad(5).row();
        }
    }

    private void playMusic(Music musicToPlay) {
        if (musicToPlay.isPlaying()) {
            musicToPlay.stop();
        } else {
            // تمام آهنگ‌های دیگر را متوقف کن
            for (Music m : musicList) {
                m.stop();
            }
            musicToPlay.play();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        musicUpdateTimer += delta;
        if (musicUpdateTimer > 2f) { // هر ۲ ثانیه چک کن
            musicUpdateTimer = 0;
            try {
                updateMusicListFromServer();
            } catch (Exception e) {
                // e.printStackTrace();
            }
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
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}
