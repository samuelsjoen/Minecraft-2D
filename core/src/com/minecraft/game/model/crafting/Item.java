package com.minecraft.game.model.crafting;

import java.util.HashMap;

public enum Item {
    // Core blocks
    WOOD("wood", "A block of wood", "assets/inventoryItems/wood.png", 64, null, ItemType.BLOCK),
    GRASS("grass", "A block of grass", "assets/inventoryItems/grass.png", 64, null, ItemType.BLOCK),
    DIRT("dirt", "A block of dirt", "assets/inventoryItems/dirt.png", 64, null, ItemType.BLOCK),
    STONE("stone", "A block of stone", "assets/inventoryItems/stone.png", 64, null, ItemType.BLOCK),
    BEDROCK("bedrock", "A block of bedrock", "assets/inventoryItems/bedrock.png", 64, null, ItemType.BLOCK),
    DIAMOND_ORE("diamond_ore", "A block of diamond ore", "assets/inventoryItems/diamond_ore.png", 64, null, ItemType.BLOCK),
    IRON_ORE("iron_ore", "A block of iron ore", "assets/inventoryItems/iron_ore.png", 64, null, ItemType.BLOCK),
    STONE_SNOW("stone_snow", "A block of stone with snow", "assets/inventoryItems/stone_snow.png", 64, null, ItemType.BLOCK),
    DIRT_SNOW("dirt_snow", "A block of dirt with snow", "assets/inventoryItems/dirt_snow.png", 64, null, ItemType.BLOCK),
    LEAVES("leaves", "A block of leaves", "assets/inventoryItems/leaves.png", 64, null, ItemType.BLOCK),

    // Crafting items
    STICK("stick", "A wooden stick", "assets/inventoryItems/stick.png", 64, new Item[][] {
        {null, WOOD, null},
        {null, WOOD, null},
        {null, null, null}
    }, ItemType.MISC),
    WOODEN_SWORD("Sword", "A sharp sword for fighting", "assets/inventoryItems/sword.png", 1, new Item[][] {
        {null, WOOD, null},
        {null, WOOD, null},
        {null, STICK, null}
    }, ItemType.WEAPON),
    WOODEN_PICKAXE("Pickaxe", "A pickaxe for mining", "assets/inventoryItems/pickaxe.png", 1, new Item[][] {
        {WOOD, WOOD, WOOD},
        {null, Item.STICK, null},
        {null, Item.STICK, null}
    }, ItemType.TOOL),
    IRON_HELMET("iron helmet", "A helmet made of iron", "assets/inventoryItems/iron_helmet.png", 1, new Item[][] {
        {IRON_ORE, IRON_ORE, IRON_ORE},
        {IRON_ORE, null, IRON_ORE},
        {null, null, null}
    }, ItemType.HELMET),
    IRON_CHESTPLATE("iron chestplate", "A chestplate made of iron", "assets/inventoryItems/iron_chestplate.png", 1, new Item[][] {
        {IRON_ORE, null, IRON_ORE},
        {IRON_ORE, IRON_ORE, IRON_ORE},
        {IRON_ORE, IRON_ORE, IRON_ORE}
    }, ItemType.CHESTPLATE),
    IRON_GLOVES("iron gloves", "Gloves made of iron", "assets/inventoryItems/iron_gloves.png", 1, new Item[][] {
        {IRON_ORE, null, IRON_ORE},
        {null, null, null},
        {null, null, null}
    }, ItemType.GLOVES),
    IRON_LEGGINGS("iron leggings", "Leggings made of iron", "assets/inventoryItems/iron_leggings.png", 1, new Item[][] {
        {IRON_ORE, IRON_ORE, IRON_ORE},
        {IRON_ORE, null, IRON_ORE},
        {IRON_ORE, null, IRON_ORE}
    }, ItemType.LEGGINGS),
    IRON_BOOTS("iron boots", "Boots made of iron", "assets/inventoryItems/iron_boots.png", 1, new Item[][] {
        {null, null, null},
        {IRON_ORE, null, IRON_ORE},
        {IRON_ORE, null, IRON_ORE}
    }, ItemType.BOOTS),
    ;

    private final String name;
    private final String description;
    private final String texture;
    private final int maxAmount;
    private final Item[][] recipe;
    private final ItemType type;

    Item(String name, String description, String texture, int maxAmount, Item[][] recipe, ItemType type) {
        this.name = name;
        this.description = description;
        this.texture = texture;
        this.maxAmount = maxAmount;
        this.recipe = recipe;
        this.type = type;
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


    // get the item with name
    public static Item getItemWithName(String name) {
        return itemMapName.get(name);
    }

    public static HashMap<Item[][], Item> getRecipeMap() {
        return recipeMap;
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

    public ItemType getType() {
        return type;
    }

}
