package com.mygdx.game.utils;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.minecraft.game.utils.TileMapLoader;
import com.mygdx.game.LibgdxUnitTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TileMapLoaderTest extends LibgdxUnitTest {

    @Test
    public void testLoadValidTileMap() {
        // added ../ to the path to get into the parent directory of the assets folder
        String mapPath = "../assets/map/mapExample2-64.tmx";
        TiledMap tiledMap = TileMapLoader.loadTileMap(mapPath);

        assertNotNull(tiledMap);
    }

    @Test
    public void testLoadInvalidTileMap() {
        String mapPath = "../assets/map/invalidMap.tmx";
        TiledMap tiledMap = TileMapLoader.loadTileMap(mapPath);

        assertNull(tiledMap);
    }
}
