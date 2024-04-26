package com.minecraft.game.view.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.crafting.Crafting;
import com.minecraft.game.model.crafting.Item;

public class CraftingView {
    private final Crafting crafting;
    final Texture craftingSprite;
    final Texture selectedItem;
    final BitmapFont font;
    final SpriteBatch batch;
    private final int jump;

    private float xCrafting;
    private float yCrafting;
    private float xItem;
    private float yItem;
    private float xDescription;
    private float yDescription;

    public CraftingView(Crafting crafting, SpriteBatch batch, BitmapFont font) {
        this.crafting = crafting;
        this.craftingSprite = new Texture(Gdx.files.internal("assets/overlay/crafting.png"));
        this.selectedItem = new Texture(Gdx.files.internal("assets/overlay/selectedItem.png"));
        this.font = new BitmapFont();
        this.batch = batch;
        this.jump = 40;
    }

    public void render() {
        if (crafting.isOpen()) {
            renderCraftingTable();
            renderCraftingTableItems();
            renderCraftableItems();
            renderSelectedSlot();
            renderPotentialItem();
            renderSelectedItemText();
        }
    }

    private void renderCraftingTable() {
        batch.draw(craftingSprite, xCrafting, yCrafting);
    }

    private void renderCraftingTableItems() {
        Item[][] table = crafting.getTable();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Item item = table[row][col];
                if (item != null) {
                    float x = xItem + (col * jump);
                    float y = yItem - (row * jump);
                    Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
                    batch.draw(itemTexture, x, y, 30, 30);
                }
            }
        }
    }


    private void renderCraftableItems() {
        Item[][] craftableItems = crafting.getCraftableItems();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                Item item = craftableItems[row][col];
                if (item != null) {
                    float x = xCrafting + 22 + (col * jump);
                    float y = yCrafting + 182 - (row * jump);
                    Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
                    batch.draw(itemTexture, x, y, 30, 30);
                }
            }
        }
    }

    private void renderSelectedSlot() {
        int selectedRow = crafting.getSelectedRow();
        int selectedCol = crafting.getSelectedCol();
        float x = xCrafting + 17 + (selectedCol * jump);
        float y = yCrafting + 176 - (selectedRow * jump);
        batch.draw(selectedItem, x, y);}

    private void renderPotentialItem() {
        Item item = crafting.getSelectedItem();
        if (item != null) {
            Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
            batch.draw(itemTexture, xCrafting + 344, yCrafting + 302, 30, 30);
        }
    }

    public void update(Vector2 lowerLeftCorner) {        
        xCrafting = lowerLeftCorner.x + 820;
        yCrafting = lowerLeftCorner.y + 240;
        xItem = xCrafting + 62;
        yItem = yCrafting + 342;
        xDescription = xCrafting + 20;
        yDescription = yCrafting + 240;
    }

    private void renderSelectedItemText() {
        Item item = crafting.getSelectedItem();
        if (item != null) {
        font.draw(batch, item.getName() + ": " + item.getDescription(), xDescription, yDescription); }
    }

    public void dispose() {
        craftingSprite.dispose();
        selectedItem.dispose();
        font.dispose();
    }
}
