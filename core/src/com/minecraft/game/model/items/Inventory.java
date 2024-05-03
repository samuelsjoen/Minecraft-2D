package com.minecraft.game.model.items;

import java.util.LinkedHashMap;

/**
 * The Inventory class represents a player's inventory in the game.
 * It stores the items and their quantities, as well as the current slot and maximum item slots.
 */
public class Inventory implements IInventory {
    private LinkedHashMap<Item, Integer> items;
    private LinkedHashMap<Item, Integer> armorInventory;
    private final int maxItemSlots;
    private int currentSlot;

    /**
     * Constructs an Inventory object with the specified default items.
     * 
     * @param defaultItems an array of default items to add to the inventory
     */
    public Inventory(Item[] defaultItems) {
        this.items = new LinkedHashMap<Item, Integer>();
        this.armorInventory = new LinkedHashMap<>();
        this.maxItemSlots = 9;
        this.currentSlot = 0;

        for (Item item : defaultItems) {
            addItem(item);
        }
    }
       
    @Override
    public LinkedHashMap<Item, Integer> getInventory() {
        return items;
    }

    /**
     * @return the currentSlot
     */
    public int getCurrentSlot() {
        return currentSlot;
    }

    public void clearInventory() {
        items.clear();
    }

    /**
     * Add an item to the inventory with a specified quantity
     * 
     * @param item     the item to add
     * @param quantity the quantity of the item to add
     */
    public void addItem(Item item, int quantity) {
        if (canAddMore(item)) {
            if (!isArmor(item)) {
                addItemToInventory(item, quantity);
            }
        }
    }

    private boolean canAddMore(Item item) {
        if (isFull()) {
            if (items.keySet().toArray()[maxItemSlots] == item) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
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

    /**
     * Checks if an item is armor
     * 
     * @param item the item to check
     */
    public boolean isArmor(Item item) {
        return item.getType() == ItemType.HELMET || item.getType() == ItemType.CHESTPLATE
                || item.getType() == ItemType.GLOVES || item.getType() == ItemType.LEGGINGS
                || item.getType() == ItemType.BOOTS;
    }

    @Override
    public void addItem(Item name) {
        addItem(name, 1);
    }

    /**
     * Remove an item from the inventory
     * 
     * @param name     the item to remove
     * @param quantity the quantity of the item to remove
     */
    public void removeItem(Item name, int quantity) {
        if (items.containsKey(name)) {
            items.put(name, items.get(name) - quantity);
            if (items.get(name) <= 0) {
                items.remove(name);
            }
        }
    }

    @Override
    public void removeItem(Item name) {
        removeItem(name, 1);
    }

    private boolean isFull() {
        return items.size() >= maxItemSlots + 1;
    }

    /**
     * Change the current slot
     * 
     * @param slot amount to change the slot by
     */
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

    /**
     * Drop the item in the currently selected slot
     */
    public void dropItem() {
        if (currentSlot < items.size()) {
            Item item = (Item) items.keySet().toArray()[currentSlot];
            removeItem(item);
        }
    }

    /**
     * Get the item in the currently selected slot
     * 
     * @return the item in the currently selected slot
     */
    public Item getSelectedItem() {
        if (currentSlot < items.size()) {
            return (Item) items.keySet().toArray()[currentSlot];
        } else
            return null;
    }

    /**
     * Get the size of the inventory
     * 
     * @return the amount of unique items in the inventory, i.e currently occupied
     *         slots
     */
    public int getSize() {
        return items.size();
    }

    @Override
    public boolean contains(Item item) {
        return items.containsKey(item) || armorInventory.containsKey(item);
    }

    /**
     * Get the amount of a specific item in the inventory
     * 
     * @param item
     * @return the amount of the item in the inventory
     */
    public int getAmount(Item item) {
        return items.getOrDefault(item, 0);
    }

}
