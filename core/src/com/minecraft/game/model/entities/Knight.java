package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Player;
import com.minecraft.game.utils.BodyHelperService;
import com.minecraft.game.utils.Constants;

/**
 * Represents the Knight entity in the game, extending the GameEntity class.
 * Knights are enemies that can jump, attack, and follow the player within the
 * game world.
 * They have health, states of behavior, and can be marked for removal when
 * defeated.
 */
public class Knight extends GameEntity implements IViewableEntityModel {
    private float stateTime;
    public State currentState;
    public boolean isFacingRight = true;
    private Player player;
    private float detectionRange = 6.0f; // range within which the enemy detects the player
    private float jumpForce = 150;
    private float jumpThreshold = 0.9f; // Vertical distance threshold for jumping
    public Health health;
    private boolean markForRemoval = false;
    private float deadStateTime = 0f; // Timer for the dead animation
    private boolean attackFrame = false;
    private boolean isInvincible;
    private float invincibilityTimer;
    private static final float INVINCIBILITY_DURATION = 1.0f; // 1 seconds

    /**
     * Constructs a Knight entity with specified dimensions, position, and health.
     * 
     * @param width  Width of the knight.
     * @param height Height of the knight.
     * @param world  The game world where the knight exists.
     * @param player The player character that the knight will interact with.
     * @param x      The initial x-coordinate of the knight.
     * @param y      The initial y-coordinate of the knight.
     * @param health The health object managing the knight's health.
     */
    public Knight(float width, float height, World world, Player player, float x, float y, Health health) {
        super(width, height, BodyHelperService.createBody(x, y, width, height, null, false, world,
                Constants.CATEGORY_ENEMY, Constants.MASK_ENEMY, "knight", false));
        this.player = player;
        this.speed = Constants.ENEMY_SPEED;
        this.health = new Health(4, 4);
        stateTime = 0f;
    }

    /**
     * Updates the state of the knight based on elapsed time and interactions with
     * the player.
     * 
     * @param deltaTime The time elapsed since the last update call.
     */
    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;

        float distanceToPlayerX = Math.abs(player.getBody().getPosition().x - this.body.getPosition().x);
        float distanceToPlayerY = Math.abs(player.getBody().getPosition().y - this.body.getPosition().y);
        float distanceToPlayerYnotABS = player.getBody().getPosition().y - this.body.getPosition().y;

        // vertical range within which the enemy can attack
        float verticalAttackRange = 2.0f;

        if (isInvincible) {
            invincibilityTimer -= deltaTime;
            if (invincibilityTimer <= 0) {
                isInvincible = false;
                // Ensure the player is visible after invincibility ends
            }
        }

        if (currentState != State.DEAD) {
            // jump logic for enemy
            if (distanceToPlayerX < detectionRange && distanceToPlayerYnotABS > jumpThreshold
                    && Math.abs(body.getLinearVelocity().y) == 0 && player.getBody().getLinearVelocity().y == 0
                    && player.getCurrentState() != Player.State.DEAD) {
                // The last condition checks if the enemy is not already jumping or falling
                body.applyLinearImpulse(new Vector2(0, jumpForce), body.getWorldCenter(), true);
            }

            // Check if the enemy is close enough to attack but not currently attacking
            if (distanceToPlayerX < 3.0f && distanceToPlayerY <= verticalAttackRange
                    && player.getCurrentState() != Player.State.DEAD) {

                currentState = State.ATTACKING;
                if (attackFrame == true) {
                    player.getHit();

                    // Push the player when he gets hit (from the left or right)
                    if (player.getCurrentState() != Player.State.ATTACKING) {
                        if (this.body.getPosition().x > player.getBody().getPosition().x) {
                            // Enemy is to the right of the player, push player left and up
                            player.getBody().applyLinearImpulse(new Vector2(-6, 0),
                                    player.getBody().getWorldCenter(), true);

                        } else {
                            // Enemy is to the left of the player, push player right and up
                            player.getBody().applyLinearImpulse(new Vector2(6, 0),
                                    player.getBody().getWorldCenter(), true);
                        }
                    }
                }
                // Stop moving when attacking
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            } else if (distanceToPlayerX < detectionRange && player.getCurrentState() != Player.State.DEAD) {

                if (player.getBody().getPosition().x > this.body.getPosition().x) {
                    body.setLinearVelocity(speed, body.getLinearVelocity().y); // Move right towards the player
                    isFacingRight = true;
                } else {
                    body.setLinearVelocity(-speed, body.getLinearVelocity().y); // Move left towards the player
                    isFacingRight = false;
                }
                currentState = State.RUNNING;
            } else {
                currentState = State.IDLE;
                // stop moving when idle
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }
        }
        if (health.getHealth() <= 0 && currentState != State.DEAD) {
            currentState = State.DEAD;

        }
        if (currentState == State.DEAD) {
            deadStateTime += deltaTime; // Update dead animation time
        }

    }

    /**
     * Returns whether the knight is currently alive.
     * 
     * @return true if alive, false otherwise.
     */
    public boolean isAlive() {
        return health.isAlive();
    }

    /**
     * Gets the current health state of the knight.
     * 
     * @return the health object of the knight.
     */
    public Health getHealth() {
        return health;
    }

    /**
     * Handles the logic when the knight takes damage.
     * 
     * @param damage The amount of damage to apply to the knight.
     */
    public void getHit(int damage) {
        if (!isInvincible) {
            health.damage(damage); // call damage method and reduces health
            isInvincible = true;
            invincibilityTimer = INVINCIBILITY_DURATION;
        }
    }

    /**
     * Checks if the knight is marked for removal from the game world.
     * 
     * @return true if the knight is marked for removal, false otherwise.
     */
    public boolean isMarkedForRemoval() {
        return markForRemoval;
    }

    /**
     * Toggles the removal flag for the knight.
     */
    public void setMarkedForRemoval() {
        markForRemoval = !markForRemoval;
    }

    /**
     * Gets the amount of time the knight has been in the DEAD state.
     * 
     * @return The dead state time in seconds.
     */
    public float getDeadStateTime() {
        return deadStateTime;
    }

    /**
     * Gets the total time the knight has been in its current state.
     * 
     * @return The state time in seconds.
     */
    public float getStateTime() {
        return stateTime;
    }

    /**
     * Gets the current state of the knight (e.g., IDLE, RUNNING, ATTACKING, DEAD).
     * 
     * @return The current state.
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Checks if the knight is facing right.
     * 
     * @return true if facing right, false if facing left.
     */
    public boolean isFacingRight() {
        return isFacingRight;
    }

    /**
     * Sets the attack frame state to true, indicating the knight is in an attack
     * frame.
     */
    public void setAttackFrameTrue() {
        attackFrame = true;
    }

    /**
     * Sets the attack frame state to false, indicating the knight is not in an
     * attack frame.
     */
    public void setAttackFrameFalse() {
        attackFrame = false;
    }

    /**
     * Gets the current state of the attack frame.
     * 
     * @return true if currently in an attack frame, false otherwise.
     */
    public boolean AttackFrame() {
        return attackFrame;
    }

    /**
     * Checks if the knight is currently invincible.
     * 
     * @return true if invincible, false otherwise.
     */
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * Sets whether the knight's attack frame is active.
     * 
     * @param isActive true to activate the attack frame, false to deactivate.
     */
    @Override
    public void setAttackFrame(boolean isActive) {
        attackFrame = isActive;
    }

    /**
     * Sets the knight's removal status.
     * 
     * @param isMarked true to mark the knight for removal, false otherwise.
     */
    @Override
    public void setMarkedForRemoval(boolean isMarked) {
        markForRemoval = isMarked;
    }

    /**
     * Gets the current position of the knight.
     * 
     * @return A Vector2 representing the position of the knight.
     */
    @Override
    public Vector2 getPosition() {
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }

    /**
     * Gets the width of the knight.
     * 
     * @return The width in game units.
     */
    @Override
    public float getWidth() {
        return width;
    }

    /**
     * Gets the height of the knight.
     * 
     * @return The height in game units.
     */
    @Override
    public float getHeight() {
        return height;
    }

    /**
     * Gets the time the knight has spent in the secondary attack state (not used in
     * this class).
     * 
     * @return The time in seconds spent in the attack2 state.
     */
    @Override
    public float getAttack2StateTime() {
        return 0;
    }
}
