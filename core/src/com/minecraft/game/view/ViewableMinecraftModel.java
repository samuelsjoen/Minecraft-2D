package com.minecraft.game.view;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.GameState;
import com.minecraft.game.model.Player;

public interface ViewableMinecraftModel {

    GameState getGameState();

    OrthogonalTiledMapRenderer getMapRenderer();

    TiledMap getTiledMap();

    World getWorld();

    Player getPlayer();

    void setPlayer(Player player);

}
