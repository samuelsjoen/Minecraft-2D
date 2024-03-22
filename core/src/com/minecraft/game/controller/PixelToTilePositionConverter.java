package com.minecraft.game.controller;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.minecraft.game.utils.Constants;

public class PixelToTilePositionConverter {
    private Camera camera;

    public PixelToTilePositionConverter(Camera camera) {
        this.camera = camera;
    }

       public LinkedList<Integer> calculateTileXAndY(int x, int y) {
        Vector3 touchPos = new Vector3(x, y, 0);
        // Convert screen coordinates to world coordinates
        camera.unproject(touchPos);
        float worldX = touchPos.x;
        float worldY = touchPos.y;
        // Calculate tile coordinates
        int tileX = (int) (worldX / Constants.TILE_SIZE);
        int tileY = (int) (worldY / Constants.TILE_SIZE);
        return new LinkedList<Integer>(List.of(tileX, tileY));
    }
}
