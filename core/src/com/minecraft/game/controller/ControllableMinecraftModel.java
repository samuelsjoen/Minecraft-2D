package com.minecraft.game.controller;

import com.minecraft.game.model.GameState;

/**
 * The ControllableMinecraftModel interface represents a controllable Minecraft model.
 * This interface is a connection between the controller and the model.
 * It provides methods to interact with the game state, inventory, player, and tiles/blocks.
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
     * Changes the inventory slot to the slot adjacent to the currently selected slot.
     *
     * @param direction The direction to change the inventory slot. Use -1 for left and +1 for right.
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
     * Moves the player to the right.
     */
    void movePlayerRight();

    /**
     * Moves the player to the left.
     */
    void movePlayerLeft();

    /**
     * Makes the player jump.
     */
    void playerJump();

    /**
     * Makes the player attack.
     */
    void playerAttack();

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

    float getTileDamage(int tileX, int tileY);
}
