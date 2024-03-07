package com.minecraft.game.model;

/*import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.utils.Constants;
*/

public class Health {
    private int currentHealth;
    private int maxHealth;
    private boolean alive;

    public Health(int currentHealth, int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.alive = true;
    }

    public int getHealth() {
        return currentHealth;
    }

    private void setHealth(int health) {
        if (health <= maxHealth) {
            currentHealth = health;
        }
    }

    public void damage(int damage) {
        if (currentHealth - damage <= 0) {
            setHealth(0);
            alive = false;
        } else {
            setHealth(currentHealth - damage);
        }
    }

    public void heal(int heal) {
        if (isAlive()) {
            if (currentHealth + heal >= maxHealth) {
                setHealth(maxHealth);
            } else {
                setHealth(currentHealth + heal);
            }
        }
    }

    public void revive() {
        if (!isAlive()) {
            alive = true;
            heal(maxHealth);
        }
    }

    public boolean isAlive() {
        return alive;
    }
}
