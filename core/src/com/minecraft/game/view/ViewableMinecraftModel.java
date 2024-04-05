package com.minecraft.game.view;

import com.minecraft.game.model.DayNightCycle;
import com.minecraft.game.model.GameState;
import com.minecraft.game.model.Player;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.crafting.ArmorInventory;
import com.minecraft.game.model.crafting.Crafting;
import com.minecraft.game.model.crafting.Inventory;

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

    /**
     * Returns the armor inventory.
     * @return the player's armor inventory
     */
    ArmorInventory getArmorInventory();

    /**
     * Represents the crafting system in the Minecraft game.
     * This class provides methods to interact with the crafting system,
     * such as retrieving the current crafting recipe.
     */
    Crafting getCrafting();

    /**
     * Get the DayNightCycle object
     * @return the DayNightCycle object
     */
    DayNightCycle getDayNightCycle();

}
