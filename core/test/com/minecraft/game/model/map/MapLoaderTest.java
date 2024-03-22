package com.minecraft.game.model.map;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.minecraft.game.LibgdxUnitTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MapLoaderTest extends LibgdxUnitTest {

    @Test
    public void testLoadValidTileMap() {
        // added ../ to the path to get into the parent directory of the assets folder
        String mapPath = "../assets/map/mapExample2-64.tmx";
        TiledMap tiledMap = MapLoader.loadTileMap(mapPath);

        assertNotNull(tiledMap);
    }

    @Test
    public void testLoadInvalidTileMap() {
        String mapPath = "../assets/map/invalidMap.tmx";
        TiledMap tiledMap = MapLoader.loadTileMap(mapPath);

        assertNull(tiledMap);
    }
}
