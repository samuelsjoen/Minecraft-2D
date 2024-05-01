package com.minecraft.game.view.screens;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.minecraft.game.LibgdxUnitTest;

public class GameOverScreenTest extends LibgdxUnitTest {

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private GameOverScreen gameOverScreen;

    @BeforeEach
    public void setUp() {
        // Mocking SpriteBatch
        spriteBatch = mock(SpriteBatch.class);

        // Mocking font
        font = mock(BitmapFont.class);
    
        // Mock the behavior of getData() to return a mock of BitmapFontData
        BitmapFont.BitmapFontData fontData = mock(BitmapFont.BitmapFontData.class);
        when(font.getData()).thenReturn(fontData);
        // Mock the behavior of setScale() on the BitmapFontData mock
        doNothing().when(fontData).setScale(2);

        // Create GameOverScreen instance
        gameOverScreen = new GameOverScreen(spriteBatch, font);
    }

    @Test
    public void testRender() {
        // Set up mock GL20
        GL20 gl = mock(GL20.class);
        Gdx.gl = gl;

        // Call render method
        gameOverScreen.render(0.1f);

        // Verify that glClearColor and glClear were called
        verify(gl).glClearColor(0, 0, 0, 1);
        verify(gl).glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Verify that batch.begin and batch.end were called
        verify(spriteBatch).begin();
        verify(spriteBatch).end();

        // Verify that the message is drawn
        verify(font).draw(any(SpriteBatch.class), eq("Press any button to restart."), anyFloat(), anyFloat(), anyFloat(), anyInt(), anyBoolean());
    }

    @Test
    public void testDispose() {
        // Call dispose method
        gameOverScreen.dispose();

        // Verify that font and batch were disposed
        verify(gameOverScreen.font).dispose();
        verify(gameOverScreen.batch).dispose();
    }

    @Test
    public void testResize() {
        // Mock behavior of SpriteBatch
        Matrix4 mockMatrix = mock(Matrix4.class);
        when(spriteBatch.getProjectionMatrix()).thenReturn(mockMatrix);
                
        // Call resize method
        gameOverScreen.resize(800, 600);

        // Verify that batch projection matrix was set properly
        verify(spriteBatch.getProjectionMatrix()).setToOrtho2D(0, 0, 800, 600);
    }
}
