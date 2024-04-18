package com.minecraft.game.model.crafting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.minecraft.game.model.Health;

public class ArmorInventoryTest {

    @Test
    void testAddArmorItem() {
        ArmorInventory inventory = new ArmorInventory(new Health(5, 5));
        inventory.addItem(Item.IRON_BOOTS);
        assertTrue(inventory.contains(Item.IRON_BOOTS));
        inventory.addItem(Item.DIAMOND_BOOTS);
        assertTrue(inventory.contains(Item.DIAMOND_BOOTS));
        assertFalse(inventory.contains(Item.IRON_BOOTS));
    }

    @Test
    void testAddArmorHealth() {
        Health health = new Health(5, 5);
        ArmorInventory inventory = new ArmorInventory(health);
        assertTrue(health.getArmorHealth() == 0);
        inventory.addItem(Item.IRON_BOOTS);
        assertTrue(health.getArmorHealth() == 1);
        inventory.addItem(Item.DIAMOND_BOOTS);
        assertTrue(health.getArmorHealth() == 3);
    }

    @Test
    void testDamageArmorHelth() {
        Health health = new Health(5, 5);
        ArmorInventory inventory = new ArmorInventory(health);
        health.setArmorInventory(inventory);
        inventory.addItem(Item.DIAMOND_BOOTS);
        health.damage(1);
        assertTrue(health.getArmorHealth() == 1);
        assertTrue(inventory.getArmorPieceHealth(Item.DIAMOND_BOOTS) == 1);
    }

    @Test
    void testBreakArmor() {
        Health health = new Health(5, 5);
        ArmorInventory inventory = new ArmorInventory(health);
        inventory.addItem(Item.DIAMOND_BOOTS);
        inventory.breakArmor();
        assertFalse(inventory.contains(Item.DIAMOND_BOOTS));
    }

    @Test
    void testGetNextBreakableArmorItem() {
        Health health = new Health(5, 5);
        ArmorInventory inventory = new ArmorInventory(health);
        inventory.addItem(Item.DIAMOND_BOOTS);
        assertTrue(inventory.getNextBreakableArmorItem() == Item.DIAMOND_BOOTS);
        inventory.addItem(Item.DIAMOND_CHESTPLATE);
        assertTrue(inventory.getNextBreakableArmorItem() == Item.DIAMOND_BOOTS);
        inventory.breakArmor();
        assertTrue(inventory.getNextBreakableArmorItem() == Item.DIAMOND_CHESTPLATE);
    }

    @Test
    void testGetArmorInventory() {
        Health health = new Health(5, 5);
        ArmorInventory inventory = new ArmorInventory(health);
        inventory.addItem(Item.DIAMOND_BOOTS);
        inventory.addItem(Item.DIAMOND_CHESTPLATE);
        assertTrue(inventory.getArmorInventory().size() == 2);
        assertTrue(inventory.getArmorInventory().containsKey(Item.DIAMOND_BOOTS));
        assertTrue(inventory.getArmorInventory().containsKey(Item.DIAMOND_CHESTPLATE));
    }
}
