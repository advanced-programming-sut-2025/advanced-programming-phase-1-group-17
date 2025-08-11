package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.controllers.ChatService; // سرویس چت که ساختیم
import io.github.StardewValley.shared.dto.ChatMessageDTO;

public class ChatView implements Screen {
    private Stage stage;
    private Skin skin; // پوسته برای ظاهر UI
    private ChatService chatService;

    // عناصر UI
    private Table messageTable; // جدولی برای نگهداری لیبل پیام‌ها
    private ScrollPane scrollPane; // برای اسکرول کردن پیام‌ها
    private TextField messageField; // فیلد برای نوشتن پیام
    private TextButton sendButton; // دکمه ارسال

    private int lastMessageIndex = 0; // برای ردیابی آخرین پیام نمایش داده شده

    public ChatView(Skin skin, ChatService chatService) {
        this.skin = skin;
        this.chatService = chatService;
        this.stage = new Stage(new ScreenViewport());

        buildUI();
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
    private void buildUI() {
        // جدول اصلی که کل صفحه را می‌گیرد
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // ۱. ساخت بخش نمایش پیام‌ها
        messageTable = new Table();
        scrollPane = new ScrollPane(messageTable, skin);
        scrollPane.setFadeScrollBars(false); // برای اینکه نوار اسکرول همیشه دیده شود

        // ۲. ساخت بخش ورودی پیام
        messageField = new TextField("", skin);
        sendButton = new TextButton("Send", skin);

        // ۳. چیدمان عناصر در جدول اصلی
        rootTable.pad(10);
        // بخش پیام‌ها ۹۰٪ ارتفاع را می‌گیرد
        rootTable.add(scrollPane).grow().row();
        // بخش ورودی ۱۰٪ ارتفاع را می‌گیرد
        Table inputTable = new Table();
        inputTable.add(messageField).growX();
        inputTable.add(sendButton).padLeft(5);
        rootTable.add(inputTable).growX().padTop(10);

        // اضافه کردن منطق به دکمه ارسال
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String messageText = messageField.getText();
        if (!messageText.isEmpty()) {
            String username = GameClient.getLoggedInUser().getUsername();
            chatService.sendMessage(username, messageText);
            messageField.setText(""); // خالی کردن فیلد پس از ارسال
        }
    }
    // این متد را در حلقه render صدا می‌زنیم
    private void updateChatLog() {
        // اگر تعداد پیام‌های دریافتی بیشتر از پیام‌های نمایش داده شده است
        if (chatService.messages.size() > lastMessageIndex) {
            // به ازای هر پیام جدید
            for (int i = lastMessageIndex; i < chatService.messages.size(); i++) {
                ChatMessageDTO message = chatService.messages.get(i);
                String formattedMessage = message.getSenderUsername() + ": " + message.getContent();

                Label messageLabel = new Label(formattedMessage, skin);
                messageLabel.setWrap(true); // برای شکستن خطوط طولانی

                messageTable.add(messageLabel).growX().pad(5).row();
            }
            lastMessageIndex = chatService.messages.size(); // به‌روزرسانی اندیس

            // اسکرول را به پایین‌ترین نقطه ببر
            scrollPane.layout();
            scrollPane.setScrollPercentY(1);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.2f, 1); // یک پس‌زمینه تیره

        // چک کن آیا پیام جدیدی برای نمایش وجود دارد
        updateChatLog();

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

    }

}
