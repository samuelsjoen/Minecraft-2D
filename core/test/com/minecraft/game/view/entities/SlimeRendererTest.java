package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.model.entities.Slime;
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

class SlimeRendererTest extends LibgdxUnitTest {

    @Mock
    private Slime mockSlime;
    @Mock
    private SpriteBatch mockBatch;
    private SlimeRenderer slimeRenderer;
    @Mock
    private Texture mockTexture;
    @Mock
    private TextureRegion mockTextureRegion;
    @Mock
    private Animation<TextureRegion> mockIdleAnimation, mockAttackAnimation, mockDeadAnimation;
    @Mock
    private Body mockBody;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mockTexture.getWidth()).thenReturn(256);
        when(mockTexture.getHeight()).thenReturn(75);

        when(mockIdleAnimation.getKeyFrame(anyFloat(), anyBoolean())).thenReturn(mockTextureRegion);
        when(mockAttackAnimation.getKeyFrame(anyFloat(), anyBoolean())).thenReturn(mockTextureRegion);
        when(mockDeadAnimation.getKeyFrame(anyFloat(), anyBoolean())).thenReturn(mockTextureRegion);
        when(mockSlime.getPosition()).thenReturn(new Vector2(10, 10));

        when(mockBody.getPosition()).thenReturn(new Vector2(10, 10));
        when(mockSlime.getBody()).thenReturn(mockBody);

        slimeRenderer = new SlimeRenderer(mockBatch);
    }

    @Test
    void testRenderAnimationBasedOnSlimeState() {
        when(mockSlime.getCurrentState()).thenReturn(State.IDLE);
        when(mockSlime.getStateTime()).thenReturn(1f); // Simulate 1 second has passed
        when(mockSlime.isFacingRight()).thenReturn(true); // Slime is facing right

        slimeRenderer.render(mockSlime);

        verify(mockBatch, times(1)).draw(
                any(TextureRegion.class),
                anyFloat(), anyFloat(),
                anyFloat(), anyFloat());
    }

    @Test
    void testIdleAnimationSelected() {
        when(mockSlime.getCurrentState()).thenReturn(State.IDLE);

        slimeRenderer.render(mockSlime);
    }

    @Test
    void testRunningAnimationSelected() {
        when(mockSlime.getCurrentState()).thenReturn(State.RUNNING);

        slimeRenderer.render(mockSlime);
    }

    @Test
    void testAttackAnimationSelected() {
        when(mockSlime.getCurrentState()).thenReturn(State.ATTACKING);
        when(mockSlime.getStateTime()).thenReturn(0.2f); // 0.2 seconds passed to manipulate index

        slimeRenderer.render(mockSlime);
    }

    @Test
    void testDeadAnimationSelectedAndMarkedForRemoval() {
        when(mockSlime.getCurrentState()).thenReturn(State.DEAD);
        when(mockSlime.getDeadStateTime()).thenReturn(Float.MAX_VALUE); // Simulate animation has finished

        slimeRenderer.render(mockSlime);

        // Verify Slime is marked for removal after dead animation is finished.
        verify(mockSlime, times(1)).setMarkedForRemoval(true);
    }

    @Test
    void testDisposeReleasesResources() {
        slimeRenderer.dispose();
    }

}
