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

    // Methods to set player input state
    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
        handleInput();
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
        handleInput();
    }

    // Method to handle player input
    private void handleInput() {
        controllableModel.handleInput(this.moveLeft, this.moveRight);
    }
}
