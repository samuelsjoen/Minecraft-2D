package com.minecraft.game.model.crafting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class CraftingTest {


    @Test
    void testMoveSelection() {
        Crafting crafting = new Crafting(new Inventory(new Item[]{}));
        assertTrue(crafting.getSelectedRow() == 0);
        assertTrue(crafting.getSelectedCol() == 0);
        crafting.moveCraftableTableSelection(1, 0);
        assertTrue(crafting.getSelectedRow() == 1);
        assertTrue(crafting.getSelectedCol() == 0);
        crafting.moveCraftableTableSelection(0, 1);
        assertTrue(crafting.getSelectedRow() == 1);
        assertTrue(crafting.getSelectedCol() == 1);
        crafting.moveCraftableTableSelection(-1, 0);
        assertTrue(crafting.getSelectedRow() == 0);
        assertTrue(crafting.getSelectedCol() == 1);
        crafting.moveCraftableTableSelection(0, -1);
        assertTrue(crafting.getSelectedRow() == 0);
        assertTrue(crafting.getSelectedCol() == 0);
        crafting.moveCraftableTableSelection(0, -1);
        assertTrue(crafting.getSelectedRow() == 0);
        assertTrue(crafting.getSelectedCol() == 0);
        crafting.moveCraftableTableSelection(-1, 0);
        assertTrue(crafting.getSelectedRow() == 0);
        assertTrue(crafting.getSelectedCol() == 0);
        crafting.moveCraftableTableSelection(4, 9);
        assertTrue(crafting.getSelectedRow() == 4);
        assertTrue(crafting.getSelectedCol() == 9);
        crafting.moveCraftableTableSelection(0, 1);
        assertTrue(crafting.getSelectedRow() == 4);
        assertTrue(crafting.getSelectedCol() == 9);
        crafting.moveCraftableTableSelection(1, 0);
        assertTrue(crafting.getSelectedRow() == 4);
        assertTrue(crafting.getSelectedCol() == 9);
    }

    @Test
    void testCrafting() {
        Inventory inventory = new Inventory(new Item[]{
            Item.WOOD,
            Item.WOOD,
        });
        Crafting crafting = new Crafting(inventory);
        assertTrue(inventory.contains(Item.WOOD));
        assertTrue(inventory.getSelectedItem() == Item.WOOD);
        assertTrue(crafting.getSelectedItem() == Item.STICK);
        crafting.craft(null);
        assertTrue(inventory.contains(Item.STICK));
        assertFalse(inventory.contains(Item.WOOD));
        assertTrue(inventory.getSelectedItem() == Item.STICK);
        assertTrue(crafting.getSelectedItem() == null);
    }

    @Test
    void testCraftingTable() {
        Crafting crafting = new Crafting(new Inventory(new Item[]{
            Item.WOOD,
            Item.WOOD,
        }));
        Item[][] table = crafting.getTable();
        for (int row = 0; row < table.length; row++) {
            for (int col = 0; col < table[row].length; col++) {
                if (row == 0 && col == 1) {
                    assertTrue(table[row][col] == Item.WOOD);
                } else if (row == 1 && col == 1) {
                    assertTrue(table[row][col] == Item.WOOD);
                } else {
                    assertTrue(table[row][col] == null);
                }
            }
        }
        crafting.craft(null);
        table = crafting.getTable();
        for (int row = 0; row < table.length; row++) {
            for (int col = 0; col < table[row].length; col++) {
                assertTrue(table[row][col] == null);
            }
        }
    }

    @Test
    void testCraftableItems() {
        Crafting crafting = new Crafting(new Inventory(new Item[]{
            Item.WOOD,
            Item.WOOD,
            Item.STICK,
        }));
        Item[][] craftableItems = crafting.getCraftableItems();
        ArrayList<Item> items = new ArrayList<Item>();
        for (int row = 0; row < craftableItems.length; row++) {
            for (int col = 0; col < craftableItems[row].length; col++) {
                if (craftableItems[row][col] != null) {
                    items.add(craftableItems[row][col]);
                }
            }
        }
        assertTrue(items.size() == 2);
        assertTrue(items.contains(Item.WOODEN_SWORD));
        assertTrue(items.contains(Item.STICK));
    }
}
