package com.minecraft.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EnemyManagerTest {

    @Mock
    private World mockWorld;
    @Mock
    private Player mockPlayer;
    @Mock
    private TiledMap mockTiledMap;

    private EnemyManager enemyManager;

    @BeforeEach
    void setUp() {
        mockWorld = mock(World.class);
        mockPlayer = mock(Player.class);
        mockTiledMap = mock(TiledMap.class);

        Body mockBody = mock(Body.class);
        when(mockBody.getPosition()).thenReturn(new Vector2(0, 0)); // or any vector
        when(mockPlayer.getBody()).thenReturn(mockBody);

        MapLayers mockMapLayers = mock(MapLayers.class);
        when(mockTiledMap.getLayers()).thenReturn(mockMapLayers);

        TiledMapTileLayer mockTiledMapTileLayer = mock(TiledMapTileLayer.class);
        when(mockMapLayers.get(anyString())).thenReturn(mockTiledMapTileLayer);

        enemyManager = new EnemyManager(mockWorld, mockPlayer, mockTiledMap);
    }

    @Test
    void testInitialization() {
        assertTrue(EnemyManager.getEnemies().isEmpty(), "Enemies list should initially be empty");
    }

    @Test
    void testValidSpawnLocation() {
        // Check for a known valid location
        boolean isValid = enemyManager.isSpawnLocationValid(5.0f, 5.0f, false);

        // Assert the location is considered valid
        assertTrue(isValid, "The location should be valid for spawning an enemy");
    }
}
