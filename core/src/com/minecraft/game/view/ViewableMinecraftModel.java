package com.minecraft.game.view;

import com.minecraft.game.model.DayNightCycle;
import com.minecraft.game.model.GameState;
import com.minecraft.game.model.Player;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.entities.EntityModel;
import com.minecraft.game.model.items.ArmorInventory;
import com.minecraft.game.model.items.Crafting;
import com.minecraft.game.model.items.Inventory;
import com.minecraft.game.model.Score;

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

    /**
     * Check if the game is over and update the game state accordingly.
     */
    void checkAndUpdateGameState(); 

    /**
     * Get the armor inventory
     * @return the armor inventory
     */
    ArmorInventory getArmorInventory();

    /**
     * Get the entity model
     * @return the entity model
     */
    EntityModel getEntityModel();

    /**
     * Get the player's score-class
     * @return the player's score-class
     */
    Score getScore();
}
