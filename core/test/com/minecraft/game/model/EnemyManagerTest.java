package com.minecraft.game.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Score;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Projectile;
import com.minecraft.game.model.entities.Slime;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapLayers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedConstruction;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Fixture;

public class EnemyManagerTest {

    @Mock
    private World mockWorld;
    @Mock
    private Player mockPlayer;
    @Mock
    private TiledMap mockTiledMap;
    @Mock
    private Body mockBody;
    private EnemyManager enemyManager;
    private TiledMapTileLayer mockMineableLayer;
    @Mock
    private Knight mockKnight;
    @Mock
    private Slime mockSlime;
    @Mock
    private PinkMonster mockPinkMonster;
    @Mock
    private Projectile mockProjectile;
    @Mock
    private DayNightCycle mockDayNightCycle;
    @Mock
    private Score mockScore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockWorld = mock(World.class);
        mockPlayer = mock(Player.class);
        mockTiledMap = mock(TiledMap.class);
        mockMineableLayer = mock(TiledMapTileLayer.class);
        mockDayNightCycle = mock(DayNightCycle.class);

        Body mockBody = mock(Body.class);
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);

        Fixture mockFixture = mock(Fixture.class);
        when(mockBody.createFixture(any(FixtureDef.class))).thenReturn(mockFixture);

        when(mockBody.getPosition()).thenReturn(new Vector2(0, 0));
        when(mockPlayer.getBody()).thenReturn(mockBody);

        MapLayers mockMapLayers = mock(MapLayers.class);
        when(mockTiledMap.getLayers()).thenReturn(mockMapLayers);

        mockScore = mock(Score.class);
        when(mockScore.getScore()).thenReturn(0);

        when(mockMapLayers.get(anyString())).thenReturn(mockMineableLayer);
        when(mockDayNightCycle.getIsNight()).thenReturn(true);

        try (MockedConstruction<Knight> knight = Mockito.mockConstruction(Knight.class);
            MockedConstruction<Slime> slime = Mockito.mockConstruction(Slime.class);
            MockedConstruction<PinkMonster> pinkMonster = Mockito.mockConstruction(PinkMonster.class);
            MockedConstruction<Projectile> projectile = Mockito.mockConstruction(Projectile.class)) {
                enemyManager = new EnemyManager(mockWorld, mockPlayer, mockTiledMap, mockDayNightCycle, mockScore);
        }

        EnemyManager.knights.clear();
        EnemyManager.slimes.clear();
        EnemyManager.pinkMonsters.clear();

        // Mocking entities
        mockKnight = mock(Knight.class);
        mockSlime = mock(Slime.class);
        mockPinkMonster = mock(PinkMonster.class);
        mockProjectile = mock(Projectile.class);

        // Ensure getBody() returns a non-null Body for all entities
        when(mockKnight.getBody()).thenReturn(mockBody);
        when(mockSlime.getBody()).thenReturn(mockBody);
        when(mockPinkMonster.getBody()).thenReturn(mockBody);
        when(mockProjectile.getBody()).thenReturn(mockBody);

        // Clear lists to start fresh
        EnemyManager.knights.clear();
        EnemyManager.slimes.clear();
        EnemyManager.pinkMonsters.clear();
        EnemyManager.projectiles.clear();
    }

    @Test
    void testGetProjectiles_ReturnsEmptyListWhenNoProjectilesExist() {
        assertTrue(EnemyManager.getProjectiles().isEmpty(), "Projectiles list should be empty initially.");
    }

    @Test
    void testGetPinkMonsters() {
        PinkMonster testPinkMonster = new PinkMonster(0, 0, mockWorld, mockPlayer, 0, 0, null);
        EnemyManager.pinkMonsters.add(testPinkMonster);
        EnemyManager.pinkMonsters.add(testPinkMonster);
        EnemyManager.pinkMonsters.add(testPinkMonster);
        EnemyManager.pinkMonsters.add(testPinkMonster);
        EnemyManager.pinkMonsters.add(testPinkMonster);
        assertEquals(5, EnemyManager.getPinkMonsters().size(), "Should return a list containing 1 pink monster.");
        assertEquals(testPinkMonster, EnemyManager.getPinkMonsters().get(0),
                "The returned list should contain the added pink monster.");
    }

    @BeforeAll
    static void setupLibGDX() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {
            }

            @Override
            public void dispose() {
            }

            @Override
            public void pause() {
            }

            @Override
            public void render() {
            }

            @Override
            public void resize(int arg0, int arg1) {
            }

            @Override
            public void resume() {
            }

        }, config);
        // Wait for the LibGDX environment to initialize.
        Gdx.gl = mock(GL20.class);
    }

}
