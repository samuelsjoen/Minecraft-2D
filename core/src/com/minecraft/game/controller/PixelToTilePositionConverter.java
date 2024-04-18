package com.minecraft.game.controller;

import java.awt.Point;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.minecraft.game.utils.Constants;

/**
 * The PixelToTilePositionConverter class is responsible for converting pixel coordinates to tile coordinates.
 */
public class PixelToTilePositionConverter {
    private Camera camera;

    /**
     * Constructs a new PixelToTilePositionConverter with the specified camera.
     *
     * @param camera the camera used for converting coordinates
     */
    public PixelToTilePositionConverter(Camera camera) {
        this.camera = camera;
    }

    /**
     * Calculates the tile X and Y coordinates based on the given pixel coordinates.
     *
     * @param x the x-coordinate in pixels
     * @param y the y-coordinate in pixels
     * @return a point containing the tile X and Y coordinates
     */
    public Point calculateTileXAndY(int x, int y) {
        Vector3 touchPos = new Vector3(x, y, 0);
        
        // Convert screen coordinates to world coordinates
        camera.unproject(touchPos);
        float worldX = touchPos.x;
        float worldY = touchPos.y;

        // Calculate tile coordinates
        int tileX = (int) (worldX / Constants.TILE_SIZE);
        int tileY = (int) (worldY / Constants.TILE_SIZE);
        return new Point(tileX, tileY);
    }
}
