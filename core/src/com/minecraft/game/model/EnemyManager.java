package com.minecraft.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Slime;
import com.minecraft.game.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    public static List<Knight> enemies = new ArrayList<>();
    public static List<Slime> slimes = new ArrayList<>();
    public static List<PinkMonster> pinkMonsters = new ArrayList<>();
    private World world;
    private Player player;
    private float spawnTimer;
    private float chooseEnemy;
    private final float spawnIntervalMin = 1.0f; // Minimum time between spawns
    private final float spawnIntervalMax = 1.0f; // Maximum time between spawns
    private TiledMap tiledMap;
    private DayNightCycle dayNightCycle;

    public EnemyManager(World world, Player player, TiledMap tiledMap, DayNightCycle dayNightCycle) {
        this.world = world;
        this.player = player;
        this.spawnTimer = MathUtils.random(spawnIntervalMin, spawnIntervalMax);
        this.tiledMap = tiledMap;
        this.dayNightCycle = dayNightCycle;
    }

    public void update(float delta) {
        spawnTimer -= delta;
        if (spawnTimer <= 0 && dayNightCycle.getIsNight()) {
            spawnEnemy();
            spawnTimer = MathUtils.random(spawnIntervalMin, spawnIntervalMax);
        }
        float deathThreshold = -10.0f;
        removeDeadEnemies(deathThreshold);
    }

    private void removeDeadEnemies(float deathThreshold) {
        ArrayList<Knight> deadEnemies = new ArrayList<>();
        ArrayList<Slime> deadSlimes = new ArrayList<>();
        ArrayList<PinkMonster> deadPinkMonsters = new ArrayList<>();
        for (Knight enemy : enemies) {
            enemy.update(Gdx.graphics.getDeltaTime());
            if (enemy.getBody().getPosition().y < deathThreshold || enemy.isMarkedForRemoval()) {
                world.destroyBody(enemy.getBody()); // Remove the enemy's body from the world
                deadEnemies.add(enemy); // Add dead enemies to the list
            }
        }
        for (Slime slime : slimes) {
            slime.update(Gdx.graphics.getDeltaTime());
            if (slime.getBody().getPosition().y < deathThreshold || slime.isMarkedForRemoval()) {
                world.destroyBody(slime.getBody()); // Remove the enemy's body from the world
                deadSlimes.add(slime); // Add dead enemies to the list
            }
        }
        for (PinkMonster pinkMonster : pinkMonsters) {
            pinkMonster.update(Gdx.graphics.getDeltaTime());
            if (pinkMonster.getBody().getPosition().y < deathThreshold || pinkMonster.isMarkedForRemoval()) {
                world.destroyBody(pinkMonster.getBody()); // Remove the enemy's body from the world
                deadPinkMonsters.add(pinkMonster); // Add dead enemies to the list
            }
        }
        enemies.removeAll(deadEnemies); // Remove all dead enemies from the list
        slimes.removeAll(deadSlimes); // Remove all dead enemies from the list
        pinkMonsters.removeAll(deadPinkMonsters); // Remove all dead enemies from the list
    }

    public boolean isSpawnLocationValid(float spawnPosX, float spawnPosY) {
        int tileX = (int) ((spawnPosX * Constants.PPM) / Constants.TILE_SIZE);
        int tileY = (int) ((spawnPosY * Constants.PPM) / Constants.TILE_SIZE);

        TiledMapTileLayer mineableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        Cell cell = mineableLayer.getCell(tileX, tileY);
        Cell aboveCell1 = mineableLayer.getCell(tileX, tileY + 1);
        Cell aboveCell2 = mineableLayer.getCell(tileX, tileY + 2);

        boolean isAboveEmpty = (aboveCell1 == null || aboveCell1.getTile() == null) &&
                (aboveCell2 == null || aboveCell2.getTile() == null);

        if (cell != null && isAboveEmpty) {
            float playerPosX = player.getBody().getPosition().x;
            float playerPosY = player.getBody().getPosition().y;

            boolean isWithinSpawnRangeX = Math.abs(playerPosX - spawnPosX) > 6 && Math.abs(playerPosX - spawnPosX) < 20;
            boolean isWithinSpawnRangeY = Math.abs(playerPosY - spawnPosY) >= 0
                    && Math.abs(playerPosY - spawnPosY) < 20;

            return isWithinSpawnRangeX && isWithinSpawnRangeY;
        }
        return false;
    }

    public void spawnEnemy() {
        chooseEnemy = MathUtils.random(0, 2); // Choosing a random enemy
        boolean validLocationFound = false;
        float spawnPosX = 0, spawnPosY = 0;

        for (int attempts = 0; attempts < 100 && !validLocationFound; attempts++) {
            spawnPosX = MathUtils.random(player.getBody().getPosition().x - 20, player.getBody().getPosition().x + 20);
            spawnPosY = MathUtils.random(player.getBody().getPosition().y - 20, player.getBody().getPosition().y + 20);

            if (isSpawnLocationValid(spawnPosX, spawnPosY)) {
                validLocationFound = true;
            }
        }

        if (validLocationFound) {
            spawnEntityBasedOnChoice(spawnPosX, spawnPosY);
        } else {
            Gdx.app.log("EnemySpawn", "Failed to find valid spawn location after 100 attempts.");
        }
    }

    private void spawnEntityBasedOnChoice(float spawnPosX, float spawnPosY) {
        if (chooseEnemy == 0 && enemies.size() < 2) {
            Knight enemy = new Knight(2 * Constants.PPM, 4 * Constants.PPM, world, player,
                    spawnPosX * Constants.PPM, (spawnPosY + 2) * Constants.PPM,
                    new Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH, null));
            enemies.add(enemy);
        } else if (chooseEnemy == 1 && slimes.size() < 3) {
            Slime slime = new Slime(2 * Constants.PPM, 2 * Constants.PPM, world, player, spawnPosX * Constants.PPM,
                    (spawnPosY + 2) * Constants.PPM,
                    new Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH, null));
            slimes.add(slime);
        } else if (chooseEnemy == 2 && pinkMonsters.size() < 1) {
            PinkMonster pinkMonster = new PinkMonster(2 * Constants.PPM, 4 * Constants.PPM, world, player,
                    spawnPosX * Constants.PPM, (spawnPosY + 2) * Constants.PPM,
                    new Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH, null));
            pinkMonsters.add(pinkMonster);
        }
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

    public float getChooseEnemy() {
        return chooseEnemy;
    }

    public float getSpawnTimer() {
        return spawnTimer;
    }

}
