package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.utils.Constants;

public class Health {
    private int health;
    private int maxHealth;
    private boolean alive;
    private float x, y;
    private float scale;
    private SpriteBatch batch;
    private Texture healthBarSheet;
    private TextureRegion[][] splitFrames;

    public Health(SpriteBatch batch) {
        this.maxHealth = 5;
        this.health = maxHealth;
        this.alive = true;
        this.batch = batch;

        this.healthBarSheet = new Texture(Gdx.files.internal("healthBar.png"));

        this.splitFrames = TextureRegion.split(healthBarSheet, 5, 1);

        renderHealthBar();
    }

    public int getHealth() {
        return health;
    }

    private void setHealth(int health) {
        this.health = health;
        renderHealthBar();
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

    private void renderHealthBar() {
        batch.draw(splitFrames[health-1][0], x, y);
    }
}
