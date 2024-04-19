package com.minecraft.game.controller;

import com.badlogic.gdx.Input.Keys;
import com.minecraft.game.model.GameState;

public class InventoryController {

    private ControllableMinecraftModel controllableModel;

    public InventoryController(ControllableMinecraftModel controllableModel) {
        this.controllableModel = controllableModel;
    }

    public Boolean handleKeyDown(int keycode) {
        GameState gameState = controllableModel.getGameState();

        if (gameState == GameState.GAME_ACTIVE) {
            return handleGameActiveKeyDown(keycode);
        } else if (gameState == GameState.CRAFTING_SCREEN) {
            return handleCraftingScreenKeyDown(keycode);
        }        
        return false;
    }
    

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
                if (controllableModel.getGameState() == GameState.CRAFTING_SCREEN) {
                    controllableModel.moveCraftableTableSelection(-1, 0);
                }
                return true;
            
            case Keys.S:
                if (controllableModel.getGameState() == GameState.CRAFTING_SCREEN) {
                    controllableModel.moveCraftableTableSelection(1, 0);
                }

            default:
                return false;
        }
    }

}

