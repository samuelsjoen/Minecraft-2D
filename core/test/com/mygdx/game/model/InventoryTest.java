package com.mygdx.game.model;

import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

import com.minecraft.game.model.Inventory;
import com.minecraft.game.model.Item;

public class InventoryTest {

    Inventory inventory = new Inventory();
    @Test
    void testAddItem() {
        int prevSize = inventory.getSize();
        assumeTrue(inventory.getAmount(Item.DIRT) == 0);
        assumeFalse(inventory.contains(Item.DIRT));
        inventory.addItem(Item.DIRT);
        assumeTrue(inventory.getSize() == prevSize + 1);
        assumeTrue(inventory.contains(Item.DIRT));
        assumeTrue(inventory.getAmount(Item.DIRT) == 1);
    }

    @Test
    void testDropItem() {
        Item item = inventory.getSelectedItem();
        int amount = inventory.getAmount(item);
        inventory.dropItem();
        assumeTrue(amount - 1 == inventory.getAmount(item));
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

}
