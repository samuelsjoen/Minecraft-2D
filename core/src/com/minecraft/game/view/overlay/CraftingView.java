package com.minecraft.game.view.overlay;

import com.badlogic.gdx.Gdx;
import java.util.HashMap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.crafting.Crafting;
import com.minecraft.game.model.crafting.Item;

public class CraftingView {
    private final Crafting crafting;
    private final Texture craftingSprite;
    private final Texture selectedItem;
    private final int jump;

    private float xCrafting;
    private float yCrafting;
    private float xItem;
    private float yItem;
    private HashMap<Float[], Item> itemXYTable;

    public CraftingView(Crafting crafting){
        this.crafting = crafting;
        this.craftingSprite = new Texture(Gdx.files.internal("assets/overlay/crafting.png"));
        this.selectedItem = new Texture(Gdx.files.internal("assets/overlay/selectedItem.png"));
        this.jump = 40;
    }

    public void render(SpriteBatch batch) {
        if (crafting.isOpen()) {
            renderCraftingTable(batch);
            renderCraftingTableItems(batch);
            renderCraftableItems(batch);
            renderSelectedSlot(batch);
            renderPotentialItem(batch);
        }
    }

    private void renderCraftingTable(SpriteBatch batch) {
        batch.draw(craftingSprite, xCrafting, yCrafting);
    }

    private void renderCraftingTableItems(SpriteBatch batch) {
        this.itemXYTable = new HashMap<Float[], Item>();
        Item[][] table = crafting.getTable();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Item item = table[row][col];
                if (item != null) {
                    float x = xItem + (col * jump);
                    float y = yItem - (row * jump);
                    Float[] xy = {x, y};
                    itemXYTable.put(xy, item);
                    Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
                    batch.draw(itemTexture, x, y, 30, 30);
                }
            }
        }
    }


    private void renderCraftableItems(SpriteBatch batch) {
        int i = 0;
        for (Item item : crafting.getCraftableItems()) {
            float x = xCrafting + 22 + (i * jump);
            float y = yCrafting + 182 + ((i%10) * jump);
            Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
            batch.draw(itemTexture, x, y, 30, 30);
            i++;
        }
    }

    private void renderSelectedSlot(SpriteBatch batch) {
        batch.draw(selectedItem, xCrafting + 17 + (crafting.getSelectedSlot()*jump), yCrafting + 176 + ((crafting.getSelectedSlot()%10) * jump));
    }

    private void renderPotentialItem(SpriteBatch batch) {
        Item item = crafting.getSelectedItem();
        Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
        batch.draw(itemTexture, xCrafting + 344, yCrafting + 302, 30, 30);
    }

    public void update(Vector2 lowerLeftCorner) {        
        xCrafting = lowerLeftCorner.x + 820;
        yCrafting = lowerLeftCorner.y + 240;
        xItem = xCrafting + 62;
        yItem = yCrafting + 342;
    }

    public HashMap<Float[], Item> getItemXYTable() {
        return itemXYTable;
    }
}
