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

class KnightTest {

    private Knight knight;
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
        knight = new Knight(1.0f, 1.0f, mockWorld, mockPlayer, 5.0f, 5.0f, health);
    }

    @Test
    void knightShouldTransitionToDeadStateWhenHealthDepletes() {
        knight.getHit(4);
        knight.update(0.1f);
        assertEquals(State.DEAD, knight.getCurrentState(), "Knight should transition to DEAD state");
    }

    @Test
    void knightShouldFaceTheCorrectWay() {
        // Place player to the right of the knight
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(10.5f, 5.0f)); // Player is now to the left
        knight.update(0.1f);
        assertTrue(knight.isFacingRight(), "Knight should be facing right when player is to the right");
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(0.5f, 5.0f)); // Player is now to the left
        knight.update(0.1f);
        assertFalse(knight.isFacingRight(), "Knight should be facing left when player is to the left");
    }

    @Test
    void knightShouldTransitionToAttackingWhenCloseToPlayer() {
        // Place player close enough to trigger attacking state
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(5.0f, 5.0f)); // Close to the knight
        knight.update(0.1f);
        assertEquals(State.ATTACKING, knight.getCurrentState(), "Knight should transition to ATTACKING state");
    }

    @Test
    void knightBecomesVulnerableAfterInvincibilityExpires() {

        knight.getHit(1);
        assertTrue(knight.isInvincible(), "Knight should become invincible after being hit");

        knight.update(0.2f);
        assertTrue(knight.isInvincible(), "Knight should become vulnerable after invincibility expires");

        knight.update(0.2f);
        assertTrue(knight.isInvincible(), "Knight should become vulnerable after invincibility expires");

        knight.update(0.2f);
        assertTrue(knight.isInvincible(), "Knight should become vulnerable after invincibility expires");

        knight.update(0.2f);
        assertTrue(knight.isInvincible(), "Knight should become vulnerable after invincibility expires");

        knight.update(0.3f);
        assertFalse(knight.isInvincible(), "Knight should become vulnerable after invincibility expires");
    }

    @Test
    void TestKnightStatesBasedOfDistanceWithThePlayer() {
        // Place player far enough from the knight
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(50f, 5.0f)); // Far from the slime
        knight.update(0.1f);
        assertEquals(State.IDLE, knight.getCurrentState(), "Knight should transition to ATTACKING state");

        // Place player close enought to trigger running state of the knight
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(10f, 5.0f)); // Close to attack
        knight.update(0.1f);
        assertEquals(State.RUNNING, knight.getCurrentState(), "Knight should transition to ATTACKING state");

        // Place player close enough from the knight
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(5.0f, 5.0f)); // Close to run
        knight.update(0.1f);
        assertEquals(State.ATTACKING, knight.getCurrentState(), "Knight should transition to ATTACKING state");
    }

    @Test
    void knightMarkedForRemovalTest() {
        knight.setMarkedForRemoval();
        assertTrue(knight.isMarkedForRemoval(), "Knight should be marked for removal when health reaches zero");
    }

    @Test
    void getterMethodsShouldReturnCorrectValues() {
        assertTrue(knight.isAlive(), "isAlive should return true when knight's health is greater than zero");

        assertEquals(health.getClass(), knight.getHealth().getClass(),
                "getHealth should return the knight's health object");

        assertEquals(0f, knight.getDeadStateTime(), "getDeadStateTime should return 0 initially");
        assertEquals(0f, knight.getStateTime(), "getStateTime should return 0 initially");
    }

    @Test
    void setAttackFrameTest() {
        knight.setAttackFrameFalse();
        assertFalse(knight.AttackFrame(), "setAttackFrameFalse should set attackFrame to false");
        knight.setAttackFrameTrue();
        assertTrue(knight.AttackFrame(), "setAttackFrameFalse should set attackFrame to false");
    }

    @Test
    void knightShouldJumpWhenPlayerIsWithinRangeAndConditionsAreMet() {
        // Place player within jump range and close enough
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(5.0f, 6.0f)); // Player is above knight

        knight.update(0.1f);
        verify(mockBody).applyLinearImpulse(new Vector2(0, 150), mockBody.getWorldCenter(), true);
    }

    @Test
    void testSetAttackFrame() {
        knight.setAttackFrame(true);
        assertTrue(knight.AttackFrame(), "Attack frame should be true after being set to true");

        knight.setAttackFrame(false);
        assertFalse(knight.AttackFrame(), "Attack frame should be false after being set to false");
    }

    @Test
    void testSetMarkedForRemoval() {
        assertFalse(knight.isMarkedForRemoval(), "Initially knight should not be marked for removal");
        knight.setMarkedForRemoval(true);
        assertTrue(knight.isMarkedForRemoval(), "Knight should be marked for removal after being set to true");

        knight.setMarkedForRemoval(false);
        assertFalse(knight.isMarkedForRemoval(), "Knight should not be marked for removal after being set to false");
    }

    @Test
    void testGetPosition() {
        Vector2 position = knight.getPosition();
        assertEquals(5.0f, position.x, "X position should match the mocked body position");
        assertEquals(5.0f, position.y, "Y position should match the mocked body position");
    }

    @Test
    void testGetWidth() {
        assertEquals(1.0f, knight.getWidth(), "Width should match the width set in the constructor");
    }

    @Test
    void testGetHeight() {
        assertEquals(1.0f, knight.getHeight(), "Height should match the height set in the constructor");
    }

    @Test
    void testGetAttack2StateTime() {
        assertEquals(0.0f, knight.getAttack2StateTime(), "Attack2StateTime should return 0 as it is not implemented");
    }

}
