package com.minecraft.game.model;

import java.util.HashMap;

public enum Item {
    PLANK(125, "plank", "A wooden plank", "assets/inventoryItems/plank.png", 64, null),
    STICK(124, "stick", "A wooden stick", "assets/inventoryItems/stick.png", 64, new Item[][] {
        {null, Item.PLANK, null},
        {null, Item.PLANK, null},
        {null, null, null}
    }),
    WOODEN_SWORD(89, "Sword", "A sharp sword for fighting", "assets/inventoryItems/sword.png", 1, new Item[][] {
        {null, PLANK, null},
        {null, PLANK, null},
        {null, STICK, null}
    }),
    WOODEN_PICKAXE(38, "Pickaxe", "A pickaxe for mining", "assets/inventoryItems/pickaxe.png", 1, new Item[][] {
        {Item.PLANK, Item.PLANK, Item.PLANK},
        {null, Item.STICK, null},
        {null, Item.STICK, null}
    }),
    GRASS(1, "grass", "A block of grass", "assets/inventoryItems/grass.png", 64, null),
    DIRT(2, "Dirt", "A block of dirt", "assets/inventoryItems/dirt.png", 64, null),
    STONE(3, "Stone", "A block of stone", "assets/inventoryItems/stone.png", 64, null),
    BEDROCK(6, "bedrock", "A block of bedrock", "assets/inventoryItems/bedrock.png", 64, null),
    DIAMOND_ORE(7, "diamond_ore", "A block of diamond ore", "assets/inventoryItems/diamond_ore.png", 64, null),
    IRON_ORE(8, "iron_ore", "A block of iron ore", "assets/inventoryItems/iron_ore.png", 64, null),
    STONE_SNOW(11, "stone_snow", "A block of stone with snow", "assets/inventoryItems/stone_snow.png", 64, null),
    DIRT_SNOW(12, "dirt_snow", "A block of dirt with snow", "assets/inventoryItems/dirt_snow.png", 64, null),
    LEAVES(13, "leaves", "A block of leaves", "assets/inventoryItems/leaves.png", 64, null),
    WOOD(14, "wood", "A block of wood", "assets/inventoryItems/wood.png", 64, null);

    private final int id;
    private final String name;
    private final String description;
    private final String texture;
    private final int maxAmount;
    private final Item[][] recipe;

    Item(int id, String name, String description, String texture, int maxAmount, Item[][] recipe) {
        this.name = name;
        this.description = description;
        this.texture = texture;
        this.maxAmount = maxAmount;
        this.recipe = recipe;
        this.id = id;
    }

    // Storing the items with name as key
    private static HashMap<String, Item> itemMapName;
    // Static initializer
    static {
        itemMapName = new HashMap<String, Item>();
        for (Item item : Item.values()) {
            itemMapName.put(item.getName(), item);
        }
    }

    private static HashMap<Item[][], Item> recipeMap;
    static {
        recipeMap = new HashMap<Item[][], Item>();
        for (Item item : Item.values()) {
            if (item.getRecipe() != null) {
                recipeMap.put(item.getRecipe(), item);
            }
        }
    }

    private static HashMap<Integer, Item> itemMap;
    static {
        itemMap = new HashMap<Integer, Item>();
        for (Item item : Item.values()) {
            itemMap.put(item.getId(), item);
        }
    }

    // get the item with id
    public static Item getItemWithId(int id) {
        return itemMap.get(id);
    }

    // get the item with name
    public static Item getItemWithName(String name) {
        return itemMapName.get(name);
    }

    public static HashMap<Item[][], Item> getRecipeMap() {
        return recipeMap;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTexture() {
        return texture;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
    
    public Item[][] getRecipe() {
        return recipe;
    }

}
