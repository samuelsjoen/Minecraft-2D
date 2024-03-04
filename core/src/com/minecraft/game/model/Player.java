package com.minecraft.game.model;

import com.badlogic.gdx.physics.box2d.Body;

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
    private float stateTime;
    private boolean isFacingRight = true;

    public enum State {
        IDLE, RUNNING, ATTACKING
    }

    private State currentState;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 10f;
        this.jumpCounter = 0;

        Texture knightSheet = new Texture("assets/knight.png");
        TextureRegion[][] tmpFrames = TextureRegion.split(knightSheet, knightSheet.getWidth() / 10,
                knightSheet.getHeight() / 4);

        TextureRegion[] attackFrames = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            attackFrames[i] = tmpFrames[0][i];
        }
        // attackAnimation = new Animation<>(0.1f, tmpFrames[0]); // Adjust the frame
        // duration as needed

        idleAnimation = new Animation<>(0.1f, tmpFrames[2]); // Assuming row 3 for idle
        runningAnimation = new Animation<>(0.1f, tmpFrames[3]); // Assuming row 4 for running
        attackAnimation = new Animation<>(0.1f, attackFrames); // Adjust the frame duration as needed

        stateTime = 0f;
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

        // Teleport the player back to the middle if they fall too low
        float yfall = -10f;
        if (body.getPosition().y < yfall) {
            float middleX = Gdx.graphics.getWidth() / 2 / Constants.PPM; // Middle of the screen on X-axis
            float middleY = Gdx.graphics.getHeight() / 2 / Constants.PPM; // Middle of the screen on Y-axis
            body.setTransform(middleX, middleY, body.getAngle()); // Teleport the player to the middle
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame;

        switch (currentState) {
            case RUNNING:
                currentFrame = runningAnimation.getKeyFrame(stateTime, true);
                break;
            default:
                currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        }

        // attack animation
        if (currentState == State.ATTACKING) {
            currentFrame = attackAnimation.getKeyFrame(stateTime, true);
            // Reset to IDLE or RUNNING after the attack animation finishes
            if (attackAnimation.isAnimationFinished(stateTime)) {
                currentState = State.IDLE; // Or RUNNING, based on movement
            }
        }

        // Check if we need to flip the frame
        if ((isFacingRight && currentFrame.isFlipX()) || (!isFacingRight && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }

        // Render the sprite at the new position based on the direction
        // WORK IN PROGRESS
        if (isFacingRight == true) {
            batch.draw(currentFrame, x - 150, y - 63, width + 200, height + 200);
        } else {
            batch.draw(currentFrame, x - 179, y - 63, width + 200, height + 200);
        }
        // batch.draw(currentFrame, x - 150, y - 63, width + 200, height + 200);

    }

    public void setCurrentState(State state) {
        this.currentState = state;
    }

    public State getCurrentState() {
        return this.currentState;
    }

}
