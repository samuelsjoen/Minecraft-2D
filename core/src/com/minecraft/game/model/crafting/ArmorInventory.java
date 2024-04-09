package com.minecraft.game.model.crafting;

import java.util.ArrayList;

public class ArmorInventory {
    ArrayList<Item> armor;

    public ArmorInventory() {
        this.armor = new ArrayList<>();
    }

    public void addOrUpgradeArmor(Item item) {
        for (Item oldItem: armor) {
            if (oldItem.getType() == item.getType()) {
                armor.remove(oldItem);
                break;
            }
        }
        armor.add(item);
    }

    public boolean contains(Item item) {
        return armor.contains(item);
    }
}