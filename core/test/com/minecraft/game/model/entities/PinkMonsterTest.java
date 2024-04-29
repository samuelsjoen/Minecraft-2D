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

class PinkMonsterTest {

    private PinkMonster pinkMonster;
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
        pinkMonster = new PinkMonster(1.0f, 1.0f, mockWorld, mockPlayer, 5.0f, 5.0f, health);
    }

    @Test
    void pinkMonsterShouldTransitionToDeadStateWhenHealthDepletes() {
        pinkMonster.getHit(4);
        pinkMonster.update(0.1f);
        assertEquals(State.DEAD, pinkMonster.getCurrentState(),
                "pinkMonster should transition to DEAD state");
    }

    @Test
    void pinkMonsterShouldFaceTheCorrectWay() {
        // Place player to the right of the pinkMonster
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(10.5f, 5.0f)); // Player is now to the left
        pinkMonster.update(0.1f);
        assertTrue(pinkMonster.isFacingRight(), "pinkMonster should be facing right when player is to the right");
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(0.5f, 5.0f)); // Player is now to the left
        pinkMonster.update(0.1f);
        assertFalse(pinkMonster.isFacingRight(), "pinkMonster should be facing left when player is to the left");
    }

    @Test
    void pinkMonsterShouldTransitionToAttackingWhenCloseToPlayer() {
        // Place player close enough to trigger attacking state
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(5.0f, 5.0f)); // Close to the pinkMonster
        pinkMonster.update(0.1f);
        assertEquals(State.ATTACKING, pinkMonster.getCurrentState(),
                "pinkMonster should transition to ATTACKING state");
    }

    @Test
    void pinkMonsterBecomesVulnerableAfterInvincibilityExpires() {

        pinkMonster.getHit(1);
        assertTrue(pinkMonster.isInvincible(), "pinkMonster should become invincible after being hit");

        pinkMonster.update(0.2f);
        assertTrue(pinkMonster.isInvincible(), "pinkMonster should become vulnerable after invincibility expires");

        pinkMonster.update(0.2f);
        assertTrue(pinkMonster.isInvincible(), "pinkMonster should become vulnerable after invincibility expires");

        pinkMonster.update(0.2f);
        assertTrue(pinkMonster.isInvincible(), "pinkMonster should become vulnerable after invincibility expires");

        pinkMonster.update(0.2f);
        assertTrue(pinkMonster.isInvincible(), "pinkMonster should become vulnerable after invincibility expires");

        pinkMonster.update(0.3f);
        assertFalse(pinkMonster.isInvincible(), "pinkMonster should become vulnerable after invincibility expires");
    }

    // new tests

    @Test
    void TestpinkMonsterStatesBasedOfDistanceWithThePlayer() {
        // Place player far enough from the pinkMonster
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(50f, 5.0f)); // Far from the slime
        pinkMonster.update(0.1f);
        assertEquals(State.IDLE, pinkMonster.getCurrentState(),
                "pinkMonster should transition to ATTACKING state");

        // Place player close enought to trigger running state of the pinkMonster
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(10f, 5.0f)); // Close to run
        pinkMonster.update(0.1f);
        assertEquals(State.RUNNING, pinkMonster.getCurrentState(),
                "pinkMonster should transition to ATTACKING state");

        // Place player close enough from the pinkMonster
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(5.0f, 5.0f)); // Close to attack
        pinkMonster.update(0.1f);
        assertEquals(State.ATTACKING, pinkMonster.getCurrentState(),
                "pinkMonster should transition to ATTACKING state");
    }

    @Test
    void pinkMonsterMarkedForRemovalTest() {
        pinkMonster.setMarkedForRemoval();
        assertTrue(pinkMonster.isMarkedForRemoval(),
                "pinkMonster should be marked for removal when health reaches zero");
    }

    @Test
    void getterMethodsShouldReturnCorrectValues() {
        assertTrue(pinkMonster.isAlive(), "isAlive should return true when pinkMonster's health is greater than zero");

        assertEquals(health.getClass(), pinkMonster.getHealth().getClass(),
                "getHealth should return the pinkMonster's health object");

        assertEquals(0f, pinkMonster.getDeadStateTime(), "getDeadStateTime should return 0 initially");
        assertEquals(0f, pinkMonster.getStateTime(), "getStateTime should return 0 initially");
    }

    @Test
    void setAttackFrameTest() {
        pinkMonster.setAttackFrameFalse();
        assertFalse(pinkMonster.AttackFrame(), "setAttackFrameFalse should set attackFrame to false");
        pinkMonster.setAttackFrameTrue();
        assertTrue(pinkMonster.AttackFrame(), "setAttackFrameFalse should set attackFrame to false");
    }

    @Test
    void pinkMonsterShouldJumpWhenPlayerIsWithinRangeAndConditionsAreMet() {
        // Place player within jump range and close enough
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(5.0f, 7.0f)); // Player is above pinkMonster

        pinkMonster.update(0.1f);
        verify(mockBody).applyLinearImpulse(new Vector2(0, 105), mockBody.getWorldCenter(), true);
    }

    @Test
    void pinkMonsterShouldTransitionToAttacking2WhenPlayerIsWithinRangeAndConditionsAreMet() {
        // Place player within range to trigger attacking2 state
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(16.0f, 5.0f)); // Player is within range

        // Ensure enough time has passed since last attack
        pinkMonster.update(4.1f);
        assertEquals(State.ATTACKING2, pinkMonster.getCurrentState(),
                "pinkMonster should transition to ATTACKING2 state");
    }

    @Test
    void pinkMonsterShouldThrowRockWhenInAttack2StateAndConditionsAreMet() {
        // Place player within range to trigger attacking2 state
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(16.0f, 5.0f)); // Player is within range

        // Ensure enough time has passed since last attack
        pinkMonster.update(4.0f);

        // Transition to ATTACKING2 state
        pinkMonster.update(0.1f);

        // Ensure attackFrame is set to true before the update call
        pinkMonster.setAttackFrameTrue();

        pinkMonster.update(0.1f);

        // Verify that a rock has been thrown
        assertTrue(pinkMonster.hasThrownRock(), "pinkMonster should have thrown a rock in ATTACKING2 state");
    }

    @Test
    void pinkMonsterShouldNotThrowRockWhenInAttack2StateAndConditionsAreNotMet() {
        // Place player out of range to prevent transitioning to attacking2 state
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(50.0f, 5.0f)); // Player is out of range

        // Ensure enough time has passed since last attack
        pinkMonster.update(4.0f);
        assertEquals(State.IDLE, pinkMonster.getCurrentState(),
                "pinkMonster should transition to ATTACKING2 state");

        // Ensure attackFrame is set to true before the update call
        pinkMonster.setAttackFrameTrue();

        pinkMonster.update(0.1f);

        // Verify that no rock has been thrown
        assertFalse(pinkMonster.hasThrownRock(), "pinkMonster should not throw a rock if conditions are not met");
    }

    @Test
    void pinkMonsterAttack2StateTimeShouldIncrease() {
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(16.0f, 5.0f)); // Player is out of range

        // Ensure enough time has passed since last attack
        pinkMonster.update(4.0f);

        // Transition to ATTACKING2 state
        pinkMonster.update(0.1f);

        pinkMonster.update(0.1f);

        // Verify that attack2StateTime has increased
        assertTrue(pinkMonster.getAttack2StateTime() > 0, "attack2StateTime should increase in ATTACKING2 state");
    }

    @Test
    void pinkMonsterShouldTransitionToIdleStateAfterAttackCooldown() {
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(16.0f, 5.0f)); // Player is out of range
        // Ensure enough time has passed since last attack

        pinkMonster.update(4.0f);
        pinkMonster.setAttackFrameTrue();
        pinkMonster.update(4.0f);
        // simulate time passing
        pinkMonster.update(2.5f);

        // Verify that pinkMonster transitions to IDLE state
        assertEquals(State.IDLE, pinkMonster.getCurrentState(),
                "pinkMonster should transition to IDLE state after attack cooldown");
    }

    @Test
    void testGetWidth() {
        assertEquals(1.0f, pinkMonster.getWidth(), "Width should match the width set in the constructor");
    }

    @Test
    void testGetHeight() {
        assertEquals(1.0f, pinkMonster.getHeight(), "Height should match the height set in the constructor");
    }

    @Test
    void testSetAttackFrame() {
        pinkMonster.setAttackFrame(true);
        assertTrue(pinkMonster.AttackFrame(), "Attack frame should be true after being set to true");

        pinkMonster.setAttackFrame(false);
        assertFalse(pinkMonster.AttackFrame(), "Attack frame should be false after being set to false");
    }

    @Test
    void testSetMarkedForRemoval() {
        assertFalse(pinkMonster.isMarkedForRemoval(), "Initially, pinkMonster should not be marked for removal");
        pinkMonster.setMarkedForRemoval(true);
        assertTrue(pinkMonster.isMarkedForRemoval(),
                "PinkMonster should be marked for removal after being set to true");

        pinkMonster.setMarkedForRemoval(false);
        assertFalse(pinkMonster.isMarkedForRemoval(),
                "PinkMonster should not be marked for removal after being set to false");
    }

    @Test
    void testGetPosition() {
        Vector2 position = pinkMonster.getPosition();
        assertEquals(5.0f, position.x, "X position should match the mocked body position");
        assertEquals(5.0f, position.y, "Y position should match the mocked body position");
    }

}
