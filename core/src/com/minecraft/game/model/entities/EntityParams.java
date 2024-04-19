package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Player;

public class EntityParams {
    public World world;
    public Player player;
    public float spawnX;
    public float spawnY;
    public Health health;
    public Vector2 targetPosition; // Optional for projectile targeting

    public EntityParams(World world, Player player, float spawnX, float spawnY, Health health) {
        this.world = world;
        this.player = player;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.health = health;
    }

    public void setTargetPosition(float targetX, float targetY) {
        this.targetPosition = new Vector2(targetX, targetY);
    }

}
