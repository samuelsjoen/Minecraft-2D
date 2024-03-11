package com.minecraft.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Disposable;
import com.minecraft.game.model.Player;
import com.minecraft.game.model.Player.State;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.model.Item;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class SpriteManager implements Disposable {
    private Player player;
    private static float stateTime = 0f;
    private HashMap<String, Animation<TextureRegion>> animations = new HashMap<>();
    private ArrayList<String> currentSprites = new ArrayList<>();
    private HashMap<Item, String[]> itemToSpriteMapping = new HashMap<>();
    private Inventory inventory;
    private static Animation<TextureRegion> currentAnimation;
    private boolean deathAnimationStarted = false;

    public SpriteManager(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;

        loadSprites();
        initializeItemToSpriteMapping();
    }

    private void loadSprites() {
        // Loading animations from sprite sheets with multiple frames and rows
        loadAnimation("Character_Idle.png", 4, 4, 0.1f, "Character_Idle");
        loadAnimation("Character_Move.png", 6, 4, 0.1f, "Character_Move");
        loadAnimation("Character_Attack.png", 6, 4, 0.1f, "Character_Attack");
        loadAnimation("Character_Death.png", 11, 4, 0.1f, "Character_Death");
        Animation<TextureRegion> deathAnim = animations.get("Character_Death");
        if (deathAnim != null) {
            deathAnim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        // Iron Gear for attacking
        loadAnimation("Character_Attack_Boots.png", 6, 4, 0.1f, "Character_Boots_Attack");
        loadAnimation("Character_Attack_Chestplate.png", 6, 4, 0.1f, "Character_Chestplate_Attack");
        loadAnimation("Character_Attack_Gloves.png", 6, 4, 0.1f, "Character_Gloves_Attack");
        loadAnimation("Character_Attack_Hat.png", 6, 4, 0.1f, "Character_Hat_Attack");
        loadAnimation("Character_Attack_Leggings.png", 6, 4, 0.1f, "Character_Leggings_Attack");

        // Iron Gear for idle
        loadAnimation("Character_Idle_Boots.png", 4, 4, 0.1f, "Character_Boots_Idle");
        loadAnimation("Character_Idle_Chestplate.png", 4, 4, 0.1f, "Character_Chestplate_Idle");
        loadAnimation("Character_Idle_Gloves.png", 4, 4, 0.1f, "Character_Gloves_Idle");
        loadAnimation("Character_Idle_Hat.png", 4, 4, 0.1f, "Character_Hat_Idle");
        loadAnimation("Character_Idle_Leggings.png", 4, 4, 0.1f, "Character_Leggings_Idle");

        // Iron Gear for moving
        loadAnimation("Character_Move_Boots.png", 6, 4, 0.1f, "Character_Boots_Move");
        loadAnimation("Character_Move_Chestplate.png", 6, 4, 0.1f, "Character_Chestplate_Move");
        loadAnimation("Character_Move_Gloves.png", 6, 4, 0.1f, "Character_Gloves_Move");
        loadAnimation("Character_Move_Hat.png", 6, 4, 0.1f, "Character_Hat_Move");
        loadAnimation("Character_Move_Leggings.png", 6, 4, 0.1f, "Character_Leggings_Move");

        // Some cosmetics and FX
        loadAnimation("Character_Attack_Beard.png", 6, 4, 0.1f, "Character_Beard_Attack");
        loadAnimation("Character_Attack_Hair.png", 6, 4, 0.1f, "Character_Hair_Attack");
        loadAnimation("Character_Attack_FX.png", 6, 4, 0.1f, "Character_FX_Attack");
        loadAnimation("Character_Idle_Beard.png", 4, 4, 0.1f, "Character_Beard_Idle");
        loadAnimation("Character_Idle_Hair.png", 4, 4, 0.1f, "Character_Hair_Idle");
        loadAnimation("Character_Move_Beard.png", 6, 4, 0.1f, "Character_Beard_Move");
        loadAnimation("Character_Move_Hair.png", 6, 4, 0.1f, "Character_Hair_Move");

        // Iron Sword
        loadAnimation("Character_Attack_Sword_2.png", 6, 4, 0.1f, "Character_Sword_2_Attack");
        loadAnimation("Character_Idle_Sword_2.png", 4, 4, 0.1f, "Character_Sword_2_Idle");
        loadAnimation("Character_Move_Sword_2.png", 6, 4, 0.1f, "Character_Sword_2_Move");

        // Wooden Sword
        loadAnimation("Character_Attack_Sword.png", 6, 4, 0.1f, "Character_Sword_Attack");
        loadAnimation("Character_Idle_Sword.png", 4, 4, 0.1f, "Character_Sword_Idle");
        loadAnimation("Character_Move_Sword.png", 6, 4, 0.1f, "Character_Sword_Move");
    }

    private void loadAnimation(String filePath, int cols, int rows, float frameDuration, String key) {
        Texture sheet = new Texture(Gdx.files.internal("assets/player/" + filePath));
        TextureRegion[][] regions = TextureRegion.split(sheet, sheet.getWidth() / cols, sheet.getHeight() / rows);

        // Using the 3rd row for the animation
        TextureRegion[] animationFrames = new TextureRegion[cols];
        for (int i = 0; i < cols; i++) {
            animationFrames[i] = regions[2][i]; // 3rd row (index 2) for facing direction
        }

        Animation<TextureRegion> animation = new Animation<>(frameDuration, animationFrames);
        animations.put(key, animation);
    }

    // Get the current frame index of the current animation
    public static int getCurrentFrameIndex() {
        float frameDuration = currentAnimation.getFrameDuration();
        int currentFrameIndex = (int) (stateTime / frameDuration) % currentAnimation.getKeyFrames().length;
        return currentFrameIndex;
    }

    private String getItemStateKey(String spriteKey) {
        String stateSuffix = "";
        switch (player.getCurrentState()) {
            case IDLE:
                stateSuffix = "_Idle";
                break;
            case RUNNING:
                stateSuffix = "_Move";
                break;
            case ATTACKING:
                stateSuffix = "_Attack";
                break;
            default:
                stateSuffix = "_Idle";
        }
        return spriteKey + stateSuffix;
    }

    private void initializeItemToSpriteMapping() {
        itemToSpriteMapping.put(Item.DIRT,
                new String[] { "Character_Leggings", "Character_Chestplate", "Character_Hat", "Character_Boots",
                        "Character_Gloves" });
        itemToSpriteMapping.put(Item.WOOD, new String[] { "Character_Sword", "Character_FX" });

        itemToSpriteMapping.put(Item.STONE, new String[] { "Character_Sword_2", "Character_FX" });
    }

    public void update() {
        stateTime += Gdx.graphics.getDeltaTime();
        // Update the current animation based on the player's state
        // Check if the player has just died to start the death animation
        if (player.getCurrentState() == State.DEAD && !deathAnimationStarted) {
            // Reset the state time to start the animation from the beginning
            stateTime = 0;
            deathAnimationStarted = true;
        } else if (player.getCurrentState() != State.DEAD) {
            // If player is not dead, ensure we're ready to play the death animation again
            deathAnimationStarted = false;
        }

        switch (player.getCurrentState()) {
            case IDLE:
                currentAnimation = animations.get("Character_Idle");
                break;
            case RUNNING:
                currentAnimation = animations.get("Character_Move");
                break;
            case ATTACKING:
                currentAnimation = animations.get("Character_Attack");
                break;
            case DEAD:
                currentAnimation = animations.get("Character_Death");
                break;
            default:
                // currentAnimation = animations.get("Character_Idle");

        }

        updateSpritesBasedOnInventory();
        getCurrentFrameIndex();
    }

    private void updateSpritesBasedOnInventory() {
        currentSprites.clear();
        for (Item item : itemToSpriteMapping.keySet()) {
            if (inventory.contains(item)) {
                Collections.addAll(currentSprites, itemToSpriteMapping.get(item));
            }
        }
    }

    public void render(SpriteBatch batch, float x, float y) {
        if (!player.isInvincible() || (int) (player.invincibilityTimer() * 10) % 2 == 0) {

            // Draw the base character animation frame
            TextureRegion baseFrame = currentAnimation.getKeyFrame(stateTime,
                    !deathAnimationStarted || player.getCurrentState() != State.DEAD);

            // Correct flip based on direction
            if ((player.isFacingRight() && baseFrame.isFlipX())
                    || (!player.isFacingRight() && !baseFrame.isFlipX())) {
                baseFrame.flip(true, false);
            }

            batch.draw(baseFrame, x - 175, y - 265, player.getWidth() + 225, player.getHeight() + 475);
            if (player.getCurrentState() != State.DEAD) {
                boolean hasHat = currentSprites.stream().anyMatch(s -> s.contains("Hat"));
                boolean hasSword = currentSprites.stream().anyMatch(s -> s.contains("Sword"));
                // Decide whether to add FX, based on whether a sword is present
                if (hasSword && !currentSprites.contains("Character_FX_Attack")) {
                    currentSprites.add("Character_FX_Attack");
                }

                // Draw item-specific and always-on sprites if player is not dead
                // if (player.getCurrentState() != State.DEAD) {
                // for (String spriteKey : currentSprites) {
                // Animation<TextureRegion> itemAnimation =
                // animations.get(getItemStateKey(spriteKey));
                // if (itemAnimation != null) {
                // TextureRegion itemFrame = itemAnimation.getKeyFrame(stateTime, true);
                // if ((player.isFacingRight() && itemFrame.isFlipX())
                // || (!player.isFacingRight() && !itemFrame.isFlipX())) {
                // itemFrame.flip(true, false);
                // }
                // // batch.draw(itemFrame, x - 175, y - 265, player.getWidth() + 225,
                // // player.getHeight() + 475);
                // }
                // }

                // Always draw beard and hair with the character
                String[] alwaysOnSprites = { "Character_Beard", "Character_Hair" };
                for (String spriteKey : alwaysOnSprites) {
                    if (hasHat && (spriteKey.contains("Beard") || spriteKey.contains("Hair"))) {
                        continue;
                    }
                    Animation<TextureRegion> spriteAnimation = animations.get(getItemStateKey(spriteKey));
                    if (spriteAnimation != null) {
                        TextureRegion spriteFrame = spriteAnimation.getKeyFrame(stateTime, true);
                        if ((player.isFacingRight() && spriteFrame.isFlipX())
                                || (!player.isFacingRight() && !spriteFrame.isFlipX())) {
                            spriteFrame.flip(true, false);
                        }
                        batch.draw(spriteFrame, x - 175, y - 265, player.getWidth() + 225,
                                player.getHeight() + 475);
                    }
                }
                // }
                // Draw non-top layer items (everything but swords and FX)
                for (String spriteKey : currentSprites) {
                    if (spriteKey.contains("Sword") || spriteKey.equals("Character_FX")) {
                        continue; // Skip swords and FX for now
                    }
                    drawItemSprite(batch, spriteKey, x, y);
                }

                // Draw top layer items (swords and FX)
                for (String spriteKey : currentSprites) {
                    if (spriteKey.contains("Sword") || spriteKey.equals("Character_FX")) {
                        drawItemSprite(batch, spriteKey, x, y);
                    }
                }

                // Handle death animation staying on last frame
                if (player.getCurrentState() == State.DEAD && deathAnimationStarted) {
                    if (currentAnimation.isAnimationFinished(stateTime)) {
                        TextureRegion lastFrame = currentAnimation.getKeyFrames()[currentAnimation.getKeyFrames().length
                                - 1];
                        batch.draw(lastFrame, x - 175, y - 265, player.getWidth() + 225, player.getHeight() + 475);
                    }
                }
            }
        }
    }

    private void drawItemSprite(SpriteBatch batch, String spriteKey, float x, float y) {
        Animation<TextureRegion> itemAnimation = animations.get(getItemStateKey(spriteKey));
        if (itemAnimation != null) {
            TextureRegion itemFrame = itemAnimation.getKeyFrame(stateTime, true);
            // Correct flip based on direction
            if ((player.isFacingRight() && itemFrame.isFlipX())
                    || (!player.isFacingRight() && !itemFrame.isFlipX())) {
                itemFrame.flip(true, false);
            }
            // Adjust FX size differently from the other sprites
            if (spriteKey.equals("Character_FX") && player.isFacingRight()) {
                batch.draw(itemFrame, x - 175, y - 265, player.getWidth() + 340, player.getHeight() + 475); // Adjusted

            } else if (spriteKey.equals("Character_FX") && !player.isFacingRight()) {
                batch.draw(itemFrame, x - 375, y - 265, player.getWidth() + 540, player.getHeight() + 475); // Adjusted

            } else {
                batch.draw(itemFrame, x - 175, y - 265, player.getWidth() + 225, player.getHeight() + 475);
            }
        }
    }

    @Override
    public void dispose() {
        for (Animation<TextureRegion> animation : animations.values()) {
            for (TextureRegion region : animation.getKeyFrames()) {
                region.getTexture().dispose();
            }
        }
    }

}