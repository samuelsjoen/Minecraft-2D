package com.minecraft.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.Crafting;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.model.Item;
import com.minecraft.game.model.Player;

public class OverlayView {

    private float x, y;
    private float xItm, yItm;
    private float xHealth, yHealth;
    private float craftingX, craftingY;

    private Inventory inventory;
    private Texture inventorySprite;
    private Texture selectedItem;
    private float invJump;
    @SuppressWarnings("unused")
    private Health health;
    private Texture healthBarSheet;
    private TextureRegion[][] splitFrames;
    private BitmapFont font;
    private OrthographicCamera camera;

    Crafting crafting;
    boolean open;
    Texture craftingSprite;
    int jump;

    public OverlayView(Inventory inventory, Health health, Crafting crafting, OrthographicCamera camera) {
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

        this.crafting = crafting;
        this.craftingSprite = new Texture(Gdx.files.internal("assets/overlay/crafting.png"));
        this.jump = 50;

    }

    // TODO: should use the same identical method from gamescreen instead
    private Vector2 getLowerLeftCorner() {
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
        if (crafting.isOpen() == true) {
            renderTable(batch);
            renderTableItems(batch);
        }

        batch.draw(inventorySprite, x, y);
        renderCurrentSlot(batch);
        renderItems(batch);

        int playerHealth = Player.getHealth().getHealth();
        if (playerHealth > 0) {
            batch.draw(splitFrames[playerHealth - 1][0], xHealth, yHealth, 200, 40);
        }
    }

    public void renderTable(SpriteBatch batch) {
        batch.draw(craftingSprite, craftingX, craftingY);
    }

    public void renderTableItems(SpriteBatch batch) {
        Item[][] table = crafting.getTable();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Item item = table[row][col];
                if (item != null) {
                    Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
                    batch.draw(itemTexture, x + (row * jump), (col * jump), 50, 50);
                }
            }
        }
    }

    private void renderCurrentSlot(SpriteBatch batch) {
        batch.draw(selectedItem, x + (inventory.getCurrentSlot() * invJump), y);
    }

    public void update() {
        x = getLowerLeftCorner().x + 200 + 640;
        y = getLowerLeftCorner().y + 300 + 360;

        xItm = x + 7;
        yItm = y + 7;

        xHealth = getLowerLeftCorner().x - 600 + 640 ;
        yHealth = getLowerLeftCorner().y + 305 + 360;

        craftingX = getLowerLeftCorner().x + 185 + 640;
        craftingY = getLowerLeftCorner().y + 120 + 360;
    }
}
