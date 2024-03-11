package com.minecraft.game.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector3;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.model.Item;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.TileMapHelper;
import com.minecraft.game.utils.TileType;
import com.minecraft.game.view.GameScreen;

public class WorldInputProcessor implements InputProcessor {

    private GameScreen gameScreen;
    private TileMapHelper tileMapHelper;

    public WorldInputProcessor(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.tileMapHelper = new TileMapHelper(gameScreen);
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

    private LinkedList<Integer> calculateTileXAndY(int x, int y) {
        Vector3 touchPos = new Vector3(x, y, 0);
        // Convert screen coordinates to world coordinates
        gameScreen.getCamera().unproject(touchPos);
        float worldX = touchPos.x;
        float worldY = touchPos.y;
        // Calculate tile coordinates
        int tileX = (int) (worldX / Constants.TILE_SIZE);
        int tileY = (int) (worldY / Constants.TILE_SIZE);
        return new LinkedList<Integer>(List.of(tileX, tileY));
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Calculate tile coordinates
        LinkedList<Integer> tileXAndY = calculateTileXAndY(screenX, screenY);
        int tileX = tileXAndY.remove();
        int tileY = tileXAndY.pop();

        // Get the mineable layer, and the cell at the tile coordinates
        TiledMap tiledMap = gameScreen.getTiledMap();
        TiledMapTileLayer mineableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        Cell cell = mineableLayer.getCell(tileX, tileY);

        // Place a block
        if (button == Input.Buttons.LEFT) {
            if (cell != null) {
                // Get the tile type based on the tile coordinates
                int tileId = cell.getTile().getId();
                TileType tiletype = TileType.getTileTypeWithId(tileId);

                // Get the item based on the tile type
                Item item = Item.getItemWithName(tiletype.getTextureName());
                if (item != null) {
                    // Add the item to the inventory
                    gameScreen.getInventory().addItem(item);
                    // Remove the block from the mineable layer
                    tileMapHelper.removeBlock(tileX, tileY, gameScreen.getTiledMap());
                }
            }
            // Put down a block
        } else if (button == Input.Buttons.RIGHT) {
            // If the cell is null, we can place a tile
            if (cell == null) {
                // Get selected item from inventory
                Inventory inventory = gameScreen.getInventory();
                Item item = inventory.getSelectedItem();
                if (item != null) {
                    String itemName = item.getName();

                    TileType tileType = TileType.getTileTypeWithName(itemName);
                    if (tileType != null) {
                        // Remove the item from the inventory
                        inventory.removeItem(item);
                        // Add the block to the mineable layer
                        tileMapHelper.addBlock(tileX, tileY, tileType, gameScreen.getTiledMap());
                    }
                }
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
        // default button
        int button = Input.Buttons.LEFT;
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            button = Input.Buttons.RIGHT;
        }

        // Call touchDown method
        touchDown(screenX, screenY, pointer, button);

        return true;
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
