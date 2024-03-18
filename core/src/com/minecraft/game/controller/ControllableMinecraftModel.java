package com.minecraft.game.controller;

import com.minecraft.game.model.GameState;

public interface ControllableMinecraftModel {

    // GameState

    GameState getGameState();

    void setGameState(GameState state);

    
}
