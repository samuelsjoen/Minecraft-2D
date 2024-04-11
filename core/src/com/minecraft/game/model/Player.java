package com.minecraft.game.model;

import com.badlogic.gdx.physics.box2d.Body;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.minecraft.game.model.crafting.Inventory;
import com.minecraft.game.model.entities.GameEntity;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Slime;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.SpriteManager;

public class Player extends GameEntity {

    // count amount of jumps (only want double, not triple/friple... etc.)
    @SuppressWarnings("unused")
    private int jumpCounter;

    private boolean isFacingRight = true;
    private static Health health;
    private static Inventory inventory;

    private boolean isInvincible;
    private boolean isAttacking = false;
    private float invincibilityTimer;
    private static final float INVINCIBILITY_DURATION = 1.0f; // 1 seconds
    public static float deadStateTime = 0f; // Timer for the dead animation

    public enum State {
        IDLE, RUNNING, ATTACKING, DEAD
    }

    public static State currentState;

    public Player(float width, float height, Body body, Inventory inventory) {
        super(width, height, body);
        this.speed = 10f;
        this.jumpCounter = 0;
        Player.inventory = inventory;
        Player.health = new Health(5, 5, inventory);
        currentState = State.IDLE;
    }

    @Override
    public void update() {
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

        // TODO: fix that player dies here instead
        // Teleport the player back to the middle if he falls too low
        float yfall = -10f;
        if (body.getPosition().y < yfall) {
            float middleX = Gdx.graphics.getWidth() / 2 / Constants.PPM; // Middle of the screen on X-axis
            float middleY = Gdx.graphics.getHeight() / 0.5f / Constants.PPM; // A lil more above the middle of the
                                                                             // screen on Y-axis
            body.setTransform(middleX, middleY, body.getAngle()); // Teleport the player
        }

        if (isInvincible) {
            invincibilityTimer -= Gdx.graphics.getDeltaTime();
            if (invincibilityTimer <= 0) {
                isInvincible = false;
                // Ensure the player is visible after invincibility ends
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

        if (currentState == State.DEAD) {
            deadStateTime += Gdx.graphics.getDeltaTime(); // Update dead animation time
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

    private int calculateDamage() {
        if (inventory.getSelectedItem() == null) {
            return 1;
        }
        switch(inventory.getSelectedItem()) {
            case WOODEN_SWORD: return 2;
            case IRON_SWORD: return 3;
            case DIAMOND_SWORD: return 4;
            default: return 1;
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

    // @Override
    // public void render(SpriteBatch batch) {
    // }
}
