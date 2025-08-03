package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class NotificationWindow extends Table {
    public NotificationWindow(String message, Skin skin, Runnable onClose) {
        super(skin);
        this.setFillParent(false);
        this.setBackground("window2");

        Label messageLabel = new Label(message, skin);
        TextButton closeButton = new TextButton("OK", skin);

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                NotificationWindow.this.remove(); // Remove from stage
                if (onClose != null) onClose.run();
            }
        });

        this.pad(20);
        this.defaults().pad(10);
        this.add(messageLabel).row();
        this.add(closeButton).center();

        // Optional: set size manually
        this.pack();
        this.setPosition(
            (Gdx.graphics.getWidth() - this.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - this.getHeight()) / 2f
        );
    }
}

