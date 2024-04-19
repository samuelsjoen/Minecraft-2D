package com.minecraft.game.model.crafting;

import java.util.LinkedHashMap;

public interface InventoryInterface {
    public void addItem(Item item);
    public boolean contains(Item item);
    public void removeItem(Item item);
    public LinkedHashMap<Item, Integer> getInventory();
}
