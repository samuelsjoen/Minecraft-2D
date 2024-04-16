package com.minecraft.game.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.model.crafting.Inventory;
import com.minecraft.game.model.entities.GameEntity;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Slime;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.SpriteManager;

public class Player extends GameEntity {

    private boolean isFacingRight = true;
    private static Health health;
    private static Inventory inventory;

    private boolean isInvincible;
    private boolean isAttacking = false;
    private float invincibilityTimer;
    private static final float INVINCIBILITY_DURATION = 1.0f; // 1 seconds

    private float velX = 0;

    public enum State {
        IDLE, RUNNING, ATTACKING, DEAD
    }

    public static State currentState;

    private Boolean moveLeft;
    private Boolean moveRight;

    public Player(float width, float height, Body body, Inventory inventory) {
        super(width, height, body);
        this.speed = 10f;
        Player.inventory = inventory;
        Player.health = new Health(5, 5, inventory);
        currentState = State.IDLE;

        this.moveLeft = false;
        this.moveRight = false; 
    }

    // Gets information from PlayerController through MinecraftModel
    public void handleInput(Boolean moveLeft, Boolean moveRight) {
        this.moveLeft = moveLeft;
        this.moveRight = moveRight; 
    }

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
            // Optional: Add blinking logic/Sound/Cool effect here
        }
        if (isAttacking) {
            currentState = State.ATTACKING;
            attack();
        }

        if (health.getHealth() <= 0 && currentState != State.DEAD) {
            currentState = State.DEAD;
        }

        // playerController.handleContinousInput()

        // if (currentState == State.DEAD) {
        // deadStateTime += Gdx.graphics.getDeltaTime(); // Update dead animation time
        // }

        body.setLinearVelocity(velX, body.getLinearVelocity().y);


    }

    public void playerOutOfbounds(float screenWidth, float screenHeight) {
        float yfall = -10f;
        if (body.getPosition().y < yfall) {
            currentState = State.DEAD;
            float middleX = screenWidth / 2 / Constants.PPM;
            float middleY = screenHeight / 0.15f / Constants.PPM;
            body.setTransform(middleX, middleY, body.getAngle());
        }
    }

    public void getHit() {
        if (!isInvincible) {
            health.damage(1); // call damage method and reduces health
            isInvincible = true;
            invincibilityTimer = INVINCIBILITY_DURATION;
        }
    }

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

    public void setCurrentState(State state) {
        Player.currentState = state;
    }

    public State getCurrentState() {
        return Player.currentState;
    }

    public static Health getHealth() {
        return health;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setIsInvincible() {
        isInvincible = !isInvincible;
    }

    public void toggleIsAttacking() {
        isAttacking = !isAttacking;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public float invincibilityTimer() {
        return invincibilityTimer;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void movePlayer(int direction) {
        // direction = 1 for right, -1 for left
        velX = direction * Constants.PLAYER_MOVE_SPEED;
        body.setLinearVelocity(velX, body.getLinearVelocity().y);
    }

}
