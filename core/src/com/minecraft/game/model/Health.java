package com.minecraft.game.model;

import com.minecraft.game.model.crafting.Inventory;
import com.minecraft.game.model.crafting.Item;

public class Health {
    private int currentHealth;
    private int maxHealth;
    private int armorHealth;
    private int maxArmorHealth;
    private boolean alive;
    private Inventory inventory;

    public Health(int currentHealth, int maxHealth, Inventory inventory) {
        this.armorHealth = 0;
        this.maxArmorHealth = 10;
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.alive = true;
        this.inventory = inventory;
    }

    public int getHealth() {
        return currentHealth;
    }

    private void setHealth(int health) {
        if (health <= maxHealth) {
            currentHealth = health;
        } else {
            currentHealth = maxHealth;
        }
    }

    // public void damage(int damage) {
    //     if (armorHealth > 0) {
    //         int damageRemaining = damage - armorHealth;
    //         if (armorHealth - damage <= 0) {
    //             setArmorHealth(0);
    //             damage(damageRemaining);
    //         } else {
    //             setArmorHealth(armorHealth - damage);
    //         }
    //     } else {
    //         if (currentHealth - damage <= 0) {
    //             setHealth(0);
    //             alive = false;
    //         } else {
    //             setHealth(currentHealth - damage);
    //         }
    //     }
    // }

    public void damage(int damage) {
        if (armorHealth > 0) {
            Item armorPiece = inventory.getNextBreakableArmorItem();
            int armorPieceHealth = inventory.getArmorPieceHealth(armorPiece);
            if (damage < armorPieceHealth) {
                inventory.damageArmor(damage);
                armorHealth -= damage;
            } else {
                int remainingDamage = damage - armorPieceHealth;
                inventory.breakArmor();
                armorHealth -= damage;
                damage(remainingDamage);
            }
        } else if (currentHealth - damage <= 0) {
            setHealth(0);
            alive = false;
        } else {
            setHealth(currentHealth-damage);
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

    public int getArmorHealth() {
        return armorHealth;
    }

    public void setArmorHealth(int health) {
        if (armorHealth <= maxArmorHealth) {
            armorHealth = health;
        } else {
            armorHealth = maxArmorHealth;
        }
    }
}
