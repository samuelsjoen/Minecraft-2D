package com.minecraft.game.model.items;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.minecraft.game.model.Health;

public class ArmorInventory implements IInventory {
    private LinkedHashMap<Item, Integer> armorInventory;
    private Health health;

    public ArmorInventory(Health health) {
        this.armorInventory = new LinkedHashMap<>();
        this.health = health;
    }

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

    public boolean contains(Item item) {
        return armorInventory.containsKey(item);
    }

    /**
     * Remove the next breakable armor item from the inventory
     */
    public void removeItem() {
        armorInventory.remove(getNextBreakableArmorItem());
    }

    public void removeItem(Item item) {
        armorInventory.remove(item);
    }

    /**
     * Damage the next breakable armor item
     * 
     * @param damage the amount of damage to deal
     */
    public void damageArmor(int damage) {
        Item item = getNextBreakableArmorItem();
        armorInventory.put(item, getArmorPieceHealth(item) - damage);
    }

    public LinkedHashMap<Item, Integer> getInventory() {
        return armorInventory;
    }

    /**
     * Get the health of an armor piece
     * 
     * @param item to get health of
     * @return the health of the armor piece
     */
    public int getArmorPieceHealth(Item item) {
        return armorInventory.get(item);
    }

    /**
     * Get the armor piece that will break next
     * 
     * @return the armor piece that will break next
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

}
