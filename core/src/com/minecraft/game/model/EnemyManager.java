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
import com.minecraft.game.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages all enemy-related activities within the game, including spawning,
 * updating, and removing enemies based on game rules and player interactions.
 */
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
    private TiledMap tiledMap;
    private DayNightCycle dayNightCycle;
    private EntityFactory entityFactory;
    private Score score;

    /**
     * Constructs an EnemyManager with necessary game elements like the game world,
     * player, and game map.
     *
     * @param world         The game world where physics entities exist.
     * @param player        The player to interact with enemies.
     * @param tiledMap      The tiled map containing the game's layout and
     *                      boundaries.
     * @param dayNightCycle The game's day-night cycle affecting enemy behavior.
     */
    public EnemyManager(World world, Player player, TiledMap tiledMap, DayNightCycle dayNightCycle, Score score) {
        this.world = world;
        this.player = player;
        this.tiledMap = tiledMap;
        this.dayNightCycle = dayNightCycle;
        this.score = score;
        killAllEntities(); // Killing all the entities will add points to the score
        removeDeadEnemies(-10.0f); // We now need to remove the enemies that was marked for removal.
        score.resetScore(); // reset score and set it to 0
        entityFactory = new EntityFactory();
    }

    /**
     * Updates the state of all managed enemies and handles their spawning and
     * removal based on game logic.
     *
     * @param delta The time elapsed since the last frame, used for updates.
     */
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

    /**
     * Adds a projectile to the game.
     *
     * @param projectile The projectile to be added.
     */
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
                if (entity instanceof Knight) {
                    score.addPoints(30); // 30 points for Knight
                } else if (entity instanceof Slime) {
                    score.addPoints(10); // 10 points for Slime
                } else if (entity instanceof PinkMonster) {
                    score.addPoints(50); // 50 points for PinkMonster
                }
            }
        }
        entities.removeAll(deadEntities);
    }

    /**
     * Checks if the proposed spawn location is valid based on the tile map and
     * proximity to the player. It ensures that enemies do not spawn in blocked
     * areas or too close to the player.
     *
     * @param spawnPosX The x-coordinate for the potential spawn location.
     * @param spawnPosY The y-coordinate for the potential spawn location.
     * @return true if the location is valid for spawning an enemy, false otherwise.
     */
    public boolean isSpawnLocationValid(float spawnPosX, float spawnPosY) {
        int tileX = (int) ((spawnPosX * Constants.PPM) / Constants.TILE_SIZE);
        int tileY = (int) ((spawnPosY * Constants.PPM) / Constants.TILE_SIZE);

        TiledMapTileLayer mineableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        Cell cell = mineableLayer.getCell(tileX, tileY - 1);
        Cell aboveCell1 = mineableLayer.getCell(tileX, tileY);
        Cell aboveCell2 = mineableLayer.getCell(tileX, tileY + 1);

        boolean isAboveEmpty = (aboveCell1 == null || aboveCell1.getTile() == null)
                && (aboveCell2 == null || aboveCell2.getTile() == null);
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

    /**
     * Initiates the process of spawning an enemy if the conditions allow it, such
     * as it being nighttime and having available capacity in the enemy lists.
     */
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
            chooseEnemy = 4;
            Gdx.app.log("EnemySpawn", "Failed to find valid spawn location after 100 attempts.");
        }
    }

    /**
     * Handles the decision-making process of what type of enemy to spawn based on
     * a random choice and current game state conditions.
     *
     * @param spawnPosX The x-coordinate where the enemy should spawn.
     * @param spawnPosY The y-coordinate where the enemy should spawn.
     */
    private void spawnEntityBasedOnChoice(float spawnPosX, float spawnPosY) {
        EntityParams params = new EntityParams(world, player, spawnPosX * Constants.PPM,
                (spawnPosY + 2) * Constants.PPM,
                new Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH));

        GameEntity entity = null;
        if (chooseEnemy == 0 && knights.size() < 2) {
            entity = entityFactory.create("Knight", params);
            if (entity != null)
                knights.add((Knight) entity);
        } else if (chooseEnemy == 1 && slimes.size() < 3) {
            entity = entityFactory.create("Slime", params);
            if (entity != null)
                slimes.add((Slime) entity);
        } else if (chooseEnemy == 2 && pinkMonsters.size() < 1) {
            entity = entityFactory.create("PinkMonster", params);
            if (entity != null)
                pinkMonsters.add((PinkMonster) entity);
        }
    }

    /**
     * Marks all existing entities for removal. This method is typically called
     * during game resets or cleanup phases.
     */
    public static void killAllEntities() {
        // Mark all knights for removal
        for (Knight knight : knights) {
            knight.setMarkedForRemoval();
        }
        // Mark all slimes for removal
        for (Slime slime : slimes) {
            slime.setMarkedForRemoval();
        }
        // Mark all pink monsters for removal
        for (PinkMonster pinkMonster : pinkMonsters) {
            pinkMonster.setMarkedForRemoval();
        }
        // Mark all projectiles for removal
        for (Projectile projectile : projectiles) {
            projectile.setMarkedForRemoval();
        }
    }

    /**
     * Retrieves the list of currently active knights.
     *
     * @return A list of active knights.
     */
    public static List<Knight> getEnemies() {
        return knights;
    }

    /**
     * Retrieves the list of currently active slimes.
     *
     * @return A list of active slimes.
     */
    public static List<Slime> getSlimes() {
        return slimes;
    }

    /**
     * Retrieves the list of currently active pink monsters.
     *
     * @return A list of active pink monsters.
     */
    public static List<PinkMonster> getPinkMonsters() {
        return pinkMonsters;
    }

    /**
     * Retrieves the list of currently active projectiles.
     *
     * @return A list of active projectiles.
     */
    public static List<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     * Gets the current choice index for enemy spawning.
     *
     * @return The index corresponding to the type of enemy chosen to spawn next.
     */
    public int getChooseEnemy() {
        return chooseEnemy;
    }

    /**
     * Gets the current countdown timer until the next spawn attempt.
     *
     * @return The current spawn timer value.
     */
    public float getSpawnTimer() {
        return spawnTimer;
    }

}
