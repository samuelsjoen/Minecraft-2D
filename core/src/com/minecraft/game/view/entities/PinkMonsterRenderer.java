package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.minecraft.game.model.entities.IViewableEntityModel;
import com.minecraft.game.utils.Constants;

/**
 * Responsible for rendering the Pink Monster entity using various animations.
 * This class manages the animations for different states of the Pink Monster
 * such as idle, running,
 * attacking, attacking2, and dead. It chooses the appropriate animation based
 * on the Pink Monster's current state
 * and renders it at the Pink Monster's position on the screen.
 */
public class PinkMonsterRenderer {
    private Animation<TextureRegion> idleAnimation, runningAnimation, attackAnimation, attack2Animation, deadAnimation;
    private Texture sheet;
    private SpriteBatch batch;
    TextureRegion[] attackFrames = new TextureRegion[6];
    TextureRegion[] attack2Frames = new TextureRegion[4];
    TextureRegion[] deadFrames = new TextureRegion[8];
    TextureRegion[] idleFrames = new TextureRegion[4];
    TextureRegion[] runFrames = new TextureRegion[6];

    /**
     * Constructs a PinkMonsterRenderer with a provided SpriteBatch.
     * Initializes all animations used for the Pink Monster.
     *
     * @param batch The SpriteBatch used to draw the Pink Monster's animations.
     */
    public PinkMonsterRenderer(SpriteBatch batch) {
        sheet = new Texture("entities/Pink_Monster.png");
        this.batch = batch;
        TextureRegion[][] splitFrames = TextureRegion.split(sheet, sheet.getWidth() / 8, sheet.getHeight() / 14);
        for (int i = 0; i < 6; i++) {
            attackFrames[i] = splitFrames[2][i];
        }
        for (int i = 0; i < 4; i++) {
            attack2Frames[i] = splitFrames[10][i];
        }
        for (int i = 0; i < 8; i++) {
            deadFrames[i] = splitFrames[4][i];
        }
        for (int i = 0; i < 4; i++) {
            idleFrames[i] = splitFrames[6][i];
        }
        for (int i = 0; i < 6; i++) {
            runFrames[i] = splitFrames[9][i];
        }
        idleAnimation = new Animation<>(0.1f, idleFrames);
        runningAnimation = new Animation<>(0.1f, runFrames);
        attackAnimation = new Animation<>(0.1f, attackFrames);
        attack2Animation = new Animation<>(0.2f, attack2Frames);
        deadAnimation = new Animation<>(0.1f, deadFrames);

    }

    /**
     * Renders the Pink Monster based on its current state and position.
     * Chooses and applies the correct animation and adjusts the sprite based on the
     * Pink Monster's direction.
     *
     * @param pinkMonster The Pink Monster entity to be rendered, which implements
     *                    the IViewableEntityModel interface.
     */
    public void render(IViewableEntityModel pinkMonster) {
        Animation<TextureRegion> currentAnimation = getCurrentAnimation(pinkMonster);

        TextureRegion currentFrame;
        if (currentAnimation == deadAnimation) {
            currentFrame = currentAnimation.getKeyFrame(pinkMonster.getDeadStateTime(), false);

        } else if (currentAnimation == attack2Animation) {
            currentFrame = currentAnimation.getKeyFrame(pinkMonster.getAttack2StateTime(), false);

        } else
            currentFrame = currentAnimation.getKeyFrame(pinkMonster.getStateTime(), true);

        float posX = pinkMonster.getPosition().x * Constants.PPM;
        float posY = pinkMonster.getPosition().y * Constants.PPM;
        float spriteWidth = pinkMonster.getWidth() / 2 / Constants.PPM * 330;
        float spriteHeight = pinkMonster.getHeight() / 4 / Constants.PPM * 270;

        // Flip texture region based on knight direction
        if ((pinkMonster.isFacingRight() && currentFrame.isFlipX())
                || (!pinkMonster.isFacingRight() && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }

        if (pinkMonster.isFacingRight()) {

            batch.draw(currentFrame, (posX - 55), (posY - 68),
                    (spriteWidth / 4) + 20, (spriteHeight / 2) + 20);
        } else {

            batch.draw(currentFrame, (posX - 50), (posY - 68),
                    (spriteWidth / 4) + 20, (spriteHeight / 2) + 20);
        }

    }

    /**
     * Determines the current animation for the Pink Monster based on its state.
     *
     * @param pinkMonster The Pink Monster entity.
     * @return The current animation based on the Pink Monster's state.
     */
    private Animation<TextureRegion> getCurrentAnimation(IViewableEntityModel pinkMonster) {
        switch (pinkMonster.getCurrentState()) {
            case IDLE:
                return idleAnimation;
            case RUNNING:
                return runningAnimation;
            case ATTACKING:
                if (getCurrentAtkIndex(pinkMonster) == 3 || getCurrentAtkIndex(pinkMonster) == 5) {
                    pinkMonster.setAttackFrame(true);
                } else
                    pinkMonster.setAttackFrame(false);
                return attackAnimation;
            case ATTACKING2:
                if (getCurrentAtk2Index(pinkMonster) == 3) {
                    pinkMonster.setAttackFrame(true);
                } else
                    pinkMonster.setAttackFrame(false);
                return attack2Animation;
            case DEAD:
                if (deadAnimation.isAnimationFinished(pinkMonster.getDeadStateTime())) {
                    pinkMonster.setMarkedForRemoval(true);
                }
                return deadAnimation;
            default:
                return idleAnimation;
        }
    }

    private int getCurrentAtkIndex(IViewableEntityModel pinkMonster) {
        float frameDuration = attackAnimation.getFrameDuration();
        int currentFrameIndex = (int) (pinkMonster.getStateTime() / frameDuration) % 6;
        return currentFrameIndex;
    }

    private int getCurrentAtk2Index(IViewableEntityModel pinkMonster) {
        float frameDuration = attack2Animation.getFrameDuration();
        int currentFrameIndex = (int) (pinkMonster.getAttack2StateTime() / frameDuration) % 4;
        return currentFrameIndex;
    }

    /**
     * Disposes of the texture resources used by this renderer.
     */
    public void dispose() {
        sheet.dispose();
    }
}
