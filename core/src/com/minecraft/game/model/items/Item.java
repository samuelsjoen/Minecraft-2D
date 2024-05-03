package com.minecraft.game.model.items;

import java.util.HashMap;

/**
 * The enum represents different items in the game.
 */
public enum Item {
    // 1. Core blocks
    WOOD("Wood", "A block of wood", "inventoryItems/wood.png", 64, null, ItemType.BLOCK, null),
    GRASS("Grass", "A block of grass", "inventoryItems/grass.png", 64, null, ItemType.BLOCK, null),
    DIRT("Dirt", "A block of dirt", "inventoryItems/dirt.png", 64, null, ItemType.BLOCK, null),
    STONE("Stone", "A block of stone", "inventoryItems/stone.png", 64, null, ItemType.BLOCK, null),
    BEDROCK("Bedrock", "A block of bedrock", "inventoryItems/bedrock.png", 64, null, ItemType.BLOCK, null),
    DIAMOND_ORE("Diamond ore", "A block of diamond ore", "inventoryItems/diamond_ore.png", 64, null,
            ItemType.BLOCK, null),
    IRON_ORE("Iron ore", "A block of iron ore", "inventoryItems/iron_ore.png", 64, null, ItemType.BLOCK, null),
    STONE_SNOW("Snow covered stone", "A block of stone covered with snow", "inventoryItems/stone_snow.png", 64,
            null, ItemType.BLOCK, null),
    DIRT_SNOW("Snow covered dirt", "A block of dirt covered with dirt", "inventoryItems/dirt_snow.png", 64, null,
            ItemType.BLOCK, null),
    LEAVES("Leaves", "A block of leaves", "inventoryItems/leaves.png", 64, null, ItemType.BLOCK, null),

    // 2. Crafting items
    // 2.0 Misc
    STICK("Stick", "A wooden stick", "inventoryItems/stick.png", 64, new Item[][] {
            { null, WOOD, null },
            { null, WOOD, null },
            { null, null, null }
    }, ItemType.MISC, null),

    // 2.1 Tools and weapons
    WOODEN_SWORD("Wooden sword", "A weak sword for fighting", "inventoryItems/wooden_sword.png", 1,
            new Item[][] {
                    { null, WOOD, null },
                    { null, WOOD, null },
                    { null, STICK, null }
            }, ItemType.SWORD, ItemMaterial.WOOD),
    WOODEN_PICKAXE("Wooden pickaxe", "A weak pickaxe for mining", "inventoryItems/wooden_pickaxe.png", 1,
            new Item[][] {
                    { WOOD, WOOD, WOOD },
                    { null, STICK, null },
                    { null, STICK, null }
            }, ItemType.PICKAXE, ItemMaterial.WOOD),
    IRON_SWORD("Iron sword", "A decent sword for fighting", "inventoryItems/iron_sword.png", 1, new Item[][] {
            { null, IRON_ORE, null },
            { null, IRON_ORE, null },
            { null, STICK, null },
    }, ItemType.SWORD, ItemMaterial.IRON),
    IRON_PICKAXE("Iron pickaxe", "A decent pickaxe for mining", "inventoryItems/iron_pickaxe.png", 1,
            new Item[][] {
                    { IRON_ORE, IRON_ORE, IRON_ORE },
                    { null, STICK, null },
                    { null, STICK, null }
            }, ItemType.PICKAXE, ItemMaterial.IRON),
    DIAMOND_SWORD("Diamond sword", "A powerful sword for fighting", "inventoryItems/diamond_sword.png", 1,
            new Item[][] {
                    { null, DIAMOND_ORE, null },
                    { null, DIAMOND_ORE, null },
                    { null, STICK, null }
            }, ItemType.SWORD, ItemMaterial.DIAMOND),
    DIAMOND_PICKAXE("Diamond pickaxe", "A powerful pickaxe for mining", "inventoryItems/diamond_pickaxe.png", 1,
            new Item[][] {
                    { DIAMOND_ORE, DIAMOND_ORE, DIAMOND_ORE },
                    { null, STICK, null },
                    { null, STICK, null }
            }, ItemType.PICKAXE, ItemMaterial.DIAMOND),

    // 2.2 Armor
    IRON_HELMET("Iron helmet", "A decent helmet for protection", "inventoryItems/iron_helmet.png", 1,
            new Item[][] {
                    { IRON_ORE, IRON_ORE, IRON_ORE },
                    { IRON_ORE, null, IRON_ORE },
                    { null, null, null }
            }, ItemType.HELMET, ItemMaterial.IRON),
    IRON_CHESTPLATE("Iron chestplate", "A decent chestplate for protection",
            "inventoryItems/iron_chestplate.png", 1, new Item[][] {
                    { IRON_ORE, null, IRON_ORE },
                    { IRON_ORE, IRON_ORE, IRON_ORE },
                    { IRON_ORE, IRON_ORE, IRON_ORE }
            }, ItemType.CHESTPLATE, ItemMaterial.IRON),
    IRON_GLOVES("Iron gloves", "A decent set of gloves for protection", "inventoryItems/iron_gloves.png", 1,
            new Item[][] {
                    { IRON_ORE, null, IRON_ORE },
                    { null, null, null },
                    { null, null, null }
            }, ItemType.GLOVES, ItemMaterial.IRON),
    IRON_LEGGINGS("Iron leggings", "A decent pair of leggings for protection",
            "inventoryItems/iron_leggings.png", 1, new Item[][] {
                    { IRON_ORE, IRON_ORE, IRON_ORE },
                    { IRON_ORE, null, IRON_ORE },
                    { IRON_ORE, null, IRON_ORE }
            }, ItemType.LEGGINGS, ItemMaterial.IRON),
    IRON_BOOTS("Iron boots", "A decent pair of boots for protection", "inventoryItems/iron_boots.png", 1,
            new Item[][] {
                    { null, null, null },
                    { IRON_ORE, null, IRON_ORE },
                    { IRON_ORE, null, IRON_ORE }
            }, ItemType.BOOTS, ItemMaterial.IRON),
    DIAMOND_HELMET("Diamond helmet", "A strong helmet for protection", "inventoryItems/diamond_helmet.png", 1,
            new Item[][] {
                    { DIAMOND_ORE, DIAMOND_ORE, DIAMOND_ORE },
                    { DIAMOND_ORE, null, DIAMOND_ORE },
                    { null, null, null }
            }, ItemType.HELMET, ItemMaterial.DIAMOND),
    DIAMOND_CHESTPLATE("Diamond chestplate", "A strong chestplate for protection",
            "inventoryItems/diamond_chestplate.png", 1, new Item[][] {
                    { DIAMOND_ORE, null, DIAMOND_ORE },
                    { DIAMOND_ORE, DIAMOND_ORE, DIAMOND_ORE },
                    { DIAMOND_ORE, DIAMOND_ORE, DIAMOND_ORE }
            }, ItemType.CHESTPLATE, ItemMaterial.DIAMOND),
    DIAMOND_GLOVES("Diamond gloves", "A strong set of gloves for protection",
            "inventoryItems/diamond_gloves.png", 1, new Item[][] {
                    { DIAMOND_ORE, null, DIAMOND_ORE },
                    { null, null, null },
                    { null, null, null }
            }, ItemType.GLOVES, ItemMaterial.DIAMOND),
    DIAMOND_LEGGINGS("Diamond leggings", "A strong pair of leggings for protection",
            "inventoryItems/diamond_leggings.png", 1, new Item[][] {
                    { DIAMOND_ORE, DIAMOND_ORE, DIAMOND_ORE },
                    { DIAMOND_ORE, null, DIAMOND_ORE },
                    { DIAMOND_ORE, null, DIAMOND_ORE }
            }, ItemType.LEGGINGS, ItemMaterial.DIAMOND),
    DIAMOND_BOOTS("Diamond boots", "A strong pair of boots for protection", "inventoryItems/diamond_boots.png",
            1, new Item[][] {
                    { null, null, null },
                    { DIAMOND_ORE, null, DIAMOND_ORE },
                    { DIAMOND_ORE, null, DIAMOND_ORE }
            }, ItemType.BOOTS, ItemMaterial.DIAMOND),
            ;

    private final String name;
    private final String description;
    private final String texture;
    private final int maxAmount;
    private final Item[][] recipe;
    private final ItemType type;
    private final ItemMaterial material;

/**
 * Constructs a new Item object with the specified parameters.
 *
 * @param name        the name of the item
 * @param description the description of the item
 * @param texture     the texture of the item
 * @param maxAmount   the maximum amount of the item that can be held
 * @param recipe      the recipe required to craft the item
 * @param type        the type of the item
 * @param material    the material of the item
 */
    Item(String name, String description, String texture, int maxAmount, Item[][] recipe, ItemType type,
            ItemMaterial material) {
        this.name = name;
        this.description = description;
        this.texture = texture;
        this.maxAmount = maxAmount;
        this.recipe = recipe;
        this.type = type;
        this.material = material;
    }

    private static HashMap<String, Item> itemMapName;
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

    /** Returns the whole item based on it's name */
    public static Item getItemWithName(String name) {
        return itemMapName.get(name);
    }

    /** Returns a map of all recipes and the return item of said recipe */
    public static HashMap<Item[][], Item> getRecipeMap() {
        return recipeMap;
    }

    /** Returns the name of the item */
    public String getName() {
        return name;
    }

    /** Returns the description of the item */
    public String getDescription() {
        return description;
    }

    /** Returns the texture of the item */
    public String getTexture() {
        return texture;
    }

    /** Returns the max amount you can carry of an item in the inventory */
    public int getMaxAmount() {
        return maxAmount;
    }

    /** Returns the recipe for the item */
    public Item[][] getRecipe() {
        return recipe;
    }

    /** Returns the type of the item */
    public ItemType getType() {
        return type;
    }

    /** Returns the material of the item */
    public ItemMaterial getMaterial() {
        return material;
    }

}
