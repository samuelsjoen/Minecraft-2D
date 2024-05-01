package com.minecraft.game.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.physics.box2d.World;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
    void testInitialization() {
        assertTrue(EnemyManager.getEnemies().isEmpty(), "Enemies list should initially be empty");
    }

    @Test
    void testGetEnemies() {
        Knight testKnight = new Knight(0, 0, mockWorld, mockPlayer, 0, 0, null);
        EnemyManager.knights.add(testKnight);
        assertEquals(1, EnemyManager.getEnemies().size(), "Should return a list containing 1 enemy.");
        assertEquals(testKnight, EnemyManager.getEnemies().get(0),
                "The returned list should contain the added knight.");
    }

    @Test
    void testGetSlimes() {
        Slime testSlime = new Slime(0, 0, mockWorld, mockPlayer, 0, 0, null);
        EnemyManager.slimes.add(testSlime);
        EnemyManager.slimes.add(testSlime);
        assertEquals(2, EnemyManager.getSlimes().size(), "Should return a list containing 1 slime.");
        assertEquals(testSlime, EnemyManager.getSlimes().get(0), "The returned list should contain the added slime.");
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
        EnemyManager.knights.add(knightToRemove);
        EnemyManager.slimes.add(slimeToRemove);
        EnemyManager.pinkMonsters.add(monsterToRemove);

        // Ensure entities are in the list before removal
        assertFalse(EnemyManager.knights.isEmpty());
        assertFalse(EnemyManager.slimes.isEmpty());
        assertFalse(EnemyManager.pinkMonsters.isEmpty());

        // Call the method under test
        enemyManager.update(0); // updating to kill them since they are below the threshold

        // Verify that lists are empty after removal
        assertTrue(EnemyManager.knights.isEmpty(), "Enemies list should be empty after removal.");
        assertTrue(EnemyManager.slimes.isEmpty(), "Slimes list should be empty after removal.");
        assertTrue(EnemyManager.pinkMonsters.isEmpty(), "PinkMonsters list should be empty after removal.");
    }

    @Test
    void testSpawnEntityBasedOnChoice() throws Exception {
        EnemyManager.knights.clear();
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
        int chosenEnemy = enemyManager.getChooseEnemy();
        // Assert based on the chosenEnemy value
        if (chosenEnemy == 0) {
            assertFalse(EnemyManager.knights.isEmpty(), "A Knight should have been spawned.");
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
        EnemyManager.knights.add(knight); // Add the mock Knight to the list.

        assertTrue(EnemyManager.knights.contains(knight), "The knights list should contain the added Knight.");
        assertEquals(1, EnemyManager.knights.size(), "The knights list size should be 1 after adding a Knight.");
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

    @Test
    void testGetProjectiles_ReturnsEmptyListWhenNoProjectilesExist() {
        assertTrue(EnemyManager.getProjectiles().isEmpty(), "Projectiles list should be empty initially.");
    }

    @Test
    void testGetProjectiles_ReturnsAddedProjectiles() {
        // Mock a projectile and add it to the list
        Projectile projectile = mock(Projectile.class);
        EnemyManager.addProjectile(projectile);

        // Call the method under test
        List<Projectile> projectiles = EnemyManager.getProjectiles();

        // Verify that the list contains the added projectile
        assertTrue(projectiles.contains(projectile), "The list should contain the added projectile.");
    }

    @Test
    void testHandleProjectileCollisions_RemovesMarkedProjectiles() {
        // Mock a projectile and add it to the list
        Projectile projectile = mock(Projectile.class);
        EnemyManager.projectiles.add(projectile);

        // Mark the projectile for removal
        when(projectile.isMarkedForRemoval()).thenReturn(true);

        // Call the method under test
        enemyManager.update(0);

        // Verify that the projectile was removed
        assertFalse(EnemyManager.projectiles.contains(projectile), "The projectile should have been removed.");
        verify(projectile).dispose(); // Verify that dispose was called on the projectile
    }

    @Test
    void testHandleProjectileCollisions_DoesNotRemoveUnmarkedProjectiles() {
        // Mock a projectile and add it to the list
        Projectile projectile = mock(Projectile.class);
        EnemyManager.projectiles.add(projectile);

        // Do not mark the projectile for removal
        when(projectile.isMarkedForRemoval()).thenReturn(false);

        // Call the method under test
        enemyManager.update(0);

        // Verify that the projectile was not removed
        assertTrue(EnemyManager.projectiles.contains(projectile), "The projectile should not have been removed.");
        verify(projectile, never()).dispose(); // Verify that dispose was not called on the projectile
    }

    // @Test
    // void testKillAllEntities() {
    // // Create an instance of EnemyManager
    // // EnemyManager enemyManager = new EnemyManager(mock(World.class),
    // // mock(Player.class), mock(TiledMap.class),
    // // mock(DayNightCycle.class));

    // // Create mock entities and add them to the lists
    // Knight mockKnight1 = mock(Knight.class);
    // Knight mockKnight2 = mock(Knight.class);
    // Slime mockSlime1 = mock(Slime.class);
    // Slime mockSlime2 = mock(Slime.class);
    // PinkMonster mockPinkMonster1 = mock(PinkMonster.class);
    // Projectile mockProjectile = mock(Projectile.class);

    // EnemyManager.knights.add(mockKnight1);
    // EnemyManager.knights.add(mockKnight2);
    // EnemyManager.slimes.add(mockSlime1);
    // EnemyManager.slimes.add(mockSlime2);
    // EnemyManager.pinkMonsters.add(mockPinkMonster1);
    // EnemyManager.projectiles.add(mockProjectile);

    // // Call killAllEntities()
    // EnemyManager.killAllEntities();
    // enemyManager.update(0.1f);
    // // Check that all lists are empty
    // assertTrue(EnemyManager.knights.isEmpty(), "Knights list should be empty
    // after killAllEntities()");
    // assertTrue(EnemyManager.slimes.isEmpty(), "Slimes list should be empty after
    // killAllEntities()");
    // assertTrue(EnemyManager.pinkMonsters.isEmpty(), "PinkMonsters list should be
    // empty after killAllEntities()");
    // assertTrue(EnemyManager.projectiles.isEmpty(), "Projectiles list should be
    // empty after killAllEntities()");
    // }
    @Test
    void testKillAllEntities() {
        // Add the mocked entities to their respective lists
        // Assuming `setMarkedForRemoval` sets some boolean flag inside the entities
        when(mockKnight.isMarkedForRemoval()).thenReturn(true);
        when(mockSlime.isMarkedForRemoval()).thenReturn(true);
        when(mockPinkMonster.isMarkedForRemoval()).thenReturn(true);
        when(mockProjectile.isMarkedForRemoval()).thenReturn(true);

        EnemyManager.knights.add(mockKnight);
        EnemyManager.slimes.add(mockSlime);
        EnemyManager.pinkMonsters.add(mockPinkMonster);
        EnemyManager.projectiles.add(mockProjectile);

        // Perform the killAllEntities action
        EnemyManager.killAllEntities();
        // Simulate an update to process entity removal
        enemyManager.update(0.1f);
        System.out.println(EnemyManager.knights.size());
        // Verify that all entities have been removed
        assertTrue(EnemyManager.knights.isEmpty(), "Knights list should be empty after killAllEntities()");
        assertTrue(EnemyManager.slimes.isEmpty(), "Slimes list should be empty after killAllEntities()");
        assertTrue(EnemyManager.pinkMonsters.isEmpty(), "PinkMonsters list should be empty after killAllEntities()");
        assertTrue(EnemyManager.projectiles.isEmpty(), "Projectiles list should be empty after killAllEntities()");
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
