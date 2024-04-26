package com.minecraft.game.view.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.crafting.Inventory;
import com.minecraft.game.model.crafting.Item;

public class InventoryView {
    private final Inventory inventory;
    final Texture inventorySprite;
    final Texture selectedItem;
    final SpriteBatch batch;
    final BitmapFont font;

    private float xInventory;
    private float yInventory;
    private float xItem;
    private float yItem;
    private float xDescription;
    private float yDescription;

    private final float invJump;
    
    public InventoryView(Inventory inventory, SpriteBatch batch, BitmapFont font){
        this.inventory = inventory;
        this.inventorySprite = new Texture(Gdx.files.internal("assets/overlay/inventory.png"));
        this.selectedItem = new Texture(Gdx.files.internal("assets/overlay/selectedItem.png"));
        this.font = font;
        this.batch = batch;
        this.invJump = 40;
    }

    public void render() {
        batch.draw(inventorySprite, xInventory, yInventory);
        renderCurrentInventorySlot();
        renderItems();
        renderSelectedItemText();
    }

    private void renderItems() {
        int iteration = 0;
        for (Item item : inventory.getInventory().keySet()) {
            Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
            batch.draw(itemTexture, xItem + (iteration * invJump), yItem, 23, 23);
            font.draw(batch, Integer.toString(inventory.getAmount(item)), xItem + (iteration * invJump), yInventory + 35);
            iteration++;
        }
    }

    private void renderCurrentInventorySlot() {
        batch.draw(selectedItem, xInventory + (inventory.getCurrentSlot() * invJump), yInventory);
    }

    private void renderSelectedItemText() {
        Item item = inventory.getSelectedItem();
        if (item != null) {
        font.draw(batch, item.getName() + ": " + item.getDescription(), xDescription, yDescription); }
    }

    public void update(Vector2 lowerLeftCorner) {        
        xInventory = lowerLeftCorner.x + 840;
        yInventory = lowerLeftCorner.y + 660;

        xItem = xInventory + 7;
        yItem = yInventory + 7;
        
        xDescription = xInventory;
        yDescription = yInventory - 10;
    }

    public void dispose() {
        inventorySprite.dispose();
        selectedItem.dispose();
    }
}