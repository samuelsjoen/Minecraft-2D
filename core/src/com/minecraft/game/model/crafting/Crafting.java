package com.minecraft.game.model.crafting;

import java.util.HashMap;
import java.io.*;

public class Crafting {

    private int selectedRow;
    private int selectedCol;
    private Inventory inventory;
    private Item[][] table;
    private Item[][] craftableItems;
    private HashMap<Item[][], Item> recipeTable;
    boolean open;

    public Crafting(Inventory inventory) {
        this.inventory = inventory;
        this.selectedRow = 0;
        this.selectedCol = 0;
        clearCraftableItems();
        this.recipeTable = Item.getRecipeMap();
        updateCraftableItems();
        this.table = new Item[][] {
                { null, null, null },
                { null, null, null },
                { null, null, null }
        };
        updateCraftingTable();
        this.open = false;
    }

    private void addBlock(Item item, int row, int col) {
        table[row][col] = item;
    }

    private void clearCraftableItems() {
        this.craftableItems = new Item[5][10];
    }

    private void clearTable(boolean craft) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (craft) {
                    inventory.removeItem(table[row][col]);
                }
                addBlock(null, row, col);
            }
        }
    }

    /** Crafts the currently selected item in the craftable items section */
    public void craft() {
        clearTable(true);
        inventory.addItem(getSelectedItem());
        updateCraftableItems();
    }

    /** Returms the currently selected item in the craftable items section */
    public Item getSelectedItem() {
        return craftableItems[selectedRow][selectedCol];
    }

    /** Returns the currently selected row in the craftable items section */
    public int getSelectedRow() {
        return selectedRow;
    }

    /** Returns the currently selected col in the craftable items section */
    public int getSelectedCol() {
        return selectedCol;
    }

    /** Returns the table of craftable items */
    public Item[][] getCraftableItems() {
        return craftableItems;
    }

    /** Returns the crafting table */
    public Item[][] getTable() {
        return table;
    }

    /** Checks if the crafting section is currently open */
    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = !open;
        updateCraftableItems();
    }

    private void updateCraftableItems() {
        clearCraftableItems();
        int freeRow = 0;
        int freeCol = 0;
        for (Item[][] recipe : recipeTable.keySet()) {
            Item item = recipeTable.get(recipe);
            if (canCraft(recipe)) {
                if (inventory.getAmount(item) != item.getMaxAmount()) {
                    craftableItems[freeRow][freeCol] = item;
                    freeCol++;
                    if (freeCol == 10) {
                        freeCol = 0;
                        freeRow++;
                    }
                }
            }
        }
    }

    private boolean canCraft(Item[][] recipe) {
        HashMap<Item, Integer> itemCount = new HashMap<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Item item = recipe[row][col];
                if (item != null) {
                    if (!inventory.contains(item)) {
                        return false;
                    } else {
                        addOrUpdateKey(itemCount, item);
                        if (inventory.getAmount(item) < itemCount.get(item)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private static void addOrUpdateKey(HashMap<Item, Integer> hashMap, Item key) {
        if (hashMap.containsKey(key)) {
            hashMap.put(key, hashMap.get(key) + 1);
        } else {
            hashMap.put(key, 1);
        }
    }

    private void updateCraftingTable() {
        if (craftableItems[selectedRow][selectedCol] != null) {
            Item item = craftableItems[selectedRow][selectedCol];
            clearTable(false);

            Item[][] recipe = item.getRecipe();
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (recipe[row][col] != null) {
                        addBlock(recipe[row][col], row, col);
                    }
                }
            }
        }

    }

    /** Moves the selector in the craftable items table
     * @param row the amount of rows to move
     * @param col the amount of cols to move
     */
    public void moveCraftableTableSelection(int row, int col) {
        if (selectedRow + row >= 0 && selectedRow + row < 5 && selectedCol + col >= 0 && selectedCol + col < 10) {
            selectedRow += row;
            selectedCol += col;
            updateCraftingTable();
        }
    }

}
