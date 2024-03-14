package com.mygdx.game.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
// headless backend
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;
import com.badlogic.gdx.backends.headless.HeadlessNet;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.minecraft.game.Minecraft;
import com.minecraft.game.utils.TileMapLoader;
import com.mygdx.game.LibgdxUnitTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
// mockito
import static org.mockito.Mockito.*;

public class TileMapLoaderTest extends LibgdxUnitTest {

    @Test
    public void testLoadValidTileMap() {
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
