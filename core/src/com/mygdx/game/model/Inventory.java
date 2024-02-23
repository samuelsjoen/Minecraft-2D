package com.mygdx.game.model;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Inventory {
    private HashMap<Item, Integer> items;
    private int maxItems;
    private int currentItems;
    private int currentSlot;
    private Texture inventorySprite;
    private Texture selectedItem;
    private float xInv, yInv;
    private float xItm, yItm;
    private float invJump;
    private float scale;
    private SpriteBatch batch;

    public Inventory(SpriteBatch batch) {
        this.batch = batch;
        this.items = new HashMap<Item, Integer>();
        this.maxItems = 10;
        this.currentItems = items.size();
        this.currentSlot = 0;

        this.inventorySprite = new Texture(Gdx.files.internal("inventory.png"));
        this.selectedItem = new Texture(Gdx.files.internal("selectedItem.png"));

        // Coordinates for the inventory
        this.xInv = 0;
        this.yInv = 0;

        // Coordinates for the first item in inventory
        this.xItm = xInv + 5;
        this.yItm = yInv + 5;

        // The amount of pixels to jump to the next item slot
        this.invJump = 25;

        this.scale = 1f;

        // Default items
        addItem(Item.SWORD);
        addItem(Item.PICKAXE);
    }

    public void addItem(Item name, int quantity) {
        if (items.containsKey(name)) {
            items.put(name, items.get(name) + quantity);
        } else {
            if (!isFull()) {
                items.put(name, quantity);
            }
        }
        renderItems();
    }

    public void addItem(Item name) {
        addItem(name, 1);
    }

    public void removeItem(Item name, int quantity) {
        if (items.containsKey(name)) {
            items.put(name, items.get(name) - quantity);
            if (items.get(name) <= 0) {
                items.remove(name);
            }
        }
        renderItems();
    }

    public void removeItem(Item name) {
        removeItem(name, 1);
    }

    public void removeAllItems(Item name) {
        items.remove(name);
        renderItems();
    }

    private boolean isFull() {
        return currentItems >= maxItems;
    }

    public void changeSlot(int slot) {
        currentSlot = (currentSlot + slot) % maxItems;
        renderCurrentSlot();
    }

    private void renderItems() {
        int iteration = 0;
        for (Item item : items.keySet()) {
            Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
            batch.draw(itemTexture, xItm + (iteration * invJump), yItm + (iteration * invJump));
            iteration++;
        }
    }

    public void renderInventory() {
        batch.draw(inventorySprite, xInv, yInv);
        renderCurrentSlot();
        renderItems();
    }

    private void renderCurrentSlot() {
        batch.draw(selectedItem, xItm + (currentSlot * invJump), yItm + (currentSlot * invJump));
    }
}