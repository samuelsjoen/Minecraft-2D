package com.minecraft.game.controller;

import com.badlogic.gdx.Input.Keys;

public class PlayerController {
    private ControllableMinecraftModel controllableModel;

    private boolean moveLeft;
    private boolean moveRight;

    private boolean isAttacking;

    public PlayerController(ControllableMinecraftModel controllableModel) {
        this.controllableModel = controllableModel;

        this.moveLeft = false;
        this.moveRight = false;
        this.isAttacking = false;
    }

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
                //controllableModel.playerAttack();
                return true;
        }
        return false;
    }

    public Boolean handleKeyUp(int keycode) {
        if (keycode == Keys.TAB) {
            //controllableModel.playerAttack();
            setIsAttacking(false);
        } 
        
        else if (keycode == Keys.A) {
            setMoveLeft(false);
        } 
        
        else if (keycode == Keys.D) {
            setMoveRight(false);
        }
        return true;    
    }

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

    public void stopMovement() {
        this.moveLeft = false;
        this.moveRight = false;
        this.isAttacking = false;
        updateMovement();
    }

    // Method to update player input
    public void updateMovement() {
        controllableModel.updateMovement(this.moveLeft, this.moveRight, this.isAttacking);
    }

    // Getters
    public Boolean getMoveLeft() {
        return this.moveLeft;
    }   
    
    public Boolean getMoveRight() {
        return this.moveRight;
    }

    public Boolean getIsAttacking() {
        return this.isAttacking;
    }

}
