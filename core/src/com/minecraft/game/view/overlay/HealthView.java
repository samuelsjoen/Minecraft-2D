package com.minecraft.game.view.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.Health;

public class HealthView implements IOverlay {
    private final Health health;
    private final Texture healthBarSheet;
    private final Texture armorHealthSheet;
    private final SpriteBatch batch;
    private final TextureRegion[][] splitHealthFrames;
    private final TextureRegion[][] splitArmorFrames;

    private float xHealth;
    private float yHealth;
    private float xArmor1;
    private float yArmor1;
    private float xArmor2;
    private float yArmor2;

    public HealthView(Health health, SpriteBatch batch) {
        this.healthBarSheet = new Texture(Gdx.files.internal("overlay/healthBar.png"));
        this.splitHealthFrames = TextureRegion.split(healthBarSheet, healthBarSheet.getWidth(),
                healthBarSheet.getHeight() / 5);
        this.armorHealthSheet = new Texture(Gdx.files.internal("overlay/armorBar.png"));
        this.splitArmorFrames = TextureRegion.split(armorHealthSheet, armorHealthSheet.getWidth(),
                armorHealthSheet.getHeight() / 5);
        this.health = health;
        this.batch = batch;
    }

    public void render() {
        if (health.isAlive()) {
            int playerHealth = health.getHealth();
            int armorHealth = health.getArmorHealth();
            int armorHealth1 = 0;
            int armorHealth2 = 0;
            if (armorHealth > 5) {
                armorHealth1 = 5;
                armorHealth2 = armorHealth - 5;
            } else {
                armorHealth1 = armorHealth;
            }
            batch.draw(splitHealthFrames[playerHealth - 1][0], xHealth, yHealth, 200, 40);
            if (armorHealth1 > 0) {
                batch.draw(splitArmorFrames[armorHealth1 - 1][0], xArmor1, yArmor1, 200, 40);
            }
            if (armorHealth2 > 0) {
                batch.draw(splitArmorFrames[armorHealth2 - 1][0], xArmor2, yArmor2, 200, 40);
            }
        }
    }

    public void update(Vector2 lowerLeftCorner) {
        xHealth = lowerLeftCorner.x + 40;
        yHealth = lowerLeftCorner.y + 665;
        xArmor1 = lowerLeftCorner.x + 40;
        yArmor1 = lowerLeftCorner.y + 615;
        xArmor2 = lowerLeftCorner.x + 40;
        yArmor2 = lowerLeftCorner.y + 565;
    }

    public void dispose() {
        healthBarSheet.dispose();
        armorHealthSheet.dispose();
    }

}