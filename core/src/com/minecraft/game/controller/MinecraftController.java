package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;
import com.minecraft.game.model.DayNightCycle;
import com.minecraft.game.model.GameState;
import com.minecraft.game.model.Player.State;
import com.minecraft.game.view.MinecraftView;
import com.minecraft.game.utils.Constants;
import java.util.LinkedList;

public class MinecraftController implements InputProcessor {

    private ControllableMinecraftModel controllableModel;
    private MinecraftView view;
    private Timer timer;
    private int lastTileX;
    private int lastTileY;
    private DayNightCycle dayNightCycle;

    public MinecraftController(ControllableMinecraftModel controllableModel, MinecraftView view) {
        this.controllableModel = controllableModel;
        this.view = view;
        this.timer = new Timer();

        // default value
        this.lastTileX = -1;
        this.lastTileY = -1;

        // Get the day-night cycle from the model
        this.dayNightCycle = controllableModel.getDayNightCycle();
        // Start the day-night cycle with a # sec interval
        this.dayNightCycle.startCycle(5f);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
            return true;
        }

        if (keycode == Input.Keys.F) {
            // turn fullscreen on or off
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
            } else {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
            return true;
        }

        if (controllableModel.getGameState() == GameState.GAME_PAUSED) {
            if (keycode == Input.Keys.P) {
                controllableModel.setGameState(GameState.GAME_ACTIVE);
                view.updateScreen();
            }
            return true;
        }

        // TODO: fix so that it is drawn instantly and not after a button is pressed,
        // should maybe move some of this to model

        if (controllableModel.getGameState() == GameState.GAME_OVER) {
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
                controllableModel.restartGame();
                view.newGameScreen();
                view.updateScreen();
            }
            return true;
        }

        if (controllableModel.getGameState() == GameState.GAME_ACTIVE || controllableModel.getGameState() == GameState.CRAFTING_SCREEN) {

            // Pause the game
            if (keycode == Input.Keys.P) {
                controllableModel.setGameState(GameState.GAME_PAUSED);
                view.updateScreen();
            }

            // TODO: move parts of this game over into model or some to view (like the
            // updatescreen?)
            // Game Over
            if (controllableModel.getPlayerState() == State.DEAD) {
                controllableModel.setGameState(GameState.GAME_OVER);
                view.updateScreen();
            }

            if (controllableModel.getPlayerState() == State.DEAD) {
                if (keycode == Input.Keys.R) {
                    controllableModel.revivePlayer();
                }
            }

            // CONTROLLING PLAYER
            if (controllableModel.getPlayerState() != State.DEAD) {
                if (keycode == Input.Keys.A) {
                    controllableModel.movePlayer(-1);
                } else if (keycode == Input.Keys.D) {
                    controllableModel.movePlayer(+1);
                } else if (keycode == Input.Keys.SPACE) {
                    controllableModel.playerJump();
                } else if (keycode == Input.Keys.TAB) {
                    controllableModel.playerAttack();
                }

                // CONTROLLING INVENTORY
                if (keycode == Input.Keys.LEFT) {
                    controllableModel.changeInventorySlot(-1);
                    return true;
                } else if (keycode == Input.Keys.RIGHT) {
                    controllableModel.changeInventorySlot(+1);
                    return true;
                } else if (keycode == Input.Keys.Q) {
                    controllableModel.dropInventoryItem();
                    return true;
                }
                // CRAFTING
                if (keycode == Input.Keys.E) {
                    if (controllableModel.getGameState() == GameState.CRAFTING_SCREEN) {
                        controllableModel.setGameState(GameState.GAME_ACTIVE);
                    } else {
                        controllableModel.setGameState(GameState.CRAFTING_SCREEN);
                    }
                    controllableModel.toggleCrafting();
                }
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.TAB) {
            controllableModel.playerAttack();
        } else if (keycode == Input.Keys.A) {
            controllableModel.stopPlayer();
        } else if (keycode == Input.Keys.D) {
            controllableModel.stopPlayer();
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        // Controlling the buttons on menuScreen
        if (controllableModel.getGameState() == GameState.WELCOME_SCREEN) {

            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (view.isStartButtonClicked(touchX, touchY)) {
                controllableModel.setGameState(GameState.GAME_ACTIVE);
                view.updateScreen();
            } else if (view.isOptionsButtonClicked(touchX, touchY)) {
                controllableModel.setGameState(GameState.OPTIONS_SCREEN);
                view.updateScreen();
            } else if (view.isQuitButtonClicked(touchX, touchY)) {
                Gdx.app.exit();
            }
            return true;
        }

        // REMOVING/PLACING TILES LOGIC
        // for removing/placing tiles/blocks

        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {
            if (controllableModel.getPlayerState() != State.DEAD) {

                if (timer.isEmpty()) {
                    timer.start();
                }

                PixelToTilePositionConverter converter = new PixelToTilePositionConverter(view.getCamera());

                // Calculate tile coordinates
                LinkedList<Integer> tileXAndY = converter.calculateTileXAndY(screenX, screenY);
                int tileX = tileXAndY.remove();
                int tileY = tileXAndY.pop();

                if (button == Input.Buttons.LEFT) {

                    // Invalid tile coordinates
                    if (tileX < 0 || tileY < 0) {
                        return true;
                    }

                    if (tileX != lastTileX || tileY != lastTileY) {

                        timer.clear();
                        timer.start();
                        Timer.Task blockRemovalTask = new Timer.Task() {
                            @Override
                            public void run() {
                                // Code to remove the block
                                controllableModel.removeBlock(tileX, tileY);
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
        }

        return false;
    }

    // For placing/removing multiple tiles/blocks at a time
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (controllableModel.getGameState() == GameState.GAME_ACTIVE) {

            int button = Gdx.input.isButtonPressed(Input.Buttons.RIGHT) ? Input.Buttons.RIGHT : Input.Buttons.LEFT;
            touchDown(screenX, screenY, pointer, button); // Call touchDown method with the specified button

            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        timer.stop();
        timer.clear();
        return true;
    }

    // Unused methods - but part of the interface for InputProcessor

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
    public boolean keyTyped(char character) {
        return false;
    }
}
