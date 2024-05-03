package com.minecraft.game.view.overlay;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Score;
import com.minecraft.game.model.items.Crafting;
import com.minecraft.game.model.items.Inventory;

public class OverlayView implements IOverlay {

    private final IOverlay inventoryView;
    private final IOverlay healthView;
    private final IOverlay craftingView;
    private final IOverlay scoreView;
    private final TextureMap textureMap;

    public OverlayView(Inventory inventory, Health health, Crafting crafting, Score score, SpriteBatch batch, BitmapFont font) {
        this.textureMap = new TextureMap();
        this.inventoryView = new InventoryView(inventory, batch, font, textureMap);
        this.healthView = new HealthView(health, batch);
        this.craftingView = new CraftingView(crafting, batch, font, textureMap);
        this.scoreView = new ScoreView(batch, score);
    }

    @Override
    public void render() {
        craftingView.render();
        inventoryView.render();
        healthView.render();
        scoreView.render();
    }

    @Override
    public void update(Vector2 lowerLeftCorner) {
        craftingView.update(lowerLeftCorner);
        inventoryView.update(lowerLeftCorner);
        healthView.update(lowerLeftCorner);
        scoreView.update(lowerLeftCorner);
    }

    @Override
    public void dispose() {
        inventoryView.dispose();
        healthView.dispose();
        craftingView.dispose();
        scoreView.dispose();
        textureMap.dispose();
    }
}

