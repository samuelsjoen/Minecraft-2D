package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.minecraft.game.model.entities.IViewableEntityModel;
import com.minecraft.game.utils.Constants;

/**
 * Responsible for rendering the Knight entity using various animations.
 * This class manages the animations for different states of the Knight such as
 * idle, running,
 * attacking, and dead. It chooses the appropriate animation based on the
 * Knight's current state
 * and renders it at the Knight's position on the screen.
 */
public class KnightRenderer {
    private SpriteBatch batch;
    private Animation<TextureRegion> idleAnimation, runningAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> deadAnimation;
    private Texture sheet;
    TextureRegion[] attackFrames = new TextureRegion[6];

    /**
     * Constructs a KnightRenderer with a provided SpriteBatch.
     * Initializes all animations used for the Knight.
     *
     * @param batch The SpriteBatch used to draw the Knight's animations.
     */
    public KnightRenderer(SpriteBatch batch) {
        this.batch = batch;
        sheet = new Texture("entities/enemyKnight.png");
        TextureRegion[][] splitFrames = TextureRegion.split(sheet, sheet.getWidth() / 10, sheet.getHeight() / 4);
        for (int i = 0; i < 6; i++) {
            attackFrames[i] = splitFrames[0][i];
        }
        idleAnimation = new Animation<>(0.1f, splitFrames[2]);
        runningAnimation = new Animation<>(0.1f, splitFrames[3]);
        attackAnimation = new Animation<>(0.1f, attackFrames);
        deadAnimation = new Animation<>(0.1f, splitFrames[1]);
    }

    /**
     * Renders the Knight based on its current state and position.
     * Chooses and applies the correct animation and adjusts the sprite based on the
     * Knight's direction.
     *
     * @param knight The Knight entity to be rendered, which implements the
     *               IViewableEntityModel interface.
     */
    public void render(IViewableEntityModel knight) {
        Animation<TextureRegion> currentAnimation = getCurrentAnimation(knight);

        TextureRegion currentFrame;
        if (currentAnimation == deadAnimation) {
            currentFrame = currentAnimation.getKeyFrame(knight.getDeadStateTime(), false);

        } else
            currentFrame = currentAnimation.getKeyFrame(knight.getStateTime(), true);

        float posX = knight.getPosition().x * Constants.PPM;
        float posY = knight.getPosition().y * Constants.PPM;
        float spriteWidth = knight.getWidth() / 2 / Constants.PPM * 330;
        float spriteHeight = knight.getHeight() / 4 / Constants.PPM * 270;

        // Flip texture region based on knight direction
        if ((knight.isFacingRight() && currentFrame.isFlipX())
                || (!knight.isFacingRight() && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }

        if (knight.isFacingRight()) {

            batch.draw(currentFrame, (posX - spriteWidth / 2) + 15, (posY - spriteHeight / 4) + 4,
                    spriteWidth, spriteHeight);
        } else {

            batch.draw(currentFrame, (posX - spriteWidth / 2) - 15, (posY - spriteHeight / 4) + 4,
                    spriteWidth, spriteHeight);
        }

    }

    /**
     * Determines the current animation for the Knight based on its state.
     *
     * @param knight The Knight entity.
     * @return The current animation based on the Knight's state.
     */
    private Animation<TextureRegion> getCurrentAnimation(IViewableEntityModel knight) {
        switch (knight.getCurrentState()) {
            case IDLE:
                return idleAnimation;
            case RUNNING:
                return runningAnimation;
            case ATTACKING:
                if (getCurrentAtkIndex(knight) == 2) {
                    knight.setAttackFrame(true);
                } else
                    knight.setAttackFrame(false);
                return attackAnimation;
            case DEAD:
                if (deadAnimation.isAnimationFinished(knight.getDeadStateTime())) {
                    knight.setMarkedForRemoval(true);
                }
                return deadAnimation;
            default:
                return idleAnimation;
        }
    }

    private int getCurrentAtkIndex(IViewableEntityModel knight) {
        float frameDuration = attackAnimation.getFrameDuration();
        int currentFrameIndex = (int) (knight.getStateTime() / frameDuration) % 6;
        return currentFrameIndex;
    }

    /**
     * Disposes of the texture resources used by this renderer.
     */
    public void dispose() {
        sheet.dispose();
    }
}
