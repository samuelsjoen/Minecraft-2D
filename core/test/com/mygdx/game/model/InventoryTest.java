package com.mygdx.game.model;

import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

import com.minecraft.game.model.Inventory;
import com.minecraft.game.model.Item;

public class InventoryTest {

    Inventory inventory = new Inventory(new Item[]{});
    @Test
    void testAddItem() {
        int prevSize = inventory.getSize();
        assumeTrue(inventory.getAmount(Item.WOODEN_PICKAXE) == 0);
        assumeFalse(inventory.contains(Item.WOODEN_PICKAXE));
        inventory.addItem(Item.WOODEN_PICKAXE);
        assumeTrue(inventory.getSize() == prevSize + 1);
        assumeTrue(inventory.contains(Item.WOODEN_PICKAXE));
        assumeTrue(inventory.getAmount(Item.WOODEN_PICKAXE) == 1);
        inventory.addItem(Item.WOODEN_PICKAXE);
        assumeTrue(inventory.getAmount(Item.WOODEN_PICKAXE) == 1);
        inventory.addItem(Item.DIRT, 5);
        assumeTrue(inventory.getAmount(Item.DIRT) == 5);
        inventory.addItem(Item.DIRT, 100);
        assumeTrue(inventory.getAmount(Item.DIRT) == 64);
    }

    @Test
    void testChangeSlot() {
        assumeTrue(inventory.getCurrentSlot() == 0);
        inventory.changeSlot(1);
        assumeTrue(inventory.getCurrentSlot() == 1);
        inventory.changeSlot(-1);
        assumeTrue(inventory.getCurrentSlot() == 0);
        inventory.changeSlot(-1);
        assumeTrue(inventory.getCurrentSlot() == 9);
        inventory.changeSlot(1);
        assumeTrue(inventory.getCurrentSlot() == 0);
    }

    @Test
    void testDropItem() {
        inventory.addItem(Item.WOODEN_PICKAXE);
        inventory.addItem(Item.DIRT, 10);
        assumeTrue(inventory.getSelectedItem() == Item.WOODEN_PICKAXE);
        inventory.dropItem();
        assumeFalse(inventory.contains(Item.WOODEN_PICKAXE));
        assumeTrue(inventory.getSelectedItem() == Item.DIRT);
        int amount = inventory.getAmount(inventory.getSelectedItem());
        inventory.dropItem();
        assumeTrue(amount - 1 == inventory.getAmount(inventory.getSelectedItem()));
    }
}
