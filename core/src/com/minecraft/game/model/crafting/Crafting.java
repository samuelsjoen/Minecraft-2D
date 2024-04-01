package com.minecraft.game.model.crafting;

import java.util.HashMap;

public class Crafting {

    private Inventory inventory;
    private Item[][] table;
    private HashMap<Item[][], Item> recipeTable;
    boolean open;

    public Crafting(Inventory inventory) {
        this.inventory = inventory;
        this.table = new Item[][] {
            {Item.DIAMOND_ORE, Item.DIRT, Item.DIRT_SNOW},
            {Item.LEAVES, Item.BEDROCK, Item.DIAMOND_ORE},
            {Item.WOODEN_SWORD, Item.STONE, Item.STONE_SNOW}
        };
        this.recipeTable = Item.getRecipeMap();
        this.open = false;
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

    public Item[][] getTable() {
        return table;
    }

    public void open() {
        open = !open;
    }

    public boolean isOpen() {
        return open;
    }
}
