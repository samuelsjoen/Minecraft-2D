package com.minecraft.game.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InventoryTest {

    Inventory inventory = new Inventory(new Item[]{});
    @Test
    void testAddItem() {
        int prevSize = inventory.getSize();
        assertTrue(inventory.getAmount(Item.WOODEN_PICKAXE) == 0);
        assertFalse(inventory.contains(Item.WOODEN_PICKAXE));
        inventory.addItem(Item.WOODEN_PICKAXE);
        assertTrue(inventory.getSize() == prevSize + 1);
        assertTrue(inventory.contains(Item.WOODEN_PICKAXE));
        assertTrue(inventory.getAmount(Item.WOODEN_PICKAXE) == 1);
        inventory.addItem(Item.WOODEN_PICKAXE);
        assertTrue(inventory.getAmount(Item.WOODEN_PICKAXE) == 1);
        inventory.addItem(Item.DIRT, 5);
        assertTrue(inventory.getAmount(Item.DIRT) == 5);
        inventory.addItem(Item.DIRT, 100);
        assertTrue(inventory.getAmount(Item.DIRT) == 64);
    }

    @Test
    void testChangeSlot() {
        assertTrue(inventory.getCurrentSlot() == 0);
        inventory.changeSlot(1);
        assertTrue(inventory.getCurrentSlot() == 1);
        inventory.changeSlot(-1);
        assertTrue(inventory.getCurrentSlot() == 0);
        inventory.changeSlot(-1);
        assertTrue(inventory.getCurrentSlot() == 9);
        inventory.changeSlot(1);
        assertTrue(inventory.getCurrentSlot() == 0);
    }

    @Test
    void testDropItem() {
        inventory.addItem(Item.WOODEN_PICKAXE);
        inventory.addItem(Item.DIRT, 10);
        assertTrue(inventory.getSelectedItem() == Item.WOODEN_PICKAXE);
        inventory.dropItem();
        assertFalse(inventory.contains(Item.WOODEN_PICKAXE));
        assertTrue(inventory.getSelectedItem() == Item.DIRT);
        int amount = inventory.getAmount(inventory.getSelectedItem());
        inventory.dropItem();
        assertTrue(amount - 1 == inventory.getAmount(inventory.getSelectedItem()));
    }
}
