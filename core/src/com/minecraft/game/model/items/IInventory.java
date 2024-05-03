package com.minecraft.game.model.items;

import java.util.LinkedHashMap;

public interface IInventory {
    /**
     * Adds item to the inventory
     * 
     * @param item item to add
     */
    public void addItem(Item item);

    /**
     * Checks if the inventory contains the item
     * 
     * @param item item to check
     * @return true if the inventory contains the item
     */
    public boolean contains(Item item);

    /**
     * Removes the item from the inventory
     * 
     * @param item item to remove
     */
    public void removeItem(Item item);

    /**
     * Returns the inventory
     * 
     * @return a LinkedHashMap containing the items and their quantities in the inventory
     */
    public LinkedHashMap<Item, Integer> getInventory();

    /**
     * Clears the inventory
     */
    public void clearInventory();
}
