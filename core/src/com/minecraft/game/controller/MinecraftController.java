package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Buttons;

import com.minecraft.game.model.GameState;
import com.minecraft.game.view.MinecraftView;

public class MinecraftController extends InputAdapter {

    private ControllableMinecraftModel controllableModel;
    private MinecraftView view;

    private PlayerController playerController;
    private BlockPlacementController blockPlacementController;
    private InventoryController inventoryController;
    // TODO: check if this is actually used
    @SuppressWarnings("unused")
    private HelpScreenController helpScreenController;

    public MinecraftController(ControllableMinecraftModel controllableModel, MinecraftView view) {
        this.controllableModel = controllableModel;
        this.view = view;

        this.playerController = new PlayerController(controllableModel);
        this.blockPlacementController = new BlockPlacementController(controllableModel, view);
        this.inventoryController = new InventoryController(controllableModel);
        this.helpScreenController = new HelpScreenController(view, this);
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

        switch (gameState) {
            case HELP_SCREEN:
                if (keycode == Keys.S) {
                    setGameStateAndUpdateScreen(GameState.GAME_ACTIVE);
                    return true;
                }
                break;

            case GAME_ACTIVE:
                if (keycode == Keys.R) {
                    playerController.stopMovement();
                    controllableModel.restartGame();
                    view.newGameScreen();
                    return true;
                }
                if (keycode == Keys.P) {
                    togglePause(gameState); 
                    return true;
                }
                if (keycode == Keys.H) { // heal player
                    playerController.stopMovement();
                    controllableModel.revivePlayer();
                    setGameStateAndUpdateScreen(GameState.GAME_ACTIVE);
                    return true;
                }
                playerController.handleKeyDown(keycode);
                inventoryController.handleKeyDown(keycode);

            case GAME_PAUSED:
                if (keycode == Keys.P) {
                    togglePause(gameState); 
                    return true;
                }
                if (keycode == Keys.R) {
                    playerController.stopMovement();
                    controllableModel.restartGame();
                    view.newGameScreen();
                    return true;
                }
                break;

            case WELCOME_SCREEN:
                break;

            case CRAFTING_SCREEN:
                if (keycode == Keys.R) {
                    playerController.stopMovement();
                    controllableModel.restartGame();
                    view.newGameScreen();
                    return true;
                }
                if (keycode == Keys.H) { // heal player
                    playerController.stopMovement();
                    controllableModel.revivePlayer();
                    setGameStateAndUpdateScreen(GameState.GAME_ACTIVE);
                    return true;
                }
                playerController.handleKeyDown(keycode);
                inventoryController.handleKeyDown(keycode);
                break;

            case GAME_OVER:
                if (keycode == Keys.R) {
                    playerController.stopMovement();
                    controllableModel.restartGame();
                    view.newGameScreen();
                    return true;
                }
                if (keycode == Keys.H) { // heal player
                    playerController.stopMovement();
                    controllableModel.revivePlayer();
                    setGameStateAndUpdateScreen(GameState.GAME_ACTIVE);
                    return true;
                }

            case GAME_WON:
                if (keycode == Keys.R) {
                    playerController.stopMovement();
                    controllableModel.restartGame();
                    view.newGameScreen();
                    return true;
                }
                if (keycode == Keys.H) { // heal player
                    playerController.stopMovement();
                    controllableModel.revivePlayer();
                    setGameStateAndUpdateScreen(GameState.GAME_ACTIVE);
                    return true;
                }
                return false;
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

        if (gameState == GameState.WELCOME_SCREEN) {
            handleWelcomeScreenTouch();
            return true;
        }
        if (gameState == GameState.GAME_ACTIVE) {
            blockPlacementController.handleTouchDown(screenX, screenY, button); 
            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        GameState gameState = controllableModel.getGameState();

        if (gameState == GameState.GAME_ACTIVE) {
            blockPlacementController.handleTouchUp();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int button = Gdx.input.isButtonPressed(Buttons.RIGHT) ? Buttons.RIGHT : Buttons.LEFT;
        return touchDown(screenX, screenY, pointer, button); // Call touchDown method with the specified button
    }

    public void handleWelcomeScreenTouch() {
        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (view.isStartButtonClicked(touchX, touchY)) {
            setGameStateAndUpdateScreen(GameState.GAME_ACTIVE);
        } else if (view.isHelpButtonClicked(touchX, touchY)) {
            setGameStateAndUpdateScreen(GameState.HELP_SCREEN);
            Gdx.input.setInputProcessor(view.getHelpScreenStage());
        } else if (view.isQuitButtonClicked(touchX, touchY)) {
            Gdx.app.exit();
        }
    }

    public void setGameStateAndUpdateScreen(GameState gameState) {
        controllableModel.setGameState(gameState);
        view.updateScreen();
    }

    public void togglePause(GameState gameState) {
        if (gameState == GameState.GAME_ACTIVE) {
            setGameStateAndUpdateScreen(GameState.GAME_PAUSED);
        } else if (gameState == GameState.GAME_PAUSED) {
            setGameStateAndUpdateScreen(GameState.GAME_ACTIVE);
        }
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
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

    // For testing:
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }
    
    public void setBlockPlacementController(BlockPlacementController blockPlacementController) {
        this.blockPlacementController = blockPlacementController;
    }
    
    public void setInventoryController(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
    }

    public void setHelpScreenController(HelpScreenController helpScreenController) {
        this.helpScreenController = helpScreenController;
    }

}
