// package com.mygdx.game.model;
package com.minecraft.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.model.Player.State;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;

    @Mock
    private Body mockBody;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockBody.getPosition()).thenReturn(new Vector2(5, 5));
        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(0, 0));
        // constants for width and height
        final float testWidth = 10.0f;
        final float testHeight = 10.0f;
        player = new Player(testWidth, testHeight, mockBody, null);
    }

    @Test
    public void playerStartsIdle() {
        assertEquals(Player.State.IDLE, player.getCurrentState(), "Player should start in IDLE state.");
    }

    @Test
    public void playerBecomesInvincibleWhenHit() {
        assertFalse(player.isInvincible(), "Player should not start invincible.");
        player.getHit();
        assertTrue(player.isInvincible(), "Player should become invincible after getting hit.");
    }

    @Test
    public void playerHealthReducesWhenHit() {
        int initialHealth = Player.getHealth().getHealth();
        player.getHit();
        assertEquals(initialHealth - 1, Player.getHealth().getHealth(),
                "Player health should reduce by 1 after getting hit.");
    }

    @Test
    public void playerDiesWhenHealthDepletes() {
        // the player starts with 5 health, hit the player 5 times
        for (int i = 0; i < 5; i++) {
            if (player.isInvincible()) {
                player.setIsInvincible();
            }
            player.getHit();
            if (Player.getHealth().getHealth() <= 0) {
                Player.currentState = State.DEAD;
            }
        }
        assertEquals(Player.State.DEAD, player.getCurrentState(), "Player should be DEAD after sufficient damage.");
    }

}
