package com.minecraft.game.model.crafting;

public enum Recipe {
    EMPTY(new Item[][] {
        {null, null, null},
        {null, null, null},
        {null, null, null}
    }),
    STICK(new Item[][] {
        {null, Item.PLANK, null},
        {null, Item.PLANK, null},
        {null, null, null}
    }),
    WOODEN_SWORD(new Item[][] {
        {null, Item.PLANK, null},
        {null, Item.PLANK, null},
        {null, Item.STICK, null}
    }),
    STONE_SWORD(new Item[][] {
        {null, Item.STONE, null},
        {null, Item.STONE, null},
        {null, Item.STICK, null}
    }),
    WOODEN_PICKAXE(new Item[][] {
        {Item.PLANK, Item.PLANK, Item.PLANK},
        {null, Item.STICK, null},
        {null, Item.STICK, null}
    }),
    STONE_PICKAXE(new Item[][] {
        {Item.STONE, Item.STONE, Item.STONE},
        {null, Item.STICK, null},
        {null, Item.STICK, null}
    });

    private final Item[][] recipe;

    Recipe(Item[][] recipe) {
        this.recipe = recipe;
    }

    public Item[][] getRecipe() {
        return recipe;
    }
}
