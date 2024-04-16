package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;
import com.minecraft.game.model.GameState;
import com.minecraft.game.model.Player.State;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.view.MinecraftView;
import java.util.LinkedList;

public class MinecraftController implements InputProcessor {//extends InputAdapter {

    private ControllableMinecraftModel controllableModel;
    private PlayerController playerController;
    private MinecraftView view;
    private Timer timer;
    private int lastTileX;
    private int lastTileY;

    public MinecraftController(ControllableMinecraftModel controllableModel, MinecraftView view) {
        this.controllableModel = controllableModel; 
        this.view = view;
        this.timer = new Timer(); // Timer used for mining blocks

        this.playerController = new PlayerController(controllableModel);

        // Default value
        this.lastTileX = -1;
        this.lastTileY = -1;
    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Constants.ESCAPE_KEY:
                Gdx.app.exit();
                return true;
            case Constants.TOGGLE_FULLSCREEN_KEY:
                view.toggleFullscreen();
                return true;
        }

        GameState gameState = controllableModel.getGameState();

        switch (gameState) {
            case HELP_SCREEN:
                if (keycode == Constants.START_KEY) {
                    setGameStateAndUpdateScreen(GameState.GAME_ACTIVE);
                    return true;
                }
                break;
            case GAME_ACTIVE:
                if (handleActiveGameInput(keycode))
                    return true;
                break;
            case GAME_PAUSED:
                if (keycode == Constants.TOGGLE_PAUSE_KEY) {
                    setGameStateAndUpdateScreen(GameState.GAME_ACTIVE);
                    return true;
                }
                break;
            case GAME_WON:
                controllableModel.restartGame();
                view.newGameScreen();
                return true;
            case GAME_OVER:
                controllableModel.restartGame();
                view.newGameScreen();
                return true;
            case CRAFTING_SCREEN:
                if (handleActiveGameInput(keycode))
                    return true;
            case WELCOME_SCREEN:
                break;
        }
        return false;
    }

    private boolean handleActiveGameInput(int keycode) {
        if (controllableModel.getPlayerState() != State.DEAD) {

            switch (keycode) {
                case Constants.REVIVE_KEY:
                    controllableModel.restartGame();
                    view.newGameScreen();
                    return true;
                case Constants.TOGGLE_PAUSE_KEY: // Pause the game
                    setGameStateAndUpdateScreen(GameState.GAME_PAUSED);
                    return true;
                case Constants.MOVE_LEFT_KEY:
                    //controllableModel.movePlayer(-1); // Move left
                    playerController.setMoveLeft(true); // Move left
                    return true;
                case Constants.MOVE_RIGHT_KEY:
                    //controllableModel.movePlayer(+1); // Move right
                    playerController.setMoveRight(true); // Move right
                    return true;
                case Constants.JUMP_KEY:
                    controllableModel.playerJump();
                    //playerController.setJump(true); // Player jump
                    return true;
                case Constants.ATTACK_KEY:
                    controllableModel.playerAttack();
                    return true;
                case Constants.CHANGE_INVENTORY_LEFT_KEY:
                    if (controllableModel.getGameState() == GameState.CRAFTING_SCREEN) {
                        controllableModel.moveCraftableTableSelection(0, -1);
                    } else {
                    controllableModel.changeInventorySlot(-1); // Change inventory slot to the left
                    }
                    return true;
                case Constants.CHANGE_INVENTORY_RIGHT_KEY:
                    if (controllableModel.getGameState() == GameState.CRAFTING_SCREEN) {
                        controllableModel.moveCraftableTableSelection(0, 1);
                    } else {
                    controllableModel.changeInventorySlot(+1); // Change inventory slot to the right
                    }
                    return true;
                case Constants.DROP_INVENTORY_ITEM_KEY:
                    controllableModel.dropInventoryItem();
                    return true;
                case Input.Keys.ENTER:
                    if (controllableModel.getGameState() == GameState.CRAFTING_SCREEN) {
                        controllableModel.craftItem();
                    }    
                case Input.Keys.UP:
                    if (controllableModel.getGameState() == GameState.CRAFTING_SCREEN) {
                        controllableModel.moveCraftableTableSelection(-1, 0);
                    }
                    return true;
                case Input.Keys.DOWN:
                    if (controllableModel.getGameState() == GameState.CRAFTING_SCREEN) {
                        controllableModel.moveCraftableTableSelection(1, 0);
                    }
                    return true;
                case Constants.TOGGLE_CRAFTING_KEY:
                    if (controllableModel.getGameState() == GameState.CRAFTING_SCREEN) {
                        controllableModel.setGameState(GameState.GAME_ACTIVE);
                    } else {
                        controllableModel.setGameState(GameState.CRAFTING_SCREEN);
                    }
                    controllableModel.toggleCrafting();
                    return true;
            }
        return false;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            if (keycode == Constants.ATTACK_KEY) {
                controllableModel.playerAttack();
            } else if (keycode == Constants.MOVE_LEFT_KEY) {
                playerController.setMoveLeft(false);
            } else if (keycode == Constants.MOVE_RIGHT_KEY) {
                playerController.setMoveRight(false);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        GameState gameState = controllableModel.getGameState();

        if (gameState == GameState.WELCOME_SCREEN) {
            handleWelcomeScreenTouch(screenX, screenY);
            return true;
        }
        if (gameState == GameState.GAME_ACTIVE) {
            handleGameActiveTouch(screenX, screenY, button);
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            // For placing/removing multiple tiles/blocks at a time
            int button = Gdx.input.isButtonPressed(Input.Buttons.RIGHT) ? Input.Buttons.RIGHT : Input.Buttons.LEFT;
            touchDown(screenX, screenY, pointer, button); // Call touchDown method with the specified button
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            timer.stop();
            timer.clear();
            view.stopMineBlockSound();
            return true;
        }
        return false;
    }

    private void setGameStateAndUpdateScreen(GameState gameState) {
        controllableModel.setGameState(gameState);
        view.updateScreen();
    }

    private void handleWelcomeScreenTouch(int screenX, int screenY) {
        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
    
        if (view.isStartButtonClicked(touchX, touchY)) {
            setGameStateAndUpdateScreen(GameState.GAME_ACTIVE);
        } else if (view.isHelpButtonClicked(touchX, touchY)) {
            setGameStateAndUpdateScreen(GameState.HELP_SCREEN);
        } else if (view.isQuitButtonClicked(touchX, touchY)) {
            Gdx.app.exit();
        }
    }

    private void handleGameActiveTouch(int screenX, int screenY, int button) {
        if (controllableModel.getPlayerState() == State.DEAD) { // If player is dead it cannot do anything
            return;
        }

        if (timer.isEmpty()) {
            timer.start();
        }

        PixelToTilePositionConverter converter = new PixelToTilePositionConverter(view.getCamera());
        // Calculate tile coordinates
        LinkedList<Integer> tileXAndY = converter.calculateTileXAndY(screenX, screenY);
        int tileX = tileXAndY.remove();
        int tileY = tileXAndY.pop();

        if (button == Input.Buttons.LEFT) {
            if (tileX < 0 || tileY < 0) { // Invalid tile coordinates
                return;
            }
    
            if (tileX != lastTileX || tileY != lastTileY) { // Check if the player is still mining the same block
    
                if (controllableModel.isBlockMineable(tileX, tileY)) {
                    view.playMineBlockSound();
                }

                timer.clear();
                timer.start();
                Timer.Task blockRemovalTask = new Timer.Task() {
                    @Override
                    public void run() {
                        controllableModel.removeBlock(tileX, tileY); // When the task is excuted the block is removed
                        view.stopMineBlockSound();
                    }
                };
    
                float delay = controllableModel.getTileDamage(tileX, tileY);
                timer.scheduleTask(blockRemovalTask, delay);
            }
    
            // Store the previous tile coordinates
            lastTileX = tileX;
            lastTileY = tileY;
        } else if (button == Input.Buttons.RIGHT) {
            controllableModel.addBlock(tileX, tileY);
        }
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
