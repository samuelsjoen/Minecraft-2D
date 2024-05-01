package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.utils.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Factory class for creating game entities.
 * This class uses a functional programming style to map entity types to their
 * respective constructors,
 * allowing for flexible creation of various game entity types based on runtime
 * parameters.
 *
 * <p>
 * Entity creation is centralized in this factory to decouple the instantiation
 * logic and to facilitate
 * easy management of entity creation throughout the game.
 * </p>
 */

public class EntityFactory {
    /**
     * Maps entity type names to their corresponding constructor functions.
     */

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

    /**
     * Creates a game entity of the specified type with the provided parameters.
     *
     * @param type   the type of the entity to create, as a string.
     * @param params the parameters necessary for creating the entity, encapsulated
     *               in an {@link EntityParams} object.
     * @return the created {@link GameEntity} instance.
     * @throws IllegalArgumentException if the entity type is unknown.
     */
    public static GameEntity createEntity(String type, EntityParams params) {
        Function<EntityParams, GameEntity> creator = entityCreators.get(type);
        if (creator != null) {
            return creator.apply(params);
        } else {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }
}
