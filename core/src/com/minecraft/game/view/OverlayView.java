package com.minecraft.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.model.Item;
import com.minecraft.game.model.Player;

public class OverlayView {

    private Texture inventorySprite;
    private Texture selectedItem;
    private float xItm, yItm;
    private float invJump;
    private Inventory inventory;
    private BitmapFont font;

    private Texture healthBarSheet;
    private TextureRegion[][] splitFrames;
    @SuppressWarnings("unused")
    private Health health;
    private float xHealth, yHealth;
    private OrthographicCamera camera;
    private float x, y;

    public OverlayView(Inventory inventory, Health health, OrthographicCamera camera) {
        this.inventory = inventory;
        this.inventorySprite = new Texture(Gdx.files.internal("assets/overlay/inventory.png"));
        this.selectedItem = new Texture(Gdx.files.internal("assets/overlay/selectedItem.png"));
        this.font = new BitmapFont();
        this.camera = camera;

        // The amount of pixels to jump to the next item slot
        this.invJump = 40;

        this.healthBarSheet = new Texture(Gdx.files.internal("assets/overlay/healthBar.png"));
        this.splitFrames = TextureRegion.split(healthBarSheet, healthBarSheet.getWidth(),
                healthBarSheet.getHeight() / 5);
        this.health = health;
    }

    private Vector2 getMiddleOfScreen() {
        float cameraX = camera.position.x - camera.viewportWidth / 2;
        float cameraY = camera.position.y - camera.viewportHeight / 2;
        return new Vector2(cameraX, cameraY);
    }

    private void renderItems(SpriteBatch batch) {
        int iteration = 0;
        for (Item item : inventory.getItems().keySet()) {
            Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
            batch.draw(itemTexture, xItm + (iteration * invJump), yItm, 23, 23);
            font.draw(batch, Integer.toString(inventory.getAmount(item)), xItm + (iteration * invJump), y + 35);

            iteration++;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(inventorySprite, x, y);
        renderCurrentSlot(batch);
        renderItems(batch);

        int playerHealth = Player.getHealth().getHealth();
        if (playerHealth > 0) {
            batch.draw(splitFrames[playerHealth - 1][0], xHealth, yHealth, 200, 40);
        }
    }

    private void renderCurrentSlot(SpriteBatch batch) {
        batch.draw(selectedItem, x + (inventory.getCurrentSlot() * invJump), y);
    }

    public void update() {
        x = getMiddleOfScreen().x + 200 + 640;
        y = getMiddleOfScreen().y + 300 + 360;

        xItm = x + 5;
        yItm = y + 5;

        xHealth = getMiddleOfScreen().x - 600 + 640 ;
        yHealth = getMiddleOfScreen().y + 305 + 360;
    }
}
