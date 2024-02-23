package com.mygdx.game.model;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Inventory extends GameEntity {
    private HashMap<InventoryItem, Integer> items;
    private int maxItems;
    private int currentItems;
    private int currentSlot;
    private Texture inventorySprite;
    private Texture selectedItem;
    private float xInv, yInv;
    private float xItm, yItm;
    private float invJump;
    private float scale;
    private float xPlayer, yPlayer;

    public Inventory() {
        this.items = new HashMap<InventoryItem, Integer>();
        this.maxItems = 10;
        this.currentItems = items.size();
        this.currentSlot = 0;
        this.inventorySprite = new Texture(Gdx.files.internal("inventory.png"));
        this.selectedItem = new Texture(Gdx.files.internal("selectedItem.png"));

        // Coordinates for the inventory
        xInv = GameEntity.getBody().getPosition().x;
        yInv = 0;

        // Coordinates for the first item in inventory
        this.xItm = xInv + 5;
        this.yItm = yInv + 5;

        // The amount of pixels to jump to the next item slot
        this.invJump = 25;

        this.scale = 1f;

        // Default items
        addItem(InventoryItem.SWORD);
    }

    public void addItem(InventoryItem name, int quantity) {
        if (items.containsKey(name)) {
            items.put(name, items.get(name) + quantity);
        } else {
            if (!isFull()) {
                items.put(name, quantity);
            }
        }
    }

    public void addItem(InventoryItem name) {
        addItem(name, 1);
    }

    public void removeItem(InventoryItem name, int quantity) {
        if (items.containsKey(name)) {
            items.put(name, items.get(name) - quantity);
            if (items.get(name) <= 0) {
                items.remove(name);
            }
        }
    }

    public void removeItem(InventoryItem name) {
        removeItem(name, 1);
    }

    public void removeAllItems(InventoryItem name) {
        items.remove(name);
    }

    private boolean isFull() {
        return currentItems >= maxItems;
    }

    public void changeSlot(int slot) {
        currentSlot = (currentSlot + slot) % maxItems;
    }

    private void renderItems(SpriteBatch batch) {
        int iteration = 0;
        for (InventoryItem item : items.keySet()) {
            Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
            batch.draw(itemTexture, xItm + (iteration * invJump), yItm + (iteration * invJump));
            iteration++;
        }
    }

    private void renderInventory(SpriteBatch batch) {
        batch.draw(inventorySprite, xInv, yInv, 319, 33);
        renderCurrentSlot(batch);
        renderItems(batch);
    }

    private void renderCurrentSlot(SpriteBatch batch) {
        batch.draw(selectedItem, xItm-1 + (currentSlot * invJump), yItm-1 + (currentSlot * invJump), 35, 35);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }
}