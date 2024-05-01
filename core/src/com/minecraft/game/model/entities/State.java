package com.minecraft.game.model.entities;

/**
 * Enumerates the various states that a game entity can be in during gameplay.
 * These states are used to control animations and interactions within the game.
 */
public enum State {
    /**
     * The entity is idle, not engaging in any movement or action.
     */
    IDLE,

    /**
     * The entity is running, typically moving across the game environment.
     */
    RUNNING,

    /**
     * The entity is engaging in its primary attack mode.
     */
    ATTACKING,

    /**
     * The entity is engaging in a secondary attack mode or special action.
     */
    ATTACKING2,

    /**
     * The entity is dead, not performing any actions and typically waiting to be
     * removed from the game.
     */
    DEAD
}
