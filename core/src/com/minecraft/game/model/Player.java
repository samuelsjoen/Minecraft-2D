package com.minecraft.game.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.model.entities.GameEntity;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Slime;
import com.minecraft.game.model.items.Inventory;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.SpriteManager;

/**
 * Represents the player character in the game, handling movement, interactions,
 * and combat mechanics.
 */
public class Player extends GameEntity {

    private boolean isFacingRight;
    private static Health health;
    private static Inventory inventory;

    private boolean isInvincible;
    private float invincibilityTimer;
    private static final float INVINCIBILITY_DURATION = 1.0f; // 1 seconds
    private float velX;

    public enum State {
        IDLE, RUNNING, ATTACKING, DEAD
    }

    public static State currentState;

    private Boolean moveLeft;
    private Boolean moveRight;
    private Boolean isAttacking;

    /**
     * Constructs a Player with specified parameters, initializing state and health.
     *
     * @param width     the width of the player's entity
     * @param height    the height of the player's entity
     * @param body      the physics body associated with the player
     * @param inventory the player's inventory for managing items
     * @param health    the player's health management system
     */
    public Player(float width, float height, Body body, Inventory inventory, Health health) {
        super(width, height, body);
        this.speed = 10f;
        Player.inventory = inventory;
        Player.health = health;
        currentState = State.IDLE;

        this.velX = 0;
        this.isFacingRight = true;

        this.moveLeft = false;
        this.moveRight = false;
        this.isAttacking = false;
    }

    /**
     * Updates the player's movement based on input flags from the controller.
     *
     * @param moveLeft    indicates whether the player is moving left
     * @param moveRight   indicates whether the player is moving right
     * @param isAttacking indicates whether the player is attacking
     */
    public void updateMovement(Boolean moveLeft, Boolean moveRight, boolean isAttacking) {
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
        this.isAttacking = isAttacking;
    }

    /**
     * Updates the player's state each frame, applying movement, checking for
     * out-of-bounds conditions, handling attacks, and updating invincibility
     * status.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        velX = 0;

        if (this.moveLeft) {
            velX = -Constants.PLAYER_MOVE_SPEED;
        }

        if (this.moveRight) {
            velX = Constants.PLAYER_MOVE_SPEED;
        }

        x = body.getPosition().x * Constants.PPM;
        y = body.getPosition().y * Constants.PPM;

        if (Math.abs(body.getLinearVelocity().x) > 0) {
            currentState = State.RUNNING;
        } else {
            currentState = State.IDLE;
        }

        // Update isFacingRight based on character movement (so we can flip the sprite)

        if (body.getLinearVelocity().x < 0) {
            isFacingRight = false;
        } else if (body.getLinearVelocity().x > 0) {
            isFacingRight = true;
        }

        // Teleport the player back on the map if he falls too low
        playerOutOfbounds(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        if (isInvincible) {
            invincibilityTimer -= deltaTime;
            if (invincibilityTimer <= 0) {
                isInvincible = false;
                // Ensure the plbayer is visible after invincibility ends
            }

        }

        if (this.isAttacking) {
            currentState = State.ATTACKING;
            attack();
        }

        if (health.getHealth() <= 0 && currentState != State.DEAD) {
            // currentState = State.DEAD;
            setCurrentState(State.DEAD);
        }

        body.setLinearVelocity(velX, body.getLinearVelocity().y);

    }

    /**
     * Teleports the player back onto the playing field if they fall out of bounds.
     *
     * @param screenWidth  the width of the screen to calculate bounds
     * @param screenHeight the height of the screen to calculate bounds
     */
    public void playerOutOfbounds(float screenWidth, float screenHeight) {
        float yfall = -10f;
        if (body.getPosition().y < yfall) {
            currentState = State.DEAD;
            float middleX = screenWidth / 2 / Constants.PPM;
            float middleY = screenHeight / 0.15f / Constants.PPM;
            body.setTransform(middleX, middleY, body.getAngle());
        }
    }

    /**
     * Applies damage to the player and triggers invincibility if not already
     * invincible.
     */
    public void getHit() {
        if (!isInvincible) {
            health.damage(1); // call damage method and reduces health
            isInvincible = true;
            invincibilityTimer = INVINCIBILITY_DURATION;
        }
    }

    /**
     * Executes attack mechanics, checking for nearby enemies within attack range
     * and applying damage.
     */
    public void attack() {
        float attackRange = 5.0f;
        float verticalAttackRange = 3.0f;
        for (Knight enemy : EnemyManager.getEnemies()) {
            float distanceToEnemyX = enemy.getBody().getPosition().x - this.body.getPosition().x;
            float distanceToEnemyY = Math.abs(enemy.getBody().getPosition().y - this.body.getPosition().y);

            boolean isEnemyInFront = isFacingRight ? distanceToEnemyX > 0 : distanceToEnemyX < 0;

            if (Math.abs(distanceToEnemyX) < attackRange && distanceToEnemyY <= verticalAttackRange
                    && Player.currentState == State.ATTACKING && (SpriteManager.getCurrentFrameIndex() == 2
                            || SpriteManager.getCurrentFrameIndex() == 3)
                    && isEnemyInFront) {
                enemy.getHit(calculateDamage()); // Applies damage to the targeted enemy
            }
        }
        for (Slime slime : EnemyManager.getSlimes()) {
            float distanceToSlimeX = slime.getBody().getPosition().x - this.body.getPosition().x;
            float distanceToSlimeY = Math.abs(slime.getBody().getPosition().y - this.body.getPosition().y);

            boolean isEnemyInFront = isFacingRight ? distanceToSlimeX > 0 : distanceToSlimeX < 0;

            if (Math.abs(distanceToSlimeX) < attackRange && distanceToSlimeY <= verticalAttackRange
                    && Player.currentState == State.ATTACKING && (SpriteManager.getCurrentFrameIndex() == 2
                            || SpriteManager.getCurrentFrameIndex() == 3)
                    && isEnemyInFront) {
                slime.getHit(calculateDamage()); // Applies damage to the targeted enemy
            }
        }
        for (PinkMonster pinkMonster : EnemyManager.getPinkMonsters()) {
            float distanceToPinkMonsterX = pinkMonster.getBody().getPosition().x - this.body.getPosition().x;
            float distanceToPinkMonsterY = Math.abs(pinkMonster.getBody().getPosition().y - this.body.getPosition().y);

            boolean isEnemyInFront = isFacingRight ? distanceToPinkMonsterX > 0 : distanceToPinkMonsterX < 0;

            if (Math.abs(distanceToPinkMonsterX) < attackRange && distanceToPinkMonsterY <= verticalAttackRange
                    && Player.currentState == State.ATTACKING && (SpriteManager.getCurrentFrameIndex() == 2
                            || SpriteManager.getCurrentFrameIndex() == 3)
                    && isEnemyInFront) {
                pinkMonster.getHit(calculateDamage()); // Applies damage to the targeted enemy
            }
        }
    }

    /**
     * Calculates damage based on the player's current equipment.
     *
     * @return the amount of damage the player can inflict
     */
    public int calculateDamage() {
        if (inventory.getSelectedItem() == null) {
            return 1;
        }
        switch (inventory.getSelectedItem()) {
            case WOODEN_SWORD:
                return 2;
            case IRON_SWORD:
                return 3;
            case DIAMOND_SWORD:
                return 4;
            default:
                return 1;
        }
    }

    /**
     * Sets the current state of the player.
     *
     * @param state The new state to set for the player.
     */
    public void setCurrentState(State state) {
        Player.currentState = state;
    }

    /**
     * Gets the current state of the player.
     *
     * @return the current state
     */
    public State getCurrentState() {
        return Player.currentState;
    }

    /**
     * Gets the player's health.
     *
     * @return the health of the player
     */
    public static Health getHealth() {
        return health;
    }

    /**
     * Checks if the player is facing right.
     *
     * @return true if the player is facing right, false otherwise
     */
    public boolean isFacingRight() {
        return isFacingRight;
    }

    /**
     * Checks if the player is currently invincible.
     *
     * @return true if the player is invincible, false otherwise
     */
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * Toggles the player's invincibility status.
     */
    public void setIsInvincible() {
        isInvincible = !isInvincible;
    }

    /**
     * Checks if the player is currently attacking.
     *
     * @return true if attacking, false otherwise
     */
    public boolean getIsAttacking() {
        return isAttacking;
    }

    /**
     * Checks if the player is moving left.
     *
     * @return true if moving left, false otherwise
     */
    public boolean getMoveLeft() {
        return moveLeft;
    }

    /**
     * Checks if the player is moving right.
     *
     * @return true if moving right, false otherwise
     */
    public boolean getMoveRight() {
        return moveRight;
    }

    /**
     * Gets the remaining duration of the invincibility timer.
     *
     * @return the remaining invincibility duration
     */
    public float invincibilityTimer() {
        return invincibilityTimer;
    }

    /**
     * Gets the width of the player entity.
     *
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets the height of the player entity.
     *
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * Gets the x-coordinate of the player's position.
     *
     * @return the x-coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the player's position.
     *
     * @return the y-coordinate
     */
    public float getY() {
        return y;
    }

}
