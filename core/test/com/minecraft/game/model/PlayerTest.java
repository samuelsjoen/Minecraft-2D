package com.minecraft.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.model.Player.State;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Slime;
import com.minecraft.game.model.items.Inventory;
import com.minecraft.game.model.items.Item;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.SpriteManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;

    @Mock
    private Body mockBody;
    @Mock
    private Inventory mockInventory;
    @Mock
    private Knight mockKnight;
    @Mock
    private Slime mockSlime;
    @Mock
    private PinkMonster mockPinkMonster;
    @Mock
    private EnemyManager enemyManager;
    @Mock
    private Body mockKnightBody;
    @Mock
    private Body mockSlimeBody;
    @Mock
    private Body mockPinkMonsterBody;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockBody.getPosition()).thenReturn(new Vector2(5, 5));
        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(0, 0));
        // constants for width and height
        final float testWidth = 10.0f;
        final float testHeight = 10.0f;
        player = new Player(testWidth, testHeight, mockBody, mockInventory, new Health(5, 5));
    }

    @Test
    void playerIsTeleportedWhenFallingOutOfbounds() {
        when(mockBody.getPosition()).thenReturn(new Vector2(5, -11f));
        float screenWidth = 1280;
        float screenHeight = 720;

        player.playerOutOfbounds(screenWidth, screenHeight);

        // Expected positions
        float expectedMiddleX = screenWidth / 2 / Constants.PPM;
        float expectedMiddleY = screenHeight / 0.15f / Constants.PPM;

        verify(mockBody).setTransform(eq(expectedMiddleX), eq(expectedMiddleY), anyFloat());
    }

    @Test
    void attackTargetsEnemiesWithinRange() {
        try (MockedStatic<SpriteManager> mockedSpriteManager = Mockito.mockStatic(SpriteManager.class)) {
            // Set up mock bodies for the entities
            mockKnightBody = mock(Body.class);
            mockSlimeBody = mock(Body.class);
            mockPinkMonsterBody = mock(Body.class);

            // Return specific positions for the mock bodies
            when(mockKnightBody.getPosition()).thenReturn(new Vector2(6, 5)); // Close to player attackrange
            when(mockSlimeBody.getPosition()).thenReturn(new Vector2(50, 5)); // Far away from player in x
            when(mockPinkMonsterBody.getPosition()).thenReturn(new Vector2(6, 50)); // far away from player in y

            // Associate the mock bodies with the entities
            when(mockKnight.getBody()).thenReturn(mockKnightBody);
            when(mockSlime.getBody()).thenReturn(mockSlimeBody);
            when(mockPinkMonster.getBody()).thenReturn(mockPinkMonsterBody);
            // Mock EnemyManager to return these entities
            EnemyManager.knights.clear();
            EnemyManager.slimes.clear();
            EnemyManager.pinkMonsters.clear();
            EnemyManager.knights.add(mockKnight);
            EnemyManager.slimes.add(mockSlime);
            EnemyManager.pinkMonsters.add(mockPinkMonster);

            // Mock the static method call within SpriteManager
            mockedSpriteManager.when(() -> SpriteManager.getCurrentFrameIndex()).thenReturn(2);

            // Set player state to attacking
            player.setCurrentState(Player.State.ATTACKING);

            player.attack();

            verify(mockKnight, times(1)).getHit(anyInt()); // Knight within attack range and directly in front
            verify(mockSlime, never()).getHit(anyInt()); // Slime out of attack range in x
            verify(mockPinkMonster, never()).getHit(anyInt()); // PinkMonster out of attack range in y
            when(mockSlimeBody.getPosition()).thenReturn(new Vector2(9, 5)); // Close to player in x
            when(mockPinkMonsterBody.getPosition()).thenReturn(new Vector2(6, 8)); // Close to player in y
            player.attack();

            verify(mockSlime, times(1)).getHit(anyInt()); // Slime within attack range
            verify(mockPinkMonster, times(1)).getHit(anyInt()); // PinkMonster within attack range

        }
    }

    @Test
    public void playerStartsIdle() {
        assertEquals(Player.State.IDLE, player.getCurrentState(), "Player should start in IDLE state.");
    }

    @Test
    void testPlayerInvincibilityAfterGettingHit() {
        float INVINCIBILITY_DURATION = 1.0f;
        player.getHit(); // Player gets hit once
        assertTrue(player.isInvincible());

        // Simulate invincibility duration has not yet passed
        player.update(INVINCIBILITY_DURATION / 2);
        assertTrue(player.isInvincible());

        // Simulate invincibility duration has passed
        player.update(INVINCIBILITY_DURATION);
        assertFalse(player.isInvincible());
    }

    @Test
    public void playerHealthReducesWhenHit() {
        int initialHealth = Player.getHealth().getHealth();
        player.getHit();
        assertEquals(initialHealth - 1, Player.getHealth().getHealth(),
                "Player health should reduce by 1 after getting hit.");
    }

    @Test
    void testPlayerDeathState() {
        for (int i = 0; i < 5; i++) {
            player.getHit(); // Each hit reduces health by 1 and player starts with 5 health
            player.update(1.0f); // 1.0f delta time to make sure the INVINCIBILITY TIMER passed
        }
        assertEquals(Player.State.DEAD, player.getCurrentState(), "Player state should be DEAD");
    }

    @Test
    public void playerDiesWhenHealthDepletes() {
        // Hit the player 5 times
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

    @Test
    void testPlayerStateChangeToRunningWhenMoving() {
        // Simulate player moving right
        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(Constants.PLAYER_MOVE_SPEED, 0));
        player.update(0.1f);
        assertEquals(Player.State.RUNNING, player.getCurrentState());

        // Simulate player stopping (velocity is zero)
        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(0, 0));
        player.update(0.1f);
        assertEquals(Player.State.IDLE, player.getCurrentState());
    }

    @Test
    void testSetAndGetCurrentState() {
        player.setCurrentState(Player.State.RUNNING);
        assertEquals(Player.State.RUNNING, player.getCurrentState());

        player.setCurrentState(Player.State.IDLE);
        assertEquals(Player.State.IDLE, player.getCurrentState());
    }

    @Test
    void testIsFacingRight() {
        // Player starts facing right
        assertTrue(player.isFacingRight());

        // Simulate player movement to the left
        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(-1, 0));
        player.update(0.1f);
        assertFalse(player.isFacingRight());
    }

    @Test
    void testPlayerAttackingState() {
        // Simulate the player starting to attack
        player.updateMovement(false, false, true);
        player.update(0);
        assertEquals(Player.State.ATTACKING, player.getCurrentState(), "Player state should be ATTACKING");
    }

    @Test
    void testPlayerIsNotAttackingState() {
        // Simulate the player is not attacking
        player.updateMovement(false, false, false);
        player.update(0);
        assertEquals(Player.State.IDLE, player.getCurrentState(), "Player state should be IDLE");
    }

    @Test
    void testDimensionAndPositionAccessors() {
        assertEquals(10.0f, player.getWidth());
        assertEquals(10.0f, player.getHeight());
        player.update(0.1f); // Updating to make sure player x and y gets scaled to the PPM
        assertEquals(5 * Constants.PPM, player.getX());
        assertEquals(5 * Constants.PPM, player.getY());
    }

    @Test
    void testInvincibilityTimerAfterGettingHit() {
        float INVINCIBILITY_DURATION = 1.0f;

        // Ensure invincibilityTimer starts at 0
        assertEquals(0, player.invincibilityTimer(), "Initially, invincibility timer should be 0");

        // Simulate player getting hit, which starts the invincibility timer
        player.getHit();
        assertEquals(INVINCIBILITY_DURATION, player.invincibilityTimer(),
                "Invincibility timer should match INVINCIBILITY_DURATION after getting hit");

        // Simulate the passage of time and check the timer decreases
        float deltaTime = 0.5f; // Simulating half a second has passed
        player.update(0.5f);

        // Since invincibilityTimer is decremented by deltaTime in update(), it should
        // be INVINCIBILITY_DURATION - deltaTime
        assertEquals(INVINCIBILITY_DURATION - deltaTime, player.invincibilityTimer(),
                "Invincibility timer should decrement by deltaTime after update");
    }

    @Test
    void testCalculateDamageBasedOnSelectedItem() {
        // Adds the item to the inventory
        mockInventory.addItem(Item.WOODEN_SWORD, 1);
        when(mockInventory.getSelectedItem()).thenReturn(Item.WOODEN_SWORD);
        // Simulate calling calculateDamage when the player has a WOODEN_SWORD selected
        int damage = player.calculateDamage();
        assertEquals(2, damage, "Damage should be 2 when the player has a WOODEN_SWORD selected");

        mockInventory.addItem(Item.IRON_SWORD, 1);
        when(mockInventory.getSelectedItem()).thenReturn(Item.IRON_SWORD);
        // Simulate calling calculateDamage when the player has an IRON_SWORD selected
        damage = player.calculateDamage();
        assertEquals(3, damage, "Damage should be 3 when the player has a IRON_SWORD selected");

        mockInventory.addItem(Item.DIAMOND_SWORD, 1);
        when(mockInventory.getSelectedItem()).thenReturn(Item.DIAMOND_SWORD);
        // Simulate calling calculateDamage when the player has a DIAMOND_SWORD selected
        damage = player.calculateDamage();
        assertEquals(4, damage, "Damage should be 4 when the player has a DIAMOND_SWORD selected");

        mockInventory.addItem(Item.WOOD, 1);
        when(mockInventory.getSelectedItem()).thenReturn(Item.WOOD);
        // Simulate calling calculateDamage when the player has a no sword selected
        damage = player.calculateDamage();
        assertEquals(1, damage, "Damage should be 1 when the player has no sword selected");
    }

}
