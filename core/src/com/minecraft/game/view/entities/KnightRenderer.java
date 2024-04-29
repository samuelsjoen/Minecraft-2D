package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.utils.Constants;

public class KnightRenderer implements IEntityRenderer<Knight> {
    private Animation<TextureRegion> idleAnimation, runningAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> deadAnimation;
    private Texture sheet;
    TextureRegion[] attackFrames = new TextureRegion[6];

    public KnightRenderer() {
        sheet = new Texture("enemyKnight.png");
        TextureRegion[][] splitFrames = TextureRegion.split(sheet, sheet.getWidth() / 10, sheet.getHeight() / 4);
        for (int i = 0; i < 6; i++) {
            attackFrames[i] = splitFrames[0][i];
        }
        idleAnimation = new Animation<>(0.1f, splitFrames[2]);
        runningAnimation = new Animation<>(0.1f, splitFrames[3]);
        attackAnimation = new Animation<>(0.1f, attackFrames);
        deadAnimation = new Animation<>(0.1f, splitFrames[1]);
    }

    @Override
    public void render(Knight knight, SpriteBatch batch) {
        Animation<TextureRegion> currentAnimation = getCurrentAnimation(knight);

        TextureRegion currentFrame;
        if (currentAnimation == deadAnimation) {
            currentFrame = currentAnimation.getKeyFrame(knight.getDeadStateTime(), false);

        } else
            currentFrame = currentAnimation.getKeyFrame(knight.getStateTime(), true);

        float posX = knight.getBody().getPosition().x * Constants.PPM;
        float posY = knight.getBody().getPosition().y * Constants.PPM;
        float spriteWidth = knight.width / 2 / Constants.PPM * 330;
        float spriteHeight = knight.height / 4 / Constants.PPM * 270;

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

    private Animation<TextureRegion> getCurrentAnimation(Knight knight) {
        switch (knight.getCurrentState()) {
            case IDLE:
                return idleAnimation;
            case RUNNING:
                return runningAnimation;
            case ATTACKING:
                if (getCurrentAtkIndex(knight) == 2) {
                    knight.setAttackFrameTrue();
                } else
                    knight.setAttackFrameFalse();
                return attackAnimation;
            case DEAD:
                if (deadAnimation.isAnimationFinished(knight.getDeadStateTime())) {
                    knight.setMarkedForRemoval();
                }
                return deadAnimation;
            default:
                return idleAnimation;
        }
    }

    private int getCurrentAtkIndex(Knight knight) {
        float frameDuration = attackAnimation.getFrameDuration();
        int currentFrameIndex = (int) (knight.getStateTime() / frameDuration) % 6;
        return currentFrameIndex;
    }

    public void dispose() {
        sheet.dispose();
    }
}
