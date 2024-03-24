package com.minecraft.game.view.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Player;

public class HealthView {
    private final Texture healthBarSheet;
    private final TextureRegion[][] splitFrames;

    private float xHealth;
    private  float yHealth;

    public HealthView(Health health){
        this.healthBarSheet = new Texture(Gdx.files.internal("assets/overlay/healthBar.png"));
        this.splitFrames = TextureRegion.split(healthBarSheet, healthBarSheet.getWidth(), healthBarSheet.getHeight() / 5);
    }

    public void render(SpriteBatch batch) {
        // TODO: i think this is not done accoridng to mvc. Seems like we access the model(player) directly.
        int playerHealth = Player.getHealth().getHealth();
        if (playerHealth > 0) {
            batch.draw(splitFrames[playerHealth - 1][0], xHealth, yHealth, 200, 40);
        }
    }

    public void update(Vector2 lowerLeftCorner) {        
        xHealth = lowerLeftCorner.x + 40;
        yHealth = lowerLeftCorner.y + 665;
    }

}