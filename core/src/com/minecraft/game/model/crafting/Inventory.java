package com.minecraft.game.model.crafting;

import java.util.LinkedHashMap;

public class Inventory implements InventoryInterface {
    private LinkedHashMap<Item, Integer> items;
    private LinkedHashMap<Item, Integer> armorInventory;
    private int maxItemSlots;
    private int currentSlot;

    public Inventory(Item[] defaultItems) {
        this.items = new LinkedHashMap<Item, Integer>();
        this.armorInventory = new LinkedHashMap<>();
        this.maxItemSlots = 9;
        this.currentSlot = 0;

        for (Item item : defaultItems) {
            addItem(item);
        }
    }

    public LinkedHashMap<Item, Integer> getInventory() {
        return items;
    }

    public int getCurrentSlot() {
        return currentSlot;
    }

    public void addItem(Item item, int quantity) {
        if (!isFull()) {
            if (!isArmor(item)) {
                addItemToInventory(item, quantity);
            }
        }
    }

    private void addItemToInventory(Item item, int quantity) {
        if (items.containsKey(item)) {
            if ((items.get(item) + quantity) >= item.getMaxAmount()) {
                items.put(item, item.getMaxAmount());
            } else {
                items.put(item, items.get(item) + quantity);
            }
        } else {
            items.put(item, 0);
            addItem(item, quantity);
        }
    }

    public boolean isArmor(Item item) {
        return item.getType() == ItemType.HELMET || item.getType() == ItemType.CHESTPLATE
                || item.getType() == ItemType.GLOVES || item.getType() == ItemType.LEGGINGS
                || item.getType() == ItemType.BOOTS;
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
    }

    public void removeItem(Item name) {
        removeItem(name, 1);
    }

    private boolean isFull() {
        return items.size() >= maxItemSlots+1;
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
        return items.containsKey(item) || armorInventory.containsKey(item);
    }

    public int getAmount(Item item) {
        return items.getOrDefault(item, 0);
    }

}