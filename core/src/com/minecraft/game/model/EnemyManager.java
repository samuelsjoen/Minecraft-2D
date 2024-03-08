package com.minecraft.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    public static List<Knight> enemies;
    public static List<Slime> slimes;
    public static List<PinkMonster> pinkMonsters;
    private World world;
    private Player player;
    private float spawnTimer;
    private float chooseEnemy;
    private final float spawnIntervalMin = 1.0f; // Minimum time between spawns
    private final float spawnIntervalMax = 1.0f; // Maximum time between spawns
    private TiledMap tiledMap;

    public EnemyManager(World world, Player player, TiledMap tiledMap) {
        this.world = world;
        this.player = player;
        EnemyManager.enemies = new ArrayList<>();
        EnemyManager.slimes = new ArrayList<>();
        EnemyManager.pinkMonsters = new ArrayList<>();
        this.spawnTimer = MathUtils.random(spawnIntervalMin, spawnIntervalMax);
        this.tiledMap = tiledMap;
    }

    public void update(float delta) {
        spawnTimer -= delta;
        if (spawnTimer <= 0) {
            spawnEnemy();
            spawnTimer = MathUtils.random(spawnIntervalMin, spawnIntervalMax);
        }
        // Y-coordinate threshold below which enemies should die
        float deathThreshold = -10.0f;

        // Update all enemies
        ArrayList<Knight> deadEnemies = new ArrayList<>();
        ArrayList<Slime> deadSlimes = new ArrayList<>();
        ArrayList<PinkMonster> deadPinkMonsters = new ArrayList<>();
        for (Knight enemy : enemies) {
            enemy.update();
            if (enemy.getBody().getPosition().y < deathThreshold || enemy.isMarkedForRemoval()
                    || Gdx.input.isKeyJustPressed(Keys.N)) {
                world.destroyBody(enemy.getBody()); // Remove the enemy's body from the world
                deadEnemies.add(enemy); // Add dead enemies to the list
            }
        }
        for (Slime slime : slimes) {
            slime.update();
            if (slime.getBody().getPosition().y < deathThreshold || slime.isMarkedForRemoval()
                    || Gdx.input.isKeyJustPressed(Keys.N)) {
                world.destroyBody(slime.getBody()); // Remove the enemy's body from the world
                deadSlimes.add(slime); // Add dead enemies to the list
            }
        }
        for (PinkMonster pinkMonster : pinkMonsters) {
            pinkMonster.update();
            if (pinkMonster.getBody().getPosition().y < deathThreshold || pinkMonster.isMarkedForRemoval()
                    || Gdx.input.isKeyJustPressed(Keys.N)) {
                world.destroyBody(pinkMonster.getBody()); // Remove the enemy's body from the world
                deadPinkMonsters.add(pinkMonster); // Add dead enemies to the list
            }
        }
        enemies.removeAll(deadEnemies); // Remove all dead enemies from the list
        slimes.removeAll(deadSlimes); // Remove all dead enemies from the list
        pinkMonsters.removeAll(deadPinkMonsters); // Remove all dead enemies from the list
    }

    public void render(SpriteBatch batch) {
        for (Knight enemy : enemies) {
            enemy.render(batch);
        }
        for (Slime slime : slimes) {
            slime.render(batch);
        }
        for (PinkMonster pinkMonster : pinkMonsters) {
            pinkMonster.render(batch);
        }
    }

    private boolean isSpawnLocationValid(float spawnPosX, float spawnPosY, boolean isTwoTilesHigh) {
        // Convert world coordinates to PPM
        int bottomTileX = (int) ((spawnPosX * Constants.PPM) / Constants.TILE_SIZE);
        int bottomTileY = (int) ((spawnPosY * Constants.PPM) / Constants.TILE_SIZE);

        // Check the bottom tile
        TiledMapTileLayer mineableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        Cell bottomCell = mineableLayer.getCell(bottomTileX, bottomTileY);
        if (bottomCell != null) {
            return false; // The bottom part is on a "mineable" tile, not a valid location
        }

        // If the enemy is two tiles high, also check the tile above
        if (isTwoTilesHigh) {
            Cell topCell = mineableLayer.getCell(bottomTileX, bottomTileY + 1);
            if (topCell != null) {
                return false; // The top part is on a "mineable" tile, also not a valid location
            }
        }

        return true; // Both parts (or just the bottom part for one-tile-high enemies) are not on
                     // "mineable" tiles
    }

    private void spawnEnemy() {
        chooseEnemy = MathUtils.random(0, 2); // chosing a random enemy
        // chooseEnemy = 2;
        // float spawnPosX, spawnPosY;
        int attempts = 0;
        boolean validLocationFound = false;
        float spawnPosX = 0, spawnPosY = 0;
        boolean isTwoTilesHigh = chooseEnemy == 0 || chooseEnemy == 2;

        while (attempts < 100 && !validLocationFound) {

            float offsetX = getRandomSpawnOffset(-20, 20, 3);
            float offsetY = getRandomSpawnOffset(-20, 20, 5);
            // Calculate spawn position based on the player position and offsets
            spawnPosX = player.getBody().getPosition().x + offsetX;
            spawnPosY = player.getBody().getPosition().y + offsetY;

            if (isSpawnLocationValid(spawnPosX, spawnPosY, isTwoTilesHigh)) {
                validLocationFound = true;
            }

            attempts++;
        }

        if (validLocationFound) {
            if (enemies.size() < 2 && chooseEnemy == 0) {
                Knight enemy = new Knight(1, 1, world, player, spawnPosX, spawnPosY,
                        new Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH));
                enemies.add(enemy);
            }
            if (slimes.size() < 3 && chooseEnemy == 1) {
                Slime slime = new Slime(1, 1, world, player, spawnPosX, spawnPosY,
                        new Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH));
                slimes.add(slime);
            }
            if (pinkMonsters.size() < 1 && chooseEnemy == 2) {
                PinkMonster pinkMonster = new PinkMonster(1, 1, world, player, spawnPosX, spawnPosY,
                        new Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH));
                pinkMonsters.add(pinkMonster);
            }
        } else {
            // Print out the case where no valid location was found after x attempts
            Gdx.app.log("EnemySpawn", "Failed to find valid spawn location after " + attempts + " attempts.");
        }

    }

    /**
     * Generates a random spawn offset ensuring it's not within the specified
     * exclusion range.
     * 
     * @param min            The minimum offset value.
     * @param max            The maximum offset value.
     * @param exclusionRange The range around zero to exclude.
     * @return A random offset value within the specified constraints.
     */
    private float getRandomSpawnOffset(float min, float max, float exclusionRange) {
        float offset = MathUtils.random(min, max);
        // Adjust the offset if it's within the exclusion range
        if (offset > -exclusionRange && offset < exclusionRange) {
            if (MathUtils.randomBoolean()) {
                offset = exclusionRange + (offset % exclusionRange);
            } else {
                offset = -exclusionRange - (offset % exclusionRange);
            }
        }
        return offset;
    }

    public static List<Knight> getEnemies() {
        return enemies;
    }

    public static List<Slime> getSlimes() {
        return slimes;
    }

    public static List<PinkMonster> getPinkMonsters() {
        return pinkMonsters;
    }

}
