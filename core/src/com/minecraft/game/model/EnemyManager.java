package com.minecraft.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.entities.EntityFactory;
import com.minecraft.game.model.entities.EntityParams;
import com.minecraft.game.model.entities.GameEntity;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Projectile;
import com.minecraft.game.model.entities.Slime;
// import com.minecraft.game.model.map.TileType;
import com.minecraft.game.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemyManager {
    public static List<Knight> knights = new ArrayList<>();
    public static List<Slime> slimes = new ArrayList<>();
    public static List<PinkMonster> pinkMonsters = new ArrayList<>();
    public static List<Projectile> projectiles = new ArrayList<>();

    private World world;
    private Player player;
    private float spawnTimer;
    private int chooseEnemy;
    private final float spawnIntervalMin = 1.0f; // Minimum time between spawns
    private final float spawnIntervalMax = 5.0f; // Maximum time between spawns
    // private final float spawnIntervalMin = 0.0f; // Minimum time between spawns
    // private final float spawnIntervalMax = 0.0f; // Maximum time between spawns
    private TiledMap tiledMap;
    private DayNightCycle dayNightCycle;

    public EnemyManager(World world, Player player, TiledMap tiledMap, DayNightCycle dayNightCycle) {
        this.world = world;
        this.player = player;
        // this.spawnTimer = MathUtils.random(spawnIntervalMin, spawnIntervalMax);
        this.tiledMap = tiledMap;
        this.dayNightCycle = dayNightCycle;
        killAllEntities();
    }

    public void update(float delta) {
        spawnTimer -= delta;
        if (spawnTimer <= 0 && dayNightCycle.getIsNight()) {
            spawnEnemy();
            spawnTimer = MathUtils.random(spawnIntervalMin, spawnIntervalMax);
        }
        float deathThreshold = -10.0f;
        removeDeadEnemies(deathThreshold);
        handleProjectileCollisions(delta);
    }

    public static void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    private void handleProjectileCollisions(float delta) {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update(delta);
            projectile.checkCollisionWithPlayer(player);
            if (projectile.isMarkedForRemoval()) {
                projectile.dispose();
                iterator.remove();
            }
        }
    }

    private void removeDeadEnemies(float deathThreshold) {
        removeDeadEntities(knights, deathThreshold);
        removeDeadEntities(slimes, deathThreshold);
        removeDeadEntities(pinkMonsters, deathThreshold);
    }

    private <T extends GameEntity> void removeDeadEntities(List<T> entities, float deathThreshold) {
        List<T> deadEntities = new ArrayList<>();
        for (T entity : entities) {
            entity.update(Gdx.graphics.getDeltaTime());
            if (entity.getBody().getPosition().y < deathThreshold || entity.isMarkedForRemoval()) {
                entity.dispose();
                deadEntities.add(entity);
            }
        }
        entities.removeAll(deadEntities);
    }

    public boolean isSpawnLocationValid(float spawnPosX, float spawnPosY) {
        int tileX = (int) ((spawnPosX * Constants.PPM) / Constants.TILE_SIZE);
        int tileY = (int) ((spawnPosY * Constants.PPM) / Constants.TILE_SIZE);

        TiledMapTileLayer mineableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        Cell cell = mineableLayer.getCell(tileX, tileY - 1);
        Cell aboveCell1 = mineableLayer.getCell(tileX, tileY);
        Cell aboveCell2 = mineableLayer.getCell(tileX, tileY + 1);

        boolean isAboveEmpty = (aboveCell1 == null || aboveCell1.getTile() == null)
                && (aboveCell2 == null || aboveCell2.getTile() == null);
        // boolean isAboveEmpty = (aboveCell1 == null || aboveCell1.getTile() == null)
        // &&
        // (aboveCell2 == null || aboveCell2.getTile() == null);

        // -----------------------------------------------------
        // I WANT TO USE THIS BUT IT DOESNT WORK WITH TESTS SO ITS COMMENTED OUT UNTIL
        // WE FIND A SOLUTION
        //
        // THIS MAKES SURE THAT ENEMIES WILL ONLY SPAWN ON COLLIDABLE CELLS.
        // THIS MEANS THAT THEY WONT SPAWN ON A TREE OR LEAFS
        //
        // if (cell != null && cell.getTile() != null &&
        // TileType.getTileTypeWithId(cell.getTile().getId()).isCollidable()
        // && isAboveEmpty) {
        //
        // ------------------------------------------------------
        if (cell != null && isAboveEmpty) {

            // if (cell != null && isAboveEmpty) {
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
        // Check if the size of each enemy list is already maxed out
        boolean maxKnightsReached = knights.size() >= 2;
        boolean maxSlimesReached = slimes.size() >= 3;
        boolean maxPinkMonstersReached = pinkMonsters.size() >= 1;

        // If any list has reached its maximum size, return without spawning a new enemy
        if (maxKnightsReached && maxSlimesReached && maxPinkMonstersReached) {
            return;
        }

        chooseEnemy = MathUtils.random(0, 2); // Choosing a random enemy
        // chooseEnemy = 2; // Always picking Pinkmonster
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
        EntityParams params = new EntityParams(world, player, spawnPosX * Constants.PPM,
                (spawnPosY + 2) * Constants.PPM,
                new Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH));

        GameEntity entity = null;
        if (chooseEnemy == 0 && knights.size() < 2) {
            entity = EntityFactory.createEntity("Knight", params);
            if (entity != null)
                knights.add((Knight) entity);
        } else if (chooseEnemy == 1 && slimes.size() < 3) {
            entity = EntityFactory.createEntity("Slime", params);
            if (entity != null)
                slimes.add((Slime) entity);
        } else if (chooseEnemy == 2 && pinkMonsters.size() < 1) {
            entity = EntityFactory.createEntity("PinkMonster", params);
            if (entity != null)
                pinkMonsters.add((PinkMonster) entity);
        }
    }

    public static void killAllEntities() {
        // Mark all knights for removal
        for (Knight knight : knights) {
            knight.setMarkedForRemoval();
        }
        knights.clear(); // Clear the knights list

        // Mark all slimes for removal
        for (Slime slime : slimes) {
            slime.setMarkedForRemoval();
        }
        slimes.clear(); // Clear the slimes list

        // Mark all pink monsters for removal
        for (PinkMonster pinkMonster : pinkMonsters) {
            pinkMonster.setMarkedForRemoval();
        }
        pinkMonsters.clear(); // Clear the pink monsters list

        // Mark all projectiles for removal
        for (Projectile projectile : projectiles) {
            projectile.setMarkedForRemoval();
        }
        projectiles.clear(); // Clear the projectiles list
    }

    public static List<Knight> getEnemies() {
        return knights;
    }

    public static List<Slime> getSlimes() {
        return slimes;
    }

    public static List<PinkMonster> getPinkMonsters() {
        return pinkMonsters;
    }

    public static List<Projectile> getProjectiles() {
        return projectiles;
    }

    public int getChooseEnemy() {
        return chooseEnemy;
    }

    public float getSpawnTimer() {
        return spawnTimer;
    }

}
