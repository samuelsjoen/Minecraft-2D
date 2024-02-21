package com.mygdx.game.model;

public class Health {
    private int health;
    private int maxHealth;
    private boolean alive;

    public Health() {
        this.maxHealth = 5;
        this.health = maxHealth;
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
            health = 0;
            alive = false;
        } else {
            setHealth(health - damage);
        }
    }

    public void heal(int heal) {
        if (health + heal >= maxHealth) {
            health = maxHealth;
        } else {
            setHealth(health + heal);
        }
    }

    public boolean isAlive() {
        return alive;
    }
}
