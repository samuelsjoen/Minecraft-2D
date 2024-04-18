package com.minecraft.game.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.minecraft.game.LibgdxUnitTest;
import com.minecraft.game.model.GameState;
import com.badlogic.gdx.Input.Keys;

public class InventoryControllerTest extends LibgdxUnitTest  {

    private ControllableMinecraftModel mockModel;
    private InventoryController controller;

    @BeforeEach
    public void setUp() {
        mockModel = mock(ControllableMinecraftModel.class);
        controller = new InventoryController(mockModel);
    }

    @Test
    public void testHandleKeyWithNotRelevantGameState() {
        when(mockModel.getGameState()).thenReturn(GameState.WELCOME_SCREEN);

        boolean result = controller.handleKeyDown(Keys.A);

        assertFalse(result);
    }

    @Test
    public void testHandleKeyDown_GameActive_Left() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        boolean result = controller.handleKeyDown(Keys.LEFT);

        verify(mockModel).changeInventorySlot(-1);
        assertTrue(result);
    }

    @Test
    public void testHandleKeyDown_GameActive_Right() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        boolean result = controller.handleKeyDown(Keys.RIGHT);

        verify(mockModel).changeInventorySlot(1);
        assertTrue(result);
    }

    @Test
    public void testHandleKeyDown_GameActive_E() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        boolean result = controller.handleKeyDown(Keys.E);

        verify(mockModel).toggleCrafting();
        verify(mockModel).setGameState(GameState.CRAFTING_SCREEN);
        assertTrue(result);
    }

    @Test
    public void testHandleKeyDown_GameActive_Q() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        boolean result = controller.handleKeyDown(Keys.Q);

        verify(mockModel).dropInventoryItem();
        assertTrue(result);
    }

    @Test
    public void testHandleKeyDown_CraftingScreen_Left() {
        when(mockModel.getGameState()).thenReturn(GameState.CRAFTING_SCREEN);

        boolean result = controller.handleKeyDown(Keys.LEFT);

        verify(mockModel).moveCraftableTableSelection(0, -1);
        assertTrue(result);
    }

    @Test
    public void testHandleKeyDown_CraftingScreen_Right() {
        when(mockModel.getGameState()).thenReturn(GameState.CRAFTING_SCREEN);

        boolean result = controller.handleKeyDown(Keys.RIGHT);

        verify(mockModel).moveCraftableTableSelection(0, 1);
        assertTrue(result);
    }

    @Test
    public void testHandleKeyDown_CraftingScreen_Enter() {
        when(mockModel.getGameState()).thenReturn(GameState.CRAFTING_SCREEN);

        boolean result = controller.handleKeyDown(Keys.ENTER);

        verify(mockModel).craftItem();
        assertTrue(result);
    }

    @Test
    public void testHandleKeyDown_CraftingScreen_Up() {
        when(mockModel.getGameState()).thenReturn(GameState.CRAFTING_SCREEN);

        boolean result = controller.handleKeyDown(Keys.UP);

        verify(mockModel).moveCraftableTableSelection(-1, 0);
        assertTrue(result);
    }

    @Test
    public void testHandleKeyDown_CraftingScreen_Down() {
        when(mockModel.getGameState()).thenReturn(GameState.CRAFTING_SCREEN);

        boolean result = controller.handleKeyDown(Keys.DOWN);

        verify(mockModel).moveCraftableTableSelection(1, 0);
        assertTrue(result);
    }

    @Test
    public void testHandleKeyDown_CraftingScreen_E() {
        when(mockModel.getGameState()).thenReturn(GameState.CRAFTING_SCREEN);

        boolean result = controller.handleKeyDown(Keys.E);

        verify(mockModel).toggleCrafting();
        verify(mockModel).setGameState(GameState.GAME_ACTIVE);
        assertTrue(result);
    }

    @Test
    public void testHandleKeyDown_InvalidKey() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        boolean result = controller.handleKeyDown(Keys.SPACE);

        assertFalse(result);
    }

    @Test
    public void testHandleKeyDown_InvalidKey2() {
        when(mockModel.getGameState()).thenReturn(GameState.CRAFTING_SCREEN);

        boolean result = controller.handleKeyDown(Keys.L);

        assertFalse(result);
    }
}
