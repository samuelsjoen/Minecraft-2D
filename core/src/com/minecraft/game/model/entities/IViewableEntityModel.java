package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Defines the contract for entities that are viewable and interactable within
 * the game.
 * This interface ensures that entities can be managed and manipulated within
 * the game's view systems,
 * providing necessary methods that define the entity's visual and interactive
 * properties.
 */
public interface IViewableEntityModel {

    /**
     * Determines if the entity is currently facing right.
     * 
     * @return true if the entity is facing right, false otherwise.
     */
    boolean isFacingRight();

    /**
     * Retrieves the amount of time the entity has been in the dead state.
     * This can be used to control animations or cleanup after an entity is
     * defeated.
     * 
     * @return The time in seconds the entity has been in the dead state.
     */
    float getDeadStateTime();

    /**
     * Retrieves the time spent in the secondary attack state.
     * This can help in managing complex attack animations or behaviors.
     * 
     * @return The time in seconds the entity has been performing its secondary
     *         attack.
     */
    float getAttack2StateTime();

    /**
     * Retrieves the total time the entity has been in its current state.
     * This is often used to determine state transitions or to synchronize
     * animations.
     * 
     * @return The time in seconds since the last state change.
     */
    float getStateTime();

    /**
     * Gets the width of the entity.
     * 
     * @return The width of the entity in game units.
     */
    float getWidth();

    /**
     * Gets the height of the entity.
     * 
     * @return The height of the entity in game units.
     */
    float getHeight();

    /**
     * Retrieves the current state of the entity.
     * 
     * @return The current state of the entity.
     */
    State getCurrentState();

    /**
     * Sets the attack frame activation state. This is typically used to trigger or
     * deactivate attack animations.
     * 
     * @param isActive true if the attack frame is to be activated, false otherwise.
     */
    void setAttackFrame(boolean isActive);

    /**
     * Marks the entity for removal from the game. This is typically used to flag
     * entities for cleanup.
     * 
     * @param isMarked true if the entity is to be removed, false to clear the
     *                 removal mark.
     */
    void setMarkedForRemoval(boolean isMarked);

    /**
     * Gets the current position of the entity in the game world.
     * 
     * @return A {@link Vector2} representing the current position of the entity.
     */
    Vector2 getPosition();
}
