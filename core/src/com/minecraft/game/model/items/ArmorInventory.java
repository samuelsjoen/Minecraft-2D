package com.minecraft.game.model.items;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.minecraft.game.model.Health;

/**
 * Represents an inventory for armor items.
 */
public class ArmorInventory implements IInventory {
    private LinkedHashMap<Item, Integer> armorInventory;
    private Health health;

    /**
     * Constructs a new ArmorInventory object.
     * 
     * @param health the health object associated with the player
     */
    public ArmorInventory(Health health) {
        this.armorInventory = new LinkedHashMap<>();
        this.health = health;
    }

    /**
     * Adds an item to the armor inventory.
     * 
     * @param item the item to be added
     */
    public void addItem(Item item) {
        addOrUpgradeArmor(item);
        int armorHealth = health.getArmorHealth();
        if (item.getMaterial() == ItemMaterial.IRON) {
            health.setArmorHealth(armorHealth + 1);
        } else if (item.getMaterial() == ItemMaterial.DIAMOND) {
            health.setArmorHealth(armorHealth + 2);
        }
    }

    private void addOrUpgradeArmor(Item item) {
        for (Item oldItem : armorInventory.keySet()) {
            if (oldItem.getType() == item.getType()) {
                armorInventory.remove(oldItem);
                break;
            }
        }
        armorInventory.put(item, getArmorPieceMaxHealth(item));
    }

    /**
     * Checks if the armor inventory contains the specified item.
     * 
     * @param item the item to check
     * @return true if the item is present in the inventory, false otherwise
     */
    public boolean contains(Item item) {
        return armorInventory.containsKey(item);
    }

    /**
     * Removes the next breakable armor item from the inventory.
     */
    public void removeItem() {
        armorInventory.remove(getNextBreakableArmorItem());
    }

    /**
     * Removes the specified item from the armor inventory.
     * 
     * @param item the item to be removed
     */
    public void removeItem(Item item) {
        armorInventory.remove(item);
    }

    /**
     * Damages the next breakable armor item.
     * 
     * @param damage the amount of damage to deal
     */
    public void damageArmor(int damage) {
        Item item = getNextBreakableArmorItem();
        armorInventory.put(item, getArmorPieceHealth(item) - damage);
    }

    /**
     * Returns the armor inventory as a LinkedHashMap.
     * 
     * @return the armor inventory
     */
    public LinkedHashMap<Item, Integer> getInventory() {
        return armorInventory;
    }

    /**
     * Gets the health of an armor piece.
     * 
     * @param item the item to get the health of
     * @return the health of the armor piece
     */
    public int getArmorPieceHealth(Item item) {
        return armorInventory.get(item);
    }

    /**
     * Gets the armor piece that will break next.
     * 
     * @return the armor piece that will break next, or null if there are no breakable armor pieces
     */
    public Item getNextBreakableArmorItem() {
        for (Entry<Item, Integer> entry : armorInventory.entrySet()) {
            return entry.getKey();
        }
        return null;
    }

    private int getArmorPieceMaxHealth(Item item) {
        int health;
        switch (item.getMaterial()) {
            case IRON:
                health = 1;
                break;
            case DIAMOND:
                health = 2;
                break;
            default:
                health = 0;
                break;
        }
        return health;
    }

    public void clearInventory() {
        armorInventory.clear();
    }
}
