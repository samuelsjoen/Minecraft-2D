package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class ProjectileTest {

    private Projectile projectile;
    @Mock
    private World mockWorld;
    @Mock
    private Player mockPlayer;
    @Mock
    private com.badlogic.gdx.physics.box2d.Body mockBody;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mockPlayer.getBody()).thenReturn(mockBody);

        Vector2 mockPosition = new Vector2(0.5f, 0.5f);
        when(mockBody.getPosition()).thenReturn(mockPosition);

        when(mockWorld.createBody(any())).thenReturn(mockBody);
        when(mockBody.createFixture(any(FixtureDef.class))).thenReturn(mock(Fixture.class));
        projectile = new Projectile(1.0f, 1.0f, mockWorld, new Vector2(0, 0), new Vector2(10, 10));
    }

    @Test
    void projectileShouldBeMarkedForRemovalWhenToBeDestroyedIsTrue() {
        projectile.markForDestruction();
        projectile.update(0.1f);
        assertTrue(projectile.isMarkedForRemoval(),
                "Projectile should be marked for removal when toBeDestroyed is true");
    }

    @Test
    void projectileShouldBeMarkedForRemovalWhenLifetimeExpires() {
        projectile.update(3.1f); // Exceed the lifetime
        assertTrue(projectile.isMarkedForRemoval(), "Projectile should be marked for removal after lifetime expires");
    }

    @Test
    void projectileShouldBeMarkedForRemovalWhenCollisionWithPlayerOccurs() {
        // Mock player position close to projectile position
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(0.5f, 0.5f));
        projectile.checkCollisionWithPlayer(mockPlayer);
        projectile.update(0.1f);

        assertTrue(projectile.isMarkedForRemoval(),
                "Projectile should be marked for removal after collision with player");
    }

    @Test
    void projectileShouldNotBeMarkedForRemovalWhenNoCollisionWithPlayerOccurs() {
        // Mock player position far from projectile position
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(100, 100));
        projectile.checkCollisionWithPlayer(mockPlayer);
        assertFalse(projectile.isMarkedForRemoval(),
                "Projectile should not be marked for removal when no collision with player occurs");
    }

}