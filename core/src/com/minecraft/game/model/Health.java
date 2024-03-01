package com.minecraft.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.utils.Constants;

public class Health {
    private int health;
    private int maxHealth;
    private boolean alive;

    public Health(int health, int maxHealth) {
        this.maxHealth = maxHealth;
        this.health = health;
        this.alive = true;
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
}
