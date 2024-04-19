package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameEntityTest {

    @Mock
    private Body mockBody;
    @Mock
    private World mockWorld;

    private TestGameEntity gameEntity;

    private static class TestGameEntity extends GameEntity {
        public TestGameEntity(float width, float height, Body body) {
            super(width, height, body);
        }

        @Override
        public void update(float deltaTime) {
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockBody.getPosition()).thenReturn(new Vector2(1.0f, 2.0f));
        when(mockBody.getWorld()).thenReturn(mockWorld);

        gameEntity = new TestGameEntity(10.0f, 20.0f, mockBody);
    }

    @Test
    void constructor_initializesPropertiesCorrectly() {
        assertEquals(1.0f, gameEntity.x);
        assertEquals(2.0f, gameEntity.y);
        assertEquals(10.0f, gameEntity.width);
        assertEquals(20.0f, gameEntity.height);
        assertNotNull(gameEntity.getBody());
    }

    @Test
    void markForRemoval_SetsCorrectly() {
        assertFalse(gameEntity.isMarkedForRemoval()); // Initial state
        gameEntity.setMarkedForRemoval();
        assertTrue(gameEntity.isMarkedForRemoval());
    }

    @Test
    void dispose_DisposesBodyCorrectly() {
        gameEntity.dispose();
        verify(mockWorld).destroyBody(mockBody); // Check that the body is destroyed in the world
    }

    @Test
    void markForRemoval_TogglesCorrectly() {
        // Initial state check
        assertFalse(gameEntity.isMarkedForRemoval());

        // First toggle: Set to true
        gameEntity.setMarkedForRemoval();
        assertTrue(gameEntity.isMarkedForRemoval());

        // Second toggle: Set back to false
        gameEntity.setMarkedForRemoval();
        assertFalse(gameEntity.isMarkedForRemoval());
    }

}
