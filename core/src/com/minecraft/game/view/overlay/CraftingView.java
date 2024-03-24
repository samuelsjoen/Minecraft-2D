package com.minecraft.game.view.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.crafting.Crafting;
import com.minecraft.game.model.crafting.Item;

public class CraftingView {
    private final Crafting crafting;
    private final Texture craftingSprite;
    private final int jump;

    private float xCrafting;
    private float yCrafting;

    public CraftingView(Crafting crafting){
        this.crafting = crafting;
        this.craftingSprite = new Texture(Gdx.files.internal("assets/overlay/crafting.png"));
        this.jump = 50;
    }

    public void render(SpriteBatch batch) {
        if (crafting.isOpen()) {
            renderCraftingTable(batch);
            renderCraftingTableItems(batch);
        }
    }

    private void renderCraftingTable(SpriteBatch batch) {
        batch.draw(craftingSprite, xCrafting, yCrafting);
    }

    private void renderCraftingTableItems(SpriteBatch batch) {
        Item[][] table = crafting.getTable();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Item item = table[row][col];
                if (item != null) {
                    Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
                    batch.draw(itemTexture, xCrafting + (row * jump), yCrafting + (col * jump), 50, 50);
                }
            }
        }
    }

    public void update(Vector2 lowerLeftCorner) {        
        xCrafting = lowerLeftCorner.x + 825;
        yCrafting = lowerLeftCorner.y + 480;
    }
}
