package com.mygdx.game.model;

import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

import com.minecraft.game.model.Health;

public class HealthTest {

    @Test
    void testDamage() {
        Health health = new Health(5, 5);
        assumeTrue(health.isAlive());
        health.damage(3);
        assumeTrue(health.getHealth() == 2);
        assumeTrue(health.isAlive());
        health.damage(3);
        assumeTrue(health.getHealth() == 0);
        assumeFalse(health.isAlive());
    }

    @Test
    void testHeal() {
        Health health = new Health(2, 5);
        assumeTrue(health.getHealth() == 2);
        health.heal(2);
        assumeTrue(health.getHealth() == 4);
        health.heal(2);
        assumeTrue(health.getHealth() == 5);
        health.damage(5);
        assumeFalse(health.isAlive());
        health.heal(5);
        assumeFalse(health.isAlive());
        assumeTrue(health.getHealth() == 0);
    }

    @Test
    void testRevive() {
        Health health = new Health(5, 5);
        health.damage(5);
        assumeTrue(!health.isAlive());
        health.revive();
        assumeTrue(health.isAlive());
        assumeTrue(health.getHealth() == 5);
    }
}
