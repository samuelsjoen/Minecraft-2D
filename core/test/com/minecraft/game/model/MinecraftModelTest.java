package com.minecraft.game.model;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.LibgdxUnitTest;
import com.minecraft.game.model.Player.State;
import com.minecraft.game.model.items.Inventory;
import com.minecraft.game.model.items.Item;
import com.minecraft.game.model.map.TileType;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.CursorUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    int playerX;
    int playerY;
    Inventory inventory;

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
            playerX = (int) player.getX() / Constants.TILE_SIZE;
            playerY = (int) player.getY() / Constants.TILE_SIZE;
            world = minecraftModel.getWorld();
            inventory = minecraftModel.getInventory();
            // We want an empty inventory
            inventory.clearInventory();
        }
    }

    @Test
    public void testCraftItem() {
        // Testing that it should be possible to craft a stick
        // We start with empty inventory - so need to add some items to craft
        inventory.addItem(Item.WOOD, 2);
        Item selectedItem = inventory.getSelectedItem();

        // crafting the item
        minecraftModel.getCrafting().toggleOpen();
        minecraftModel.craftItem();

        Item newSelectedItem = inventory.getSelectedItem(); // Should be Item.STICK 
        int newSelectedItemAmount = inventory.getAmount(newSelectedItem);

        // Assert that the selected item has changed after crafting
        assertTrue(selectedItem == Item.WOOD);
        assertTrue(newSelectedItem == Item.STICK);

        assertEquals(1, newSelectedItemAmount);

        // Checking that there is no wood in the inventory now
        assertEquals(0, inventory.getAmount(Item.WOOD));
    }

    @Test
    public void testMoveCraftableTableSelection() {
        // We start with empty inventory - so need to add some items to craft
        inventory.addItem(Item.WOOD, 2);

        // crafting the item
        minecraftModel.getCrafting().toggleOpen();
        minecraftModel.moveCraftableTableSelection(1, 0);
        minecraftModel.craftItem(); // Now nothing should be crafted

        // So we should not have stick in inventory
        assertEquals(0, inventory.getAmount(Item.STICK));

        // Now when we move the selector back - we should be able to craft a stick
        minecraftModel.moveCraftableTableSelection(-1, 0);
        minecraftModel.craftItem();
        assertEquals(1, inventory.getAmount(Item.STICK));  
    }

    @Test
    public void testConstructor() {
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

    @Test
    public void testAddBlockWithEmptyInventory() {
        assertNull(tiledMapLayer.getCell(playerX+1, playerY));
        minecraftModel.addBlock(playerX+1, playerY);
        assertNull(tiledMapLayer.getCell(playerX+1, playerY));
    }

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

    @Test
    public void testToggleCrafting() {
        Boolean craftingUnopened = minecraftModel.getCrafting().isOpen();
        minecraftModel.toggleCrafting();
        Boolean craftingOpened = minecraftModel.getCrafting().isOpen();
        assertNotEquals(craftingUnopened, craftingOpened);
    }

    @Test
    public void testDropInventoryItem() {
        // Add an item to the inventory
        inventory.addItem(Item.DIRT);

        // Get the amount of the item in the inventory
        Item selectedItem = inventory.getSelectedItem();
        int selectedItemAmount = inventory.getAmount(selectedItem);

        minecraftModel.dropInventoryItem();

        // Check if the item has been removed from the inventory
        int selectedItemAmountAfterDrop = inventory.getAmount(selectedItem);

        // Assert that the amount of the item in the inventory has decreased after dropping the item
        assertNotEquals(selectedItemAmount, selectedItemAmountAfterDrop);
    }

    @Test
    public void testIsBlockMineable() {

        // Testing a tile that should be mineable
        Boolean isMineable = minecraftModel.isBlockMineable(playerY+1, playerX+1);
        assertEquals(true, isMineable);

        // Testing a tile that should not be mineable
        int mapHeight = tiledMapLayer.getHeight() - 1;
        Boolean isNotMineable = minecraftModel.isBlockMineable(0, mapHeight);
        assertEquals(false, isNotMineable);

        assertNotEquals(isMineable, isNotMineable);

    }

    @Test
    public void testUpdateMovement() {
        // Testing no movement
        minecraftModel.updateMovement(false, false, false);
        player.update(0.1f);
        // Then player should not move
        assertEquals(0, player.getBody().getLinearVelocity().x, 0.01f);

        // Testing movement to the left
        minecraftModel.updateMovement(true, false, false);
        player.update(0.1f);
        // Then player should move to the left
        assertNotEquals(0, player.getBody().getLinearVelocity().x);

        // Testing movement to the right
        minecraftModel.updateMovement(false, true, false);
        player.update(0.1f);
        // Then player should move to the right
        assertNotEquals(0, player.getBody().getLinearVelocity().x);

        // Testing attacking
        State previousState = player.getCurrentState(); // should be State.Idle
        minecraftModel.updateMovement(false, false, true);
        player.update(0.1f);
        // Then player should be in the attacking state
        State newState = player.getCurrentState();
        assertNotEquals(previousState, newState);
        assertEquals(State.ATTACKING, newState);  
    }

    @Test
    public void testChangeInventorySlot() {
        inventory.addItem(Item.DIRT);
        inventory.addItem(Item.STONE);
        // Get current item that is selected in inventory
        Item selectedItem = inventory.getSelectedItem();

        minecraftModel.changeInventorySlot(1);
        Item selectedItem2 = inventory.getSelectedItem();

        // Checks if the selected item is not the same as the previous one (since we
        // should have changed slots)
        assertNotEquals(selectedItem, selectedItem2);

        // Check current cursor
        String currentCursor = CursorUtils.getCurrentCursorImagePath();

        // Adds a wooden pickaxe to the inventory
        inventory.addItem(Item.WOODEN_PICKAXE);
        // Change inventory slot until the wooden pickaxe is selected
        while (inventory.getSelectedItem() != Item.WOODEN_PICKAXE) {
            minecraftModel.changeInventorySlot(-1);
        }

        String newCursor = CursorUtils.getCurrentCursorImagePath();

        // Check if the cursor has changed after selecting a pickaxe
        assertNotEquals(currentCursor, newCursor);
    }

    @Test
    public void testGetTileDamage() {
        inventory.addItem(Item.DIRT);
        float damage = minecraftModel.getTileDamage(0, 0);
        // code to get TileType from the 0,0 coordinates
        int tileId = tiledMapLayer.getCell(0, 0).getTile().getId();
        TileType tiletype = TileType.getTileTypeWithId(tileId);

        assertEquals(tiletype.getBaseDamage(), damage);
    }

    @Test
    public void testAddBlock() {
        // Add a block to the inventory for testing
        inventory.addItem(Item.DIRT);
        assertNull(tiledMapLayer.getCell(playerX+1, playerY));
        minecraftModel.addBlock(playerX+1, playerY);
        assertNotNull(tiledMapLayer.getCell(playerX+1, playerY));
    }

}