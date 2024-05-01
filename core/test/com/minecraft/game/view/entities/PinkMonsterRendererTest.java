package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.model.entities.PinkMonster;
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

class PinkMonsterRendererTest extends LibgdxUnitTest {

    @Mock
    private PinkMonster mockPinkMonster;
    @Mock
    private SpriteBatch mockBatch;
    private PinkMonsterRenderer pinkMonsterRenderer;
    @Mock
    private Texture mockTexture;
    @Mock
    private TextureRegion mockTextureRegion;
    @Mock
    private Animation<TextureRegion> mockIdleAnimation, mockRunningAnimation, mockAttackAnimation, mockAttack2Animation,
            mockDeadAnimation;
    @Mock
    private Body mockBody;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mockTexture.getWidth()).thenReturn(258);
        when(mockTexture.getHeight()).thenReturn(476);

        // Setup animation mocks to return specific frames
        when(mockIdleAnimation.getKeyFrame(anyFloat(), anyBoolean())).thenReturn(mockTextureRegion);
        when(mockRunningAnimation.getKeyFrame(anyFloat(), anyBoolean())).thenReturn(mockTextureRegion);
        when(mockAttackAnimation.getKeyFrame(anyFloat(), anyBoolean())).thenReturn(mockTextureRegion);
        when(mockAttack2Animation.getKeyFrame(anyFloat(), anyBoolean())).thenReturn(mockTextureRegion);
        when(mockDeadAnimation.getKeyFrame(anyFloat(), anyBoolean())).thenReturn(mockTextureRegion);
        when(mockPinkMonster.getPosition()).thenReturn(new Vector2(10, 10));

        when(mockBody.getPosition()).thenReturn(new Vector2(10, 10));
        when(mockPinkMonster.getBody()).thenReturn(mockBody);

        pinkMonsterRenderer = new PinkMonsterRenderer(mockBatch);
    }

    @Test
    void testRenderAnimationBasedOnPinkMonsterState() {
        when(mockPinkMonster.getCurrentState()).thenReturn(State.ATTACKING2);
        when(mockPinkMonster.getAttack2StateTime()).thenReturn(1f); // Simulate time has passed

        pinkMonsterRenderer.render(mockPinkMonster);

        verify(mockBatch, times(1)).draw(
                any(TextureRegion.class),
                anyFloat(), anyFloat(),
                anyFloat(), anyFloat());
    }

    @Test
    void testIdleAnimationSelectedForIdleState() {
        when(mockPinkMonster.getCurrentState()).thenReturn(State.IDLE);
        when(mockPinkMonster.getStateTime()).thenReturn(1f); // Simulate 1 second has passed

        pinkMonsterRenderer.render(mockPinkMonster);
        verify(mockBatch).draw(
                any(TextureRegion.class),
                anyFloat(), anyFloat(),
                anyFloat(), anyFloat());
    }

    @Test
    void testRunningAnimationSelectedForRunningState() {
        when(mockPinkMonster.getCurrentState()).thenReturn(State.RUNNING);
        when(mockPinkMonster.getStateTime()).thenReturn(1f);

        pinkMonsterRenderer.render(mockPinkMonster);

        verify(mockBatch).draw(
                any(TextureRegion.class),
                anyFloat(), anyFloat(),
                anyFloat(), anyFloat());
    }

    @Test
    void testAttackAnimationSelectedForAttackingState() {
        when(mockPinkMonster.getCurrentState()).thenReturn(State.ATTACKING);
        when(mockPinkMonster.getStateTime()).thenReturn(1f);

        pinkMonsterRenderer.render(mockPinkMonster);

        verify(mockBatch).draw(
                any(TextureRegion.class),
                anyFloat(), anyFloat(),
                anyFloat(), anyFloat());
    }

    @Test
    void testAttack2AnimationSelectedForAttacking2State() {
        when(mockPinkMonster.getCurrentState()).thenReturn(State.ATTACKING2);
        when(mockPinkMonster.getAttack2StateTime()).thenReturn(1f);

        pinkMonsterRenderer.render(mockPinkMonster);

        verify(mockBatch).draw(
                any(TextureRegion.class),
                anyFloat(), anyFloat(),
                anyFloat(), anyFloat());
    }

    @Test
    void testDeadAnimationSelectedForDeadState() {
        when(mockPinkMonster.getCurrentState()).thenReturn(State.DEAD);
        when(mockPinkMonster.getDeadStateTime()).thenReturn(Float.MAX_VALUE); // Simulating the animation has finished

        pinkMonsterRenderer.render(mockPinkMonster);

        verify(mockPinkMonster, times(1)).setMarkedForRemoval(true);
        verify(mockBatch).draw(
                any(TextureRegion.class),
                anyFloat(), anyFloat(),
                anyFloat(), anyFloat());
    }

    @Test
    void testDisposeReleasesResources() {
        pinkMonsterRenderer.dispose();
    }

}
