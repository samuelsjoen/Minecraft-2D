package com.minecraft.game.model.map;

import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * A subclass for the MinecraftMap class that provides extra functionality for testing.
 */
public class MinecraftMapHelper extends MinecraftMap {

    public MinecraftMapHelper() {
        super();
    }

    /**
     * Set up the tiledmap without rendering for testing purposes
     * @return The TiledMap object.
     */
    TiledMap setupMapNoRender(String mapPath) {
        tiledMap = MapLoader.loadTileMap(mapPath);
        createMapObjectsForAllTiles();
        parseMapObjects(tiledMap.getLayers().get("collisions").getObjects());
        return tiledMap;
    }
}
