package com.minecraft.game.controller;

import com.badlogic.gdx.Input.Keys;

/**
 * The PlayerController class is responsible for handling player input.
 * It manages player movement and actions, such as moving left, right, jumping, and attacking.
 */
public class PlayerController {
    private ControllableMinecraftModel controllableModel;

    private boolean moveLeft;
    private boolean moveRight;
    private boolean isAttacking;

    /**
     * Constructs a new PlayerController with the controllable model.
     * The controllableModel is used for updating the player movement and actions.
     * Initializes the moveLeft, moveRight, and isAttacking variables to false.
     * @param controllableModel the controllable model for the Minecraft game
     */
    public PlayerController(ControllableMinecraftModel controllableModel) {
        this.controllableModel = controllableModel;
    
        this.moveLeft = false;
        this.moveRight = false;
        this.isAttacking = false;
    }

    /**
     * Handle key down events for player movement and actions
     * @param keycode the key code connected to the key
     * @return true if the key event was handled, false otherwise
     */
    public Boolean handleKeyDown(int keycode) {
        switch (keycode) {
            case Keys.A:
                setMoveLeft(true); // Move left
                return true;
            case Keys.D:
                setMoveRight(true); // Move right
                return true;
            case Keys.SPACE:
                controllableModel.playerJump();
                return true;
            case Keys.TAB:
                setIsAttacking(true);
                return true;
        }
        return false;
    }

    /**
     * Handle key up events for player movement and actions
     * @param keycode the key code connected to the key
     * @return true if the key event was handled, false otherwise
     */
    public Boolean handleKeyUp(int keycode) {
        if (keycode == Keys.TAB) {
            setIsAttacking(false);
            return true;
        } 
        
        else if (keycode == Keys.A) {
            setMoveLeft(false);
            return true;
        } 
        
        else if (keycode == Keys.D) {
            setMoveRight(false);
            return true;
        }
        return false;    
    }

    /**
     * Set the isAttacking variable to true or false, and handle the input
     */
    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
        updateMovement();  
    }

    /**
     * Set the moveLeft variable to true or false, and handle the input
     */
    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
        updateMovement();
    }

    /**
     * Set the moveRight variable to true or false, and handle the input
     */
    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
        updateMovement();
    }

    /**
     * Stop the player movement
     * Sets moveLeft, moveRight, and isAttacking to false and updates the movement
     */
    public void stopMovement() {
        this.moveLeft = false;
        this.moveRight = false;
        this.isAttacking = false;
        updateMovement();
    }

    /**
     * Update the player movement based on stored input
     */
    public void updateMovement() {
        controllableModel.updateMovement(this.moveLeft, this.moveRight, this.isAttacking);
    }

    // These are only used for testing. 
    /**
     * Get the moveLeft variable
     * @return true if the player is moving left, false otherwise
     */
    public Boolean getMoveLeft() {
        return this.moveLeft;
    }   
    
    /**
     * Get the moveRight variable
     * @return true if the player is moving right, false otherwise
     */
    public Boolean getMoveRight() {
        return this.moveRight;
    }

    /**
     * Get the isAttacking variable
     * @return true if the player is attacking, false otherwise
     */
    public Boolean getIsAttacking() {
        return this.isAttacking;
    }
}
