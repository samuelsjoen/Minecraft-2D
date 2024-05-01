package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Player;

/**
 * Encapsulates parameters required for creating entities within the game world.
 * This class is used to pass necessary data to {@link EntityFactory} methods
 * when creating instances of game entities such as knights, slimes, or
 * projectiles.
 */

public class EntityParams {
    public World world; // The game world where the entity will exist.
    public Player player; // The player instance, used for interactions or reference.
    public float spawnX; // The X-coordinate for spawning the entity.
    public float spawnY; // The Y-coordinate for spawning the entity.
    public Health health; // Health for the entity.
    public Vector2 targetPosition; // Optional target position used primarily for projectiles.

    /**
     * Constructs an instance of EntityParams to provide necessary parameters for
     * entity creation.
     *
     * @param world  The physics world where the entity will exist.
     * @param player The player instance, which may interact with or be referenced
     *               by the entity.
     * @param spawnX The initial X-coordinate where the entity will be spawned.
     * @param spawnY The initial Y-coordinate where the entity will be spawned.
     * @param health The health configuration for entities that require health
     *               management.
     */
    public EntityParams(World world, Player player, float spawnX, float spawnY, Health health) {
        this.world = world;
        this.player = player;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.health = health;
    }

    /**
     * Sets the target position for entities that require a specific target, like
     * projectiles.
     * This method is useful for setting the aim or destination of an entity's
     * movement or effect.
     *
     * @param targetX The target X-coordinate.
     * @param targetY The target Y-coordinate.
     */
    public void setTargetPosition(float targetX, float targetY) {
        this.targetPosition = new Vector2(targetX, targetY);
    }

}
