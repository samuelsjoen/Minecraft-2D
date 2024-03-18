package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.minecraft.game.model.GameState;
import com.minecraft.game.view.MinecraftView;

public class MinecraftController implements InputProcessor {

    private ControllableMinecraftModel controllableModel;
    private MinecraftView view;

    public MinecraftController(ControllableMinecraftModel controllableModel, MinecraftView view) {
        this.controllableModel = controllableModel;
        this.view = view;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
            return true;
        }

        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            /*
            // CONTROLLING PLAYER
            if (keycode == Input.Keys.LEFT) {
                controllableModel.movePlayerLeft();
                return true;
            } else if (keycode == Input.Keys.RIGHT) {
                controllableModel.movePlayerRight();
                return true;
            } else if (keycode == Input.Keys.UP) {
                controllableModel.playerJump();
                return true;
            } else if (keycode == Input.Keys.DOWN) {
                controllableModel.playerAttack();
                return true;
            } else if (keycode == Input.Keys.R) {
                controllableModel.revivePlayer();
                return true;
            }

            // CONTROLLING INVENTORY
            else if (keycode == Input.Keys.A) {
                controllableModel.changeInventorySlot(-1);
                return true;
            } else if (keycode ==Input.Keys.D) {
                controllableModel.changeInventorySlot(+1);
                return true;
            } else if (keycode == Input.Keys.Q) {
                controllableModel.dropInventoryItem();
                return true;
            }
            */
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (controllableModel.getGameState() == GameState.WELCOME_SCREEN) {

        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (view.isStartButtonClicked(touchX, touchY)) {
            System.out.println("Start button clicked");
            controllableModel.setGameState(GameState.GAME_ACTIVE);
            System.out.println("GameState is: " + controllableModel.getGameState());
            return true;
        } else if (view.isOptionsButtonClicked(touchX, touchY)) {
            System.out.println("Options button clicked");
            controllableModel.setGameState(GameState.PAUSED_SCREEN);
            System.out.println("GameState is: " + controllableModel.getGameState());
            return true;
        } else if (view.isQuitButtonClicked(touchX, touchY)) {
            System.out.println("Quit button clicked");
            Gdx.app.exit();
            return true;
        }
    }

    // REMOVING/PLACING TILES LOGIC
    // for removing/placing tiles/blocks
    if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
        /*
        if (button == Input.Buttons.LEFT) {                 // Remove a block
            controllableModel.removeBlock(screenX, screenY);
        } else if (button == Input.Buttons.RIGHT) {         // Place a block
            controllableModel.placeBlock(screenX, screenY);
        }
        return true;*/
    }
        return false;
    }

    // For placing/removing multiple tiles/blocks at a time
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {

            int button = Input.Buttons.LEFT; // default button
            if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                touchDown(screenX, screenY, pointer, button);
                button = Input.Buttons.RIGHT;
            }
            touchDown(screenX, screenY, pointer, button); // Call touchDown method with specified button

            return true;
        }
        return false;
    }

    // Unused methods - but part of the interface for InputProcessor

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

}
