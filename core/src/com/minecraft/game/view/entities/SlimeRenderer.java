package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.minecraft.game.model.entities.IViewableEntityModel;
import com.minecraft.game.utils.Constants;

/**
 * Responsible for rendering the slime entity using various animations.
 * This class manages the animations for different states of the slime
 * such as idle, running,
 * attacking and dead. It chooses the appropriate animation based
 * on the slime's current state
 * and renders it at the slime's position on the screen.
 */
public class SlimeRenderer {
    private Animation<TextureRegion> idleAnimation, attackAnimation, deadAnimation;
    private Texture sheet;
    private SpriteBatch batch;
    TextureRegion[] attackFrames = new TextureRegion[8];
    TextureRegion[] deadFrames = new TextureRegion[5];

    /**
     * Constructs a SlimeRenderer with a given SpriteBatch.
     * Initializes all animations used for the slime.
     * 
     * @param batch The SpriteBatch used to draw the slime's animations.
     */
    public SlimeRenderer(SpriteBatch batch) {
        this.batch = batch;
        sheet = new Texture("entities/slime-Sheet.png");
        TextureRegion[][] splitFrames = TextureRegion.split(sheet, sheet.getWidth() / 8, sheet.getHeight() / 3);
        for (int i = 0; i < 8; i++) {
            attackFrames[i] = splitFrames[1][i];
        }
        for (int i = 0; i < 5; i++) {
            deadFrames[i] = splitFrames[2][i];
        }
        idleAnimation = new Animation<>(0.1f, splitFrames[0]);
        attackAnimation = new Animation<>(0.1f, attackFrames);
        deadAnimation = new Animation<>(0.1f, deadFrames);

    }

    /**
     * Renders the slime based on its current state and position.
     * Chooses and applies the correct animation and adjusts the sprite based on the
     * slime's direction.
     *
     * @param slime The slime entity to be rendered, which implements the
     *              IViewableEntityModel interface.
     */
    public void render(IViewableEntityModel slime) {
        Animation<TextureRegion> currentAnimation = getCurrentAnimation(slime);
        TextureRegion currentFrame;
        if (currentAnimation == deadAnimation) {
            currentFrame = currentAnimation.getKeyFrame(slime.getDeadStateTime(), false);

        } else
            currentFrame = currentAnimation.getKeyFrame(slime.getStateTime(), true);

        float posX = slime.getPosition().x * Constants.PPM;
        float posY = slime.getPosition().y * Constants.PPM;
        float spriteWidth = slime.getWidth() / 2 / Constants.PPM * 330;
        float spriteHeight = slime.getHeight() / 2 / Constants.PPM * 270;

        // Flip texture region based on slime direction
        if ((slime.isFacingRight() && currentFrame.isFlipX())
                || (!slime.isFacingRight() && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }

        batch.draw(currentFrame, (posX - 40), (posY - 35),
                spriteWidth / 4, spriteHeight / 4);

    }

    /**
     * Determines the current animation for the slime based on its state.
     *
     * @param slime The slime entity.
     * @return The current animation based on the slime's state.
     */
    private Animation<TextureRegion> getCurrentAnimation(IViewableEntityModel slime) {
        switch (slime.getCurrentState()) {
            case IDLE:
                return idleAnimation;
            case RUNNING:
                return idleAnimation;
            case ATTACKING:
                if (getCurrentAtkIndex(slime) == 4) {
                    slime.setAttackFrame(true);
                } else
                    slime.setAttackFrame(false);
                return attackAnimation;
            case DEAD:
                if (deadAnimation.isAnimationFinished(slime.getDeadStateTime())) {
                    slime.setMarkedForRemoval(true);
                }
                return deadAnimation;
            default:
                return idleAnimation;
        }
    }

    private int getCurrentAtkIndex(IViewableEntityModel slime) {
        float frameDuration = attackAnimation.getFrameDuration();
        int currentFrameIndex = (int) (slime.getStateTime() / frameDuration) % 8;
        return currentFrameIndex;
    }

    /**
     * Disposes of the texture resources used by this renderer.
     */
    public void dispose() {
        sheet.dispose();
    }
}
