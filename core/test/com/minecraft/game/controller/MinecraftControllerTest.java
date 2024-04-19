package com.minecraft.game.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.minecraft.game.LibgdxUnitTest;
import com.minecraft.game.model.GameState;
import com.minecraft.game.view.MinecraftView;

public class MinecraftControllerTest extends LibgdxUnitTest {

    private MinecraftController minecraftController;
    private ControllableMinecraftModel mockModel;
    private MinecraftView mockView;
    private PlayerController mockPlayerController;
    private BlockPlacementController mockBlockPlacementController;
    private InventoryController mockInventoryController;

    @BeforeEach
    public void setUp() {
        mockModel = mock(ControllableMinecraftModel.class);
        mockView = mock(MinecraftView.class);

        minecraftController = new MinecraftController(mockModel, mockView);

        OrthographicCamera mockCamera = mock(OrthographicCamera.class);
        when(mockView.getCamera()).thenReturn(mockCamera);
        
        mockPlayerController = mock(PlayerController.class);
        minecraftController.setPlayerController(mockPlayerController);
        
        mockBlockPlacementController = mock(BlockPlacementController.class);
        minecraftController.setBlockPlacementController(mockBlockPlacementController);

        mockInventoryController = mock(InventoryController.class);
        minecraftController.setInventoryController(mockInventoryController);

    }

    @Test
    public void testKeyDownEscape() {
        assertTrue(minecraftController.keyDown(Keys.ESCAPE));
        verifyNoInteractions(mockModel);
        verifyNoInteractions(mockView);
    }

    @Test
    public void testKeyDownF() {
        assertTrue(minecraftController.keyDown(Keys.F));
        verify(mockView).toggleFullscreen();
    }

    @Test
    public void testKeyDownSInHelpScreen() {
        when(mockModel.getGameState()).thenReturn(GameState.HELP_SCREEN);

        assertTrue(minecraftController.keyDown(Keys.S));
        verify(mockModel).getGameState();
        verify(mockModel).setGameState(GameState.GAME_ACTIVE);
        verify(mockView).updateScreen();
    }

    @Test
    public void testKeyDownPInGameActive() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        assertTrue(minecraftController.keyDown(Keys.P));
        verify(mockModel).getGameState();
        verify(mockModel).setGameState(GameState.GAME_PAUSED);
        verify(mockView).updateScreen();
    }

    @Test
    public void testKeyDownPInGamePaused() {

        when(mockModel.getGameState()).thenReturn(GameState.GAME_PAUSED);

        assertTrue(minecraftController.keyDown(Keys.P));
        verify(mockModel).getGameState();
        verify(mockModel).setGameState(GameState.GAME_ACTIVE);
        verify(mockView).updateScreen();
    }

    @Test
    public void testKeyDownRInGameActive() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        assertTrue(minecraftController.keyDown(Keys.R));
        verify(mockModel).getGameState();
        verify(mockModel).restartGame();
        verify(mockView).newGameScreen();
    }

    @Test
    public void testKeyDownRInGameWon() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_WON);

        assertTrue(minecraftController.keyDown(Keys.R));
        verify(mockModel).getGameState();
        verify(mockModel).restartGame();
        verify(mockView).newGameScreen();
    }

    @Test
    public void testKeyDownRInGamePaused() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_PAUSED);

        assertTrue(minecraftController.keyDown(Keys.R));
        verify(mockModel).getGameState();
        verify(mockModel).restartGame();
        verify(mockView).newGameScreen();
    }

    @Test
    public void testKeyDownRInCraftingScreen() {
        when(mockModel.getGameState()).thenReturn(GameState.CRAFTING_SCREEN);

        assertTrue(minecraftController.keyDown(Keys.R));
        verify(mockModel).getGameState();
        verify(mockModel).restartGame();
        verify(mockView).newGameScreen();
    }

    @Test
    public void testKeyDownRInGameOver() {

        when(mockModel.getGameState()).thenReturn(GameState.GAME_OVER);

        assertTrue(minecraftController.keyDown(Keys.R));
        verify(mockModel).getGameState();
        verify(mockModel).restartGame();
        verify(mockView).newGameScreen();

    }

    @Test
    public void testKeyDownHInGameActive() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        assertTrue(minecraftController.keyDown(Keys.H));
        verify(mockModel).getGameState();
        verify(mockModel).revivePlayer();
        verify(mockModel).setGameState(GameState.GAME_ACTIVE);
        verify(mockView).updateScreen();
    }

    @Test
    public void testKeyDownHInCraftingScreen() {
        when(mockModel.getGameState()).thenReturn(GameState.CRAFTING_SCREEN);

        assertTrue(minecraftController.keyDown(Keys.H));
        verify(mockModel).getGameState();
        verify(mockModel).revivePlayer();
        verify(mockModel).setGameState(GameState.GAME_ACTIVE);
        verify(mockView).updateScreen();
    }


    @Test
    public void testKeyDownHInGameWonOrGameOver() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_WON);

        assertTrue(minecraftController.keyDown(Keys.H));
        verify(mockModel).getGameState();
        verify(mockModel).revivePlayer();
        verify(mockModel).setGameState(GameState.GAME_ACTIVE);
        verify(mockView).updateScreen();
    }

    @Test
    public void testKeyDownHInGameOver() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_OVER);

        assertTrue(minecraftController.keyDown(Keys.H));
        verify(mockModel).getGameState();
        verify(mockModel).revivePlayer();
        verify(mockModel).setGameState(GameState.GAME_ACTIVE);
        verify(mockView).updateScreen();
    }


    @Test
    public void testKeyUpInGameActive() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        assertTrue(minecraftController.keyUp(Keys.A));
        verify(mockModel).getGameState();
        verify(mockPlayerController).handleKeyUp(Keys.A);
    }

    @Test
    public void testTouchDownInWelcomeScreen() {
        when(mockModel.getGameState()).thenReturn(GameState.WELCOME_SCREEN);
        when(mockView.isStartButtonClicked(anyFloat(), anyFloat())).thenReturn(true);

        minecraftController.touchDown(100, 100, 0, Buttons.LEFT);
        verify(mockModel).getGameState();
        verify(mockModel).setGameState(GameState.GAME_ACTIVE);
        verify(mockView).updateScreen();
    }

    @Test
    public void testTouchDownInGameActive() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        assertTrue(minecraftController.touchDown(100, 100, 0, Buttons.LEFT));
        verify(mockModel).getGameState();
        verify(mockBlockPlacementController).handleTouchDown(100, 100, Buttons.LEFT);
    }

    @Test
    public void testTouchUpInGameActive() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        assertTrue(minecraftController.touchUp(100, 100, 0, Buttons.LEFT));
        verify(mockModel).getGameState();
        verify(mockBlockPlacementController).handleTouchUp();
    }

    @Test
    public void testTouchDraggedInGameActiveWithRightButton() {

        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);
        when(mockBlockPlacementController.handleTouchDown(anyInt(), anyInt(), anyInt())).thenReturn(true);

        Gdx.input = mock(Input.class);
        when(Gdx.input.isButtonPressed(Buttons.RIGHT)).thenReturn(true);
        

        assertTrue(minecraftController.touchDragged(100, 100, 1));
        verify(mockModel, times(1)).getGameState();
        verify(mockBlockPlacementController).handleTouchDown(100, 100, Buttons.RIGHT);
    }

    @Test
    public void testTouchDraggedInGameActiveWithLeftButton() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);
        when(mockBlockPlacementController.handleTouchDown(anyInt(), anyInt(), anyInt())).thenReturn(true);
        
        Gdx.input = mock(Input.class);
        when(Gdx.input.isButtonPressed(Buttons.RIGHT)).thenReturn(false);
        when(Gdx.input.isButtonPressed(Buttons.LEFT)).thenReturn(true);
        
        assertTrue(minecraftController.touchDragged(100, 100, 0));
        verify(mockModel, times(1)).getGameState();
        verify(mockBlockPlacementController).handleTouchDown(100, 100, Buttons.LEFT);
    }

    @Test
    public void testHandleWelcomeScreenTouchStartButtonClicked() {
        when(mockView.isStartButtonClicked(anyFloat(), anyFloat())).thenReturn(true);

        minecraftController.handleWelcomeScreenTouch();
        verify(mockModel).setGameState(GameState.GAME_ACTIVE);
        verify(mockView).updateScreen();
    }

    @Test
    public void testHandleWelcomeScreenTouchHelpButtonClicked() {
        when(mockView.isHelpButtonClicked(anyFloat(), anyFloat())).thenReturn(true);

        minecraftController.handleWelcomeScreenTouch();
        verify(mockModel).setGameState(GameState.HELP_SCREEN);
        verify(mockView).updateScreen();
    }

    @Test
    public void testHandleWelcomeScreenTouchQuitButtonClicked() {
        when(mockView.isQuitButtonClicked(anyFloat(), anyFloat())).thenReturn(true);

        minecraftController.handleWelcomeScreenTouch();
        verify(mockModel, never()).setGameState(any(GameState.class));
        verify(mockView, never()).updateScreen();
    }

    @Test
    public void testSetGameStateAndUpdateScreen() {
        minecraftController.setGameStateAndUpdateScreen(GameState.GAME_ACTIVE);
        verify(mockModel).setGameState(GameState.GAME_ACTIVE);
        verify(mockView).updateScreen();
    }

    @Test
    public void testTogglePauseGameActive() {
        minecraftController.togglePause(GameState.GAME_ACTIVE);
        verify(mockModel).setGameState(GameState.GAME_PAUSED);
        verify(mockView).updateScreen();
    }

    @Test
    public void testTogglePauseGamePaused() {
        minecraftController.togglePause(GameState.GAME_PAUSED);
        verify(mockModel).setGameState(GameState.GAME_ACTIVE);
        verify(mockView).updateScreen();
    }
}