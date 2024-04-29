package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.graphics.Texture;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.State;
import com.minecraft.game.LibgdxUnitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;

class KnightRendererTest extends LibgdxUnitTest {

    @Mock
    private Knight mockKnight;
    @Mock
    private SpriteBatch mockBatch;
    private KnightRenderer knightRenderer;
    @Mock
    private Texture mockTexture;
    @Mock
    private TextureRegion mockTextureRegion;
    @Mock
    private Animation<TextureRegion> mockAnimation;
    @Mock
    private Body mockBody;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mockTexture.getWidth()).thenReturn(1200);
        when(mockTexture.getHeight()).thenReturn(320);

        // Mocking a texture region which is part of the animations
        mockTextureRegion = new TextureRegion(mockTexture, 0, 0, 120, 80);

        // Mocking animations to return the mocked texture region
        when(mockAnimation.getKeyFrame(anyFloat(), anyBoolean())).thenReturn(mockTextureRegion);
        when(mockKnight.getPosition()).thenReturn(new Vector2(10, 10));

        when(mockBody.getPosition()).thenReturn(new Vector2(10, 10));
        // when(mockKnight.getBody()).thenReturn(mockBody);

        // Setup the KnightRenderer with mocked dependencies
        knightRenderer = new KnightRenderer(mockBatch);
    }

    @Test
    void testRenderAnimationBasedOnKnightState() {
        // Setup the knight's state
        when(mockKnight.getCurrentState()).thenReturn(State.IDLE);
        when(mockKnight.getStateTime()).thenReturn(1f); // Simulate 1 second has passed
        when(mockKnight.isFacingRight()).thenReturn(true); // Knight is facing right

        // Perform the rendering
        knightRenderer.render(mockKnight);

        verify(mockBatch, times(1)).draw(
                any(TextureRegion.class),
                anyFloat(), anyFloat(),
                anyFloat(), anyFloat());
    }

    @Test
    void testIdleAnimationSelected() {
        when(mockKnight.getCurrentState()).thenReturn(State.IDLE);

        knightRenderer.render(mockKnight);
    }

    @Test
    void testRunningAnimationSelected() {
        when(mockKnight.getCurrentState()).thenReturn(State.RUNNING);

        knightRenderer.render(mockKnight);
    }

    @Test
    void testAttackAnimationSelected() {
        when(mockKnight.getCurrentState()).thenReturn(State.ATTACKING);
        when(mockKnight.getStateTime()).thenReturn(0.2f); // 0.2 seconds passed to manipulate index

        knightRenderer.render(mockKnight);
    }

    @Test
    void testDeadAnimationSelectedAndMarkedForRemoval() {
        when(mockKnight.getCurrentState()).thenReturn(State.DEAD);
        when(mockKnight.getDeadStateTime()).thenReturn(Float.MAX_VALUE); // Simulate animation has finished

        knightRenderer.render(mockKnight);

        // Verify knight is marked for removal after dead animation is finished.
        verify(mockKnight, times(1)).setMarkedForRemoval(true);
    }

    @Test
    void testDisposeReleasesResources() {
        knightRenderer.dispose();
    }

}
