package com.minecraft.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.model.GameEntity;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.model.Item;
import com.minecraft.game.utils.Constants;

public class InventoryView extends GameEntity {

    private Texture inventorySprite;
    private Texture selectedItem;
    private float xItm, yItm;
    private float invJump;
    private Inventory inventory;

    public InventoryView(float width, float height, Body body, Inventory inventory) {
        super(width, height, body);
        this.inventory = inventory;
        this.inventorySprite = new Texture(Gdx.files.internal("assets/overlay/inventory.png"));
        this.selectedItem = new Texture(Gdx.files.internal("assets/overlay/selectedItem.png"));
        
        // The amount of pixels to jump to the next item slot
        this.invJump = 40;
    }

    private void renderItems(SpriteBatch batch) {
        int iteration = 0;
        for (Item item : inventory.getItems().keySet()) {
            Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
            batch.draw(itemTexture, xItm + (iteration * invJump), yItm + (iteration * invJump), 23, 23);
            iteration++;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(inventorySprite, x, y);
        renderCurrentSlot(batch);
        renderItems(batch);
    }

    private void renderCurrentSlot(SpriteBatch batch) {
        batch.draw(selectedItem, x + (inventory.getCurrentSlot() * invJump), y);
    }

    @Override
    public void update() {
        x = body.getPosition().x * Constants.PPM+200;
        y = body.getPosition().y * Constants.PPM+300;
        xItm = x + 5;
        yItm = y + 5;
        checkUserInput();
    }

    private void checkUserInput() {
        if (Gdx.input.isKeyJustPressed(Constants.INVENTORY_LEFT)) {
            inventory.changeSlot(-1);
        }
        if (Gdx.input.isKeyJustPressed(Constants.INVENTORY_RIGHT)) {
            inventory.changeSlot(+1);
        }
        if (Gdx.input.isKeyJustPressed(Constants.INVENTORY_DROP)) {
            inventory.dropItem();
        }
    }
}
