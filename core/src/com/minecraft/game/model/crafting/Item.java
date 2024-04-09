package com.minecraft.game.model.crafting;

import java.util.HashMap;

public enum Item {
    // 1. Core blocks
    WOOD("wood", "A block of wood", "assets/inventoryItems/wood.png", 64, null, ItemType.BLOCK, null),
    GRASS("grass", "A block of grass", "assets/inventoryItems/grass.png", 64, null, ItemType.BLOCK, null),
    DIRT("dirt", "A block of dirt", "assets/inventoryItems/dirt.png", 64, null, ItemType.BLOCK, null),
    STONE("stone", "A block of stone", "assets/inventoryItems/stone.png", 64, null, ItemType.BLOCK, null),
    BEDROCK("bedrock", "A block of bedrock", "assets/inventoryItems/bedrock.png", 64, null, ItemType.BLOCK, null),
    DIAMOND_ORE("diamond_ore", "A block of diamond ore", "assets/inventoryItems/diamond_ore.png", 64, null, ItemType.BLOCK, null),
    IRON_ORE("iron_ore", "A block of iron ore", "assets/inventoryItems/iron_ore.png", 64, null, ItemType.BLOCK, null),
    STONE_SNOW("stone_snow", "A block of stone with snow", "assets/inventoryItems/stone_snow.png", 64, null, ItemType.BLOCK, null),
    DIRT_SNOW("dirt_snow", "A block of dirt with snow", "assets/inventoryItems/dirt_snow.png", 64, null, ItemType.BLOCK, null),
    LEAVES("leaves", "A block of leaves", "assets/inventoryItems/leaves.png", 64, null, ItemType.BLOCK, null),

    // 2. Crafting items
    STICK("stick", "A wooden stick", "assets/inventoryItems/stick.png", 64, new Item[][] {
        {null, WOOD, null},
        {null, WOOD, null},
        {null, null, null}
    }, ItemType.MISC, null),
    // 2.1 Tools and weapons
    WOODEN_SWORD("Sword", "A sharp sword for fighting", "assets/inventoryItems/wooden_sword.png", 1, new Item[][] {
        {null, WOOD, null},
        {null, WOOD, null},
        {null, STICK, null}
    }, ItemType.SWORD, ItemMaterial.WOOD),
    WOODEN_PICKAXE("Pickaxe", "A pickaxe for mining", "assets/inventoryItems/wooden_pickaxe.png", 1, new Item[][] {
        {WOOD, WOOD, WOOD},
        {null, STICK, null},
        {null, STICK, null}
    }, ItemType.PICKAXE, ItemMaterial.WOOD),
    IRON_SWORD("Iron sword", "A sword made of iron", "assets/inventoryItems/iron_sword.png", 1, new Item[][] {
        {IRON_ORE, IRON_ORE, IRON_ORE},
        {null, STICK, null},
        {null, STICK, null},
    }, ItemType.SWORD, ItemMaterial.IRON),
    IRON_PICKAXE("Iron pickaxe", "A pickaxe made of iron", "assets/inventoryItems/iron_pickaxe.png", 1, new Item[][] {
        {IRON_ORE, IRON_ORE, IRON_ORE},
        {null, STICK, null},
        {null, STICK, null}
    }, ItemType.PICKAXE, ItemMaterial.IRON),
    DIAMOND_SWORD("Diamond sword", "A sword made of diamond", "assets/inventoryItems/diamond_sword.png", 1, new Item[][] {
        {null, DIAMOND_ORE, null},
        {null, DIAMOND_ORE, null},
        {null, STICK, null}
    }, ItemType.SWORD, ItemMaterial.DIAMOND),
    DIAMOND_PICKAXE("Diamond pickaxe", "A pickaxe made of diamond", "assets/inventoryItems/diamond_pickaxe.png", 1, new Item[][] {
        {DIAMOND_ORE, DIAMOND_ORE, DIAMOND_ORE},
        {null, STICK, null},
        {null, STICK, null}
    }, ItemType.PICKAXE, ItemMaterial.DIAMOND),
    // 2.2 Armor
    IRON_HELMET("iron helmet", "A helmet made of iron", "assets/inventoryItems/iron_helmet.png", 1, new Item[][] {
        {IRON_ORE, IRON_ORE, IRON_ORE},
        {IRON_ORE, null, IRON_ORE},
        {null, null, null}
    }, ItemType.HELMET, ItemMaterial.IRON),
    IRON_CHESTPLATE("iron chestplate", "A chestplate made of iron", "assets/inventoryItems/iron_chestplate.png", 1, new Item[][] {
        {IRON_ORE, null, IRON_ORE},
        {IRON_ORE, IRON_ORE, IRON_ORE},
        {IRON_ORE, IRON_ORE, IRON_ORE}
    }, ItemType.CHESTPLATE, ItemMaterial.IRON),
    IRON_GLOVES("iron gloves", "Gloves made of iron", "assets/inventoryItems/iron_gloves.png", 1, new Item[][] {
        {IRON_ORE, null, IRON_ORE},
        {null, null, null},
        {null, null, null}
    }, ItemType.GLOVES, ItemMaterial.IRON),
    IRON_LEGGINGS("iron leggings", "Leggings made of iron", "assets/inventoryItems/iron_leggings.png", 1, new Item[][] {
        {IRON_ORE, IRON_ORE, IRON_ORE},
        {IRON_ORE, null, IRON_ORE},
        {IRON_ORE, null, IRON_ORE}
    }, ItemType.LEGGINGS, ItemMaterial.IRON),
    IRON_BOOTS("iron boots", "Boots made of iron", "assets/inventoryItems/iron_boots.png", 1, new Item[][] {
        {null, null, null},
        {IRON_ORE, null, IRON_ORE},
        {IRON_ORE, null, IRON_ORE}
    }, ItemType.BOOTS, ItemMaterial.IRON),
    DIAMOND_HELMET("diamond helmet", "A helmet made of diamond", "assets/inventoryItems/diamond_helmet.png", 1, new Item[][] {
        {DIAMOND_ORE, DIAMOND_ORE, DIAMOND_ORE},
        {DIAMOND_ORE, null, DIAMOND_ORE},
        {null, null, null}
    }, ItemType.HELMET, ItemMaterial.DIAMOND),
    DIAMOND_CHESTPLATE("diamond chestplate", "A chestplate made of diamond", "assets/inventoryItems/diamond_chestplate.png", 1, new Item[][] {
        {DIAMOND_ORE, null, DIAMOND_ORE},
        {DIAMOND_ORE, DIAMOND_ORE, DIAMOND_ORE},
        {DIAMOND_ORE, DIAMOND_ORE, DIAMOND_ORE}
    }, ItemType.CHESTPLATE, ItemMaterial.DIAMOND),
    DIAMOND_GLOVES("diamond gloves", "Gloves made of diamond", "assets/inventoryItems/diamond_gloves.png", 1, new Item[][] {
        {DIAMOND_ORE, null, DIAMOND_ORE},
        {null, null, null},
        {null, null, null}
    }, ItemType.GLOVES, ItemMaterial.DIAMOND),
    DIAMOND_LEGGINGS("diamond leggings", "Leggings made of diamond", "assets/inventoryItems/diamond_leggings.png", 1, new Item[][] {
        {DIAMOND_ORE, DIAMOND_ORE, DIAMOND_ORE},
        {DIAMOND_ORE, null, DIAMOND_ORE},
        {DIAMOND_ORE, null, DIAMOND_ORE}
    }, ItemType.LEGGINGS, ItemMaterial.DIAMOND),
    DIAMOND_BOOTS("diamond boots", "Boots made of diamond", "assets/inventoryItems/diamond_boots.png", 1, new Item[][] {
        {null, null, null},
        {DIAMOND_ORE, null, DIAMOND_ORE},
        {DIAMOND_ORE, null, DIAMOND_ORE}
    }, ItemType.BOOTS, ItemMaterial.DIAMOND),
    ;

    private final String name;
    private final String description;
    private final String texture;
    private final int maxAmount;
    private final Item[][] recipe;
    private final ItemType type;
    private final ItemMaterial material;

    Item(String name, String description, String texture, int maxAmount, Item[][] recipe, ItemType type, ItemMaterial material) {
        this.name = name;
        this.description = description;
        this.texture = texture;
        this.maxAmount = maxAmount;
        this.recipe = recipe;
        this.type = type;
        this.material = material;
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

    public ItemMaterial getMaterial() {
        return material;
    }

}
