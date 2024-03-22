package com.minecraft.game.view;

import com.minecraft.game.model.GameState;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.model.Player;

/**
 * The ViewableMinecraftModel interface represents a viewable Minecraft model.
 * It provides access for the view to various components of the game, such as 
 * the game state, map renderer, tiled map, world, player, and inventory.
 */
public interface ViewableMinecraftModel {

    /**
     * Returns the current game state.
     *
     * @return the game state
     */
    GameState getGameState();

    /**
     * Returns the tiled map.
     *
     * @return the tiled map
     */
    TiledMap getTiledMap();

    /**
     * Returns the map renderer used to render the tiled map.
     *
     * @return the map renderer
     */
    OrthogonalTiledMapRenderer getMapRenderer();

    /**
     * Retrieves the world object.
     *
     * @return the world
     */
    World getWorld();

    /**
     * Returns the player object.
     *
     * @return the player object
     */
    Player getPlayer();

    /**
     * Returns the player's inventory.
     *
     * @return the player's inventory
     */
    Inventory getInventory();

    /*boolean getIsNight();*/

}
