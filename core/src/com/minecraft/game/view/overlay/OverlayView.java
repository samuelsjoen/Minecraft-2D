package com.minecraft.game.view.overlay;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.crafting.Crafting;
import com.minecraft.game.model.crafting.Inventory;
import com.minecraft.game.model.Health;

public class OverlayView {

    private final InventoryView inventoryView;
    private final HealthView healthView;
    private final CraftingView craftingView;

    public OverlayView(Inventory inventory, Health health, Crafting crafting) {
        this.inventoryView = new InventoryView(inventory);
        this.healthView = new HealthView(health);
        this.craftingView = new CraftingView(crafting);
    }

    public void render(SpriteBatch batch) {
        craftingView.render(batch);
        inventoryView.render(batch);
        healthView.render(batch);
    }

    public void update(Vector2 lowerLeftCorner) {
        craftingView.update(lowerLeftCorner);
        inventoryView.update(lowerLeftCorner);
        healthView.update(lowerLeftCorner);
    }
}
