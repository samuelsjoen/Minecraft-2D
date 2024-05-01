package com.minecraft.game.controller;

import com.badlogic.gdx.Input.Keys;
import com.minecraft.game.model.GameState;

/**
 * The InventoryController class is responsible for handling input related to the player's inventory and crafting.
 */
public class InventoryController {

    private ControllableMinecraftModel controllableModel;

    /**
     * Constructs a new InventoryController with the specified controllable model.
     * @param controllableModel the controllable model for the Minecraft game
     */
    public InventoryController(ControllableMinecraftModel controllableModel) {
        this.controllableModel = controllableModel;
    }

    /**
     * Handle key down events for inventory and crafting actions
     * @param keycode the key code connected to the key
     * @return true if the key event was handled, false otherwise
     */
    public Boolean handleKeyDown(int keycode) {
        GameState gameState = controllableModel.getGameState();

        if (gameState == GameState.GAME_ACTIVE) {
            return handleGameActiveKeyDown(keycode);
        } else if (gameState == GameState.CRAFTING_SCREEN) {
            return handleCraftingScreenKeyDown(keycode);
        }        
        return false;
    }
    
    /**
     * Handle key down events for the game active state
     * @param keycode the key code connected to the key
     * @return true if the key event was handled, false otherwise
     */
    private Boolean handleGameActiveKeyDown(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                controllableModel.changeInventorySlot(-1);
                return true;
            
            case Keys.RIGHT:
                controllableModel.changeInventorySlot(1);
                return true;

            case Keys.E:
                controllableModel.toggleCrafting();
                controllableModel.setGameState(GameState.CRAFTING_SCREEN);
                return true;

            case Keys.Q:
                controllableModel.dropInventoryItem();
                return true; 

            default:
                return false;
        }
    }

    /**
     * Handle key down events for the crafting screen state
     * @param keycode the key code connected to the key
     * @return true if the key event was handled, false otherwise
     */
    private Boolean handleCraftingScreenKeyDown(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                controllableModel.moveCraftableTableSelection(0, -1);
                return true;
            
            case Keys.RIGHT:
                controllableModel.moveCraftableTableSelection(0, 1);
                return true;

            case Keys.ENTER:
                controllableModel.craftItem();
                return true;

            case Keys.UP:
                controllableModel.moveCraftableTableSelection(-1, 0);
                return true;

            case Keys.DOWN:
                controllableModel.moveCraftableTableSelection(1, 0);
                return true;

            case Keys.E:
                controllableModel.toggleCrafting();
                controllableModel.setGameState(GameState.GAME_ACTIVE);
                return true;
            
            case Keys.W:
                controllableModel.moveCraftableTableSelection(-1, 0);
                return true;
            
            case Keys.S:
                controllableModel.moveCraftableTableSelection(1, 0);
                return true;

            default:
                return false;
        }
    }
}

