package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.minecraft.game.model.entities.Slime;
import com.minecraft.game.utils.Constants;

public class SlimeRenderer implements EntityRenderer<Slime> {
    private Animation<TextureRegion> idleAnimation, attackAnimation, deadAnimation;
    private Texture sheet;
    TextureRegion[] attackFrames = new TextureRegion[8];
    TextureRegion[] deadFrames = new TextureRegion[5];

    public SlimeRenderer() {
        sheet = new Texture("assets/slime-Sheet.png");
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

    @Override
    public void render(Slime slime, SpriteBatch batch) {
        Animation<TextureRegion> currentAnimation = getCurrentAnimation(slime);
        TextureRegion currentFrame;
        if (currentAnimation == deadAnimation) {
            currentFrame = currentAnimation.getKeyFrame(slime.getDeadStateTime(), false);

        } else
            currentFrame = currentAnimation.getKeyFrame(slime.getStateTime(), true);

        float posX = slime.getBody().getPosition().x * Constants.PPM;
        float posY = slime.getBody().getPosition().y * Constants.PPM;
        float spriteWidth = slime.width * 330;
        float spriteHeight = slime.height * 270;

        // Flip texture region based on knight direction
        if ((slime.isFacingRight() && currentFrame.isFlipX())
                || (!slime.isFacingRight() && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }

        batch.draw(currentFrame, (posX - 40), (posY - 35),
                spriteWidth / 4, spriteHeight / 4);

    }

    private Animation<TextureRegion> getCurrentAnimation(Slime slime) {
        switch (slime.getCurrentState()) {
            case IDLE:
                return idleAnimation;
            case RUNNING:
                return idleAnimation;
            case ATTACKING:
                if (getCurrentAtkIndex(slime) == 4) {
                    slime.setAttackFrameTrue();
                } else
                    slime.setAttackFrameFalse();
                return attackAnimation;
            case DEAD:
                if (deadAnimation.isAnimationFinished(slime.getDeadStateTime())) {
                    slime.setMarkedForRemoval();
                }
                return deadAnimation;
            default:
                return idleAnimation;
        }
    }

    private int getCurrentAtkIndex(Slime slime) {
        float frameDuration = attackAnimation.getFrameDuration();
        int currentFrameIndex = (int) (slime.getStateTime() / frameDuration) % 8;
        return currentFrameIndex;
    }

    public void dispose() {
        sheet.dispose();
    }
}
