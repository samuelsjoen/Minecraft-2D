package com.minecraft.game.model.map;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.minecraft.game.LibgdxUnitTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MapLoaderTest extends LibgdxUnitTest {

    @Test
    public void testLoadValidTileMap() {
        String mapPath = "map/testMap-64.tmx";
        TiledMap tiledMap = MapLoader.loadTileMap(mapPath);

        // The path is valid, so the TiledMap object should not be null
        assertNotNull(tiledMap);
    }

    @Test
    public void testLoadInvalidTileMap() {
        String mapPath = "map/invalidMap.tmx";
        TiledMap tiledMap = MapLoader.loadTileMap(mapPath);

        // The path is invalid, so the TiledMap object should be null
        assertNull(tiledMap);
    }
}
