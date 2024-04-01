package com.minecraft.game.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Player;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.view.screens.GameScreen;

public class PinkMonster extends GameEntity {
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

    public enum State {
        IDLE, RUNNING, ATTACKING, ATTACKING2, DEAD
    }

    public PinkMonster(float width, float height, World world, Player player, float x, float y, Health health) {
        super(width, height, createBody(width, height, world, x, y, Constants.CATEGORY_ENEMY, Constants.MASK_ENEMY));
        this.player = player;
        this.speed = Constants.ENEMY_SPEED;
        this.health = new Health(1, 1);
        this.world = world;

        // Load the texture and set up animations
        // Texture enemySheet = new Texture("assets/Pink_Monster.png");
        // TextureRegion[][] splitFrames = TextureRegion.split(enemySheet,
        // enemySheet.getWidth() / 8,
        // enemySheet.getHeight() / 14);
        // for (int i = 0; i < 6; i++) {
        // attackFrames[i] = splitFrames[2][i];
        // }
        // for (int i = 0; i < 4; i++) {
        // attack2Frames[i] = splitFrames[10][i];
        // }
        // for (int i = 0; i < 8; i++) {
        // deadFrames[i] = splitFrames[4][i];
        // }
        // for (int i = 0; i < 4; i++) {
        // idleFrames[i] = splitFrames[6][i];
        // }
        // for (int i = 0; i < 6; i++) {
        // runFrames[i] = splitFrames[9][i];
        // }
        // idleAnimation = new Animation<>(0.1f, idleFrames);
        // runningAnimation = new Animation<>(0.1f, runFrames);
        // attackAnimation = new Animation<>(0.1f, attackFrames);
        // attack2Animation = new Animation<>(0.2f, attack2Frames);
        // deadAnimation = new Animation<>(0.1f, deadFrames);
        currentState = State.IDLE;

        stateTime = 0f;
    }

    private static Body createBody(float width, float height, World world, float x, float y, short categoryBits,
            short maskBits) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true; // Prevent the body from rotating

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 1, height / 0.5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;

        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    @Override
    public void update() {
        stateTime += Gdx.graphics.getDeltaTime();
        timeSinceLastAttack += Gdx.graphics.getDeltaTime();
        // float distanceToPlayer = Math.abs(player.getBody().getPosition().x -
        // this.body.getPosition().x);
        float distanceToPlayerX = Math.abs(player.getBody().getPosition().x - this.body.getPosition().x);
        float distanceToPlayerY = Math.abs(player.getBody().getPosition().y - this.body.getPosition().y);
        float distanceToPlayerYnotABS = player.getBody().getPosition().y - this.body.getPosition().y;

        // vertical range within which the enemy can attack
        float verticalAttackRange = 1.5f;

        // Get the current frame index/number
        // float frameDuration = attackAnimation.getFrameDuration();
        // float frame2Duration = attack2Animation.getFrameDuration();
        // int currentAttackFrameIndex = (int) (stateTime / frameDuration) %
        // attackFrames.length;
        // int currentAtack2FrameIndex = (int) (stateTime / frame2Duration) %
        // attack2Frames.length;

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
                    // Create a new projectile
                    Projectile projectile = new Projectile(50, 50, world, startPosition, targetPosition);
                    GameScreen.addProjectile(projectile);
                    System.out.println("reset");
                    hasThrownRock = true; // Mark that a rock has been thrown
                    timeSinceLastAttack = 0; // Reset the timer immediately after throwing a rock
                    attack2StateTime = 0f; // Reset the animation time for attack2

                }

            } else if (distanceToPlayerX < 2f && distanceToPlayerY <= verticalAttackRange
                    && player.getCurrentState() != Player.State.DEAD) {

                currentState = State.ATTACKING;
                if (attackFrame == true) {
                    // player.getHealth().damage(1);
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
            deadStateTime += Gdx.graphics.getDeltaTime(); // Update dead animation time
        }
        if (currentState == State.ATTACKING2) {
            attack2StateTime += Gdx.graphics.getDeltaTime(); // Update dead animation time
        }
        if (timeSinceLastAttack >= attackCooldown) {
            hasThrownRock = false;
        }
        if (distanceToPlayerX <= 15 && distanceToPlayerX > 10 && !hasThrownRock) {
            hasThrownRock = true;
        }

    }

    // @Override
    // public void render(SpriteBatch batch) {
    // TextureRegion currentFrame = getCurrentFrame();
    // float posX = body.getPosition().x * Constants.PPM;
    // float posY = body.getPosition().y * Constants.PPM;

    // float spriteWidth = width * 330;
    // float spriteHeight = height * 270;

    // if ((isFacingRight && currentFrame.isFlipX()) || (!isFacingRight &&
    // !currentFrame.isFlipX())) {
    // currentFrame.flip(true, false);
    // }

    // if (isFacingRight) {

    // batch.draw(currentFrame, (posX - 55), (posY - 68),
    // (spriteWidth / 4) + 20, (spriteHeight / 2) + 20);
    // } else {

    // batch.draw(currentFrame, (posX - 50), (posY - 68),
    // (spriteWidth / 4) + 20, (spriteHeight / 2) + 20);
    // }
    // }

    // private TextureRegion getCurrentFrame() {
    // TextureRegion region;
    // switch (currentState) {
    // case DEAD:
    // region = deadAnimation.getKeyFrame(deadStateTime, false);
    // if (deadAnimation.isAnimationFinished(deadStateTime)) {
    // markForRemoval = true; // This flag indicates that the enemy is ready to be
    // removed
    // }
    // break;
    // case ATTACKING:
    // region = attackAnimation.getKeyFrame(stateTime, true);
    // break;
    // case ATTACKING2:
    // region = attack2Animation.getKeyFrame(attack2StateTime, false);
    // break;
    // case RUNNING:
    // region = runningAnimation.getKeyFrame(stateTime, true);
    // break;
    // case IDLE:
    // default:
    // region = idleAnimation.getKeyFrame(stateTime, true);
    // break;
    // }
    // return region;
    // }

    public boolean isAlive() {
        return health.isAlive();
    }

    public Health getHealth() {
        return health;
    }

    public void getHit() {
        health.damage(1); // call damage method and reduces health
    }

    public boolean isMarkedForRemoval() {
        return markForRemoval;
    }

    public void setMarkedForRemoval() {
        markForRemoval = !markForRemoval;
    }

    public float getDeadStateTime() {
        return deadStateTime;
    }

    public float getAttack2StateTime() {
        return attack2StateTime;
    }

    public float getStateTime() {
        return stateTime;
    }

    public State getCurrentState() {
        return currentState;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public void setAttackFrameTrue() {
        attackFrame = true;
    }

    public void setAttackFrameFalse() {
        attackFrame = false;
    }

}
