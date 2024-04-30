package com.minecraft.game.model;

import java.util.function.Function;
import java.util.Map;
import java.util.HashMap;

public abstract class Factory<T, Params> {

    // ??
    protected Map<String, Function<Params, T>> creators = new HashMap<>();

    /**
     * Register a new type of object with its constructor
     * @param type The type of object (e.g. "Knight")
     * # TODO : Magnus can you add a description for the creator parameter?
     */
    public void register(String type, Function<Params, T> creator) {
        creators.put(type, creator);
    }

    /**
     * Create a new object of the specified type
     * @param type The type of object to create
     * @param params The parameters to pass to the object's constructor(?) # TODO : is that right?
     */
    public abstract T create(String type, Params params);
}

