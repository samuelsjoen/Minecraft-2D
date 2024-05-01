package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.model.entities.Projectile;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.LibgdxUnitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

class ProjectileRendererTest extends LibgdxUnitTest {

    @Mock
    private Projectile mockProjectile;
    @Mock
    private SpriteBatch mockBatch;
    @Mock
    private Body mockBody;
    private ProjectileRenderer projectileRenderer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockProjectile = mock(Projectile.class);
        mockBody = mock(Body.class);

        when(mockProjectile.getBody()).thenReturn(mockBody);
        when(mockBody.getPosition()).thenReturn(new Vector2(10, 10));
        when(mockProjectile.getPosition()).thenReturn(new Vector2(10, 10));
        when(mockProjectile.getWidth()).thenReturn(16.0f);
        when(mockProjectile.getHeight()).thenReturn(16.0f);

        mockProjectile.width = 16.0f;
        mockProjectile.height = 16.0f;

        projectileRenderer = new ProjectileRenderer(mockBatch);
    }

    @Test
    void testRender() {
        projectileRenderer.render(mockProjectile);

        verify(mockBatch).draw(
                any(Texture.class),
                eq((10 * Constants.PPM) - 25), // Position X adjusted by Constants.PPM and offset
                eq((10 * Constants.PPM) - 25), // Position Y adjusted by Constants.PPM and offset
                eq(16.0f), // Width of the projectile
                eq(16.0f) // Height of the projectile
        );
    }

    @Test
    void testRenderWhenSheetIsNull() throws NoSuchFieldException, IllegalAccessException {
        ProjectileRenderer renderer = new ProjectileRenderer(mockBatch);
        Field sheetField = ProjectileRenderer.class.getDeclaredField("sheet");
        sheetField.setAccessible(true);
        sheetField.set(renderer, null); // Set the sheet to null

        renderer.render(mockProjectile);

        // Verify that batch.draw() is not called since sheet is expected to be null
        verify(mockBatch, never()).draw(
                isA(Texture.class), // This specifies that the first parameter is a Texture.
                anyFloat(), // Using anyFloat() for the other parameters as they are not ambiguous.
                anyFloat(),
                anyFloat(),
                anyFloat());
    }

    @Test
    void testDisposeReleasesResources() {
        projectileRenderer.dispose();
    }
}
