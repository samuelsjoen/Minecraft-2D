package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.Factory;
import com.minecraft.game.utils.Constants;
import java.util.function.Function;

/**
 * A factory for creating entities.
 */
public class EntityFactory extends Factory<GameEntity, EntityParams> {

    /**
     * Constructs an EntityFactory and registers entity creators.
     */
    public EntityFactory() {
        super();
        registerCreators();
    }

    /**
     * Registers entity creators for each entity type.
     */
    private void registerCreators() {
        register("Knight",
                (params) -> new Knight(2 * Constants.PPM, 4 * Constants.PPM, params.world, params.player,
                        params.spawnX, params.spawnY, params.health));
        register("Slime",
                (params) -> new Slime(2 * Constants.PPM, 2 * Constants.PPM, params.world, params.player,
                        params.spawnX, params.spawnY, params.health));
        register("PinkMonster",
                (params) -> new PinkMonster(2 * Constants.PPM, 4 * Constants.PPM, params.world, params.player,
                        params.spawnX, params.spawnY, params.health));
        register("Projectile", (params) -> {
            if (params.targetPosition == null) {
                params.targetPosition = new Vector2(params.spawnX + 10, params.spawnY + 10);
            }
            return new Projectile(50, 50, params.world, new Vector2(params.spawnX, params.spawnY),
                    params.targetPosition);
        });
    }

    @Override
    public GameEntity create(String type, EntityParams params) {
        Function<EntityParams, GameEntity> creator = creators.get(type);
        if (creator != null) {
            return creator.apply(params);
        } else {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }
}