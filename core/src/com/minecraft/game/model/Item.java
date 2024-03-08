package com.minecraft.game.model;

import java.util.HashMap;

public enum Item {
    SWORD(89, "Sword", "A sharp sword for fighting", "assets/inventoryItems/sword.png"),
    PICKAXE(38, "Pickaxe", "A pickaxe for mining", "assets/inventoryItems/pickaxe.png"),
    GRASS(1, "grass", "A block of grass", "assets/inventoryItems/grass.png"),
    DIRT(2, "Dirt", "A block of dirt", "assets/inventoryItems/dirt.png"),
    STONE(3, "Stone", "A block of stone", "assets/inventoryItems/stone.png"),
    BEDROCK(6, "bedrock", "A block of bedrock", "assets/inventoryItems/grass.png"),
    DIAMOND_ORE(7, "diamond_ore", "A block of diamond ore", "assets/inventoryItems/grass.png"),
    IRON_ORE(8, "iron_ore", "A block of iron ore", "assets/inventoryItems/grass.png"),
    STONE_SNOW(11, "stone_snow", "A block of stone with snow", "assets/inventoryItems/grass.png"),
    DIRT_SNOW(12, "dirt_snow", "A block of dirt with snow", "assets/inventoryItems/grass.png"),
    LEAVES(13, "leaves", "A block of leaves", "assets/inventoryItems/grass.png"),
    WOOD(14, "wood", "A block of wood", "assets/inventoryItems/grass.png");

    private final int id;
    private final String name;
    private final String description;
    private final String texture;

    Item(int id, String name, String description, String texture) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.texture = texture;

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
}
