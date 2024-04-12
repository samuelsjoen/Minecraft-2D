package com.minecraft.game.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
    private DayNightCycle mockDayNightCycle;

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

        when(mockMapLayers.get(anyString())).thenReturn(mockMineableLayer);
        when(mockDayNightCycle.getIsNight()).thenReturn(true);

        enemyManager = new EnemyManager(mockWorld, mockPlayer, mockTiledMap, mockDayNightCycle);
        EnemyManager.enemies.clear();
        EnemyManager.slimes.clear();
        EnemyManager.pinkMonsters.clear();

    }

    @Test
    void testInitialization() {
        assertTrue(EnemyManager.getEnemies().isEmpty(), "Enemies list should initially be empty");
    }

    @Test
    void testGetEnemies() {
        Knight testKnight = new Knight(0, 0, mockWorld, mockPlayer, 0, 0, null);
        EnemyManager.enemies.add(testKnight);
        assertEquals(1, enemyManager.getEnemies().size(), "Should return a list containing 1 enemy.");
        assertEquals(testKnight, enemyManager.getEnemies().get(0),
                "The returned list should contain the added knight.");
    }

    @Test
    void testGetSlimes() {
        Slime testSlime = new Slime(0, 0, mockWorld, mockPlayer, 0, 0, null);
        EnemyManager.slimes.add(testSlime);
        EnemyManager.slimes.add(testSlime);
        assertEquals(2, enemyManager.getSlimes().size(), "Should return a list containing 1 slime.");
        assertEquals(testSlime, enemyManager.getSlimes().get(0), "The returned list should contain the added slime.");
    }

    @Test
    void testGetPinkMonsters() {
        PinkMonster testPinkMonster = new PinkMonster(0, 0, mockWorld, mockPlayer, 0, 0, null);
        EnemyManager.pinkMonsters.add(testPinkMonster);
        EnemyManager.pinkMonsters.add(testPinkMonster);
        EnemyManager.pinkMonsters.add(testPinkMonster);
        EnemyManager.pinkMonsters.add(testPinkMonster);
        EnemyManager.pinkMonsters.add(testPinkMonster);
        assertEquals(5, enemyManager.getPinkMonsters().size(), "Should return a list containing 1 pink monster.");
        assertEquals(testPinkMonster, enemyManager.getPinkMonsters().get(0),
                "The returned list should contain the added pink monster.");
    }

    @Test
    void testSpawnEnemy() {
        // Mock player position within the valid range
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(15, 15));

        // Simulate valid spawn locations.
        when(mockMineableLayer.getCell(anyInt(), anyInt()))
                .thenAnswer(invocation -> {
                    Object[] args = invocation.getArguments();
                    int y = (int) args[1];
                    // Simulate that the cell at the spawn level is mineable and the ones above are
                    // empty
                    if (y == 10) {
                        Cell mockCell = mock(Cell.class);
                        return mockCell;
                    }
                    return null; // Simulate empty cells above
                });

        enemyManager.spawnEnemy();

        // Verify that at least one list has a entity.
        boolean enemySpawned = !EnemyManager.getEnemies().isEmpty() ||
                !EnemyManager.getSlimes().isEmpty() ||
                !EnemyManager.getPinkMonsters().isEmpty();

        assertTrue(enemySpawned, "An enemy should have been spawned.");
    }

    @Test
    void testRemoveDeadEnemies() {
        // Create mock enemies with positions set for removal
        Knight knightToRemove = mock(Knight.class);
        Slime slimeToRemove = mock(Slime.class);
        PinkMonster monsterToRemove = mock(PinkMonster.class);

        when(knightToRemove.getBody()).thenReturn(mockBody);
        when(slimeToRemove.getBody()).thenReturn(mockBody);
        when(monsterToRemove.getBody()).thenReturn(mockBody);

        // Positioning these entities so they should be removed
        when(knightToRemove.getBody().getPosition()).thenReturn(new Vector2(0, -20)); // Below the threshold
        when(slimeToRemove.isMarkedForRemoval()).thenReturn(true); // Marked for removal
        when(monsterToRemove.getBody().getPosition()).thenReturn(new Vector2(0, -20)); // Below the threshold

        // Adding mock entities to their respective lists
        EnemyManager.enemies.add(knightToRemove);
        EnemyManager.slimes.add(slimeToRemove);
        EnemyManager.pinkMonsters.add(monsterToRemove);

        // Ensure entities are in the list before removal
        assertFalse(EnemyManager.enemies.isEmpty());
        assertFalse(EnemyManager.slimes.isEmpty());
        assertFalse(EnemyManager.pinkMonsters.isEmpty());

        // Call the method under test
        enemyManager.update(0); // updating to kill them since they are below the threshold

        // Verify that lists are empty after removal
        assertTrue(EnemyManager.enemies.isEmpty(), "Enemies list should be empty after removal.");
        assertTrue(EnemyManager.slimes.isEmpty(), "Slimes list should be empty after removal.");
        assertTrue(EnemyManager.pinkMonsters.isEmpty(), "PinkMonsters list should be empty after removal.");
    }

    @Test
    void testSpawnEntityBasedOnChoice() throws Exception {
        EnemyManager.enemies.clear();
        EnemyManager.slimes.clear();
        EnemyManager.pinkMonsters.clear();
        // Mock player position within the valid range
        when(mockPlayer.getBody().getPosition()).thenReturn(new Vector2(15, 15));

        // Simulate valid spawn locations.
        when(mockMineableLayer.getCell(anyInt(), anyInt()))
                .thenAnswer(invocation -> {
                    Object[] args = invocation.getArguments();
                    int y = (int) args[1];
                    // Simulate that the cell at the spawn level is mineable and the ones above are
                    // empty
                    if (y == 10) {
                        Cell mockCell = mock(Cell.class);
                        return mockCell;
                    }
                    return null; // Simulate empty cells above
                });

        enemyManager.spawnEnemy();

        // Use the getter to check which enemy was chosen
        float chosenEnemy = enemyManager.getChooseEnemy();
        System.out.println(chosenEnemy);
        // Assert based on the chosenEnemy value
        if (chosenEnemy == 0) {
            assertFalse(EnemyManager.enemies.isEmpty(), "A Knight should have been spawned.");
        } else if (chosenEnemy == 1) {
            assertFalse(EnemyManager.slimes.isEmpty(), "A Slime should have been spawned.");
        } else if (chosenEnemy == 2) {
            assertFalse(EnemyManager.pinkMonsters.isEmpty(), "A PinkMonster should have been spawned.");
        } else {
            fail("Invalid enemy type chosen.");
        }
    }

    @Test
    void testAddKnight() {
        Knight knight = mock(Knight.class);
        EnemyManager.enemies.add(knight); // Add the mock Knight to the list.

        assertTrue(EnemyManager.enemies.contains(knight), "The enemies list should contain the added Knight.");
        assertEquals(1, EnemyManager.enemies.size(), "The enemies list size should be 1 after adding a Knight.");
    }

    @Test
    void testAddSlime() {
        Slime slime = mock(Slime.class);
        EnemyManager.slimes.add(slime); // Add the mock Slime to the list.

        assertTrue(EnemyManager.slimes.contains(slime), "The slimes list should contain the added Slime.");
        assertEquals(1, EnemyManager.slimes.size(), "The slimes list size should be 1 after adding a Slime.");
    }

    @Test
    void testAddPinkMonster() {
        PinkMonster pinkMonster = mock(PinkMonster.class);
        EnemyManager.pinkMonsters.add(pinkMonster); // Add the mock PinkMonster to the list.

        assertTrue(EnemyManager.pinkMonsters.contains(pinkMonster),
                "The pinkMonsters list should contain the added PinkMonster.");
        assertEquals(1, EnemyManager.pinkMonsters.size(),
                "The pinkMonsters list size should be 1 after adding a PinkMonster.");
    }

    @Test
    void testUpdateCallsSpawnEnemy() {
        float initialSpawnTimer = enemyManager.getSpawnTimer();
        float delta = initialSpawnTimer + 1; // Ensure spawnTimer will be <= 0
        enemyManager.update(delta);
        float newSpawnTimer = enemyManager.getSpawnTimer();
        assertTrue(newSpawnTimer > 0, "spawnTimer should be reset to a value greater than 0.");
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
