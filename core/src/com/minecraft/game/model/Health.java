package com.minecraft.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.utils.Constants;

public class Health extends GameEntity {
    private int health;
    private int maxHealth;
    private boolean alive;
    private float scale;
    private Texture healthBarSheet;
    private TextureRegion[][] splitFrames;

    public Health(float width, float height, Body body) {
        super(width, height, body);
        this.maxHealth = 5;
        this.health = maxHealth;
        this.alive = true;

        this.healthBarSheet = new Texture(Gdx.files.internal("assets/healthBar.png"));

        this.splitFrames = TextureRegion.split(healthBarSheet, 1, 5);
    }

    public int getHealth() {
        return health;
    }

    private void setHealth(int health) {
        this.health = health;
    }

    public void damage(int damage) {
        if (health - damage <= 0) {
            setHealth(0);
            alive = false;
        } else {
            setHealth(health - damage);
        }
    }

    public void heal(int heal) {
        if (health + heal >= maxHealth) {
            setHealth(maxHealth);
        } else {
            setHealth(health + heal);
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void render(SpriteBatch batch) {
        batch.draw(splitFrames[health-1][0], x, y);
    }

    @Override
    public void update() {
        x = body.getPosition().x * Constants.PPM;
        y = body.getPosition().y * Constants.PPM;
    }
}
