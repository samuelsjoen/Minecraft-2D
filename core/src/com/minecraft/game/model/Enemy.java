package com.minecraft.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.minecraft.game.utils.BodyHelperService;
import com.minecraft.game.utils.Constants;

public class Enemy extends GameEntity {
    private Animation<TextureRegion> idleAnimation, runningAnimation, attackAnimation;
    private float stateTime;
    private State currentState;
    private boolean isFacingRight = true;
    private Player player;
    private float detectionRange = 6.0f; // range within which the enemy detects the player
    TextureRegion[] attackFrames = new TextureRegion[6];

    private enum State {
        IDLE, RUNNING, ATTACKING
    }

    public Enemy(float width, float height, World world, Player player, float x, float y) {
        super(width, height, createBody(width, height, world, x, y));
        this.player = player;
        this.speed = Constants.ENEMY_SPEED;

        // Load the texture and set up animations
        Texture enemySheet = new Texture("assets/enemyKnight.png");
        TextureRegion[][] splitFrames = TextureRegion.split(enemySheet, enemySheet.getWidth() / 10,
                enemySheet.getHeight() / 4);
        for (int i = 0; i < 6; i++) {
            attackFrames[i] = splitFrames[0][i];
        }
        idleAnimation = new Animation<>(0.1f, splitFrames[2]); // 2 row is running
        runningAnimation = new Animation<>(0.1f, splitFrames[3]); // 3 row is running
        attackAnimation = new Animation<>(0.1f, attackFrames); // attacking
        currentState = State.IDLE;
        stateTime = 0f;
    }

    private static Body createBody(float width, float height, World world, float x, float y) {
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

        // vertical range within which the enemy can attack
        float verticalAttackRange = 2.0f; // Example value, adjust as needed

        float frameDuration = attackAnimation.getFrameDuration();
        int currentFrameIndex = (int) (stateTime / frameDuration) % attackFrames.length;

        // Check if the enemy is close enough to attack but not currently attacking
        // if (distanceToPlayer < 3.0f) {
        if (distanceToPlayerX < 3.0f && distanceToPlayerY <= verticalAttackRange) {

            currentState = State.ATTACKING;
            if (currentFrameIndex == 2) {
                if (this.body.getPosition().x > player.getBody().getPosition().x) {
                    // Enemy is to the right of the player, push player left and up
                    player.getBody().applyLinearImpulse(new Vector2(-2, 2),
                            player.getBody().getWorldCenter(), true);
                } else {
                    // Enemy is to the left of the player, push player right and up
                    player.getBody().applyLinearImpulse(new Vector2(2, 2),
                            player.getBody().getWorldCenter(), true);
                }
            }
            // Stop moving when attacking
            body.setLinearVelocity(0, body.getLinearVelocity().y);
            // } else if (distanceToPlayer < detectionRange) {
        } else if (distanceToPlayerX < detectionRange) {

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

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();
        float posX = body.getPosition().x * Constants.PPM;
        float posY = body.getPosition().y * Constants.PPM;

        float spriteWidth = width * 330;
        float spriteHeight = height * 270;

        if ((isFacingRight && currentFrame.isFlipX()) || (!isFacingRight && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }

        if (isFacingRight) {

            batch.draw(currentFrame, (posX - spriteWidth / 2) + 15, (posY - spriteHeight / 4) + 4,
                    spriteWidth, spriteHeight);
        } else {

            batch.draw(currentFrame, (posX - spriteWidth / 2) - 15, (posY - spriteHeight / 4) + 4,
                    spriteWidth, spriteHeight);
        }
    }

    private TextureRegion getCurrentFrame() {
        TextureRegion region;
        switch (currentState) {
            case ATTACKING:
                region = attackAnimation.getKeyFrame(stateTime, true);
                break;
            case RUNNING:
                region = runningAnimation.getKeyFrame(stateTime, true);
                break;
            case IDLE:
            default:
                region = idleAnimation.getKeyFrame(stateTime, true);
                break;
        }
        return region;
    }
}
