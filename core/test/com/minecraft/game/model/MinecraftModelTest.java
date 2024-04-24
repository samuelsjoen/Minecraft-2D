package com.minecraft.game.model;

import com.badlogic.gdx.maps.MapLayer;
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
            player = minecraftModel.getPlayer();
            // Need to call update, or else the player.getX() and player.getY() will  have the default values from the Body class (which is (0,1)).
            player.update(0.1f);
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

    // @Test
    // public void testObjectLayer() {
    //     // print out object in objectlayer if it is a rectangle
    //     for (MapObject object : objectLayer.getObjects()) {
    //         if (object instanceof RectangleMapObject) {
    //             System.out.println("Object in objectLayer: " + object.getName());
    //             // and the coordinates to the rectangle
    //             System.out.println("Coordinates: for player rectangle  " + ((RectangleMapObject) object).getRectangle());
    //         }
    //     }
    // }

    // TODO: implement this test
    @Test
    public void testPlayerJump() {
        minecraftModel.playerJump();
    }

    @Test
    public void testRestartGame() {
        try (MockedConstruction<OrthogonalTiledMapRenderer> mocked = Mockito.mockConstruction(OrthogonalTiledMapRenderer.class)) {
            minecraftModel.restartGame();
        }

        assertNotNull(minecraftModel.getInventory());
        // should also check that the inventory is only the default items now.
        
        assertNotNull(minecraftModel.getTiledMap());
        assertNotNull(minecraftModel.getMapRenderer());
        assertNotNull(minecraftModel.getPlayer());
        assertNotNull(minecraftModel.getDayNightCycle());

        // Verify that the game state is set to WELCOME_SCREEN
        GameState.WELCOME_SCREEN.equals(minecraftModel.getGameState());
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
    @Test
    public void testAddBlockAtPlayer() {
        int playerX = (int) player.getX() / Constants.TILE_SIZE;
        int playerY = (int) player.getY() / Constants.TILE_SIZE;

        // Placing a block on the top of the player with the coordinates playerX and playerY

        // There should be no tile nor object at the given coordinates
		assertNull(tiledMapLayer.getCell(playerX, playerY));
		assertNull(objectLayer.getObjects().get(playerX + ", " + playerY));
        minecraftModel.addBlock(playerX, playerY);
        // There still should be no tile nor object at the given coordinates
        assertNull(tiledMapLayer.getCell(playerX, playerY));
		assertNull(objectLayer.getObjects().get(playerX + ", " + playerY));

        // and for playerX and (playerY - 1) for the bottom of the player
        assertNull(tiledMapLayer.getCell(playerX, playerY - 1));
        assertNull(objectLayer.getObjects().get(playerX + ", " + (playerY - 1)));
        minecraftModel.addBlock(playerX, playerY - 1);
        assertNull(tiledMapLayer.getCell(playerX, playerY - 1));
        assertNull(objectLayer.getObjects().get(playerX + ", " + (playerY - 1)));
    }

}