package com.mygdx.game.model;

import java.util.HashMap;

public class Inventory {
    private HashMap<String, Integer> items;
    private int maxItems;
    private int currentItems;

    public Inventory() {
        items = new HashMap<String, Integer>();
        maxItems = 10;
        addItem(Item.SWORD.getName());
        addItem(Item.PICKAXE.getName());
        currentItems = items.size();
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
}
