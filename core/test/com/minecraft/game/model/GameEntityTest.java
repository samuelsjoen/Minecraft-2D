package com.minecraft.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameEntityTest {

    @Mock
    private Body mockBody;

    private TestGameEntity gameEntity;

    // A concrete subclass of GameEntity for testing
    private static class TestGameEntity extends GameEntity {
        public TestGameEntity(float width, float height, Body body) {
            super(width, height, body);
        }

        @Override
        public void update() {
        }

        @Override
        public void render(SpriteBatch batch) {
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockBody.getPosition()).thenReturn(new Vector2(1.0f, 2.0f)); // Example position

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

}
