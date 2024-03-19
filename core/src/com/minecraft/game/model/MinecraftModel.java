package com.minecraft.game.model;

import com.minecraft.game.controller.ControllableMinecraftModel;
import com.minecraft.game.model.entities.EntityFactory;
import com.minecraft.game.utils.MinecraftMap;
import com.minecraft.game.view.ViewableMinecraftModel;

public class MinecraftModel implements ViewableMinecraftModel, ControllableMinecraftModel {

    @SuppressWarnings("unused")
    private MinecraftMap map;
    @SuppressWarnings("unused")
    private EntityFactory factory;

    private GameState gameState;

    public MinecraftModel(MinecraftMap map, EntityFactory factory) {
        this.map = map;
        this.factory = factory;

        //this.gameState = GameState.GAME_ACTIVE; 
        this.gameState = GameState.WELCOME_SCREEN;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void setGameState(GameState state) {
        gameState = state;
    }

}
