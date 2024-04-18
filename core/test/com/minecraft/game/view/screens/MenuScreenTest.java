package com.minecraft.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.minecraft.game.LibgdxUnitTest;
import com.minecraft.game.Minecraft;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MenuScreenTest extends LibgdxUnitTest {

    @Mock
    private SpriteBatch mockBatch;
    @Mock
    private Texture mockBackgroundTexture, mockTitleTexture, mockStartButtonTexture, mockHelpButtonTexture, mockQuitButtonTexture;
    private MenuScreen menuScreen;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        menuScreen = new MenuScreen(mock(Minecraft.class), mockBatch);

        menuScreen.setBackgroundTexture(mockBackgroundTexture);
        menuScreen.setTitleTexture(mockTitleTexture);
        menuScreen.setStartButtonTexture(mockStartButtonTexture);
        menuScreen.setHelpButtonTexture(mockHelpButtonTexture);
        menuScreen.setQuitButtonTexture(mockQuitButtonTexture);
    }

    @Test
    void testRender() {
        menuScreen.render(0.1f);

        verify(mockBatch, times(1)).begin();
        verify(mockBatch, times(1)).end();
    }

    @Test
    void testDrawMenu() {
        menuScreen.drawMenu();

        // Verify that drawBackground, drawTitle, and drawButtons methods are called
        verify(mockBatch, times(1)).draw(eq(mockBackgroundTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(mockBatch, times(1)).draw(eq(mockTitleTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(mockBatch, times(1)).draw(eq(mockStartButtonTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(mockBatch, times(1)).draw(eq(mockHelpButtonTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(mockBatch, times(1)).draw(eq(mockQuitButtonTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    void testDrawBackground() {
        menuScreen.drawBackground();

        // Verify that draw method is called with the correct parameters
        verify(mockBatch, times(1)).draw(eq(mockBackgroundTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    void testDrawTitle() {
        menuScreen.drawTitle();

        // Verify that draw method is called with the correct parameters
        verify(mockBatch, times(1)).draw(eq(mockTitleTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    void testDrawButtons() {
        menuScreen.drawButtons();

        // Verify that drawButton method is called for each button texture
        verify(mockBatch, times(1)).draw(eq(mockStartButtonTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(mockBatch, times(1)).draw(eq(mockHelpButtonTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(mockBatch, times(1)).draw(eq(mockQuitButtonTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    void testDrawButton() {
        float buttonY = 300;
        float expectedButtonY = buttonY - mockStartButtonTexture.getHeight();

        menuScreen.drawButton(mockStartButtonTexture, buttonY);

        // Verify that draw method is called with the correct parameters
        verify(mockBatch, times(1)).draw(eq(mockStartButtonTexture), anyFloat(), eq(expectedButtonY), anyFloat(), anyFloat());
    }

    @Test
    void testDispose() {
        menuScreen.dispose();

        // Verify that dispose method is called for each texture and the batch
        verify(mockBatch, times(1)).dispose();
        verify(mockBackgroundTexture, times(1)).dispose();
        verify(mockTitleTexture, times(1)).dispose();
        verify(mockStartButtonTexture, times(1)).dispose();
        verify(mockHelpButtonTexture, times(1)).dispose();
        verify(mockQuitButtonTexture, times(1)).dispose();
    }

    @Test
    public void testResize() {
        // Mock behavior of SpriteBatch
        Matrix4 mockMatrix = mock(Matrix4.class);
        when(mockBatch.getProjectionMatrix()).thenReturn(mockMatrix);
                
        // Call resize method
        menuScreen.resize(800, 600);

        // Verify that batch projection matrix was set properly
        verify(mockBatch.getProjectionMatrix()).setToOrtho2D(0, 0, 800, 600);
    }

    @Test
    void testIsStartButtonClicked() {
        float buttonX = (Gdx.graphics.getWidth() - mockStartButtonTexture.getWidth()) / 2;
        float buttonY = 300 - mockStartButtonTexture.getHeight();
        assertTrue(menuScreen.isStartButtonClicked(buttonX, buttonY));
        assertFalse(menuScreen.isStartButtonClicked(buttonX - 10, buttonY - 10));
    }

    @Test
    void testIsHelpButtonClicked() {
        float buttonX = (Gdx.graphics.getWidth() - mockHelpButtonTexture.getWidth()) / 2;
        float buttonY = 300 - mockStartButtonTexture.getHeight() - 5 - mockHelpButtonTexture.getHeight();
        assertTrue(menuScreen.isHelpButtonClicked(buttonX, buttonY));
        assertFalse(menuScreen.isHelpButtonClicked(buttonX - 10, buttonY - 10));
    }

    @Test
    void testIsQuitButtonClicked() {
        float buttonX = (Gdx.graphics.getWidth() - mockQuitButtonTexture.getWidth()) / 2;
        float buttonY = 300 - 2 * (mockStartButtonTexture.getHeight() + 5) - mockQuitButtonTexture.getHeight();
        assertTrue(menuScreen.isQuitButtonClicked(buttonX, buttonY));
        assertFalse(menuScreen.isQuitButtonClicked(buttonX - 10, buttonY - 10));
    }
}
