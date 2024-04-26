package com.minecraft.game.view.overlay;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.crafting.Crafting;
import com.minecraft.game.model.crafting.Inventory;
import com.minecraft.game.model.Health;

public class OverlayView {

    private final InventoryView inventoryView;
    private final HealthView healthView;
    private final CraftingView craftingView;

    public OverlayView(Inventory inventory, Health health, Crafting crafting, SpriteBatch batch, BitmapFont font) {
        this.inventoryView = new InventoryView(inventory, batch, font);
        this.healthView = new HealthView(health, batch);
        this.craftingView = new CraftingView(crafting, batch, font);
    }

    public void render() {
        craftingView.render();
        inventoryView.render();
        healthView.render();
    }

    public void update(Vector2 lowerLeftCorner) {
        craftingView.update(lowerLeftCorner);
        inventoryView.update(lowerLeftCorner);
        healthView.update(lowerLeftCorner);
    }

    public void dispose() {
        inventoryView.dispose();
        healthView.dispose();
        craftingView.dispose();
    }
}
