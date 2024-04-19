package com.minecraft.game.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * The MapLoader class is responsible for loading tiled maps.
 * Used to load a TiledMap from a file.
 */
public class MapLoader {
    
    /**
     * Loads a tiled map from the specified path.
     * 
     * @param mapPath the path to the tiled map file
     * @return the loaded TiledMap object, or null if the map failed to load
     */
    public static TiledMap loadTileMap(String mapPath) {
        try {
            return new TmxMapLoader().load(mapPath);
        } catch (Exception e) {
            String fullPath = Gdx.files.internal(mapPath).file().getAbsolutePath();
            System.err.println("Failed to load map: " + fullPath);
            e.printStackTrace();
            return null;
        }
    }
}
