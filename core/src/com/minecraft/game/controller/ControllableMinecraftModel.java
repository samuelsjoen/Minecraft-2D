package com.minecraft.game.controller;

import com.minecraft.game.model.DayNightCycle;
import com.minecraft.game.model.GameState;
import com.minecraft.game.model.Player.State;

/**
 * The ControllableMinecraftModel interface represents a controllable Minecraft
 * model.
 * This interface is a connection between the controller and the model.
 * It provides methods for the controller to interact with the various components that are in the model.
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

    /**
     * Opens or closes the crafting table.
     */
    void toggleCrafting();

    /**
     * Moves the selection in the crafting overlay.
     * 
     * @param row how many rows to move
     * @param col how many cols to move
     */
    void moveCraftableTableSelection(int row, int col);

    /**
     * Crafts the selected item in the crafting overlay.
     */
    void craftItem();

    // Player

    /**
     * Changes the values based on if player should move. 
     * @param moveLeft boolean value for moving left
     * @param moveRight boolean value for moving right
     * @param isAttacking 
     */
    void updateMovement(boolean moveLeft, boolean moveRight, boolean isAttacking);

    /**
     * Makes the player jump.
     */
    void playerJump();

    /**
     * Returns the state of the player.
     * 
     * @return State object representing the state of the player.
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
     * Checks if a block at the specified tile coordinates is mineable.
     *
     * @param tileX The x-coordinate of the tile.
     * @param tileY The y-coordinate of the tile.
     * @return true if the block is mineable, false otherwise.
     */
    boolean isBlockMineable(int tileX, int tileY);

    // Game management

    /**
     * Restarts the game.
     */
    void restartGame();

    /**
     * Get the DayNightCycle object
     * 
     * @return the DayNightCycle object
     */
    DayNightCycle getDayNightCycle();

    /**
     * Kills all enemies on the screen (useful for debugging).
     */
    void killAllEntities();
}
