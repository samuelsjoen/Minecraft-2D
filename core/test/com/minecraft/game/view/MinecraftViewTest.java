package com.minecraft.game.view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.minecraft.game.LibgdxUnitTest;
import com.minecraft.game.Minecraft;
import com.minecraft.game.model.GameState;
import com.minecraft.game.model.Player;
import com.minecraft.game.view.screens.GameOverScreen;
import com.minecraft.game.view.screens.GameScreen;
import com.minecraft.game.view.screens.GameWonScreen;
import com.minecraft.game.view.screens.HelpScreen;
import com.minecraft.game.view.screens.MenuScreen;
import com.minecraft.game.view.screens.PausedScreen;

import com.minecraft.game.model.EnemyManager;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Projectile;
import com.minecraft.game.model.entities.Slime;

import com.minecraft.game.model.Score;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MinecraftViewTest extends LibgdxUnitTest {

    @Mock
    private Minecraft mockGame;

    @Mock
    private ViewableMinecraftModel mockModel;

    private MinecraftView minecraftView;

    private SpriteBatch spriteBatch;

    private BitmapFont font;

    @Mock
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mocking SpriteBatch 
        spriteBatch = mock(SpriteBatch.class);

        // Mocking font
        font = mock(BitmapFont.class);

        BitmapFont.BitmapFontData fontData = mock(BitmapFont.BitmapFontData.class);
        when(font.getData()).thenReturn(fontData);

        // Mock the OrthogonalTiledMapRenderer
        OrthogonalTiledMapRenderer mockRenderer = mock(OrthogonalTiledMapRenderer.class);

        // Stub the dispose() method to do nothing
        doNothing().when(mockRenderer).dispose();

        // When getMapRenderer() is called on the mock model, return the mock renderer
        when(mockModel.getMapRenderer()).thenReturn(mockRenderer);
        when(mockModel.getPlayer()).thenReturn(mockPlayer);

        Score mockScore = mock(Score.class);
        when(mockModel.getScore()).thenReturn(mockScore);

        try (MockedConstruction<Box2DDebugRenderer> mockedBox2D = Mockito.mockConstruction(Box2DDebugRenderer.class);
            MockedConstruction<Stage> mockedStage = Mockito.mockConstruction(Stage.class);
            MockedConstruction<EnemyManager> enemyManager = Mockito.mockConstruction(EnemyManager.class);
            MockedConstruction<Knight> knight = Mockito.mockConstruction(Knight.class);
            MockedConstruction<Slime> slime = Mockito.mockConstruction(Slime.class);
            MockedConstruction<PinkMonster> pinkMonster = Mockito.mockConstruction(PinkMonster.class);
            MockedConstruction<Projectile> projectile = Mockito.mockConstruction(Projectile.class)) {
            // When GameScreen tries to create a Box2DDebugRenderer, it will get the mock instead
            // Similarily for HelpScreen which uses Stage. 
            minecraftView = new MinecraftView(mockGame, mockModel, spriteBatch, font);
}
    }

    @Test
    void constructor_shouldCreateAllScreens() {
        assertNotNull(minecraftView.getMenuScreen());
        assertNotNull(minecraftView.getHelpScreen());
        assertNotNull(minecraftView.getGameScreen());
        assertNotNull(minecraftView.getPausedScreen());
        assertNotNull(minecraftView.getGameOverScreen());
        assertNotNull(minecraftView.getGameWonScreen());
    }

    @Test
    void newGameScreen_shouldDisposePreviousGameScreenAndCreateNewOne() {
        try (MockedConstruction<Box2DDebugRenderer> mockedBox2D = Mockito.mockConstruction(Box2DDebugRenderer.class);
            MockedConstruction<EnemyManager> enemyManager = Mockito.mockConstruction(EnemyManager.class);
            MockedConstruction<Knight> knight = Mockito.mockConstruction(Knight.class);
            MockedConstruction<Slime> slime = Mockito.mockConstruction(Slime.class);
            MockedConstruction<PinkMonster> pinkMonster = Mockito.mockConstruction(PinkMonster.class);
            MockedConstruction<Projectile> projectile = Mockito.mockConstruction(Projectile.class)) {

                GameScreen oldGameScreen = minecraftView.getGameScreen();
                minecraftView.newGameScreen();
                GameScreen newGameScreen = minecraftView.getGameScreen();
    
                assertNotSame(oldGameScreen, newGameScreen);
        }    
    }

    @Test
    void testUpdateScreen() {
        GameScreen gameScreen = mock(GameScreen.class);
        minecraftView.setGameScreen(gameScreen);

        MenuScreen menuScreen = mock(MenuScreen.class);
        minecraftView.setMenuScreen(menuScreen);

        HelpScreen helpScreen = mock(HelpScreen.class);
        minecraftView.setHelpScreen(helpScreen);

        PausedScreen pausedScreen = mock(PausedScreen.class);
        minecraftView.setPausedScreen(pausedScreen);

        GameOverScreen gameOverScreen = mock(GameOverScreen.class);
        minecraftView.setGameOverScreen(gameOverScreen);

        GameWonScreen gameWonScreen = mock(GameWonScreen.class);
        minecraftView.setGameWonScreen(gameWonScreen);

        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);
        minecraftView.updateScreen();
        verify(mockGame).setScreen(gameScreen);

        when(mockModel.getGameState()).thenReturn(GameState.HELP_SCREEN);
        minecraftView.updateScreen();
        verify(mockGame).setScreen(helpScreen);

        when(mockModel.getGameState()).thenReturn(GameState.GAME_PAUSED);
        minecraftView.updateScreen();
        verify(mockGame).setScreen(pausedScreen);

        when(mockModel.getGameState()).thenReturn(GameState.GAME_OVER);
        minecraftView.updateScreen();
        verify(mockGame).setScreen(gameOverScreen);

        when(mockModel.getGameState()).thenReturn(GameState.GAME_WON);
        minecraftView.updateScreen();
        verify(mockGame).setScreen(gameWonScreen);

        // Act
        when(mockModel.getGameState()).thenReturn(GameState.WELCOME_SCREEN);
        minecraftView.updateScreen();
        // Assert
        verify(mockGame).setScreen(menuScreen); 
    }

    @Test
    void testDispose() {
        GameScreen gameScreen = mock(GameScreen.class);
        minecraftView.setGameScreen(gameScreen);

        MenuScreen menuScreen = mock(MenuScreen.class);
        minecraftView.setMenuScreen(menuScreen);

        HelpScreen helpScreen = mock(HelpScreen.class);
        minecraftView.setHelpScreen(helpScreen);

        PausedScreen pausedScreen = mock(PausedScreen.class);
        minecraftView.setPausedScreen(pausedScreen);

        GameOverScreen gameOverScreen = mock(GameOverScreen.class);
        minecraftView.setGameOverScreen(gameOverScreen);

        GameWonScreen gameWonScreen = mock(GameWonScreen.class);
        minecraftView.setGameWonScreen(gameWonScreen);

        minecraftView.dispose();

        verify(spriteBatch).dispose();
        verify(font).dispose();
        verify(menuScreen).dispose();
        verify(helpScreen).dispose();
        verify(pausedScreen).dispose();
        verify(gameScreen).dispose();
        verify(gameOverScreen).dispose();
    }
}
