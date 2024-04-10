package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

// import org.mockito.Mock;

class SlimeTest {

    private Slime slime;
    @Mock
    private World mockWorld;
    @Mock
    private Player mockPlayer;
    private Health health;
    @Mock
    private Body mockBody;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock the Body object to return a mocked Vector2 for getPosition()
        when(mockBody.getPosition()).thenReturn(new Vector2(10, 10)); // Example position

        // Now correctly mock the Player to return the mocked Body
        when(mockPlayer.getBody()).thenReturn(mockBody);

        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);

        // Ensure mockBody is properly set up to avoid NullPointerException when
        // createFixture is called
        when(mockBody.createFixture(any(FixtureDef.class))).thenReturn(mock(Fixture.class));

        // health = new Health(4, 4, null); // Assuming this constructor sets max health
        // and current health to 4

        // Assuming createBody method doesn't play a crucial role in logic for unit
        // testing, hence not mocked deeply
        slime = new Slime(1.0f, 1.0f, mockWorld, mockPlayer, 5.0f, 5.0f, health);
    }

    @Test
    void knightShouldTransitionToDeadStateWhenHealthDepletes() {
        // Damage the slime enough times to deplete health
        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(0, 0));
        slime.getHit(2); // Assuming one hit deals 1 damage
        slime.update(0.1f); // Simulate an update call with deltaTime
        assertEquals(Slime.State.DEAD, slime.getCurrentState(), "slime should transition to DEAD state");
    }

    @Test
    void knightShouldFaceLeftWhenPlayerIsOnTheLeft() {
        // Place player to the left of the slime
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(5.5f, 5.0f)); // Player is now to the left
        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(0, 0));
        // System.out.println("slime position: " + slime.getBody().getPosition().x);
        // System.out.println("Player position: " +
        // mockPlayer.getBody().getPosition().x);
        // System.out.println("slime facing right: " + slime.isFacingRight());

        slime.update(0.1f); // Simulate an update call with deltaTime
        // System.out.println("slime position: " + slime.getBody().getPosition().x);
        // System.out.println("Player position: " +
        // mockPlayer.getBody().getPosition().x);
        // System.out.println("slime facing right: " + slime.isFacingRight());

        assertTrue(slime.isFacingRight(), "slime should be facing left when player is to the left");
    }

    @Test
    void knightShouldTransitionToAttackingWhenCloseToPlayer() {
        // Place player close enough to trigger attacking state
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(5.5f, 5.0f)); // Close to the slime
        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(0, 0));

        slime.setAttackFrameTrue(); // Simulate conditions for attacking
        slime.update(0.1f); // Simulate an update call with deltaTime
        assertEquals(Slime.State.ATTACKING, slime.getCurrentState(), "slime should transition to ATTACKING state");
    }
}
