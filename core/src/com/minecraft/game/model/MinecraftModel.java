package com.minecraft.game.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.controller.ControllableMinecraftModel;
import com.minecraft.game.model.entities.EntityFactory;
import com.minecraft.game.utils.MinecraftMap;
import com.minecraft.game.view.ViewableMinecraftModel;

public class MinecraftModel implements ViewableMinecraftModel, ControllableMinecraftModel {

    private MinecraftMap map;
    @SuppressWarnings("unused")
    private EntityFactory factory;

    private GameState gameState;
    @SuppressWarnings("unused")
    private Player player;

    public MinecraftModel(MinecraftMap map, EntityFactory factory) {
        this.map = map;
        this.factory = factory;

        this.gameState = GameState.GAME_ACTIVE; 

        this.player = map.getPlayer();
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void setGameState(GameState state) {
        gameState = state;
    }

    @Override
    public World getWorld() {
        return map.getWorld();
    }

    @Override
    public OrthogonalTiledMapRenderer getMapRenderer() {
        return map.setupMap();
    }

    @Override
    public TiledMap getTiledMap() {
        return map.getTiledMap();
    }

    @Override
    public Player getPlayer() {
        return map.getPlayer();
    }

    @Override
    public void setPlayer(Player player) {
        // get player from map through getter and set it here?
    }

}
