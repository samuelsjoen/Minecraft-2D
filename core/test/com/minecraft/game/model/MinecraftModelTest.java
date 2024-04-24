package com.minecraft.game.model;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.LibgdxUnitTest;
import com.minecraft.game.model.Player.State;
import com.minecraft.game.model.crafting.Inventory;
import com.minecraft.game.model.crafting.Item;
import com.minecraft.game.model.map.MinecraftMapTest;
import com.minecraft.game.model.map.TileType;
import com.minecraft.game.utils.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mockingDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

public class MinecraftModelTest extends LibgdxUnitTest {

    MinecraftModel minecraftModel;
    Player player;
    TiledMap tiledMap;
    TiledMapTileLayer tiledMapLayer;
    MapLayer objectLayer;
    World world;

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
            // Need to call update, or else the player.getX() and player.getY() will have
            // the default values from the Body class (which are (0, 1)).
            player.update(0.1f);
            world = minecraftModel.getWorld();
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

    @Test
    public void testGetPlayerCoordinates() {
        int playerX = (int) player.getX() / Constants.TILE_SIZE;
        int playerY = (int) player.getY() / Constants.TILE_SIZE;
        assertEquals(playerX, 7);
        assertEquals(playerY, 49);
    }

    @Test
    public void testPlayerJump() {
        // The player's initial velocity
        float initialVelocityY = player.getBody().getLinearVelocity().y;
        assertEquals(initialVelocityY, 0.0f, 0.01f);
        // Make the player jump
        minecraftModel.playerJump();
        world.step(1 / 60f, 6, 2);
        // The player's velocity after jumping
        float updatedVelocityY = player.getBody().getLinearVelocity().y;
        // Assert that velocity has increased after jumping
        assert (updatedVelocityY > initialVelocityY);

        // Jump a second time
        minecraftModel.playerJump();
        world.step(1 / 60f, 6, 2);
        float updatedVelocityY2 = player.getBody().getLinearVelocity().y;
        // The player's velocity after jumping a second time should be the same as
        // jumping the first time
        assert (updatedVelocityY2 == updatedVelocityY);

        // Jump a third time (this should not be possible)
        minecraftModel.playerJump();
        world.step(1 / 60f, 6, 2);
        // The player's velocity after jumping a third time should be less than after
        // jumping a second time, because the player should not be able to jump anymore
        float updatedVelocityY3 = player.getBody().getLinearVelocity().y;
        assert (updatedVelocityY2 > updatedVelocityY3);

        // Jump a fourth time (this should not be possible)
        minecraftModel.playerJump();
        world.step(1 / 60f, 6, 2);
        // The player's velocity after jumping
        float updatedVelocityY4 = player.getBody().getLinearVelocity().y;
        // The player's velocity should continue to decline as the player is approaching
        // the ground.
        assert (updatedVelocityY3 > updatedVelocityY4);
    }

    @Test
    public void testRestartGame() {
        try (MockedConstruction<OrthogonalTiledMapRenderer> mocked = Mockito
                .mockConstruction(OrthogonalTiledMapRenderer.class)) {
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

    @Test
    public void testRemoveBlock() {
        // 0,0 should have a block
        assertNotNull(tiledMapLayer.getCell(0, 0));
        assertNotNull(objectLayer.getObjects().get("0, 0"));
        minecraftModel.removeBlock(0, 0);
        // 0,0 should now have no block
        assertNull(tiledMapLayer.getCell(0, 0));
        assertNull(objectLayer.getObjects().get("0, 0"));
    }

    @Test
    public void testAddBlockAtPlayer() {
        // Get the player's coordinates
        int playerX = (int) player.getX() / Constants.TILE_SIZE;
        int playerY = (int) player.getY() / Constants.TILE_SIZE;

        // Placing a block on the top of the player with the coordinates playerX and
        // playerY

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

    // I don't think the method is used anywhere in the code?
    // @Test
    // public void testSetDayNightCycle() {
    // DayNightCycle dayNightCycle = new DayNightCycle();
    // minecraftModel.setDayNightCycle(dayNightCycle);
    // assertEquals(dayNightCycle, minecraftModel.getDayNightCycle());
    // }

    @Test
    public void testSetGameState() {
        assertEquals(GameState.WELCOME_SCREEN, minecraftModel.getGameState());
        minecraftModel.setGameState(GameState.GAME_PAUSED);
        assertEquals(GameState.GAME_PAUSED, minecraftModel.getGameState());
    }

    @Test
    public void testCheckAndUpdateGamestate() {
        minecraftModel.checkAndUpdateGameState();
        assertEquals(GameState.WELCOME_SCREEN, minecraftModel.getGameState());
        player.setCurrentState(State.DEAD);
        minecraftModel.checkAndUpdateGameState();
        assertEquals(GameState.GAME_OVER, minecraftModel.getGameState());

        player.setCurrentState(State.IDLE);
        DayNightCycle dayNightCycle = minecraftModel.getDayNightCycle();
        // Run the dayNightCycle multiple times to simulate surviving 3 nights
        for (int i = 0; i < 6; i++) {
            dayNightCycle.run();
        }
        minecraftModel.checkAndUpdateGameState();
        assertEquals(GameState.GAME_WON, minecraftModel.getGameState());
    }

    @Test
    public void testGetPlayerState() {
        player.setCurrentState(State.RUNNING);
        assertEquals(State.RUNNING, minecraftModel.getPlayerState());
        player.setCurrentState(State.DEAD);
        assertEquals(State.DEAD, minecraftModel.getPlayerState());
    }

    // # TODO Implement this method
    @Test
    public void testCraftItem() {

    }

    // # TODO Implement this method
    @Test
    public void testToggleCrafting() {

    }

    // # TODO Implement this method
    @Test
    public void testDropInventoryItem() {

    }

    // # TODO Implement this method
    @Test
    public void testMoveCraftableTableSelection() {

    }

    // # TODO Implement this method
    @Test
    public void testIsBlockMineable() {

    }

    // # TODO Implement this method
    @Test
    public void testUpdateMovement() {
        
    }

    @Test
    public void testRevivePlayer() {
        player.setCurrentState(State.DEAD);
        minecraftModel.revivePlayer();
        assertEquals(State.IDLE, player.getCurrentState());
    }

    @Test
    public void testChangeInventorySlot() {
        Inventory inventory = minecraftModel.getInventory();
        // Get current item that is selected in inventory
        Item selectedItem = inventory.getSelectedItem();
        Item selectedItem2 = null;
        // There is supposed to already be a wooden pickaxe in the inventory at the
        // beginning of the game
        if (inventory.contains(Item.WOODEN_PICKAXE)) {
            minecraftModel.changeInventorySlot(-1);
            // Change inventory slot until the wooden pickaxe is selected
            while (inventory.getSelectedItem() != Item.WOODEN_PICKAXE) {
                minecraftModel.changeInventorySlot(1);
            }
            // The selected item should be the wooden pickaxe
            selectedItem2 = inventory.getSelectedItem();
        }

        // By changing the inventoryslot, the item selected should change
        assertNotEquals(selectedItem, selectedItem2);
    }

    @Test
    public void testGetTileDamage() {
        float damage = minecraftModel.getTileDamage(0, 0);
        // code to get TileType from the 0,0 coordinates
        int tileId = tiledMapLayer.getCell(0, 0).getTile().getId();
        TileType tiletype = TileType.getTileTypeWithId(tileId);

        assertEquals(tiletype.getBaseDamage(), damage);

    }

    // # TODO This test is currently identical to the one in MinecraftmapTest.
    // But it is needed to test this in minecraftModel because some private methods
    // are called.
    @Test
    public void testAddBlock() {
        // int mapHeight = tiledMapLayer.getHeight() - 1;

        // // There should be no tile nor object at the given coordinates
        // assertNull(tiledMapLayer.getCell(0, mapHeight));
        // assertNull(objectLayer.getObjects().get("0, " + mapHeight));

        // // Add a block
        // minecraftModel.addBlock(0, mapHeight);

        // // The object should be present with the given coordinates
        // int worldX = 0 * Constants.TILE_SIZE;
        // int worldY = mapHeight * Constants.TILE_SIZE;

        // // The grass tile and object should be present
        // assertNotNull(tiledMapLayer.getCell(0, mapHeight));
        // assertNotNull(objectLayer.getObjects().get(worldX + ", " + worldY));
    }

}