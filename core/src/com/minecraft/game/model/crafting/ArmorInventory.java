package com.minecraft.game.model.crafting;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.minecraft.game.model.Health;

public class ArmorInventory {
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

    public int getArmorPieceHealth(Item item) {
        return armorInventory.get(item);
    }

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

    public void breakArmor() {
        armorInventory.remove(getNextBreakableArmorItem());
    }

    public void damageArmor(int damage) {
        Item item = getNextBreakableArmorItem();
        armorInventory.put(item, getArmorPieceHealth(item) - damage);
    }

    public LinkedHashMap<Item, Integer> getArmorInventory() {
        return armorInventory;
    }
}
