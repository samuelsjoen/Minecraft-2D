package com.minecraft.game.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HealthTest {

    @Test
    void testDamage() {
        Health health = new Health(5, 5);
        assertTrue(health.isAlive());
        health.damage(3);
        assertTrue(health.getHealth() == 2);
        assertTrue(health.isAlive());
        health.damage(3);
        assertTrue(health.getHealth() == 0);
        assertFalse(health.isAlive());
    }

    @Test
    void testHeal() {
        Health health = new Health(2, 5);
        assertTrue(health.getHealth() == 2);
        health.heal(2);
        assertTrue(health.getHealth() == 4);
        health.heal(2);
        assertTrue(health.getHealth() == 5);
        health.damage(5);
        assertFalse(health.isAlive());
        health.heal(5);
        assertFalse(health.isAlive());
        assertTrue(health.getHealth() == 0);
    }

    @Test
    void testRevive() {
        Health health = new Health(5, 5);
        health.damage(5);
        assertTrue(!health.isAlive());
        health.revive();
        assertTrue(health.isAlive());
        assertTrue(health.getHealth() == 5);
    }
}
