package com.minecraft.game.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.minecraft.game.utils.Constants;

public class Player extends GameEntity {

    // count amount of jumps (only want double, not triple/friple... etc.)
    private int jumpCounter;

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runningAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> deadAnimation;
    TextureRegion[] attackFrames = new TextureRegion[6];
    private float stateTime;
    private boolean isFacingRight = true;
    private static Health health;

    // latest changes fji jiefi f
    private boolean isInvincible;
    private float invincibilityTimer;
    private static final float INVINCIBILITY_DURATION = 1.0f; // 1 seconds
    public static float deadStateTime = 0f; // Timer for the dead animation

    public enum State {
        IDLE, RUNNING, ATTACKING, DEAD
    }

    public static State currentState;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 10f;
        this.jumpCounter = 0;
        this.health = new Health(5, 5);

        Texture knightSheet = new Texture("assets/knight.png");
        TextureRegion[][] tmpFrames = TextureRegion.split(knightSheet, knightSheet.getWidth() / 10,
                knightSheet.getHeight() / 4);

        for (int i = 0; i < 6; i++) {
            attackFrames[i] = tmpFrames[0][i];
        }

        idleAnimation = new Animation<>(0.1f, tmpFrames[2]); // row 3 for idle
        runningAnimation = new Animation<>(0.1f, tmpFrames[3]); // row 4 for running
        attackAnimation = new Animation<>(0.1f, attackFrames); // atk
        deadAnimation = new Animation<>(0.1f, tmpFrames[1]); // row 2 = ded

        stateTime = 0.1f;
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

        /* 
        if (inWater) {
            body.setGravityScale(1f);
        } else {
            body.setGravityScale(1f);
        }
        */

        // Teleport the player back to the middle if they fall too low
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

        if (health.getHealth() <= 0 && currentState != State.DEAD) {
            currentState = State.DEAD;
        }

        if (currentState == State.DEAD) {
            deadStateTime += Gdx.graphics.getDeltaTime(); // Update dead animation time
        }

    }

    public void setInWater(boolean inWater) {
        this.inWater = inWater;
    }

    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame;

        switch (currentState) {
            case DEAD:
                currentFrame = deadAnimation.getKeyFrame(deadStateTime, false);
                break;
            case RUNNING:
                currentFrame = runningAnimation.getKeyFrame(stateTime, true);
                break;
            case ATTACKING:
                currentFrame = attackAnimation.getKeyFrame(stateTime, true);
                // Reset to IDLE or RUNNING after the attack animation finishes
                if (attackAnimation.isAnimationFinished(stateTime)) {
                    currentState = State.IDLE; // Or RUNNING, based on movement
                }
                break;
            default:
                currentFrame = idleAnimation.getKeyFrame(stateTime, true);

        }

        // Check if we need to flip the frame
        if ((isFacingRight && currentFrame.isFlipX()) || (!isFacingRight && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }

        // Render the sprite at the new position based on the direction
        if (!isInvincible || (int) (invincibilityTimer * 10) % 2 == 0) {
            if (isFacingRight == true) {
                batch.draw(currentFrame, x - 150, y - 63, width + 200, height + 200);
            } else {
                batch.draw(currentFrame, x - 179, y - 63, width + 200, height + 200);
            }
            // batch.draw(currentFrame, x - 150, y - 63, width + 200, height + 200);
        }
    }

    public void setCurrentState(State state) {
        this.currentState = state;
    }

    public State getCurrentState() {
        return this.currentState;
    }

    public static Health getHealth() {
        return health;
    }

    public void getHit() {
        if (!isInvincible) {
            health.damage(1); // call damage method and reduces health
            isInvincible = true;
            invincibilityTimer = INVINCIBILITY_DURATION;
            // Optional: Initial actions for becoming invincible, such as playing a
            // sound/effects/etc...
        }
    }

    public void attack() {
        float attackRange = 5.0f;
        float verticalAttackRange = 3.0f;
        float frameDuration = attackAnimation.getFrameDuration();
        int currentFrameIndex = (int) (stateTime / frameDuration) % attackFrames.length;

        for (Knight enemy : EnemyManager.getEnemies()) {
            float distanceToEnemyX = enemy.getBody().getPosition().x - this.body.getPosition().x;
            float distanceToEnemyY = Math.abs(enemy.getBody().getPosition().y - this.body.getPosition().y);

            boolean isEnemyInFront = isFacingRight ? distanceToEnemyX > 0 : distanceToEnemyX < 0;

            if (Math.abs(distanceToEnemyX) < attackRange && distanceToEnemyY <= verticalAttackRange
                    && this.currentState == State.ATTACKING && currentFrameIndex == 2 && isEnemyInFront) {
                enemy.getHit(); // Applies damage to the targeted enemy
            }
        }
        for (Slime slime : EnemyManager.getSlimes()) {
            float distanceToSlimeX = slime.getBody().getPosition().x - this.body.getPosition().x;
            float distanceToSlimeY = Math.abs(slime.getBody().getPosition().y - this.body.getPosition().y);

            boolean isEnemyInFront = isFacingRight ? distanceToSlimeX > 0 : distanceToSlimeX < 0;

            if (Math.abs(distanceToSlimeX) < attackRange && distanceToSlimeY <= verticalAttackRange
                    && this.currentState == State.ATTACKING && currentFrameIndex == 2 && isEnemyInFront) {
                slime.getHit(); // Applies damage to the targeted enemy
            }
        }
        for (PinkMonster pinkMonster : EnemyManager.getPinkMonsters()) {
            float distanceToPinkMonsterX = pinkMonster.getBody().getPosition().x - this.body.getPosition().x;
            float distanceToPinkMonsterY = Math.abs(pinkMonster.getBody().getPosition().y - this.body.getPosition().y);

            boolean isEnemyInFront = isFacingRight ? distanceToPinkMonsterX > 0 : distanceToPinkMonsterX < 0;

            if (Math.abs(distanceToPinkMonsterX) < attackRange && distanceToPinkMonsterY <= verticalAttackRange
                    && this.currentState == State.ATTACKING && currentFrameIndex == 2 && isEnemyInFront) {
                pinkMonster.getHit(); // Applies damage to the targeted enemy
            }
        }
    }

}
