package com.minecraft.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.model.GameEntity;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.model.Item;
import com.minecraft.game.model.Player;
import com.minecraft.game.utils.Constants;

public class OverlayView extends GameEntity {

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

    public OverlayView(float width, float height, Body body, Inventory inventory, Health health) {
        super(width, height, body);
        this.inventory = inventory;
        this.inventorySprite = new Texture(Gdx.files.internal("assets/overlay/inventory.png"));
        this.selectedItem = new Texture(Gdx.files.internal("assets/overlay/selectedItem.png"));
        this.font = new BitmapFont();

        // The amount of pixels to jump to the next item slot
        this.invJump = 40;

        this.healthBarSheet = new Texture(Gdx.files.internal("assets/overlay/healthBar.png"));
        this.splitFrames = TextureRegion.split(healthBarSheet, healthBarSheet.getWidth(),
                healthBarSheet.getHeight() / 5);
        this.health = health;
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

    @Override
    public void update() {
        x = body.getPosition().x * Constants.PPM + 200;
        y = body.getPosition().y * Constants.PPM + 300;
        xItm = x + 5;
        yItm = y + 5;

        xHealth = body.getPosition().x * Constants.PPM - 600;
        yHealth = body.getPosition().y * Constants.PPM + 305;

    }
}
