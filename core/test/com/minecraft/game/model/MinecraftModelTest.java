package com.minecraft.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.minecraft.game.LibgdxUnitTest;
import com.minecraft.game.utils.Constants;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

// # TODO: mock masse rart
public class MinecraftModelTest extends LibgdxUnitTest {

    MinecraftModel minecraftModel;
    Player player;
    TiledMap tiledMap;
    TiledMapTileLayer tiledMapLayer;
    MapLayer objectLayer;

    @BeforeEach
    void setUp() {

        try (MockedConstruction<OrthogonalTiledMapRenderer> mocked = Mockito
                .mockConstruction(OrthogonalTiledMapRenderer.class)) {
            // When model tries to create OrthogonalTiledMapRenderer, it will get the mock
            // instead
            minecraftModel = new MinecraftModel();
            tiledMap = minecraftModel.getTiledMap();
            tiledMapLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
            objectLayer = tiledMap.getLayers().get("collisions");
        }
    }

    @Test
    public void testConstructor() {
        // # TODO: factory, map
        assertNotNull(minecraftModel);
        GameState.WELCOME_SCREEN.equals(minecraftModel.getGameState());
        assertNotNull(minecraftModel.getMapRenderer());
        assertNotNull(minecraftModel.getPlayer());
        assertNotNull(minecraftModel.getTiledMap());
        assertNotNull(minecraftModel.getWorld());
        assertNotNull(minecraftModel.getCrafting());
        assertNotNull(minecraftModel.getInventory());
        assertNotNull(minecraftModel.getDayNightCycle());
        assertNotNull(minecraftModel.getArmorInventory());
        assertNotNull(minecraftModel.getPlayerRectangle());
    }

    // but the coordinates are wrong. The player is at (7, 48), not (0, 1)
    @Test
    public void testGetPlayerCoordinates() {
        player = minecraftModel.getPlayer();
        int playerX = (int) player.getX() / Constants.TILE_SIZE;
        int playerY = (int) player.getY() / Constants.TILE_SIZE;
        System.out.println("Player coordinates: " + playerX + ", " + playerY);
        assertNotNull(playerX);
        assertNotNull(playerY);
    }

    /*@Test
    public void testTiledMap() {
        assertNotNull(tiledMap);
        assertNotNull(tiledMapLayer);
        assertNotNull(objectLayer);
        // check if 0,1 is a part of objectlayer
        MapObject object = objectLayer.getObjects().get("0, 1");
        assertNotNull(object);
    }*/



    // @Test
    // public void addBlock() {
    //     // 8,49 should be no blocks
    //     assertNull(tiledMapLayer.getCell(8, 49));
    //     assertNull(objectLayer.getObjects().get("8, 49"));
    //     minecraftModel.addBlock(8, 49);
    //     // 8,49 should now have a block
    //     assertNotNull(tiledMapLayer.getCell(8, 49));
    //     assertNotNull(objectLayer.getObjects().get("8, 49"));
    // }

    // @Test
    // public void removeBlock() {
    //     // 8,45 should have a block
    //     assertNotNull(tiledMapLayer.getCell(8, 45));
    //     assertNotNull(objectLayer.getObjects().get("8, 45"));
    //     minecraftModel.removeBlock(8, 45);
    //     // 8,45 should now have no block
    //     assertNull(tiledMapLayer.getCell(8, 45));
    //     assertNull(objectLayer.getObjects().get("8, 45"));
    // }

    // // # TODO: THIS is where I have to test the addBlock method, not in
    // // minecraftMapTest
    /*@Test
    public void testAddBlockAtPlayer() {

        int playerX = (int) minecraftModel.getPlayer().getX();
        int playerY = (int) minecraftModel.getPlayer().getY();
        // cast coordinates to string
        String playerXString = Integer.toString(playerX);
        String playerYString = Integer.toString(playerY);

        System.out.println("PlayerTile coordinates: " + playerX + ", " + playerY);

        // There should be no tile nor object at the given coordinates
		assertNull(tiledMapLayer.getCell(0, 1));
		assertNull(objectLayer.getObjects().get("0, 1"));
        minecraftModel.addBlock(0, 1);
        assertNull(tiledMapLayer.getCell(0, 1));
		assertNull(objectLayer.getObjects().get("0, 1"));
    }*/

}