package com.minecraft.game.model;

import java.util.HashMap;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.Array;

/*
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.utils.Constants;
*/
public class Inventory {
    private HashMap<Item, Integer> items;
    private int maxItemSlots;
    private int currentItems;
    private int currentSlot;

    public Inventory(Item[] defaultItems) {
        this.items = new HashMap<Item, Integer>();
        this.maxItemSlots = 9;
        this.currentItems = items.size();
        this.currentSlot = 0;

        for (Item item : defaultItems) {
            addItem(item);
        }
    }

    public HashMap<Item, Integer> getItems() {
        return items;
    }

    public int getCurrentSlot() {
        return currentSlot;
    }

    public void addItem(Item name, int quantity) {
        if (items.containsKey(name)) {
            if ((items.get(name) + quantity) >= name.getMaxAmount()) {
                items.put(name, name.getMaxAmount());
            } else {
                items.put(name, items.get(name) + quantity);
            }
        } else {
            if (!isFull()) {
                items.put(name, 0);
                addItem(name, quantity);
            }
        }
    }

    public void addItem(Item name) {
        addItem(name, 1);
    }

    private void removeItem(Item name, int quantity) {
        if (items.containsKey(name)) {
            items.put(name, items.get(name) - quantity);
            if (items.get(name) <= 0) {
                items.remove(name);
            }
        }
    }

    public void removeItem(Item name) {
        removeItem(name, 1);
    }

    @SuppressWarnings("unused")
    private void removeAllItems(Item name) {
        items.remove(name);
    }

    private boolean isFull() {
        return currentItems >= maxItemSlots;
    }

    public void changeSlot(int slot) {
        int nextSlot = currentSlot + slot;

        if (nextSlot < 0) {
            currentSlot = 9;
        } else if (nextSlot > maxItemSlots) {
            currentSlot = 0;
        } else {
            currentSlot = nextSlot;
        }
    }

    public void dropItem() {
        if (currentSlot < items.size()) {
            Item item = (Item) items.keySet().toArray()[currentSlot];
            removeItem(item);
        }
    }

    public Item getSelectedItem() {
        if (currentSlot < items.size()) {
            return (Item) items.keySet().toArray()[currentSlot];
        } else
            return null;
    }

    public int getSize() {
        return items.size();
    }

    public boolean contains(Item item) {
        return items.containsKey(item);
    }

    public int getAmount(Item item) {
        return items.getOrDefault(item, 0);
    }
}