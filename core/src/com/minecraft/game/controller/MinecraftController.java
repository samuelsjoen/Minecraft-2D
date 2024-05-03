package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Buttons;

import com.minecraft.game.model.GameState;
import com.minecraft.game.view.MinecraftView;

/**
 * The MinecraftController class is responsible for controlling the game logic and user interactions in the Minecraft game.
 * It manages the model, view, and various controllers for player actions, block placement, inventory management,
 * and screen navigation.
 */
public class MinecraftController extends InputAdapter {

    private ControllableMinecraftModel controllableModel;
    private MinecraftView view;

    private PlayerController playerController;
    private BlockController blockController;
    private InventoryController inventoryController;
    private GameState lastGameState;

    /**
     * Constructs a new MinecraftController with the specified controllable model and view.
     * This controller manages user interactions and game logic, including player actions,
     * block placement, inventory management.
     *
     * @param controllableModel The controllable model for the Minecraft game.
     * @param view The view component for the Minecraft game.
     */
    public MinecraftController(ControllableMinecraftModel controllableModel, MinecraftView view) {
        this.controllableModel = controllableModel;
        this.view = view;

        this.lastGameState = controllableModel.getGameState();

        this.playerController = new PlayerController(controllableModel);
        this.blockController = new BlockController(controllableModel, view);
        this.inventoryController = new InventoryController(controllableModel);
        new HomeScreenController(view, this);
        new HelpScreenController(view, this);

        Gdx.input.setInputProcessor(view.getMenuScreenStage());   
    }

    @Override
    public boolean keyDown(int keycode) {

        // used for every gamestate
        if (keycode == Keys.ESCAPE) {
            Gdx.app.exit();
            return true;
        } else if (keycode == Keys.F) {
            view.toggleFullscreen();
            return true;
        }
        
        GameState gameState = controllableModel.getGameState();

        if (gameState == GameState.GAME_ACTIVE || gameState == GameState.CRAFTING_SCREEN) {
            if (keycode == Keys.R) {
                restartGame();                
                return true;
            }
            
            playerController.handleKeyDown(keycode);
            inventoryController.handleKeyDown(keycode);

            if (gameState == GameState.GAME_ACTIVE) {
                if (keycode == Keys.H) {
                    setGameStateAndUpdateScreen(GameState.HELP_SCREEN);
                    Gdx.input.setInputProcessor(view.getHelpScreenStage());
                    return true;
                }
                if (keycode == Keys.P) {
                    togglePause(gameState); 
                    return true;
                }
            }
        }

        if (gameState == GameState.GAME_PAUSED) {
            if (keycode == Keys.P) {
                togglePause(gameState); 
                return true;
            }
            if (keycode == Keys.R) {
                restartGame();                
                return true;
            }
        }

        if (gameState == GameState.GAME_OVER || gameState == GameState.GAME_WON) {
            restartGame();                
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE || controllableModel.getGameState() == GameState.CRAFTING_SCREEN){
            playerController.handleKeyUp(keycode);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        GameState gameState = controllableModel.getGameState();

        if (gameState == GameState.GAME_ACTIVE) {
            blockController.handleTouchDown(screenX, screenY, button); 
            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        GameState gameState = controllableModel.getGameState();

        if (gameState == GameState.GAME_ACTIVE) {
            blockController.handleTouchUp();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int button = Gdx.input.isButtonPressed(Buttons.RIGHT) ? Buttons.RIGHT : Buttons.LEFT;
        return touchDown(screenX, screenY, pointer, button); // Call touchDown method with the specified button
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // amountY > 0 for scroll nedover, amountY < 0 for scroll oppover
        if (amountY > 0) {
            // Scroller ned
            if (controllableModel.getGameState() == GameState.CRAFTING_SCREEN) {
                controllableModel.moveCraftableTableSelection(0, 1);
            } else {
                controllableModel.changeInventorySlot(+1);
                // Endre lagerspor til høyre
            }
        } else if (amountY < 0) {
            // Scroller opp
            if (controllableModel.getGameState() == GameState.CRAFTING_SCREEN) {
                controllableModel.moveCraftableTableSelection(0, -1);
            } else {
                controllableModel.changeInventorySlot(-1);
                // Endre lagerspor til venstre
            }
        }
        return true;
    }
    
    /**
     * Restarts the game by restarting the game model, and updating the screen with a new gamescreen.
     */
    public void restartGame() {
        playerController.stopMovement();
        controllableModel.restartGame();
        view.newGameScreen();
        Gdx.input.setInputProcessor(view.getMenuScreenStage());
    }

    /**
     * Set the game state and update the screen.
     * @param gameState The new game state.
     */
    public void setGameStateAndUpdateScreen(GameState gameState) {
        controllableModel.setGameState(gameState);
        view.updateScreen();
    }

    /**
     * Toggle the pause state of the game.
     * If the game is active, it will be paused, and vice versa.
     * @param gameState The current game state.
     */
    public void togglePause(GameState gameState) {
        if (gameState == GameState.GAME_ACTIVE) {
            setGameStateAndUpdateScreen(GameState.GAME_PAUSED);
        } else if (gameState == GameState.GAME_PAUSED) {
            setGameStateAndUpdateScreen(GameState.GAME_ACTIVE);
        }
    }

    /**
     * Get the last game state.
     * @return The last game state.
     */
    public GameState getLastGameState() {
        return this.lastGameState;
    }

    /**
     * Set the last game state.
     * @param gameState The last game state.
     */
    public void setLastGameState(GameState gameState) {
        this.lastGameState = gameState;
    }

    // For testing: 
    /**
     * Set the player controller.
     * @param playerController The player controller.
     */
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }
    
    /**
     * Set the block controller.
     * @param blockController The block controller.
     */
    public void setBlockController(BlockController blockController) {
        this.blockController = blockController;
    }
    
    /**
     * Set the inventory controller.
     * @param inventoryController The inventory controller.
     */
    public void setInventoryController(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
    }

}
