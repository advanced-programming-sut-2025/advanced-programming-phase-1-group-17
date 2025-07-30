package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Player;

public class HUD {
    private Texture clock;
    private BitmapFont font;
    private BitmapFont dateFont;
    private BitmapFont timeFont;
    private OrthographicCamera hudCamera;
    private TextureRegion arrow;
    private TextureRegion spring;
    private TextureRegion fall;
    private TextureRegion summer;
    private TextureRegion winter;
    private TextureRegion sunny;
    private TextureRegion rainy;
    private TextureRegion storm;
    private TextureRegion snow;
    private float angle = 0;

    private Rectangle energyBarBounds;
    private ShapeRenderer shapeRenderer;
    private BitmapFont tooltipFont;

    public HUD() {
        clock = new Texture("Clock.png");
        arrow = new TextureRegion(clock, 72, 0, 8, 21);
        spring = new TextureRegion(clock, 80, 9, 13, 9);
        summer = new TextureRegion(clock, 93, 9, 13, 9);
        fall = new TextureRegion(clock, 106, 9, 13, 9);
        winter = new TextureRegion(clock, 119, 9, 13, 9);
        sunny = new TextureRegion(clock, 119, 18, 13, 9);

        font = new BitmapFont();
        dateFont = new BitmapFont();
        timeFont = new BitmapFont();
        timeFont.getData().setScale(2f, 1.6f);
        timeFont.setColor(0, 0, 0, 1);
        dateFont.getData().setScale(2.2f, 1.6f);
        dateFont.setColor(0, 0, 0, 1);
        font.getData().setScale(2f);
        font.setColor(1, 0, 0, 1);

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.update();

        shapeRenderer = new ShapeRenderer();
        energyBarBounds = new Rectangle(Gdx.graphics.getWidth() - 60, 40, 20, 120);
        tooltipFont = new BitmapFont();
        tooltipFont.getData().setScale(1f);
        tooltipFont.setColor(1, 1, 1, 1);
    }

    public void render(SpriteBatch batch, float v) {
        // تنظیم دوربین HUD
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        int hudWidth = clock.getWidth();
        int hudHeight = clock.getHeight();

        int x = Gdx.graphics.getWidth() - hudWidth - 80; // فاصله از لبه راست
        int y = Gdx.graphics.getHeight() - hudHeight - 125; // فاصله از بالا
        StringBuilder date = new StringBuilder();
        date.append(App.getCurrentGame().getDate().getDayOfTheWeek().toString().substring(0, 3)).append(". ")
            .append(App.getCurrentGame().getDate().getDay()).append(" ");
        StringBuilder time = new StringBuilder();
        int hour = App.getCurrentGame().getDate().getHour();
        time.append(hour % 12).append(":");
        if (App.getCurrentGame().getDate().getMinute() == 0) {
            time.append("00");
        } else {
            time.append(App.getCurrentGame().getDate().getMinute());
        }
        if (hour < 12) {
            time.append(" am");
        } else {
            time.append(" pm");
        }


        batch.draw(clock, x, y, clock.getWidth() * 3, clock.getHeight() * 3);
        float arrowSize = 3f;
        int arrowX = 1765;
        int arrowY = 980;

        angle += v * 50;
        batch.draw(arrow
            , arrowX, arrowY
            , 10, 10
            , arrow.getRegionWidth() * arrowSize, arrow.getRegionHeight() * arrowSize
            , 1f, 1f,
            (float) ((App.getCurrentGame().getDate().getHour() - 9) * 180) / 13
        );
        float otherSize = 3.2f;
        batch.draw(sunny, 1794, 973, spring.getRegionWidth() * otherSize, spring.getRegionHeight() * otherSize);

        batch.draw(spring, 1867, 973, spring.getRegionWidth() * otherSize, spring.getRegionHeight() * otherSize);


        // کشیدن مقدار پول
        int money = (int) App.getCurrentGame().getCurrentPlayingPlayer().getBackPack().getCoin();
        int i = 0;
        while (money > 0) {
            font.draw(batch, String.valueOf(money % 10), 1885 - 18 * i, Gdx.graphics.getHeight() - 150);
            money /= 10;
            i++;
        }
        dateFont.draw(batch, date.toString(), Gdx.graphics.getWidth() - 110, Gdx.graphics.getHeight() - 25);
        timeFont.draw(batch, time.toString(), Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - 92);
        batch.end();

        renderEnergyBar(batch);
    }

    private void renderEnergyBar(SpriteBatch batch) {
        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
        // Set up shape renderer
        shapeRenderer.setProjectionMatrix(hudCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Background bar (gray)
        shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1); // Dark gray
        shapeRenderer.rect(energyBarBounds.x, energyBarBounds.y, energyBarBounds.width, energyBarBounds.height);

        // Filled part (green or red based on energy level)
        double maxEnergy = App.getCurrentGame().getCurrentPlayingPlayer().getMaxEnergy();
        float energyPercent = (float) (App.getCurrentGame().getCurrentPlayingPlayer().getEnergy() / maxEnergy);
        if (player.isEnergyUnlimited())
            energyPercent = 1;

        if (energyPercent > 0.5f) {
            shapeRenderer.setColor(0.0f, 0.8f, 0.0f, 1); // Green
        } else {
            shapeRenderer.setColor(0.8f, 0.2f, 0.2f, 1); // Red
        }
        shapeRenderer.rect(
            energyBarBounds.x,
            energyBarBounds.y,
            energyBarBounds.width,
            energyBarBounds.height * energyPercent
        );

        shapeRenderer.end();

        // Tooltip still uses batch
        batch.begin();
        if (energyBarBounds.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
            double energy = App.getCurrentGame().getCurrentPlayingPlayer().getEnergy();
            float x = energyBarBounds.x - 30;
            float y = energyBarBounds.y + energyBarBounds.height + 20;
            if (player.isEnergyUnlimited())
                tooltipFont.draw(batch, "Energy: " + "INFINITY", x, y);
            else
                tooltipFont.draw(batch, "Energy: " + energy, x, y);
        }
        batch.end();
    }

    public void resize(int width, int height) {
        hudCamera.setToOrtho(false, width, height);
        hudCamera.update();
    }

    public void dispose() {
        clock.dispose();
        font.dispose();
        tooltipFont.dispose();
        shapeRenderer.dispose();
    }
}
