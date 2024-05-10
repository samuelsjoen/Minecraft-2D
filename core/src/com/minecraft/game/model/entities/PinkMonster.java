package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.EnemyManager;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Player;
import com.minecraft.game.utils.BodyHelperService;
import com.minecraft.game.utils.Constants;

/**
 * Represents the PinkMonster entity in the game, extending the GameEntity
 * class.
 * PinkMonster are enemies that can jump, attack, throw a rock, and follow the
 * player within the
 * game world.
 * They have health, states of behavior, and can be marked for removal when
 * defeated.
 */
public class PinkMonster extends GameEntity implements IViewableEntityModel {
    private float stateTime;
    private State currentState;
    private boolean isFacingRight = true;
    private Player player;
    private float detectionRange = 10.0f; // range within which the enemy detects the player
    private float jumpForce = 105;
    private float jumpThreshold = 1.5f; // Vertical distance threshold for jumping
    public Health health;
    private boolean markForRemoval = false;
    private float deadStateTime = 0f; // Timer for the dead animation
    private float attack2StateTime = 0f; // Timer for the attack2 animation
    private World world;
    private float attackCooldown = 4.0f; // 5 seconds cooldown
    private float timeSinceLastAttack = 0; // Time since last attack
    private boolean hasThrownRock = false; // Flag to check if rock has been thrown in the current attack
    private boolean attackFrame = false;
    private boolean isInvincible;
    private float invincibilityTimer;
    private static final float INVINCIBILITY_DURATION = 1.0f; // 1 seconds
    private EntityFactory entityFactory;

    /**
     * Constructs a PinkMonster entity with specified dimensions, position, and
     * health.
     * 
     * @param width  Width of the PinkMonster.
     * @param height Height of the PinkMonster.
     * @param world  The game world where the PinkMonster exists.
     * @param player The player character that the PinkMonster will interact with.
     * @param x      The initial x-coordinate of the PinkMonster.
     * @param y      The initial y-coordinate of the PinkMonster.
     * @param health The health object managing the PinkMonster's health.
     */
    public PinkMonster(float width, float height, World world, Player player, float x, float y, Health health) {
        super(width, height, BodyHelperService.createBody(x, y, width, height, null, false, world,
                Constants.CATEGORY_ENEMY, Constants.MASK_ENEMY, "pinkMonster", false));
        this.player = player;
        this.speed = Constants.ENEMY_SPEED;
        this.health = new Health(3, 3);
        this.world = world;

        currentState = State.IDLE;

        stateTime = 0f;

        entityFactory = new EntityFactory();
    }

    /**
     * Updates the state of the pinkmonster based on elapsed time and interactions
     * with
     * the player.
     * 
     * @param deltaTime The time elapsed since the last update call.
     */
    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;
        timeSinceLastAttack += deltaTime;
        float distanceToPlayerX = Math.abs(player.getBody().getPosition().x - this.body.getPosition().x);
        float distanceToPlayerY = Math.abs(player.getBody().getPosition().y - this.body.getPosition().y);
        float distanceToPlayerYnotABS = player.getBody().getPosition().y - this.body.getPosition().y;

        // vertical range within which the enemy can attack
        float verticalAttackRange = 1.5f;

        if (isInvincible) {
            invincibilityTimer -= deltaTime;
            if (invincibilityTimer <= 0) {
                isInvincible = false;
                // Ensure the player is visible after invincibility ends
            }
            // Optional: Add blinking logic/Sound/Cool effect here
        }

        if (currentState != State.DEAD) {

            // JUMP LOGIC
            if (distanceToPlayerX < detectionRange && distanceToPlayerYnotABS > jumpThreshold
                    && Math.abs(body.getLinearVelocity().y) == 0 && player.getBody().getLinearVelocity().y == 0
                    && player.getCurrentState() != Player.State.DEAD) {
                // The last condition checks if the enemy is not already jumping or falling
                body.applyLinearImpulse(new Vector2(0, jumpForce), body.getWorldCenter(), true);
            }

            // ATTACK LOGIC--
            // ATTACK 2 LOGIC
            if (distanceToPlayerX <= 20 && distanceToPlayerX > 10 && timeSinceLastAttack >= attackCooldown) {
                currentState = State.ATTACKING2;

                // Determine the monster's orientation based on the player's position
                isFacingRight = player.getBody().getPosition().x > this.getBody().getPosition().x;

                // Only create a projectile at a specific frame of the attack animation to
                // simulate the throwing action
                if (currentState == State.ATTACKING2 && attackFrame == true) {
                    Vector2 startPosition = new Vector2(this.getBody().getPosition().x * Constants.PPM,
                            (this.getBody().getPosition().y * Constants.PPM) + 20);
                    Vector2 targetPosition = new Vector2(player.getBody().getPosition().x * Constants.PPM,
                            player.getBody().getPosition().y * Constants.PPM);

                    EntityParams projectileParams = new EntityParams(world, null, startPosition.x, startPosition.y,
                            null);
                    projectileParams.setTargetPosition(targetPosition.x, targetPosition.y);
                    Projectile projectile = (Projectile) entityFactory.create("Projectile", projectileParams);
                    EnemyManager.addProjectile(projectile);

                    hasThrownRock = true; // Mark that a rock has been thrown
                    timeSinceLastAttack = 0; // Reset the timer immediately after throwing a rock
                    attack2StateTime = 0f; // Reset the animation time for attack2

                }

            } else if (distanceToPlayerX < 2f && distanceToPlayerY <= verticalAttackRange
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

                // MOVING LOGIC
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

        // DEAD LOGIC
        if (health.getHealth() <= 0 && currentState != State.DEAD) {
            currentState = State.DEAD;
        }
        if (currentState == State.DEAD) {
            deadStateTime += deltaTime; // Update dead animation time
        }
        if (currentState == State.ATTACKING2) {
            attack2StateTime += deltaTime; // Update dead animation time
        }
        if (timeSinceLastAttack >= attackCooldown) {
            hasThrownRock = false;
        }
        if (distanceToPlayerX <= 15 && distanceToPlayerX > 10 && !hasThrownRock) {
            hasThrownRock = true;
        }

    }

    /**
     * Returns whether the pinkmonster is currently alive.
     * 
     * @return true if alive, false otherwise.
     */
    public boolean isAlive() {
        return health.isAlive();
    }

    /**
     * Gets the current health state of the pinkmonster.
     * 
     * @return the health object of the pinkmonster.
     */
    public Health getHealth() {
        return health;
    }

    /**
     * Handles the logic when the pinkmonster takes damage.
     * 
     * @param damage The amount of damage to apply to the pinkmonster.
     */
    public void getHit(int damage) {
        if (!isInvincible) {
            health.damage(damage); // call damage method and reduces health
            isInvincible = true;
            invincibilityTimer = INVINCIBILITY_DURATION;
        }
    }

    /**
     * Checks if the pinkmonster is marked for removal from the game world.
     * 
     * @return true if the pinkmonster is marked for removal, false otherwise.
     */
    public boolean isMarkedForRemoval() {
        return markForRemoval;
    }

    /**
     * Toggles the removal flag for the pinkmonster.
     */
    public void setMarkedForRemoval() {
        markForRemoval = !markForRemoval;
    }

    /**
     * Gets the amount of time the pinkmonster has been in the DEAD state.
     * 
     * @return The dead state time in seconds.
     */
    public float getDeadStateTime() {
        return deadStateTime;
    }

    /**
     * Gets the amount of time the pinkmonster has been in the Attack2(throwing a
     * rock) state.
     * 
     * @return The Attack2 state time in seconds.
     */
    public float getAttack2StateTime() {
        return attack2StateTime;
    }

    /**
     * Gets the total time the pinkmonster has been in its current state.
     * 
     * @return The state time in seconds.
     */
    public float getStateTime() {
        return stateTime;
    }

    /**
     * Gets the current state of the pinkmonster (e.g., IDLE, RUNNING, ATTACKING,
     * DEAD).
     * 
     * @return The current state.
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Checks if the pinkmonster is facing right.
     * 
     * @return true if facing right, false if facing left.
     */
    public boolean isFacingRight() {
        return isFacingRight;
    }

    /**
     * Sets the attack frame state to true, indicating the pinkmonster is in an
     * attack
     * frame.
     */
    public void setAttackFrameTrue() {
        attackFrame = true;
    }

    /**
     * Sets the attack frame state to false, indicating the pinkmonster is not in an
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
     * Checks if the pinkmonster is currently invincible.
     * 
     * @return true if invincible, false otherwise.
     */
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * Checks if the Pink Monster has thrown a rock.
     * 
     * @return true if a rock has been thrown during the current attack phase, false
     *         otherwise.
     */
    public boolean hasThrownRock() {
        return hasThrownRock;
    }

    /**
     * Gets the width of the pinkmonster.
     * 
     * @return The width in game units.
     */
    @Override
    public float getWidth() {
        return width;
    }

    /**
     * Gets the height of the pinkmonster.
     * 
     * @return The height in game units.
     */
    @Override
    public float getHeight() {
        return height;
    }

    /**
     * Sets whether the pinkmonster's attack frame is active.
     * 
     * @param isActive true to activate the attack frame, false to deactivate.
     */
    @Override
    public void setAttackFrame(boolean isActive) {
        attackFrame = isActive;
    }

    /**
     * Sets the pinkmonster's removal status.
     * 
     * @param isMarked true to mark the pinkmonster for removal, false otherwise.
     */
    @Override
    public void setMarkedForRemoval(boolean isMarked) {
        markForRemoval = isMarked;
    }

    /**
     * Gets the current position of the pinkmonster.
     * 
     * @return A Vector2 representing the position of the pinkmonster.
     */
    @Override
    public Vector2 getPosition() {
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }
}
