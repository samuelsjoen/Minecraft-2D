package com.minecraft.game.model.crafting;

import java.util.ArrayList;
import java.util.HashMap;

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
        // this.table = new Item[][] {
        // {Item.DIAMOND_ORE, Item.DIRT, Item.DIRT_SNOW},
        // {Item.LEAVES, Item.BEDROCK, Item.DIAMOND_ORE},
        // {Item.WOODEN_SWORD, Item.STONE, Item.STONE_SNOW}
        // };
        this.selectedRow = 0;
        this.selectedCol = 0;
        this.craftableItems = new Item[5][10];
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

    public void addBlock(Item item, int row, int col) {
        table[row][col] = item;
        inventory.removeItem(item);
    }

    public void removeBlock(int row, int col, boolean keepItem) {
        Item item = table[row][col];
        addBlock(null, row, col);
        if (keepItem == true && item != null) {
            inventory.addItem(item);
        }
    }

    public void clearTable(boolean keepItem) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                removeBlock(row, col, keepItem);
            }
        }
    }

    public boolean isValidRecipe() {
        Item item = recipeTable.getOrDefault(table, null);
        return item != null;
    }

    public void craft() {
        clearTable(false);
        inventory.addItem(getSelectedItem());
        updateCraftableItems();
    }

    public Item[][] getTable() {
        return table;
    }

    public int getSelectedRow() {
        return selectedRow;
    }
    
    public int getSelectedCol() {
        return selectedCol;
    }

    public Item getSelectedItem() {
        return craftableItems[selectedRow][selectedCol];
    }

    public void open() {
        open = !open;
    }

    public boolean isOpen() {
        return open;
    }

    public void updateCraftableItems() {
        int freeRow = 0;
        int freeCol = 0;
        for (Item[][] recipe : recipeTable.keySet()) {
            Item item = recipeTable.get(recipe);
            if (canCraft(recipe)) {
                craftableItems[freeRow][freeCol] = item;
                freeCol++;
                if (freeCol == 10) {
                    freeCol = 0;
                    freeRow++;
                }
            }
        }
    }

    private boolean canCraft(Item[][] recipe) {
        Inventory tempInventory = inventory;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (recipe[row][col] != null) {
                    if (!tempInventory.contains(recipe[row][col])) {
                        return false;
                    } else {
                        tempInventory.removeItem(recipe[row][col]);
                    }
                }
            }
        }
        return true;
    }

    public void updateCraftingTable() {
        Item item = craftableItems[selectedRow][selectedCol];
        if (item == null) {
            clearTable(true);
        } else {
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

    public Item[][] getCraftableItems() {
        return craftableItems;
    }

    public void moveCraftableTableSelection(int row, int col) {
        if (selectedRow + row >= 0 && selectedRow + row < 5 && selectedCol + col >= 0 && selectedCol + col < 10) {
            selectedRow += row;
            selectedCol += col;
            updateCraftingTable();
        }
    }
}
