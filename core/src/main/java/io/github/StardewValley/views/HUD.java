package io.github.StardewValley.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import io.github.StardewValley.GameClient;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.GameController;
import io.github.StardewValley.controllers.helperControllers.GameStateApiClient;
import io.github.StardewValley.controllers.PlayerClient;
import io.github.StardewValley.shared.models.HudDataDTO;

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
    private HudDataDTO hudData;

    private Rectangle energyBarBounds;
    private ShapeRenderer shapeRenderer;
    private BitmapFont tooltipFont;

    private int inventoryStartIndex = 0;
    private int chosenItemIndex = 0;
    private int inventoryVisibleSlots = 10;
    private int selectedItemIndex = 0;


    public HUD() {
        hudData = new HudDataDTO("9:00 am", "Mon. 1", "SPRING", "SUNNY", 0, 200, 200, false, 0);
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
    public void updateData(HudDataDTO newData) {
        this.hudData = newData;
    }
    private float timesinceLastUpdate = 0;

    public void render(SpriteBatch batch, float v) throws Exception {
        // تنظیم دوربین HUD
        Main.getBatch().setProjectionMatrix(hudCamera.combined);

        renderEnergyBar(batch);
        renderInventoryBar(batch);
        Main.getBatch().begin();

        int hudWidth = clock.getWidth();
        int hudHeight = clock.getHeight();
        int money = hudData.getMoney();
        int i = 0;
        int x = Gdx.graphics.getWidth() - hudWidth - 80; // فاصله از لبه راست
        int y = Gdx.graphics.getHeight() - hudHeight - 125; // فاصله از بالا
        float arrowSize = 3f;
        int arrowX = 1765;
        int arrowY = 980;

        Main.getBatch().draw(clock, x, y, clock.getWidth() * 3, clock.getHeight() * 3);
        Main.getBatch().draw(arrow
            , arrowX, arrowY
            , 10, 10
            , arrow.getRegionWidth() * arrowSize, arrow.getRegionHeight() * arrowSize
            , 1f, 1f
            ,hudData.getTimeAngle()
        );
        float otherSize = 3.2f;
        Main.getBatch().draw(sunny, 1794, 973, spring.getRegionWidth() * otherSize, spring.getRegionHeight() * otherSize);

        Main.getBatch().draw(spring, 1867, 973, spring.getRegionWidth() * otherSize, spring.getRegionHeight() * otherSize);


        // کشیدن مقدار پول

        while (money > 0) {
            font.draw(Main.getBatch(), String.valueOf(money % 10), 1885 - 18 * i, Gdx.graphics.getHeight() - 150);
            money /= 10;
            i++;
        }
        dateFont.draw(Main.getBatch(), hudData.getDateString(), Gdx.graphics.getWidth() - 110, Gdx.graphics.getHeight() - 25);
        timeFont.draw(Main.getBatch(), hudData.getTimeString(), Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - 92);

        Main.getBatch().end();
        timesinceLastUpdate +=v;
        if(timesinceLastUpdate>=1){
            timesinceLastUpdate = 0;
            updateData(GameClient.getGameStateApiClient().getHudData());
        }

    }

    private void renderEnergyBar(SpriteBatch batch) {
        PlayerClient player = GameClient.getPlayer();
        // Set up shape renderer
        shapeRenderer.setProjectionMatrix(hudCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Background bar (gray)
        shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1); // Dark gray
        shapeRenderer.rect(energyBarBounds.x, energyBarBounds.y, energyBarBounds.width, energyBarBounds.height);

        // Filled part (green or red based on energy level)
        double maxEnergy = player.getMaxEnergy();
        float energyPercent = (float) (player.getEnergy() / maxEnergy);
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
            double energy = player.getEnergy();
            float x = energyBarBounds.x - 30;
            float y = energyBarBounds.y + energyBarBounds.height + 20;
            if (player.isEnergyUnlimited())
                tooltipFont.draw(batch, "Energy: " + "INFINITY", x, y);
            else
                tooltipFont.draw(batch, "Energy: " + energy, x, y);
        }
        batch.end();
    }

    public void renderInventoryBar(SpriteBatch batch) {
        //TODO
//        Player player = App.getCurrentGame().getCurrentPlayingPlayer();
//        Map<BackPackableType, ArrayList<BackPackable>> itemTypeCounts = player.getBackPack().getBackPackItems();
//        ArrayList<BackPackableType> itemTypes = new ArrayList<>(itemTypeCounts.keySet());
//
//        int slotSize = 64;
//        int spacing = 10;
//        int totalWidth = inventoryVisibleSlots * slotSize + (inventoryVisibleSlots - 1) * spacing;
//        int startX = (Gdx.graphics.getWidth() - totalWidth) / 2;
//        int y = 10;
//
//        shapeRenderer.setProjectionMatrix(hudCamera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//
//        for (int i = 0; i < inventoryVisibleSlots; i++) {
//            int x = startX + i * (slotSize + spacing);
//
//            // Background slot
//            shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.8f);
//            shapeRenderer.rect(x, y, slotSize, slotSize);
//
//            // Selected highlight
//            if (i == selectedItemIndex) {
//                shapeRenderer.setColor(0.2f, 0.8f, 0.2f, 1);
//                shapeRenderer.rect(x - 2, y - 2, slotSize + 4, slotSize + 4);
//            }
//
//            // Item present highlight
//            if (i < itemTypes.size()) {
//                shapeRenderer.setColor(0.8f, 0.8f, 0.2f, 1f);
//                shapeRenderer.rect(x + 4, y + 4, slotSize - 8, slotSize - 8);
//            }
//        }
//
//        shapeRenderer.end();
//
//        // Draw item type name and count
//        batch.begin();
//        BitmapFont itemFont = new BitmapFont();
//        itemFont.getData().setScale(1.2f);
//        itemFont.setColor(1, 1, 1, 1);
//
//        for (int i = 0; i < inventoryVisibleSlots; i++) {
//            if (i < itemTypes.size()) {
//                int x = startX + i * (slotSize + spacing);
//                BackPackableType type = itemTypes.get(i);
//                int count = itemTypeCounts.get(type).size();
//
//                String label = type.toString(); // or type.name() or type.getDisplayName()
//                itemFont.draw(batch, label, x + 5, y + slotSize - 8);
//                itemFont.draw(batch, "x" + count, x + 5, y + 20);
//            }
//        }
//
//        batch.end();
    }


    public void handleInventoryInput() {
        // Scroll
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.UP)) {
            selectedItemIndex = (selectedItemIndex + 1) % inventoryVisibleSlots;
        }
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            selectedItemIndex = (selectedItemIndex - 1 + inventoryVisibleSlots) % inventoryVisibleSlots;
        }

        // Number keys (0–9)
        for (int i = 0; i < inventoryVisibleSlots; i++) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.NUM_0 + i)) {
                selectedItemIndex = i;
            }
        }

        // Mouse click
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // flip Y

            int slotSize = 64;
            int spacing = 10;
            int totalWidth = inventoryVisibleSlots * slotSize + (inventoryVisibleSlots - 1) * spacing;
            int startX = (Gdx.graphics.getWidth() - totalWidth) / 2;
            int y = 10;

            for (int i = 0; i < inventoryVisibleSlots; i++) {
                int x = startX + i * (slotSize + spacing);
                Rectangle rect = new Rectangle(x, y, slotSize, slotSize);
                if (rect.contains(mouseX, mouseY)) {
                    selectedItemIndex = i;
                    break;
                }
            }
        }
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
