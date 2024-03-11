package com.minecraft.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.model.Crafting;
import com.minecraft.game.model.GameEntity;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.model.Item;
import com.minecraft.game.utils.Constants;

public class CraftingView extends GameEntity {

    Crafting crafting;
    boolean open;
    Texture craftingSprite;
    int jump;

    public CraftingView(float width, float height, Body body, Crafting crafting) {
        super(width, height, body);
        this.crafting = crafting;
        this.open = false;
        this.craftingSprite = new Texture(Gdx.files.internal("assets/overlay/crafting.png"));
        this.jump = 50;
    }

    @Override
    public void update() {
        x = body.getPosition().x * Constants.PPM + 200;
        y = body.getPosition().y * Constants.PPM + 250;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (open) {
            renderTable(batch);
            renderItems(batch);
        }
    }

    public void renderTable(SpriteBatch batch) {
        batch.draw(craftingSprite, x, y);
    }

    public void renderItems(SpriteBatch batch) {
        Item[][] table = crafting.getTable();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Item item = table[row][col];
                Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
                batch.draw(itemTexture, x + (row * jump), (col * jump), 50, 50);
            }
        }
    }

    public void open() {
        open = !open;
    }
}
