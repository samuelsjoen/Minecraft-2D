package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.utils.Constants;

public class EntityFactory {

    public static GameEntity createEntity(String type, EntityParams params) {
        switch (type) {
            case "Knight":
                return new Knight(2 * Constants.PPM, 4 * Constants.PPM, params.world, params.player,
                        params.spawnX, params.spawnY, params.health);
            case "Slime":
                return new Slime(2 * Constants.PPM, 2 * Constants.PPM, params.world, params.player,
                        params.spawnX, params.spawnY, params.health);
            case "PinkMonster":
                return new PinkMonster(2 * Constants.PPM, 4 * Constants.PPM, params.world, params.player,
                        params.spawnX, params.spawnY, params.health);
            case "Projectile":
                if (params.targetPosition == null) {
                    params.targetPosition = new Vector2(params.spawnX + 10, params.spawnY + 10); // Default behavior
                }
                return new Projectile(50, 50, params.world, new Vector2(params.spawnX, params.spawnY),
                        params.targetPosition);
            default:
                throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }
}
