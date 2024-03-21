package com.minecraft.game.model.map;

import java.util.HashMap;

// Need to add damage to the tiles + documentation
public enum TileType {

    GRASS(1, true, "grass", 0.5f),
    DIRT(2, true, "dirt", 1),
    STONE(3, true, "stone", 2),
    WATER(4, false, "water", 0),
    LAVA(5, false, "lava", 0),
    BEDROCK(6, true, "bedrock", 100),
    DIAMOND_ORE(7, true, "diamond_ore", 5),
    IRON_ORE(8, true, "iron_ore", 5),
    SKY(9, false, "sky", 0),
    BLACK(10, false, "black", 0),
    STONE_SNOW(11, true, "stone_snow", 3),
    DIRT_SNOW(12, true, "dirt_snow", 3),
    LEAVES(13, false, "leaves", 0.5f),
    WOOD(14, false, "wood", 0.5f);

    private final int id;
    private final boolean collidable;
    private final String textureName;
    private final float damage;

    TileType(int id, boolean collidable, String textureName, float damage) {
        this.id = id;
        this.collidable = collidable;
        this.textureName = textureName;
        this.damage = damage;
    }

    TileType(int id, boolean collidable, String textureName) {
        this(id, collidable, textureName, 0);
    }

    // Storing the tiletypes
    private static final HashMap<Integer, TileType> tileMap;
    // Static initializer
    static {
        tileMap = new HashMap<Integer, TileType>();
        // Iterating through the tiletypes
        for (TileType tileType : TileType.values()) {
            tileMap.put(tileType.getId(), tileType);
        }
    }

    // Storing the tiletypes with name as key
    private static final HashMap<String, TileType> tileMapName;
    // Static initializer
    static {
        tileMapName = new HashMap<String, TileType>();
        for (TileType tileType : TileType.values()) {
            tileMapName.put(tileType.getTextureName(), tileType);
        }
    }

    // Get the tiletype with id
    public static TileType getTileTypeWithId(int id) {
        return tileMap.get(id);
    }

    // Get the tiletype with name
    public static TileType getTileTypeWithName(String name) {
        return tileMapName.get(name);
    }

    // Getters
    public int getId() {
        return id;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public String getTextureName() {
        return textureName;
    }

    public float getDamage() {
        return damage;
    }

}
