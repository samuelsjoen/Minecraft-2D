package com.mygdx.game.model;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.utils.Constants;

public class Inventory {
    private HashMap<String, Integer> items;
    private int maxItems;
    private int currentItems;
    private int currentSlot;
    private Texture inventorySprite;
    private Texture selectedItem;
    private float xInv, yInv;
    private float xItm, yItm;
    private float invJump;
    private float scale;

    public Inventory() {
        items = new HashMap<String, Integer>();
        maxItems = 10;
        currentItems = items.size();
        currentSlot = 0;

        inventorySprite = new Texture(Gdx.files.internal("inventory.png"));
        selectedItem = new Texture(Gdx.files.internal("selectedItem.png"));

        // Coordinates for the inventory
        xInv = 0;
        yInv = 0;

        // Coordinates for the first item in inventory
        xItm = xInv + 5;
        yItm = yInv + 5;

        // The amount of pixels to jump to the next item slot
        invJump = 25;

        this.scale = 1f;

        // Default items
        addItem(Item.SWORD.getName());
        addItem(Item.PICKAXE.getName());
    }

    public void addItem(String name, int quantity) {
        if (items.containsKey(name)) {
            items.put(name, items.get(name) + quantity);
        } else {
            if (!isFull()) {
                items.put(name, quantity);
            }
        }
    }

    public void addItem(String name) {
        addItem(name, 1);
    }

    public void removeItem(String name, int quantity) {
        if (items.containsKey(name)) {
            items.put(name, items.get(name) - quantity);
            if (items.get(name) <= 0) {
                items.remove(name);
            }
        }
    }

    public void removeItem(String name) {
        removeItem(name, 1);
    }

    public void removeAllItems(String name) {
        items.remove(name);
    }

    public boolean isFull() {
        return currentItems >= maxItems;
    }

    public void renderItem() {

    }
}
