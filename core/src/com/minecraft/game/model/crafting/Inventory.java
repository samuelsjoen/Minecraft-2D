package com.minecraft.game.model.crafting;

import java.util.HashMap;

import com.minecraft.game.model.Health;

public class Inventory {
    private HashMap<Item, Integer> items;
    private ArmorInventory armorInventory;
    private Health health;
    private int maxItemSlots;
    private int currentItems;
    private int currentSlot;

    public Inventory(Item[] defaultItems, ArmorInventory armorInventory, Health health) {
        this.items = new HashMap<Item, Integer>();
        this.armorInventory = armorInventory;
        this.health = health;
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

    public void addItem(Item item, int quantity) {
        if (!isFull()) {
            if (isArmor(item)) {
                addArmor(item);
            } else {
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

    private void addArmor(Item item) {
        armorInventory.addOrUpgradeArmor(getSelectedItem());
        int armorHealth = health.getArmorHealth();
        if (getSelectedItem().getMaterial() == ItemMaterial.IRON) {
            health.setArmorHealth(armorHealth + 1);
        } else if (getSelectedItem().getMaterial() == ItemMaterial.DIAMOND) {
            health.setArmorHealth(armorHealth + 2);
        }
    }

    private boolean isArmor(Item item) {
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