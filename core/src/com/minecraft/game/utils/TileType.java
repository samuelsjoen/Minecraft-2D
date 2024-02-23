package com.minecraft.game.utils;

import java.util.HashMap;

// Need to add damage to the tiles + documentation
public enum TileType {

    GRASS(1, true, "grass"),
    DIRT(2, true, "dirt"),
    COBBLESTONE(3, true, "cobblestone"),
    WATER(4, false, "water"),
    LAVA(5, false, "lava"),
    BEDROCK(6, true, "bedrock"),
    DIAMOND_ORE(7, true, "diamond_ore"),
    IRON_ORE(8, true, "iron_ore"),;
    
    private int id;
    private boolean collidable;
    private String textureName;
    private float damage;

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
    private static HashMap<Integer, TileType> tileMap;

    // Static initializer
    static {
        tileMap = new HashMap<Integer, TileType>();
        // Iterating through the tiletypes
        for (TileType tileType : TileType.values()) {
            tileMap.put(tileType.getId(), tileType);
        }
    }

    // Get the tiletype with id
    public static TileType getTileTypeWithId(int id) {
        return tileMap.get(id);
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
