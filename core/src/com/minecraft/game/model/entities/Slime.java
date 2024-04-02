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
import com.minecraft.game.utils.BodyHelperService;
import com.minecraft.game.utils.Constants;

public class Slime extends GameEntity {
    private float stateTime;
    public State currentState;
    public boolean isFacingRight = true;
    private Player player;
    private float detectionRange = 10.0f; // range within which the enemy detects the player
    private float jumpForce = 55;
    private float jumpThreshold = 1.5f; // Vertical distance threshold for jumping
    public Health health;
    private boolean markForRemoval = false;
    private float deadStateTime = 0f; // Timer for the dead animation
    private boolean attackFrame = false;

    public enum State {
        IDLE, RUNNING, ATTACKING, DEAD
    }

    public Slime(float width, float height, World world, Player player, float x, float y, Health health) {
        super(width, height, BodyHelperService.createBody(x, y, width, height, null, false, world, Constants.CATEGORY_ENEMY, Constants.MASK_ENEMY, "slime", false));
        this.player = player;
        this.speed = Constants.ENEMY_SPEED;
        this.health = new Health(1, 1);

        // // Load the texture and set up animations
        // Texture enemySheet = new Texture("assets/slime-Sheet.png");
        // TextureRegion[][] splitFrames = TextureRegion.split(enemySheet,
        // enemySheet.getWidth() / 8,
        // enemySheet.getHeight() / 3);
        // for (int i = 0; i < 8; i++) {
        // attackFrames[i] = splitFrames[1][i];
        // }
        // for (int i = 0; i < 5; i++) {
        // deadFrames[i] = splitFrames[2][i];
        // }
        // idleAnimation = new Animation<>(0.1f, splitFrames[0]); // 1 row is running
        // runningAnimation = new Animation<>(0.1f, splitFrames[0]); // 1 row is running
        // attackAnimation = new Animation<>(0.1f, attackFrames); // attacking
        currentState = State.IDLE;
        // deadAnimation = new Animation<>(0.1f, deadFrames); // row 3 = ded

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
        shape.setAsBox(width / 1, height / 1);
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

        // float distanceToPlayer = Math.abs(player.getBody().getPosition().x -
        // this.body.getPosition().x);
        float distanceToPlayerX = Math.abs(player.getBody().getPosition().x - this.body.getPosition().x);
        float distanceToPlayerY = Math.abs(player.getBody().getPosition().y - this.body.getPosition().y);
        float distanceToPlayerYnotABS = player.getBody().getPosition().y - this.body.getPosition().y;

        // vertical range within which the enemy can attack
        float verticalAttackRange = 1.5f;

        // float frameDuration = attackAnimation.getFrameDuration();
        // int currentFrameIndex = (int) (stateTime / frameDuration) %
        // attackFrames.length;
        if (currentState != State.DEAD) {
            // jump logic for enemy
            if (distanceToPlayerX < detectionRange && distanceToPlayerYnotABS > jumpThreshold
                    && Math.abs(body.getLinearVelocity().y) == 0 && player.getBody().getLinearVelocity().y == 0
                    && player.getCurrentState() != Player.State.DEAD) {
                // The last condition checks if the enemy is not already jumping or falling
                body.applyLinearImpulse(new Vector2(0, jumpForce), body.getWorldCenter(), true);
            }

            // Check if the enemy is close enough to attack but not currently attacking
            // if (distanceToPlayer < 3.0f) {
            if (distanceToPlayerX < 1.5f && distanceToPlayerY <= verticalAttackRange
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
            } else if (distanceToPlayerX < detectionRange && player.getCurrentState() != Player.State.DEAD) {

                if (player.getBody().getPosition().x > this.body.getPosition().x) {
                    body.setLinearVelocity(speed, body.getLinearVelocity().y); // Move right towards the player
                    isFacingRight = false;
                } else {
                    body.setLinearVelocity(-speed, body.getLinearVelocity().y); // Move left towards the player
                    isFacingRight = true;
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
            deadStateTime += Gdx.graphics.getDeltaTime(); // Update dead animation time
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

    // batch.draw(currentFrame, (posX - 40), (posY - 35),
    // spriteWidth / 4, spriteHeight / 4);
    // } else {

    // batch.draw(currentFrame, (posX - 40), (posY - 35),
    // spriteWidth / 4, spriteHeight / 4);
    // }
    // }

    // private TextureRegion getCurrentFrame() {
    // TextureRegion region;
    // switch (currentState) {
    // case DEAD:
    // region = deadAnimation.getKeyFrame(deadStateTime, false);
    // if (deadAnimation.isAnimationFinished(deadStateTime)) {
    // markForRemoval = true; // This flag indicates that the enemy is ready to be
    // // removed
    // }
    // break;

    // case ATTACKING:
    // region = attackAnimation.getKeyFrame(stateTime, true);
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
