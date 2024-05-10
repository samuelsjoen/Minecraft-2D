package com.minecraft.game.view.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.LibgdxUnitTest;
import com.minecraft.game.model.DayNightCycle;
import com.minecraft.game.model.EnemyManager;
import com.minecraft.game.model.GameState;
import com.minecraft.game.model.Player;
import com.minecraft.game.model.Player.State;
import com.minecraft.game.utils.SpriteManager;
import com.minecraft.game.view.ViewableMinecraftModel;
import com.minecraft.game.view.entities.EntityRenderer;
import com.minecraft.game.view.overlay.OverlayView;
import com.minecraft.game.view.MinecraftView;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Projectile;
import com.minecraft.game.model.entities.Slime;
import com.minecraft.game.model.Score;

import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameScreenTest extends LibgdxUnitTest {
    private GameScreen gameScreen;
    private OrthographicCamera camera;
    private ViewableMinecraftModel mockModel;
    private MinecraftView mockView;
    private SpriteBatch mockBatch;
    private Player mockPlayer;
    private SpriteManager mockSpriteManager;
    private OverlayView mockOverlayView;
    private EnemyManager mockEnemyManager;
    private DayNightCycle mockDayNightCycle;
    private EntityRenderer mockEntityRenderer;

    @BeforeEach
    public void setUp() {
        camera = new OrthographicCamera();
        mockModel = mock(ViewableMinecraftModel.class);
        mockView = mock(MinecraftView.class);
        mockBatch = mock(SpriteBatch.class);

        OrthogonalTiledMapRenderer mockRenderer = mock(OrthogonalTiledMapRenderer.class);

        mockPlayer = mock(Player.class);

        // Set up the mock objects

        World mockWorld = mock(World.class);
        when(mockModel.getWorld()).thenReturn(mockWorld);
        when(mockModel.getGameState()).thenReturn(GameState.GAME_ACTIVE);
        when(mockModel.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getCurrentState()).thenReturn(State.IDLE);
        when(mockPlayer.getX()).thenReturn(10f);
        when(mockPlayer.getY()).thenReturn(20f);

        Body mockBody = mock(Body.class);
        when(mockPlayer.getBody()).thenReturn(mockBody);
        when(mockBody.getPosition()).thenReturn(new Vector2(5, 5));
        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(0, 0));

        Score mockScore = mock(Score.class);
        when(mockModel.getScore()).thenReturn(mockScore);

        mockSpriteManager = mock(SpriteManager.class);
        mockOverlayView = mock(OverlayView.class);
        mockEnemyManager = mock(EnemyManager.class);
        mockDayNightCycle = mock(DayNightCycle.class);
        mockEntityRenderer = mock(EntityRenderer.class);
        
        try (MockedConstruction<Box2DDebugRenderer> mocked = Mockito.mockConstruction(Box2DDebugRenderer.class); 
            MockedConstruction<EnemyManager> mocked2 = Mockito.mockConstruction(EnemyManager.class);
            MockedConstruction<Knight> knight = Mockito.mockConstruction(Knight.class);
            MockedConstruction<Slime> slime = Mockito.mockConstruction(Slime.class);
            MockedConstruction<PinkMonster> pinkMonster = Mockito.mockConstruction(PinkMonster.class);
            MockedConstruction<Projectile> projectile = Mockito.mockConstruction(Projectile.class)) {
            gameScreen = new GameScreen(camera, mockModel, mockView, mockBatch);
            gameScreen.setMapRenderer(mockRenderer);
        }

        gameScreen.setSpriteManager(mockSpriteManager);
        gameScreen.setOverlayView(mockOverlayView);
        gameScreen.setEnemyManager(mockEnemyManager);
        gameScreen.setMapRenderer(mockRenderer);
        gameScreen.setDayNightCycle(mockDayNightCycle);
        gameScreen.setEntityRenderer(mockEntityRenderer);

    }

    @AfterEach
    public void tearDown() {
        gameScreen.dispose();
    }

    @Test
    public void testUpdate() {

        World mockWorld = mock(World.class);
        when(mockModel.getWorld()).thenReturn(mockWorld);

        // Call the update method
        gameScreen.update();

        // Verify the expected method calls
        verify(mockModel).checkAndUpdateGameState();

        // Verify the expected method calls
        verify(mockWorld).step(1 / 60f, 6, 2);
        verify(mockPlayer).update(anyFloat());
        verify(mockSpriteManager).update();
        verify(mockOverlayView).update(any());
        verify(mockEnemyManager).update(0.01f);

    }

    @Test
    public void testRender() {

        World mockWorld = mock(World.class);
        when(mockModel.getWorld()).thenReturn(mockWorld);

        // Set up the background image
        Texture mockBackgroundImage = mock(Texture.class);
        gameScreen.setBackgroundImage(mockBackgroundImage);

        // Call the render method
        gameScreen.render(0.1f);

        // Verify the expected method calls
        verify(mockModel).checkAndUpdateGameState();
        verify(mockBatch, times(2)).begin();
        verify(mockSpriteManager).render(mockBatch, 10f, 20f);
        verify(mockOverlayView).render();
        verify(mockBatch, times(2)).end();
        verify(mockEntityRenderer).renderAllEntities();
    }

    @Test
    public void testUpdateGameOver() {
        // Set up the mock objects
        when(mockModel.getGameState()).thenReturn(GameState.GAME_OVER);

        // Call the update method
        gameScreen.update();

        // Verify the expected method calls
        verify(mockView).updateScreen();
    }

    @Test
    public void testUpdateGameWon() {
        // Set up the mock objects
        when(mockModel.getGameState()).thenReturn(GameState.GAME_WON);

        // Call the update method
        gameScreen.update();

        // Verify the expected method calls
        verify(mockView).updateScreen();
    }

    @Test
    public void testSetDay() {
        // Before setting to day, make sure it's currently set to night
        gameScreen.setNight();
        Texture expected = gameScreen.getBackgroundDay();

        // Call setDay() method
        gameScreen.setDay();

        // Check if the background texture is changed to day
        assertEquals(expected, gameScreen.getBackgroundImage());
    }

    @Test
    public void testSetNight() {
        // Before setting to night, make sure it's currently set to day
        gameScreen.setDay();
        Texture expected = gameScreen.getBackgroundNight();

        // Call setNight() method
        gameScreen.setNight();

        // Check if the background texture is changed to night
        assertEquals(expected, gameScreen.getBackgroundImage());
    }

}
