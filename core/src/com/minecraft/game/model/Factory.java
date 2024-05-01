package com.minecraft.game.model;

import java.util.function.Function;
import java.util.Map;
import java.util.HashMap;

/**
 * The Factory class is an abstract class that represents a factory for creating objects of a specified type.
 * T is the type of object to create, and P is the type of parameters to pass to the object's constructor.
 */
public abstract class Factory<T, P> {

    // TODO: Magnus, can you add a description?
    protected Map<String, Function<P, T>> creators = new HashMap<>();

    /**
     * Register a new type of object with its constructor
     * @param type The type of object (e.g. "Knight")
     * @param creator # TODO : Magnus, can you add a description for the creator parameter?
     */
    public void register(String type, Function<P, T> creator) {
        creators.put(type, creator);
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
    public abstract T create(String type, P params);
}

