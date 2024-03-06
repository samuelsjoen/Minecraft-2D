package com.minecraft.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    public static List<Enemy> enemies;
    private World world;
    private Player player;
    private float spawnTimer;
    private final float spawnIntervalMin = 0.0f; // Minimum time between spawns
    private final float spawnIntervalMax = 5.0f; // Maximum time between spawns

    public EnemyManager(World world, Player player) {
        this.world = world;
        this.player = player;
        this.enemies = new ArrayList<>();
        this.spawnTimer = MathUtils.random(spawnIntervalMin, spawnIntervalMax);
    }

    public void update(float delta) {
        spawnTimer -= delta;
        if (spawnTimer <= 0) {
            spawnEnemy();
            spawnTimer = MathUtils.random(spawnIntervalMin, spawnIntervalMax);
        }

        // Update all enemies
        ArrayList<Enemy> deadEnemies = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemy.update();
            // if (enemy.getHealth().getHealth() <= 0) {
            if (enemy.isMarkedForRemoval()) {
                world.destroyBody(enemy.getBody()); // Remove the enemy's body from the world
                deadEnemies.add(enemy); // Add dead enemies to the list
            }
            // if (!enemy.isAlive()) {
            // world.destroyBody(enemy.getBody()); // Remove the enemy's body from the world
            // deadEnemies.add(enemy); // Add dead enemies to the list
            // }
        }
        enemies.removeAll(deadEnemies); // Remove all dead enemies from the list
    }

    public void render(SpriteBatch batch) {
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }
    }

    // private void spawnEnemy() {
    // // Spawn position relative to the player with some randomness
    // float spawnPosX = player.getBody().getPosition().x + MathUtils.random(-20,
    // 20);
    // float spawnPosY = player.getBody().getPosition().y + MathUtils.random(1, 2);
    // Enemy enemy = new Enemy(1, 1, world, player, spawnPosX, spawnPosY, new
    // Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH));
    // enemies.add(enemy);
    // }
    private void spawnEnemy() {
        if (enemies.size() < 5) { // Ensures no more than 5 enemies
            float spawnPosX, spawnPosY;
            // do {
            spawnPosX = player.getBody().getPosition().x + MathUtils.random(-10, 10);
            spawnPosY = player.getBody().getPosition().y + MathUtils.random(2, 10);
            // } while (isCollidable(spawnPosX, spawnPosY));

            Enemy enemy = new Enemy(1, 1, world, player, spawnPosX, spawnPosY,
                    new Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH));
            enemies.add(enemy);
        }
    }

    public static List<Enemy> getEnemies() {
        return enemies;
    }

}
