package com.minecraft.game.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;
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
    private BlockController mockBlockController;
    private InventoryController mockInventoryController;
    private Stage mockStage;
    private Button mockButton;
    private Stage mockMenuStage;
    private Button mockMenuButton;

    @BeforeEach
    public void setUp() {
        mockModel = mock(ControllableMinecraftModel.class);
        mockView = mock(MinecraftView.class);

        // Mock stage for help screen
        mockStage = mock(Stage.class);
        when(mockView.getHelpScreenStage()).thenReturn(mockStage);

        mockButton = mock(Button.class);
        when(mockStage.getActors()).thenReturn(new Array<>(new Actor[] { mockButton }));

        // Mock stage for menu screen
        mockMenuStage = mock(Stage.class);
        when(mockView.getMenuScreenStage()).thenReturn(mockMenuStage);

        mockMenuButton = mock(Button.class);
        when(mockMenuStage.getActors()).thenReturn(new Array<>(new Actor[] { mockMenuButton }));

        minecraftController = new MinecraftController(mockModel, mockView);

        OrthographicCamera mockCamera = mock(OrthographicCamera.class);
        when(mockView.getCamera()).thenReturn(mockCamera);
        
        mockPlayerController = mock(PlayerController.class);
        minecraftController.setPlayerController(mockPlayerController);
        
        mockBlockController = mock(BlockController.class);
        minecraftController.setBlockController(mockBlockController);

        mockInventoryController = mock(InventoryController.class);
        minecraftController.setInventoryController(mockInventoryController);

    }

    @Test
    public void testKeyDownF() {
        assertTrue(minecraftController.keyDown(Keys.F));
        verify(mockView).toggleFullscreen();
    }

    @Test
    public void testKeyDownPInGameActive() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        assertTrue(minecraftController.keyDown(Keys.P));
        verify(mockModel, times(2)).getGameState();
        verify(mockModel).setGameState(GameState.GAME_PAUSED);
        verify(mockView).updateScreen();
    }

    @Test
    public void testKeyDownPInGamePaused() {

        when(mockModel.getGameState()).thenReturn(GameState.GAME_PAUSED);

        assertTrue(minecraftController.keyDown(Keys.P));
        verify(mockModel, times(2)).getGameState();
        verify(mockModel).setGameState(GameState.GAME_ACTIVE);
        verify(mockView).updateScreen();
    }

    @Test
    public void testKeyDownRInGameActive() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        assertTrue(minecraftController.keyDown(Keys.R));
        verify(mockModel, times(2)).getGameState();
        verify(mockModel).restartGame();
        verify(mockView).newGameScreen();
    }

    @Test
    public void testKeyDownRInGameWon() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_WON);

        assertTrue(minecraftController.keyDown(Keys.R));
        verify(mockModel, times(2)).getGameState();
        verify(mockModel).restartGame();
        verify(mockView).newGameScreen();
    }

    @Test
    public void testKeyDownRInGamePaused() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_PAUSED);

        assertTrue(minecraftController.keyDown(Keys.R));
        verify(mockModel, times(2)).getGameState();
        verify(mockModel).restartGame();
        verify(mockView).newGameScreen();
    }

    @Test
    public void testKeyDownRInCraftingScreen() {
        when(mockModel.getGameState()).thenReturn(GameState.CRAFTING_SCREEN);

        assertTrue(minecraftController.keyDown(Keys.R));
        verify(mockModel, times(2)).getGameState();
        verify(mockModel).restartGame();
        verify(mockView).newGameScreen();
    }

    @Test
    public void testKeyDownRInGameOver() {

        when(mockModel.getGameState()).thenReturn(GameState.GAME_OVER);

        assertTrue(minecraftController.keyDown(Keys.R));
        verify(mockModel, times(2)).getGameState();
        verify(mockModel).restartGame();
        verify(mockView).newGameScreen();

    }
    
    @Test
    public void testKeyUpInGameActive() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        assertTrue(minecraftController.keyUp(Keys.A));
        verify(mockModel, times(2)).getGameState();
        verify(mockPlayerController).handleKeyUp(Keys.A);
    }

    @Test
    public void testTouchDownInGameActive() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        assertTrue(minecraftController.touchDown(100, 100, 0, Buttons.LEFT));
        verify(mockModel, times(2)).getGameState();
        verify(mockBlockController).handleTouchDown(100, 100, Buttons.LEFT);
    }

    @Test
    public void testTouchUpInGameActive() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);

        assertTrue(minecraftController.touchUp(100, 100, 0, Buttons.LEFT));
        verify(mockModel, times(2)).getGameState();
        verify(mockBlockController).handleTouchUp();
    }

    @Test
    public void testTouchDraggedInGameActiveWithRightButton() {

        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);
        when(mockBlockController.handleTouchDown(anyInt(), anyInt(), anyInt())).thenReturn(true);

        Gdx.input = mock(Input.class);
        when(Gdx.input.isButtonPressed(Buttons.RIGHT)).thenReturn(true);
        

        assertTrue(minecraftController.touchDragged(100, 100, 1));
        verify(mockModel, times(2)).getGameState();
        verify(mockBlockController).handleTouchDown(100, 100, Buttons.RIGHT);
    }

    @Test
    public void testTouchDraggedInGameActiveWithLeftButton() {
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);
        when(mockBlockController.handleTouchDown(anyInt(), anyInt(), anyInt())).thenReturn(true);
        
        Gdx.input = mock(Input.class);
        when(Gdx.input.isButtonPressed(Buttons.RIGHT)).thenReturn(false);
        when(Gdx.input.isButtonPressed(Buttons.LEFT)).thenReturn(true);
        
        assertTrue(minecraftController.touchDragged(100, 100, 0));
        verify(mockModel, times(2)).getGameState();
        verify(mockBlockController).handleTouchDown(100, 100, Buttons.LEFT);
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