package com.minecraft.game.model;

import java.util.HashMap;

public class Crafting {

    private Inventory inventory;
    private Item[][] table;
    private HashMap<Item[][], Item> recipeTable;

    public Crafting(Inventory inventory) {
        this.inventory = inventory;
        this.table = new Item[][] {
            {null, null, null},
            {null, null, null},
            {null, null, null}
        };
        this.recipeTable = Item.getRecipeMap();
    }

    public void addBlock(Item item, int row, int col) {
        table[row][col] = item;
        inventory.removeItem(item);
    }

    public void removeBlock(int row, int col, boolean keepItem) {
        Item item = table[row][col];
        addBlock(null, row, col);
        if (keepItem == true) {
            inventory.addItem(item);
        }
    }

    public void clearTable(boolean keepItem) {
        for (int row = 0; row < 3; row ++ ) {
            for (int col = 0; col < 3; col++ ) {
                removeBlock(row, col, keepItem);
            }
        }
    }

    public boolean isValidRecipe() {
        Item item = recipeTable.getOrDefault(table, null);
        return item != null;
    }

    public void craft(Item item) {
        clearTable(false);
        inventory.addItem(item);
    }
}
