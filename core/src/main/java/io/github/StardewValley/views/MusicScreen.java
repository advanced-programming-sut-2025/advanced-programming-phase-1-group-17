package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.shared.dto.MusicDTO;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicScreen implements Screen {

    private ArrayList<Music> musicList;
    private ArrayList<String> musicNames;
    private GameView gameView;
    private Stage stage;
    private Skin skin;
    private Table musicTable; // جدول لیست آهنگ‌ها

    public MusicScreen(GameView gameView, Skin skin) throws Exception {
        this.stage = new Stage(new ScreenViewport());
        this.skin = skin;
        this.gameView = gameView;

        musicList = new ArrayList<>();
        musicNames = new ArrayList<>();

        // جدول اصلی کل صفحه
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // جدول بالا برای دکمه‌ها
        Table topTable = new Table();
        rootTable.add(topTable).top().expandX().fillX().row();

        // دکمه Back
        TextButton backButton = new TextButton("Back", skin);
        topTable.add(backButton).right().pad(5);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                Main.getMain().setScreen(gameView);
            }
        });

        // دکمه انتخاب موسیقی
        TextButton selectMusicButton = new TextButton("Select Music File", skin);
        topTable.add(selectMusicButton).left().pad(5);
        selectMusicButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                openFileChooser();
            }
        });

        // جدول لیست موسیقی‌ها
        musicTable = new Table();
        rootTable.add(musicTable).expand().fill();
        addMusic("C:\\Users\\omidr\\Desktop\\sound effects\\Wind.mp3");
    }
    private void addMusic(String path) throws Exception {
        updateMusic();
        MusicDTO musicDTO = new MusicDTO(path,path);
        GameClient.gameStateApiClient.addMusic(musicDTO);
        updateMusic();


        // ساخت دکمه برای این موسیقی
        TextButton musicButton = new TextButton(path, skin);
        musicButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                playMusic(musicList.getLast());
            }
        });

        musicTable.row();
        musicTable.add(musicButton).expandX().fillX().pad(5);
    }

    private void playMusic(Music music) {
        if (music.isPlaying()) {
            music.stop();
        } else {
            for (Music m : musicList) {
                m.stop();
            }
            music.play();
        }
    }


    private void openFileChooser() {
        SwingUtilities.invokeLater(() -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select a Music File");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    addMusic(file.getAbsolutePath());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
    public void updateMusic() throws Exception {
        // گرفتن لیست DTO از سرور
        ArrayList<MusicDTO> dtoList = GameClient.getGameStateApiClient().getAllMusic();

        // پاک کردن لیست فعلی
        musicList.clear();
        musicNames.clear();
        musicTable.clearChildren();

        // تبدیل هر DTO به Music و ساخت دکمه
        for (MusicDTO dto : dtoList) {
            Music music = Gdx.audio.newMusic(Gdx.files.absolute(dto.getUrl()));
            musicList.add(music);
            musicNames.add(dto.getName());

            TextButton btn = new TextButton(dto.getName(), skin);
            int index = musicList.size() - 1; // index فعلی
            btn.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    playMusic(music);
                }
            });
            musicTable.row();
            musicTable.add(btn).expandX().fillX().pad(5);
        }
    }
    private float musicUpdateTimer=0;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // رنگ بک‌گراند (اینجا مشکی)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        musicUpdateTimer+=delta;
        if(musicUpdateTimer>2){
            musicUpdateTimer=0;
            try {
                updateMusic();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
