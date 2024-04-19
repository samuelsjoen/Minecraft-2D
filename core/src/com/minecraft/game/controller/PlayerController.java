package com.minecraft.game.controller;

public class PlayerController {
    private boolean moveLeft;
    private boolean moveRight;
    private ControllableMinecraftModel controllableModel;

    public PlayerController(ControllableMinecraftModel controllableModel) {
        this.moveLeft = false;
        this.moveRight = false;
        this.controllableModel = controllableModel;
    }

    /**
     * Set the moveLeft variable to true or false, and handle the input
     */
    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
        handleInput();
    }

    /**
     * Set the moveRight variable to true or false, and handle the input
     */
    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
        handleInput();
    }

    /**
     * Handle the input from the player
     */
    private void handleInput() {
        controllableModel.handleInput(this.moveLeft, this.moveRight);
    }
}
