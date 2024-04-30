package com.minecraft.game.view.screens;

//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.minecraft.game.LibgdxUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.badlogic.gdx.utils.viewport.Viewport;

import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.eq;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
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

        try (MockedConstruction<Stage> mocked = Mockito.mockConstruction(Stage.class)) {
            // Create MenuScreen instance
            menuScreen = new MenuScreen(mockBatch);
        }

        menuScreen.setBackgroundTexture(mockBackgroundTexture);
        /*menuScreen.setTitleTexture(mockTitleTexture);
        menuScreen.setStartButtonTexture(mockStartButtonTexture);
        menuScreen.setHelpButtonTexture(mockHelpButtonTexture);
        menuScreen.setQuitButtonTexture(mockQuitButtonTexture);*/
    }

    @Test
    void testRender() {
        menuScreen.render(0.1f);

        verify(mockBatch, times(1)).begin();
        verify(mockBatch, times(1)).end();
    }

    /*@Test
    void testDrawMenu() {
        menuScreen.drawMenu();

        // Verify that drawBackground, drawTitle, and drawButtons methods are called
        verify(mockBatch, times(1)).draw(eq(mockBackgroundTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        /*verify(mockBatch, times(1)).draw(eq(mockTitleTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(mockBatch, times(1)).draw(eq(mockStartButtonTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(mockBatch, times(1)).draw(eq(mockHelpButtonTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(mockBatch, times(1)).draw(eq(mockQuitButtonTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    */
    //}

    @Test
    void testDrawBackground() {
        menuScreen.drawBackground();

        // Verify that draw method is called with the correct parameters
        verify(mockBatch, times(1)).draw(eq(mockBackgroundTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    /*@Test
    void testDrawTitle() {
        menuScreen.drawTitle();

        // Verify that draw method is called with the correct parameters
        verify(mockBatch, times(1)).draw(eq(mockTitleTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }*/

    /*@Test
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
    }*/

    @Test
    void testDispose() {
        menuScreen.dispose();

        // Verify that dispose method is called for each texture and the batch
        verify(mockBatch, times(1)).dispose();
        verify(mockBackgroundTexture, times(1)).dispose();
        /*verify(mockTitleTexture, times(1)).dispose();
        verify(mockStartButtonTexture, times(1)).dispose();
        verify(mockHelpButtonTexture, times(1)).dispose();
        verify(mockQuitButtonTexture, times(1)).dispose();*/
    }

    @Test
    public void testResize() {

        Viewport viewport = new FitViewport(1000, 800);
        Stage stage = new Stage(viewport, mockBatch);
        menuScreen.setStage(stage);

        // Mock behavior of SpriteBatch
        Matrix4 mockMatrix = mock(Matrix4.class);
        when(mockBatch.getProjectionMatrix()).thenReturn(mockMatrix);
                
        // Call resize method
        menuScreen.resize(800, 600);

        // Verify that batch projection matrix was set properly
        verify(mockBatch.getProjectionMatrix()).setToOrtho2D(0, 0, 800, 600);
    }

}
