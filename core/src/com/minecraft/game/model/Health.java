package com.minecraft.game.model;

import com.minecraft.game.model.items.ArmorInventory;
import com.minecraft.game.model.items.Item;

/**
 * The Health class represents the health of the player.
 */
public class Health {
    private int currentHealth;
    private final int maxHealth;
    private int armorHealth;
    private final int maxArmorHealth;
    private boolean alive;
    private ArmorInventory armorInventory;

    /**
     * Initializes the health of the player
     * 
     * @param currentHealth the current health of the player
     * @param maxHealth     the maximum health of the player
     */
    public Health(int currentHealth, int maxHealth) {
        this.armorHealth = 0;
        this.maxArmorHealth = 10;
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.alive = true;
    }

    /** Returns the current health */
    public int getHealth() {
        return currentHealth;
    }

    /**
     * Sets the health of the player
     * 
     * @param health the health to set
     */
    private void setHealth(int health) {
        if (health <= maxHealth) {
            currentHealth = health;
        } else {
            currentHealth = maxHealth;
        }
    }

    /**
     * Damages the player
     * 
     * @param damage the amount of damage to deal
     */
    public void damage(int damage) {
        if (armorHealth > 0) {
            Item armorPiece = armorInventory.getNextBreakableArmorItem();
            int armorPieceHealth = armorInventory.getArmorPieceHealth(armorPiece);
            if (damage < armorPieceHealth) {
                armorInventory.damageArmor(damage);
                armorHealth -= damage;
            } else {
                int remainingDamage = damage - armorPieceHealth;
                armorInventory.removeItem();
                armorHealth -= damage;
                damage(remainingDamage);
            }
        } else if (currentHealth - damage <= 0) {
            setHealth(0);
            alive = false;
        } else {
            setHealth(currentHealth - damage);
        }
    }

    /**
     * Heals the player
     * 
     * @param heal the amount of health to heal
     */
    public void heal(int heal) {
        if (isAlive()) {
            if (currentHealth + heal >= maxHealth) {
                setHealth(maxHealth);
            } else {
                setHealth(currentHealth + heal);
            }
        }
    }

    /** Revives the player */
    public void revive() {
        if (!isAlive()) {
            alive = true;
            heal(maxHealth);
        }
    }

    /** Returns whether the player is alive */
    public boolean isAlive() {
        return alive;
    }

    /** Returns the current armor health */
    public int getArmorHealth() {
        return armorHealth;
    }

    /**
     * Sets the armor health of the player
     * 
     * @param health the health to set
     */
    public void setArmorHealth(int health) {
        if (armorHealth <= maxArmorHealth) {
            armorHealth = health;
        } else {
            armorHealth = maxArmorHealth;
        }
    }

    /** Initilizes the armor inventory */
    public void setArmorInventory(ArmorInventory armorInventory) {
        this.armorInventory = armorInventory;
    }
}
