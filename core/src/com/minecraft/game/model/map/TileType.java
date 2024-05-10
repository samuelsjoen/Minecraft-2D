package com.minecraft.game.model.map;

import java.util.HashMap;

import com.minecraft.game.model.items.Item;
import com.minecraft.game.model.items.ItemMaterial;

/**
 * Represents the types of tiles in the game.
 */
public enum TileType {

    GRASS(1, true, "Grass", 0.5f),
    DIRT(2, true, "Dirt", 1),
    STONE(3, true, "Stone", 2),
    BEDROCK(6, true, "Bedrock", 100),
    DIAMOND_ORE(7, true, "Diamond ore", 5),
    IRON_ORE(8, true, "Iron ore", 5),
    STONE_SNOW(11, true, "Snow covered stone", 3),
    DIRT_SNOW(12, true, "Snow covered dirt", 2),
    LEAVES(13, false, "Leaves", 0.5f),
    WOOD(14, false, "Wood", 1);

    private final int id;
    private final boolean collidable;
    private final String textureName;
    private final float baseDamage;

    /**
     * Constructs a TileType with the specified parameters.
     *
     * @param id           the ID of the tile type
     * @param collidable   whether the tile is collidable or not
     * @param textureName  the name of the texture associated with the tile
     * @param damage       the damage value of the tile
     */
    TileType(int id, boolean collidable, String textureName, float baseDamage) {
        this.id = id;
        this.collidable = collidable;
        this.textureName = textureName;
        this.baseDamage = baseDamage;
    }

    /**
     * Constructs a TileType with the specified parameters and a default damage value of 0.
     *
     * @param id           the ID of the tile type
     * @param collidable   whether the tile is collidable or not
     * @param textureName  the name of the texture associated with the tile
     */
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

    /**
     * Returns the TileType with the specified ID.
     *
     * @param id  the ID of the tile type
     * @return    the TileType with the specified ID, or null if not found
     */
    public static TileType getTileTypeWithId(int id) {
        return tileMap.get(id);
    }

    /**
     * Returns the TileType with the specified name.
     *
     * @param name  the name of the tile type
     * @return      the TileType with the specified name, or null if not found
     */
    public static TileType getTileTypeWithName(String name) {
        return tileMapName.get(name);
    }

    /**
     * Returns the ID of the tile type.
     *
     * @return the ID of the tile type
     */
    public int getId() {
        return id;
    }

    /**
     * Returns whether the tile is collidable or not.
     *
     * @return true if the tile is collidable, false otherwise
     */
    public boolean isCollidable() {
        return collidable;
    }

    /**
     * Returns the name of the texture associated with the tile.
     *
     * @return String of the texture name
     */
    public String getTextureName() {
        return textureName;
    }

    /**
     * Returns the base damage value of the tile.
     *
     * @return float value of the base damage
     */
    public float getBaseDamage() {
        return baseDamage;
    }

    /**
     * Returns the damage value of the tile.
     *
     * @return float value of the damage
     */
    public float getDamage(Item pickaxe) {
        ItemMaterial pickaxeMaterial = pickaxe.getMaterial();
        switch (pickaxeMaterial) {
            case WOOD:
                return baseDamage / 1.5f;
            case STONE:
                return baseDamage / 2.0f;
            case IRON:
                return baseDamage / 3.0f;
            case DIAMOND:
                return baseDamage / 4.0f;
            default:
                return baseDamage;
        }
    }

}
