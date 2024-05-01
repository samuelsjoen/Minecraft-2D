package com.minecraft.game.view.overlay;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.items.Crafting;
import com.minecraft.game.model.items.Inventory;

public class OverlayView implements IOverlay {

    private final IOverlay inventoryView;
    private final IOverlay healthView;
    private final IOverlay craftingView;

    public OverlayView(Inventory inventory, Health health, Crafting crafting, Player player, SpriteBatch batch, BitmapFont font) {
        this.inventoryView = new InventoryView(inventory, batch, font);
        this.healthView = new HealthView(health, batch);
        this.craftingView = new CraftingView(crafting, batch, font);
        this.scoreView = new ScoreView(batch, player);
    }

    public void render() {
        craftingView.render();
        inventoryView.render();
        healthView.render();
        scoreView.render();
    }

    public void update(Vector2 lowerLeftCorner) {
        craftingView.update(lowerLeftCorner);
        inventoryView.update(lowerLeftCorner);
        healthView.update(lowerLeftCorner);
        scoreView.update(lowerLeftCorner);
    }

    public void dispose() {
        inventoryView.dispose();
        healthView.dispose();
        craftingView.dispose();
        scoreView.dispose();
    }
}
