package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.utils.Constants;

public class EntityFactory {

    public static GameEntity createEntity(String type, EntityParams params) {
        if ("Knight".equals(type)) {
            return new Knight(2 * Constants.PPM, 4 * Constants.PPM, params.world, params.player,
                    params.spawnX, params.spawnY, params.health);
        } else if ("Slime".equals(type)) {
            return new Slime(2 * Constants.PPM, 2 * Constants.PPM, params.world, params.player,
                    params.spawnX, params.spawnY, params.health);
        } else if ("PinkMonster".equals(type)) {
            return new PinkMonster(2 * Constants.PPM, 4 * Constants.PPM, params.world, params.player,
                    params.spawnX, params.spawnY, params.health);
        } else if ("Projectile".equals(type)) {
            if (params.targetPosition == null) {
                params.targetPosition = new Vector2(params.spawnX + 10, params.spawnY + 10); // Default behavior
            }
            return new Projectile(50, 50, params.world, new Vector2(params.spawnX, params.spawnY),
                    params.targetPosition);
        } else {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }
}