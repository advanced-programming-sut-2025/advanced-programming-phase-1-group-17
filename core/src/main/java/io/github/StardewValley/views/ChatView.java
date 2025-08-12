package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.helperControllers.GameStateApiClient;
import io.github.StardewValley.shared.dto.ChatMessageDTO;

import java.util.ArrayList;
import java.util.List;

public class ChatView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final GameStateApiClient apiClient;
    private TextButton backButton ;

    // عناصر UI
    private final Table messageTable;
    private final ScrollPane scrollPane;
    private final TextField messageField;
    private final TextButton sendButton;

    // کلاینت لیست پیام‌های نمایش داده شده را در خود نگه می‌دارد
    private List<ChatMessageDTO> displayedMessages;

    // تایمر برای درخواست دوره‌ای پیام‌های جدید
    private float pollTimer = 0f;
    private static final float POLL_INTERVAL = 2.0f; // هر ۲ ثانیه یکبار

    public ChatView(Skin skin,GameView gameView) {
        this.backButton = new TextButton("back",skin);
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.apiClient = GameClient.getGameStateApiClient();
        this.displayedMessages = new ArrayList<>();

        // ۱. ساختن عناصر UI
        messageTable = new Table();
        scrollPane = new ScrollPane(messageTable, skin);
        scrollPane.setFadeScrollBars(false);
        messageField = new TextField("", skin);
        sendButton = new TextButton("Send", skin);
        Table backTable = new Table();
        backTable.setFillParent(true);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(gameView);
            }
        });
        backTable.add(backButton);
        backTable.right().top();
        stage.addActor(backTable);

        // ۲. چیدمان UI
        layoutUI();

        // ۳. اضافه کردن Listener به دکمه
        addListeners();
    }

    private void layoutUI() {
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        rootTable.pad(10);

        rootTable.add(scrollPane).grow().row();

        Table inputTable = new Table();
        inputTable.add(messageField).growX();
        inputTable.add(sendButton).padLeft(5);
        rootTable.add(inputTable).growX().padTop(10);
    }

    private void addListeners() {
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String messageText = messageField.getText();
        if (messageText == null || messageText.trim().isEmpty()) {
            return;
        }

        String username = GameClient.getPlayer().getUser().getUsername();
        ChatMessageDTO message = new ChatMessageDTO();
        message.setSenderUsername(username);
        message.setContent(messageText);

        try {
            apiClient.sendChatMessage(message);
            messageField.setText("");
            // پس از ارسال پیام، بلافاصله لیست را آپدیت کن تا پیام خودمان را ببینیم
            updateChatLog(true);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: یک پیام خطا در UI نمایش بده
        }
    }

    /**
     * پیام‌ها را از سرور می‌گیرد و در صورت وجود تغییر، UI را آپدیت می‌کند.
     * @param forceUpdate اگر true باشد، بدون در نظر گرفتن تعداد پیام‌ها، UI را بازسازی می‌کند.
     */
    // این متغیر را به عنوان فیلد در کلاس ChatView نگه دارید
    private int lastMessageIndex = 0;

    private void updateChatLog(boolean forceUpdate) {
        try {
            List<ChatMessageDTO> newMessages = apiClient.getChatMessages();
            if (newMessages == null) return;

            // فقط اگر پیام جدیدی وجود دارد یا مجبور به آپدیت هستیم، کار کن
            if (forceUpdate || newMessages.size() > lastMessageIndex) {

                // بهینه سازی: فقط پیام‌های جدید را اضافه کن
                for (int i = lastMessageIndex; i < newMessages.size(); i++) {
                    ChatMessageDTO message = newMessages.get(i);

                    String formattedMessage;
                    Color messageColor = Color.WHITE; // رنگ پیش‌فرض

                    // ۱. باگ متن اصلاح شد: اول متن و رنگ را مشخص کن
                    if (message.isPrivate()) {
                        formattedMessage = "[Private] " + message.getSenderUsername() + ": " + message.getContent();
                        messageColor = Color.MAGENTA;
                    } else {
                        formattedMessage = message.getSenderUsername() + ": " + message.getContent();
                    }

                    // ۲. سپس لیبل را با اطلاعات صحیح بساز
                    Label messageLabel = new Label(formattedMessage, skin);
                    messageLabel.setWrap(true);
                    messageLabel.setColor(messageColor);

                    messageTable.add(messageLabel).growX().left().pad(5).row();
                }

                lastMessageIndex = newMessages.size();

                // ۳. باگ اسکرول اصلاح شد: دستور اسکرول را به فریم بعدی موکول کن
                Gdx.app.postRunnable(() -> {
                    scrollPane.layout();
                    scrollPane.setScrollPercentY(1.0f);
                });
            }
        } catch (Exception e) {
            // System.err.println("Could not fetch chat messages.");
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.2f, 1);

        // تایمر برای درخواست دوره‌ای پیام‌ها
        pollTimer += delta;
        if (pollTimer >= POLL_INTERVAL) {
            pollTimer = 0f;
            updateChatLog(false); // آپدیت عادی و دوره‌ای
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // به محض باز شدن صفحه، یک بار پیام‌ها را آپدیت کن
        updateChatLog(true);
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

    }

}
