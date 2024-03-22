package com.minecraft.game.model.map;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Player;
import com.badlogic.gdx.maps.tiled.TiledMap;

public interface IMinecraftMap {

    // TODO: use this interface in MinecraftModel

    OrthogonalTiledMapRenderer setupMap(String mapPath);

    World getWorld();

    Player getPlayer();

    void removeBlock(int x, int y);

    void addBlock(int x, int y, TileType tileType);

    TiledMap getTiledMap();
}