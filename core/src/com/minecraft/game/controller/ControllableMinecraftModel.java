package com.minecraft.game.controller;

import com.minecraft.game.model.DayNightCycle;
import com.minecraft.game.model.GameState;
import com.minecraft.game.model.Player.State;

/**
 * The ControllableMinecraftModel interface represents a controllable Minecraft
 * model.
 * This interface is a connection between the controller and the model.
 * It provides methods to interact with the game state, inventory, player, and
 * tiles/blocks.
 */
public interface ControllableMinecraftModel {

    // GameState

    /**
     * Gets the current game state.
     *
     * @return The current game state.
     */
    GameState getGameState();

    /**
     * Sets the game state.
     *
     * @param state The new game state.
     */
    void setGameState(GameState state);

    // Inventory

    /**
     * Changes the inventory slot to the slot adjacent to the currently selected
     * slot.
     *
     * @param direction The direction to change the inventory slot. Use -1 for left
     *                  and +1 for right.
     */
    void changeInventorySlot(int direction);

    /**
     * Drops the currently selected inventory item.
     */
    void dropInventoryItem();

    // Player

    /**
     * Revives the player.
     */
    void revivePlayer();

    /**
     * Moves the player in the specified direction.
     * 
     * @param direction The direction to move the player. Use -1 for left and +1 for right.
     */
    void movePlayer(int direction);

    /**
     * Stops the player.
     */
    void stopPlayer();

    /**
     * Makes the player jump.
     */
    void playerJump();

    /**
     * Makes the player attack.
     */
    void playerAttack();

    /**
     * Returns the state of the player.
     * 
     * @return the state of the player
     */
    State getPlayerState();

    // Tiles/Blocks

    /**
     * Removes a block at the specified tile coordinates.
     *
     * @param tileX The x-coordinate of the tile.
     * @param tileY The y-coordinate of the tile.
     */
    void removeBlock(int tileX, int tileY);

    /**
     * Adds a block at the specified tile coordinates.
     *
     * @param tileX The x-coordinate of the tile.
     * @param tileY The y-coordinate of the tile.
     */
    void addBlock(int tileX, int tileY);

    /**
     * Returns the damage value of a tile at the specified coordinates.
     *
     * @param tileX the x-coordinate of the tile
     * @param tileY the y-coordinate of the tile
     * @return the damage value of the tile
     */
    float getTileDamage(int tileX, int tileY);

    /**
     * Restarts the game.
     */
    void restartGame();

    /**
     * Opens or closes the crafting overlay.
     */
    void toggleCrafting();

    /**
     * Get the DayNightCycle object
     * @return the DayNightCycle object
     */
    DayNightCycle getDayNightCycle();

    /**
     * Check if the block at the specified tile coordinates is mineable.
     * @param tileX
     * @param tileY
     * @return true if the block is mineable, false otherwise
     */
    boolean isBlockMineable(int tileX, int tileY);

}
