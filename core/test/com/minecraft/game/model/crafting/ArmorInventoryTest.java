package com.minecraft.game.model.crafting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ArmorInventoryTest {

    @Test
    void testAddArmorItem() {
        ArmorInventory inventory = new ArmorInventory(null);
        inventory.addItem(Item.IRON_BOOTS);
        assertTrue(inventory.contains(Item.IRON_BOOTS));
        inventory.addItem(Item.DIAMOND_BOOTS);
        assertTrue(inventory.contains(Item.DIAMOND_BOOTS));
        assertFalse(inventory.contains(Item.IRON_BOOTS));
    }

    // @Test
    // void test armorHealth() {
    // }
}
