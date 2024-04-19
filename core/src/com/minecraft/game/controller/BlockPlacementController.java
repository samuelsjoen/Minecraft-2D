package com.minecraft.game.controller;

import java.awt.Point;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.utils.Timer;
import com.minecraft.game.view.MinecraftView;

public class BlockPlacementController {
    
    private final ControllableMinecraftModel controllableModel;
    private MinecraftView view;

    private Timer timer;
    private int lastTileX;
    private int lastTileY;

    public BlockPlacementController(ControllableMinecraftModel controllableModel, MinecraftView view) {
        this.controllableModel = controllableModel;
        this.view = view;

        this.timer = new Timer(); // Timer used for mining blocks

        // Default value
        this.lastTileX = -1;
        this.lastTileY = -1;
    }

    public void handleTouchUp() {
        timer.stop();
        timer.clear();
        view.stopMineBlockSound();
    }
    
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

    private void placeBlock(int tileX, int tileY) {
        controllableModel.addBlock(tileX, tileY);
    }

    private boolean removeBlock(int tileX, int tileY) {
        if (tileX < 0 || tileY < 0) { // Invalid tile coordinates
            return false;
        } else if (tileX != lastTileX || tileY != lastTileY) { // Check if the player is still mining the same block
            startMiningBlock(tileX, tileY);
            return true;
        }
        return false;
    }

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

    public Point calculateTileXAndY(int screenX, int screenY) {        
        // Calculate tile coordinates
        PixelToTilePositionConverter converter = new PixelToTilePositionConverter(view.getCamera());
        Point tileXAndY = converter.calculateTileXAndY(screenX, screenY);
        return tileXAndY;
    }
}
