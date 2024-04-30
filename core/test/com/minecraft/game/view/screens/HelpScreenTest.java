package com.minecraft.game.view.screens;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.minecraft.game.LibgdxUnitTest;

public class HelpScreenTest extends LibgdxUnitTest {

    private SpriteBatch spriteBatch;
    private HelpScreen helpScreen;

    @BeforeEach
    public void setUp() {
        // Mocking SpriteBatch
        spriteBatch = mock(SpriteBatch.class);

        try (MockedConstruction<Stage> mocked = Mockito.mockConstruction(Stage.class)) {
            // Create HelpScreen instance
            helpScreen = new HelpScreen(spriteBatch);
        }
    }

    @Test
    public void testTextureLoading() {

        // Verify that the background texture is not null
        assertNotNull(helpScreen.getBackgroundTexture());

        // Verify that the background texture is loaded correctly
        assertTrue(helpScreen.getBackgroundTexture() instanceof Texture);

        // Verify that the file path is correct
        assertTrue(Gdx.files.internal("helpScreen/help_background.png").exists());
    }

    @Test
    public void testRender() {
        // Set up mock GL20
        GL20 gl = mock(GL20.class);
        Gdx.gl = gl;

        // Call render method
        helpScreen.render(0.1f);

        // Verify that glClearColor and glClear were called
        verify(gl).glClearColor(0, 0, 0, 1);
        verify(gl).glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Verify that batch.begin and batch.end were called
        verify(spriteBatch).begin();
        verify(spriteBatch).end();

        // Verify that the background texture is rendered
        verify(spriteBatch).draw(eq(helpScreen.getBackgroundTexture()), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    public void testDispose() {
        // Call dispose method
        helpScreen.dispose();

        // Verify that batch and stage were disposed
        verify(spriteBatch).dispose();
        verify(helpScreen.getStage()).dispose();
    }

    @Test
    public void testResize() {
        Viewport viewport = new FitViewport(1000, 800);
        Stage stage = new Stage(viewport, spriteBatch);
        helpScreen.setStage(stage);

        // Mock behavior of SpriteBatch
        Matrix4 mockMatrix = mock(Matrix4.class);
        when(spriteBatch.getProjectionMatrix()).thenReturn(mockMatrix); 
        // Call resize method
        helpScreen.resize(800, 600);

        // Verify that batch projection matrix was set properly
        verify(spriteBatch.getProjectionMatrix()).setToOrtho2D(0, 0, 800, 600);
    }
}
