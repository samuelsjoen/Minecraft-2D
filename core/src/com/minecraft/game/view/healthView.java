package com.minecraft.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.model.GameEntity;
import com.minecraft.game.model.Health;
import com.minecraft.game.utils.Constants;

public class HealthView extends GameEntity {
    private Texture healthBarSheet;
    private TextureRegion[][] splitFrames;
    private Health health;
    
    public HealthView(float width, float height, Body body, Health health) {
        super(width, height, body);
        this.healthBarSheet = new Texture(Gdx.files.internal("assets/healthBar.png"));
        this.splitFrames = TextureRegion.split(healthBarSheet, healthBarSheet.getWidth(), healthBarSheet.getHeight() / 5);
        this.health = health;

    }
        public void render(SpriteBatch batch) {
        batch.draw(splitFrames[health.getHealth()-1][0], x, y, 200, 40);
    }

    @Override
    public void update() {
        x = body.getPosition().x * Constants.PPM-600;
        y = body.getPosition().y * Constants.PPM+305;
    }
}
