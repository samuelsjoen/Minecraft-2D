package com.minecraft.game.model.crafting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;

import com.minecraft.game.model.Health;

public class InventoryTest {

    @Test
    void testAddItem() {
        Inventory inventory = new Inventory(new Item[]{});
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
        Inventory inventory = new Inventory(new Item[]{});
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
        Inventory inventory = new Inventory(new Item[]{});
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

    @Test
    void testItemMap() {
        Inventory inventory = new Inventory(new Item[]{}); {
            LinkedHashMap<Item, Integer> items = inventory.getItems();
            assertTrue(items.size() == 0);
            inventory.addItem(Item.WOODEN_PICKAXE);
            items = inventory.getItems();
            assertTrue(items.size() == 1);
            assertTrue(items.containsKey(Item.WOODEN_PICKAXE));
        }
    }

    @Test
    void testIsFull() {
        Inventory inventory = new Inventory(new Item[]{
            Item.WOODEN_PICKAXE,
            Item.WOODEN_PICKAXE,
            Item.WOODEN_PICKAXE,
            Item.WOODEN_PICKAXE,
            Item.WOODEN_PICKAXE,
            Item.WOODEN_PICKAXE,
            Item.WOODEN_PICKAXE,
            Item.WOODEN_PICKAXE,
            Item.WOODEN_PICKAXE,
        });
        inventory.addItem(Item.WOODEN_SWORD);
        assertTrue(inventory.contains(Item.WOODEN_SWORD));
        inventory.addItem(Item.DIAMOND_BOOTS);
        assertFalse(inventory.contains(Item.DIAMOND_BOOTS));
        inventory.dropItem();
        inventory.addItem(Item.DIAMOND_BOOTS);
        assertTrue(inventory.contains(Item.DIAMOND_BOOTS));
    }
}
