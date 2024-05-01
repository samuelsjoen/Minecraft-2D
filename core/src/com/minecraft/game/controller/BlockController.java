package com.minecraft.game.controller;

import java.awt.Point;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.utils.Timer;
import com.minecraft.game.view.MinecraftView;

/**
 * The BlockController class is responsible for handling block placement and removal in the Minecraft game.
 * It interacts with the ControllableMinecraftModel and MinecraftView to perform these actions.
 */
public class BlockController {
    
    private final ControllableMinecraftModel controllableModel;
    private MinecraftView view;

    private Timer timer;
    private int lastTileX;
    private int lastTileY;

    /**
     * Constructs a new BlockController with the specified ControllableMinecraftModel and MinecraftView.
     * Initializes the timer and sets the last tile coordinates to -1, -1 as default values.
     * @param controllableModel
     * @param view
     */
    public BlockController(ControllableMinecraftModel controllableModel, MinecraftView view) {
        this.controllableModel = controllableModel;
        this.view = view;

        this.timer = new Timer(); // Timer used for mining blocks

        // Default values for the last tile coordinates
        this.lastTileX = -1;
        this.lastTileY = -1;
    }

    /**
     * Handles the touch up event.
     * Stops the timer, clears it, and stops the mine block sound in the view.
     */
    public void handleTouchUp() {
        timer.stop();
        timer.clear();
        view.stopMineBlockSound();
    }
    
    /**
     * Handles the touch down event.
     * Starts the timer if it is empty.
     * Calculates the tile coordinates based on the screen coordinates.
     * If the button is left, removes the block at the tile coordinates.
     * If the button is right, places a block at the tile coordinates.
     * @param screenX The x-coordinate of the touch event on the screen.
     * @param screenY The y-coordinate of the touch event on the screen.
     * @param button The button that was pressed (left or right).
     * @return true if a block was placed or removed, false otherwise.
     */
    public boolean handleTouchDown(int screenX, int screenY, int button) {

        if (timer.isEmpty()) {
            timer.start();
        }

        Point tileXAndY = calculateTileXAndY(screenX, screenY);

        int tileX = tileXAndY.x;
        int tileY = tileXAndY.y;

        if (button == Buttons.LEFT) {
            return removeBlock(tileX, tileY);
        } else if (button == Buttons.RIGHT) {
            placeBlock(tileX, tileY);
            return true;
        }
        return false;
    }

    /**
     * Places a block at the specified tile coordinates.
     * @param tileX The x-coordinate of the tile.
     * @param tileY The y-coordinate of the tile.
     */
    private void placeBlock(int tileX, int tileY) {
        controllableModel.addBlock(tileX, tileY);
    }

    /**
     * Removes a block at the specified tile coordinates.
     * If the tile coordinates are invalid or different from the previous tile coordinates,
     * starts mining the block at the new coordinates.
     * @param tileX The x-coordinate of the tile.
     * @param tileY The y-coordinate of the tile.
     * @return true if mining started, false otherwise.
     */
    private boolean removeBlock(int tileX, int tileY) {
        if (tileX < 0 || tileY < 0) { // Invalid tile coordinates
            return false;
        } else if (tileX != lastTileX || tileY != lastTileY) { // Check if the player is still mining the same block
            startMiningBlock(tileX, tileY);
            return true;
        }
        return false;
    }

    /**
     * Starts mining the block at the specified tile coordinates.
     * If the block is mineable, plays the mine block sound in the view.
     * Clears the timer, starts it, and schedules a task to remove the block after a delay.
     * Stores the previous tile coordinates.
     * @param tileX The x-coordinate of the tile.
     * @param tileY The y-coordinate of the tile.
     */
    private void startMiningBlock(int tileX, int tileY) {

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

        // Store the previous tile coordinates
        lastTileX = tileX;
        lastTileY = tileY;
    }

    /**
     * Calculates the tile coordinates based on the screen coordinates.
     * @param screenX The x-coordinate of the touch event on the screen.
     * @param screenY The y-coordinate of the touch event on the screen.
     * @return The calculated tile coordinates as a Point object.
     */
    public Point calculateTileXAndY(int screenX, int screenY) {        
        // Calculate tile coordinates
        PixelToTilePositionConverter converter = new PixelToTilePositionConverter(view.getCamera());
        Point tileXAndY = converter.calculateTileXAndY(screenX, screenY);
        return tileXAndY;
    }
}
