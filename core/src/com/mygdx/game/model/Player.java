package com.mygdx.game.model;

import com.badlogic.gdx.physics.box2d.Body;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.utils.Constants;

public class Player {
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> jumpAnimation;
    private Animation<TextureRegion> idleAnimation;
    private TextureRegion currentFrame;
    private float stateTime;
    private Texture characterSheet;
    private float x, y;
    private float velocityY;
    private boolean facingRight;
    private boolean isJumping;
    private float scale;
    private boolean debugMode;
    private ShapeRenderer shapeRenderer;

    public Player() {
        characterSheet = new Texture(Gdx.files.internal("knight.png")); // Make sure the path matches the asset
                                                                        // directory
        TextureRegion[][] splitFrames = TextureRegion.split(characterSheet,
                characterSheet.getWidth() / Constants.FRAME_COLS,
                characterSheet.getHeight() / Constants.FRAME_ROWS);

        // Idle animation is in row 3
        idleAnimation = new Animation<>(Constants.IDLE_FRAME_DURATION, splitFrames[2]); // Arrays are 0-indexed, so row
                                                                                        // 3 is at index 2
        // Running animation is in row 4
        runAnimation = new Animation<>(Constants.RUN_FRAME_DURATION, splitFrames[3]); // Arrays are 0-indexed, so row 4
                                                                                      // is at index 3
        // Jumping animation is in row 4
        jumpAnimation = new Animation<>(Constants.RUN_FRAME_DURATION, splitFrames[3]); // Arrays are 0-indexed, so row 4
                                                                                       // is at index 3

        stateTime = 0f;
        x = 100; // Starting X position
        y = 100; // Starting Y position (adjust based on our ground level)
        facingRight = true;
        isJumping = false;

        currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        shapeRenderer = new ShapeRenderer();

        // Use constants for initial setup
        this.scale = Constants.CHARACTER_SCALE;
        this.debugMode = Constants.DEBUG_MODE;

    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        boolean isMoving = false;
        if (Gdx.input.isKeyPressed(Constants.MOVE_LEFT_KEY)) {
            if (!facingRight) { // Only flip frames if character is facing right
                flipAnimationFrames(runAnimation);
                flipAnimationFrames(idleAnimation);
                facingRight = false; // Update facing direction
            }
            if (x > 0) {
                x -= Constants.PLAYER_MOVE_SPEED * deltaTime;
            }
            // x -= Constants.PLAYER_MOVE_SPEED * deltaTime;
            isMoving = true;
        } else if (Gdx.input.isKeyPressed(Constants.MOVE_RIGHT_KEY)) {
            if (facingRight) { // Only flip frames if character is facing left
                flipAnimationFrames(runAnimation);
                flipAnimationFrames(idleAnimation);
                facingRight = true; // Update facing direction
            }
            float scaledCharacterWidth = currentFrame.getRegionWidth() * scale;
            if (x + scaledCharacterWidth < Constants.SCREEN_WIDTH) {
                x += Constants.PLAYER_MOVE_SPEED * deltaTime;
            }
            // x += Constants.PLAYER_MOVE_SPEED * deltaTime;
            isMoving = true;
        }

        // for debugmode
        if (Gdx.input.isKeyJustPressed(Constants.TOGGLE_DEBUG_KEY)) {
            debugMode = !debugMode;
        }

        // Simplified jump logic - checks if the player is on the ground and initiates a
        // jump
        if (Gdx.input.isKeyJustPressed(Constants.JUMP_KEY) && !isJumping) {
            isJumping = true;
            velocityY = Constants.PLAYER_JUMP_VELOCITY; // Initiates the jump with an upward velocity
        }

        // Apply gravity - always applying gravity unless on the ground
        velocityY += Constants.GRAVITY * deltaTime;
        y += velocityY * deltaTime;

        // Simulate landing from a jump
        if (y <= 100) { // Assume 100 is ground level
            y = 100;
            isJumping = false;
            velocityY = 0;
        }

        // Update animation frame
        if (isJumping) {
            // We should add a condition to flip the jump animation if needed (done)
            currentFrame = jumpAnimation.getKeyFrame(stateTime, false);
        } else if (isMoving) {
            currentFrame = runAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        }
    }

    private void flipAnimationFrames(Animation<TextureRegion> animation) {
        for (TextureRegion frame : animation.getKeyFrames()) {
            frame.flip(false, false);
        }
    }

    public void draw(SpriteBatch batch) {
        // Calculate scaled width and height
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;

        // Draw the character with scaling
        batch.draw(currentFrame, x, y, width, height);

        // Draw hitbox in debug mode
        if (debugMode) {
            batch.end(); // End SpriteBatch to begin ShapeRenderer
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.end();
            batch.begin(); // Resume SpriteBatch for other rendering
        }
    }

    // Method to set character scale
    public void setScale(float scale) {
        this.scale = scale;
    }

    public void dispose() {
        // Dispose of the character sheet texture
        if (characterSheet != null) {
            characterSheet.dispose();
        }

        // dispose of ShapeRenderer for debug drawing
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }

        // If there are other disposable resources, dispose them here
    }
}
