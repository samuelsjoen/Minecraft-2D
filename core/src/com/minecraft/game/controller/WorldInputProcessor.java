package com.minecraft.game.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector3;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.TileType;
import com.minecraft.game.view.GameScreen;

public class WorldInputProcessor implements InputProcessor {

    private GameScreen gameScreen;

    public WorldInputProcessor(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            int x = screenX;
            int y = screenY;
            System.out.println("Screen coordinates: " + x + ", " + y);
            Vector3 touchPos = new Vector3(x, y, 0);
            // Convert screen coordinates to world coordinates
            gameScreen.getCamera().unproject(touchPos);
            float worldX = touchPos.x;
            float worldY = touchPos.y;
            System.out.println("World coordinates: " + worldX + ", " + worldY);

            // Calculate tile coordinates
            int tileX = (int) (worldX / Constants.TILE_SIZE);
            int tileY = (int) (worldY / Constants.TILE_SIZE);
            System.out.println("Tile coordinates: " + tileX + ", " + tileY);

            // Get the tile type based on the tile coordinates
            TiledMap tiledMap = gameScreen.getTiledMap();
            TiledMapTileLayer mineableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
            Cell cell = mineableLayer.getCell(tileX, tileY);
            if (cell != null) {
                int tileId = cell.getTile().getId();
                TileType tiletype = TileType.getTileTypeWithId(tileId);
                System.out.println("Tile type: " + tiletype);
            } else {
                System.out.println("Cell is null.");
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
