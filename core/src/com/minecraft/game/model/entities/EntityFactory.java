package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.utils.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EntityFactory {

    private static final Map<String, Function<EntityParams, GameEntity>> entityCreators = new HashMap<>();

    static {
        // Register each type with its constructor reference
        entityCreators.put("Knight",
                (params) -> new Knight(2 * Constants.PPM, 4 * Constants.PPM, params.world, params.player,
                        params.spawnX, params.spawnY, params.health));
        entityCreators.put("Slime",
                (params) -> new Slime(2 * Constants.PPM, 2 * Constants.PPM, params.world, params.player,
                        params.spawnX, params.spawnY, params.health));
        entityCreators.put("PinkMonster",
                (params) -> new PinkMonster(2 * Constants.PPM, 4 * Constants.PPM, params.world, params.player,
                        params.spawnX, params.spawnY, params.health));
        entityCreators.put("Projectile", (params) -> {
            if (params.targetPosition == null) {
                params.targetPosition = new Vector2(params.spawnX + 10, params.spawnY + 10);
            }
            return new Projectile(50, 50, params.world, new Vector2(params.spawnX, params.spawnY),
                    params.targetPosition);
        });
    }

    public static GameEntity createEntity(String type, EntityParams params) {
        Function<EntityParams, GameEntity> creator = entityCreators.get(type);
        if (creator != null) {
            return creator.apply(params);
        } else {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }
}
