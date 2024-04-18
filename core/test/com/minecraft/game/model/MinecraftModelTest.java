package com.minecraft.game.model;

import com.minecraft.game.model.crafting.Inventory;

import com.minecraft.game.model.map.MinecraftMap;
//import com.minecraft.game.model.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;

public class MinecraftModelTest {

    @Mock
    private MinecraftMap mockMap;
    @Mock
    private Player mockPlayer;
    @Mock
    private Inventory mockInventory;
    @Mock    
    private MinecraftModel mockMinecraftModel;

    //private MinecraftModel minecraftModel;

    @BeforeEach
    void setUp() {
        /*MockitoAnnotations.openMocks(this);

        when(mockMinecraftModel.getPlayerRectangle()).thenReturn(new Rectangle(0, 0, 2, 4));
        when(mockMinecraftModel.initializePlayer()).thenReturn(mockPlayer);

        mockMinecraftModel.setGameState(GameState.GAME_ACTIVE);
        mockMinecraftModel.setMap(mockMap);
        mockMinecraftModel.setPlayer(mockPlayer);
        mockMinecraftModel.setInventory(mockInventory);

        PowerMockito.whenNew(MinecraftModel.class).withAnyArguments().thenReturn(mockMinecraftModel);

        minecraftModel = new MinecraftModel();

        //doReturn(new Rectangle(0, 0, 2, 4)).when(minecraftModel).getPlayerRectangle();
        //doReturn(mockPlayer).when(minecraftModel.initializePlayer());*/
    }

    @Test
    void testGetGameState() {
        //assertEquals(GameState.GAME_ACTIVE, minecraftModel.getGameState());
    }
}

/*
    @Test
    void testSetGameState() {
        minecraftModel.setGameState(GameState.GAME_PAUSED);
        assertEquals(GameState.GAME_PAUSED, minecraftModel.getGameState());
    }

    @Test
    void testGetWorld() {
        assertNotNull(minecraftModel.getWorld());
    }

    @Test
    void testGetMapRenderer() {
        assertNotNull(minecraftModel.getMapRenderer());
    }

    @Test
    void testGetTiledMap() {
        assertNotNull(minecraftModel.getTiledMap());
    }

    @Test
    void testGetPlayer() {
        assertNotNull(minecraftModel.getPlayer());
    }

    @Test
    void testGetInventory() {
        assertNotNull(minecraftModel.getInventory());
    }

    @Test
    void testChangeInventorySlot() {
        minecraftModel.changeInventorySlot(-1);
        verify(mockInventory, times(1)).changeSlot(-1);

        minecraftModel.changeInventorySlot(1);
        verify(mockInventory, times(1)).changeSlot(1);
    }

    @Test
    void testDropInventoryItem() {
        minecraftModel.dropInventoryItem();
        verify(mockInventory, times(1)).dropItem();
    }

    // Add more test cases for other methods in MinecraftModel class

*/