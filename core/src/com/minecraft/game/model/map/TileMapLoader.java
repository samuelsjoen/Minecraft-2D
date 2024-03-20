package com.minecraft.game.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Used to load a TiledMap from a file.
 */
public class TileMapLoader {
    
    public static TiledMap loadTileMap(String mapPath) {
        try {
            return new TmxMapLoader().load(mapPath);
        } catch (Exception e) {
            String fullPath = Gdx.files.internal(mapPath).file().getAbsolutePath();
            System.err.println("Failed to load map: " + fullPath);
            return null;
        }
    }
}
