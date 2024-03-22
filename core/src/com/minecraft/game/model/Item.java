package com.minecraft.game.model;

import java.util.HashMap;

public enum Item {
    SWORD("Sword", "A sharp sword for fighting", "assets/inventoryItems/sword.png", 1),
    PICKAXE("Pickaxe", "A pickaxe for mining", "assets/inventoryItems/pickaxe.png", 1),
    GRASS("grass", "A block of grass", "assets/inventoryItems/grass.png", 64),
    DIRT("dirt", "A block of dirt", "assets/inventoryItems/dirt.png", 64),
    STONE("stone", "A block of stone", "assets/inventoryItems/stone.png", 64),
    BEDROCK("bedrock", "A block of bedrock", "assets/inventoryItems/bedrock.png", 64),
    DIAMOND_ORE("diamond_ore", "A block of diamond ore", "assets/inventoryItems/diamond_ore.png", 64),
    IRON_ORE("iron_ore", "A block of iron ore", "assets/inventoryItems/iron_ore.png", 64),
    STONE_SNOW("stone_snow", "A block of stone with snow", "assets/inventoryItems/stone_snow.png", 64),
    DIRT_SNOW("dirt_snow", "A block of dirt with snow", "assets/inventoryItems/dirt_snow.png", 64),
    LEAVES("leaves", "A block of leaves", "assets/inventoryItems/leaves.png", 64),
    WOOD("wood", "A block of wood", "assets/inventoryItems/wood.png", 64);

    private final String name;
    private final String description;
    private final String texture;
    private final int maxAmount;

    Item(String name, String description, String texture, int maxAmount) {
        this.name = name;
        this.description = description;
        this.texture = texture;
        this.maxAmount = maxAmount;
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

    // get the item with name
    public static Item getItemWithName(String name) {
        return itemMapName.get(name);
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

}
