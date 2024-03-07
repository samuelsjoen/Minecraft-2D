package com.minecraft.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    public EnemyManager(World world, Player player) {
        this.world = world;
        this.player = player;
        this.enemies = new ArrayList<>();
        this.slimes = new ArrayList<>();
        this.pinkMonsters = new ArrayList<>();
        this.spawnTimer = MathUtils.random(spawnIntervalMin, spawnIntervalMax);
    }

    public void update(float delta) {
        spawnTimer -= delta;
        if (spawnTimer <= 0) {
            spawnEnemy();
            spawnTimer = MathUtils.random(spawnIntervalMin, spawnIntervalMax);
        }

        // Update all enemies
        ArrayList<Knight> deadEnemies = new ArrayList<>();
        ArrayList<Slime> deadSlimes = new ArrayList<>();
        ArrayList<PinkMonster> deadPinkMonsters = new ArrayList<>();
        for (Knight enemy : enemies) {
            enemy.update();
            // if (enemy.getHealth().getHealth() <= 0) {
            if (enemy.isMarkedForRemoval()) {
                world.destroyBody(enemy.getBody()); // Remove the enemy's body from the world
                deadEnemies.add(enemy); // Add dead enemies to the list
            }
        }
        for (Slime slime : slimes) {
            slime.update();
            // if (enemy.getHealth().getHealth() <= 0) {
            if (slime.isMarkedForRemoval()) {
                world.destroyBody(slime.getBody()); // Remove the enemy's body from the world
                deadSlimes.add(slime); // Add dead enemies to the list
            }
        }
        for (PinkMonster pinkMonster : pinkMonsters) {
            pinkMonster.update();
            // if (enemy.getHealth().getHealth() <= 0) {
            if (pinkMonster.isMarkedForRemoval()) {
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

    private void spawnEnemy() {
        chooseEnemy = MathUtils.random(0, 2);
        // chooseEnemy = 2;

        if (enemies.size() < 2 && chooseEnemy == 0) { // Ensures no more than 5 enemies spawns
            float spawnPosX, spawnPosY;
            spawnPosX = player.getBody().getPosition().x + MathUtils.random(-15, 15);
            spawnPosY = player.getBody().getPosition().y + MathUtils.random(2, 10);

            Knight enemy = new Knight(1, 1, world, player, spawnPosX, spawnPosY,
                    new Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH));
            enemies.add(enemy);
        }
        if (slimes.size() < 3 && chooseEnemy == 1) { // Ensures no more than 5 enemies spawns
            float spawnPosX, spawnPosY;
            spawnPosX = player.getBody().getPosition().x + MathUtils.random(-15, 15);
            spawnPosY = player.getBody().getPosition().y + MathUtils.random(2, 10);

            Slime slime = new Slime(1, 1, world, player, spawnPosX, spawnPosY,
                    new Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH));
            slimes.add(slime);
        }
        if (pinkMonsters.size() < 1 && chooseEnemy == 2) { // Ensures no more than 5 enemies spawns
            float spawnPosX, spawnPosY;
            spawnPosX = player.getBody().getPosition().x + MathUtils.random(-15, 15);
            spawnPosY = player.getBody().getPosition().y + MathUtils.random(2, 10);

            PinkMonster pinkMonster = new PinkMonster(1, 1, world, player, spawnPosX, spawnPosY,
                    new Health(Constants.ENEMY_MAX_HEALTH, Constants.ENEMY_MAX_HEALTH));
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

}
