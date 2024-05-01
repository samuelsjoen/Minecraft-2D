package com.minecraft.game.model.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;

/**
 * Abstract base class for all game entities in the game. This class provides
 * common functionality and state management for various entities that will be
 * used within the game world.
 */
public abstract class GameEntity implements Disposable {

    public float x; // The X-coordinate of the entity in the game world.
    public float y; // The Y-coordinate of the entity in the game world.
    protected float velX; // Velocity of the entity along the X-axis.
    protected float velY; // Velocity of the entity along the Y-axis.
    protected float speed; // Speed of the entity.
    public float width; // Width of the entity.
    public float height; // Height of the entity.
    protected Body body; // The physics body associated with this entity.
    private boolean markForRemoval = false; // Flag to mark the entity for removal from the game world.

    /**
     * Constructs a new GameEntity with specified dimensions and a physics body.
     *
     * @param width  The width of the entity.
     * @param height The height of the entity.
     * @param body   The physics body associated with the entity, provided by the
     *               physics engine.
     */
    public GameEntity(float width, float height, Body body) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.body = body;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
    }

    /**
     * Updates the entity's state. This method should be called every game tick.
     *
     * @param deltaTime The time passed since the last game tick.
     */
    public abstract void update(float deltaTime);

    /**
     * Gets the physics body associated with this entity.
     *
     * @return The physics body of the entity.
     */
    public Body getBody() {
        return body;
    }

    /**
     * Disposes of this entity's resources and removes its physics body from the
     * world.
     */
    @Override
    public void dispose() {
        body.getWorld().destroyBody(body);
    }

    /**
     * Checks if the entity is marked for removal from the game world.
     *
     * @return true if the entity is marked for removal, false otherwise.
     */
    public boolean isMarkedForRemoval() {
        return markForRemoval;
    }

    /**
     * Toggles the removal flag for the entity. This is used to mark or unmark the
     * entity for removal based on game logic.
     */
    public void setMarkedForRemoval() {
        markForRemoval = !markForRemoval;
    }

}
