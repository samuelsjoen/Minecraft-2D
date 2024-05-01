package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

class SlimeTest {

    private Slime slime;
    @Mock
    private World mockWorld;
    @Mock
    private Player mockPlayer;
    @Mock
    private Health health;
    @Mock
    private Body mockBody;
    @Mock
    private Body mockBody2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockBody.getPosition()).thenReturn(new Vector2(5.0f, 5.0f));
        when(mockBody.getAngle()).thenReturn(0f);
        when(mockBody2.getPosition()).thenReturn(new Vector2(5.0f, 5.0f));
        when(mockBody2.getAngle()).thenReturn(0f);
        when(mockPlayer.getBody()).thenReturn(mockBody2);

        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(0, 0));
        when(mockBody2.getLinearVelocity()).thenReturn(new Vector2(0, 0));

        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);

        when(mockBody.createFixture(any(FixtureDef.class))).thenReturn(mock(Fixture.class));
        when(mockBody2.createFixture(any(FixtureDef.class))).thenReturn(mock(Fixture.class));
        slime = new Slime(1.0f, 1.0f, mockWorld, mockPlayer, 5.0f, 5.0f, health);
    }

    @Test
    void slimeShouldTransitionToDeadStateWhenHealthDepletes() {
        slime.getHit(4);
        slime.update(0.1f);
        assertEquals(State.DEAD, slime.getCurrentState(), "slime should transition to DEAD state");
    }

    @Test
    void slimeShouldFaceTheCorrectWay() {
        // Place player to the right of the slime
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(10.5f, 5.0f)); // Player is now to the left
        slime.update(0.1f);
        assertFalse(slime.isFacingRight(), "slime should be facing right when player is to the right");
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(0.5f, 5.0f)); // Player is now to the left
        slime.update(0.1f);
        assertTrue(slime.isFacingRight(), "slime should be facing left when player is to the left");
    }

    @Test
    void slimeShouldTransitionToAttackingWhenCloseToPlayer() {
        // Place player close enough to trigger attacking state
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(5.0f, 5.0f)); // Close to the slime
        slime.update(0.1f);
        assertEquals(State.ATTACKING, slime.getCurrentState(), "slime should transition to ATTACKING state");
    }

    @Test
    void slimeBecomesVulnerableAfterInvincibilityExpires() {

        slime.getHit(1);
        assertTrue(slime.isInvincible(), "slime should become invincible after being hit");

        slime.update(0.2f);
        assertTrue(slime.isInvincible(), "slime should become vulnerable after invincibility expires");

        slime.update(0.2f);
        assertTrue(slime.isInvincible(), "slime should become vulnerable after invincibility expires");

        slime.update(0.2f);
        assertTrue(slime.isInvincible(), "slime should become vulnerable after invincibility expires");

        slime.update(0.2f);
        assertTrue(slime.isInvincible(), "slime should become vulnerable after invincibility expires");

        slime.update(0.3f);
        assertFalse(slime.isInvincible(), "slime should become vulnerable after invincibility expires");
    }

    // new tests

    @Test
    void TestslimeStatesBasedOfDistanceWithThePlayer() {
        // Place player far enough from the slime
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(50f, 5.0f)); // Far from the slime
        slime.update(0.1f);
        assertEquals(State.IDLE, slime.getCurrentState(), "slime should transition to ATTACKING state");

        // Place player close enought to trigger running state of the slime
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(10f, 5.0f)); // Close to run
        slime.update(0.1f);
        assertEquals(State.RUNNING, slime.getCurrentState(), "slime should transition to ATTACKING state");

        // Place player close enough from the slime
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(5.0f, 5.0f)); // Close to attack
        slime.update(0.1f);
        assertEquals(State.ATTACKING, slime.getCurrentState(), "slime should transition to ATTACKING state");
    }

    @Test
    void slimeMarkedForRemovalTest() {
        slime.setMarkedForRemoval();
        assertTrue(slime.isMarkedForRemoval(), "slime should be marked for removal when health reaches zero");
    }

    @Test
    void getterMethodsShouldReturnCorrectValues() {
        assertTrue(slime.isAlive(), "isAlive should return true when slime's health is greater than zero");

        assertEquals(health.getClass(), slime.getHealth().getClass(),
                "getHealth should return the slime's health object");

        assertEquals(0f, slime.getDeadStateTime(), "getDeadStateTime should return 0 initially");
        assertEquals(0f, slime.getStateTime(), "getStateTime should return 0 initially");
    }

    @Test
    void setAttackFrameTest() {
        slime.setAttackFrameFalse();
        assertFalse(slime.AttackFrame(), "setAttackFrameFalse should set attackFrame to false");
        slime.setAttackFrameTrue();
        assertTrue(slime.AttackFrame(), "setAttackFrameFalse should set attackFrame to false");
    }

    @Test
    void slimeShouldJumpWhenPlayerIsWithinRangeAndConditionsAreMet() {
        // Place player within jump range and close enough
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(5.0f, 7.0f)); // Player is above slime

        slime.update(0.1f);
        verify(mockBody).applyLinearImpulse(new Vector2(0, 55), mockBody.getWorldCenter(), true);
    }

    @Test
    void testGetAttack2StateTime() {
        assertEquals(0, slime.getAttack2StateTime(), "getAttack2StateTime should always return 0 for Slime.");
    }

    @Test
    void testGetWidth() {
        assertEquals(1.0f, slime.getWidth(), "getWidth should correctly return the width of the Slime.");
    }

    @Test
    void testGetHeight() {
        assertEquals(1.0f, slime.getHeight(), "getHeight should correctly return the height of the Slime.");
    }

    @Test
    void testSetAttackFrame() {
        assertFalse(slime.AttackFrame(), "Initially, attackFrame should be false.");
        slime.setAttackFrame(true);
        assertTrue(slime.AttackFrame(), "setAttackFrame(true) should set attackFrame to true.");
        slime.setAttackFrame(false);
        assertFalse(slime.AttackFrame(), "setAttackFrame(false) should set attackFrame to false.");
    }

    @Test
    void testSetMarkedForRemoval() {
        assertFalse(slime.isMarkedForRemoval(), "Initially, Slime should not be marked for removal.");
        slime.setMarkedForRemoval(true);
        assertTrue(slime.isMarkedForRemoval(), "setMarkedForRemoval(true) should mark the Slime for removal.");
        slime.setMarkedForRemoval(false);
        assertFalse(slime.isMarkedForRemoval(), "setMarkedForRemoval(false) should unmark the Slime for removal.");
    }

    @Test
    void testGetPosition() {
        Vector2 position = slime.getPosition();
        assertEquals(5.0f, position.x, "getPosition should return the correct X coordinate.");
        assertEquals(5.0f, position.y, "getPosition should return the correct Y coordinate.");
    }

}
