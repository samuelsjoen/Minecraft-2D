package com.minecraft.game.model.map;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Player;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * This interface represents a Minecraft map.
 */
public interface IMinecraftMap {

    /**
     * Sets up the map using the specified map path.
     *
     * @param mapPath the path to the map file
     * @return the renderer for the map
     */
    OrthogonalTiledMapRenderer setupMap(String mapPath);

    /**
     * Gets the world associated with the map.
     *
     * @return the world
     */
    World getWorld();

    /**
     * Gets the player associated with the map.
     *
     * @return the player
     */
    Player getPlayer();

    /**
     * Removes a block at the specified coordinates.
     *
     * @param x the x-coordinate of the block
     * @param y the y-coordinate of the block
     */
    void removeBlock(int x, int y);

    /**
     * Adds a block of the specified type at the specified coordinates.
     *
     * @param x         the x-coordinate of the block
     * @param y         the y-coordinate of the block
     * @param tileType  the type of the block
     */
    void addBlock(int x, int y, TileType tileType);

    /**
     * Gets the tiled map associated with the map.
     *
     * @return the tiled map
     */
    TiledMap getTiledMap();
}