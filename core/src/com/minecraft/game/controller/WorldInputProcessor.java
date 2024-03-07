package com.minecraft.game.controller;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

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

        TiledMap tiledMap = gameScreen.getTiledMap();
        TiledMapTileLayer mineableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        Cell cell = mineableLayer.getCell(tileX, tileY);

        if (button == Input.Buttons.LEFT) {
            if (cell != null) {

                // add an item to the inventory
                // Get the tile type based on the tile coordinates
                int tileId = cell.getTile().getId();
                TileType tiletype = TileType.getTileTypeWithId(tileId);
                System.out.println("Tile type: " + tiletype);

                int tileTypeId = tiletype.getId();
                Item item = Item.getItemWithId(tileTypeId);
                if (item != null) {
                    gameScreen.getInventory().addItem(item);
                    // remove a tile from the map
                    tileMapHelper.removeTile(tileX, tileY, gameScreen.getTiledMap());
                    // remove a map object from the map
                    tileMapHelper.removeMapObject(tileX, tileY, gameScreen.getTiledMap());
                }

                // Get the mapobject based on the tile coordinates
                String tileCoordinatesConcatinated = tileX * Constants.TILE_SIZE + ", " + tileY * Constants.TILE_SIZE;
                MapObject polygon = tiledMap.getLayers().get("collisions").getObjects()
                        .get(tileCoordinatesConcatinated);
                int polygonID = (int) polygon.getProperties().get("id");
                TileType polygonTileType = TileType.getTileTypeWithId(polygonID);
                System.out.println(polygonTileType.getTextureName());

            } else {
                System.out.println("Cell is null.");
            }
        }

        // Put down a tile/ block
        if (button == Input.Buttons.RIGHT) {
            // If the cell is null, we can place a tile
            if (cell == null) {
                // Get selected item from inventory
                Inventory inventory = gameScreen.getInventory();
                HashMap<Item, Integer> items = inventory.getItems();
                int currentSlot = inventory.getCurrentSlot();

                // Check if there are items in the inventory
                if (items.size() > 0) {
                    // INDEX OUT OF BOUNDS!!!!
                    Item item = (Item) items.keySet().toArray()[currentSlot];
                    System.out.println("Selected item: " + item);
                    int itemId = item.getId();

                    TileType tileType = TileType.getTileTypeWithId(itemId);

                    // Check if the tile type exists
                    if (tileType != null) {
                        inventory.removeItem(item);
                        tileMapHelper.addTile(gameScreen.getTiledMap(), tileType, tileX, tileY);
                        tileMapHelper.addMapObject(tileX, tileY, tileType.getId(), gameScreen.getTiledMap());
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
