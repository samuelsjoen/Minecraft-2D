package com.minecraft.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    private List<Enemy> enemies;
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
        for (Enemy enemy : enemies) {
            enemy.update();
        }
    }

    public void render(SpriteBatch batch) {
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }
    }

    private void spawnEnemy() {
        // Spawn position relative to the player with some randomness
        float spawnPosX = player.getBody().getPosition().x + MathUtils.random(-20, 20);
        float spawnPosY = player.getBody().getPosition().y + MathUtils.random(1, 2);
        Enemy enemy = new Enemy(1, 1, world, player, spawnPosX, spawnPosY);
        enemies.add(enemy);
    }
}
