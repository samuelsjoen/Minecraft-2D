package com.minecraft.game.model;

import java.util.HashMap;

public enum Item {
    SWORD(89, "Sword", "A sharp sword for fighting", "assets/inventoryItems/sword.png", 1),
    PICKAXE(38, "Pickaxe", "A pickaxe for mining", "assets/inventoryItems/pickaxe.png", 1),
    GRASS(1, "grass", "A block of grass", "assets/inventoryItems/grass.png", 64),
    DIRT(2, "Dirt", "A block of dirt", "assets/inventoryItems/dirt.png", 64),
    STONE(3, "Stone", "A block of stone", "assets/inventoryItems/stone.png", 64),
    BEDROCK(6, "bedrock", "A block of bedrock", "assets/inventoryItems/bedrock.png", 64),
    DIAMOND_ORE(7, "diamond_ore", "A block of diamond ore", "assets/inventoryItems/diamond_ore.png", 64),
    IRON_ORE(8, "iron_ore", "A block of iron ore", "assets/inventoryItems/iron_ore.png", 64),
    STONE_SNOW(11, "stone_snow", "A block of stone with snow", "assets/inventoryItems/stone_snow.png", 64),
    DIRT_SNOW(12, "dirt_snow", "A block of dirt with snow", "assets/inventoryItems/dirt_snow.png", 64),
    LEAVES(13, "leaves", "A block of leaves", "assets/inventoryItems/leaves.png", 64),
    WOOD(14, "wood", "A block of wood", "assets/inventoryItems/wood.png", 64);

    private final int id;
    private final String name;
    private final String description;
    private final String texture;
    private final int maxAmount;

    Item(int id, String name, String description, String texture, int maxAmount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.texture = texture;
        this.maxAmount = maxAmount;
    }

    // static initializer
    // storing the item constants in a hashmap
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTexture() {
        return texture;
    }

    public int getId() {
        return id;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
    
}
