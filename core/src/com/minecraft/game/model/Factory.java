package com.minecraft.game.model;

import java.util.function.Function;
import java.util.Map;
import java.util.HashMap;

/**
 * The Factory class is an abstract class that represents a factory for creating objects of a specified type.
 * T is the type of object to create, and P is the type of parameters to pass to the object's constructor.
 */
public abstract class Factory<T, P> {

    // A map storing the object creation functions associated with string keys.
    // Each function defines how to create an instance of type T using parameters of type P.
    protected Map<String, Function<P, T>> creators;

    /**
     * Constructs a Factory object with an empty map of creators.
     */
    public Factory() {
        creators = new HashMap<>();
    }

    /**
     * Register a new type of object with its constructor
     * @param type The type of object (e.g. "Knight")
     * @param creator A function that constructs an object of type T given parameters of type P.
     *                This function encapsulates the creation logic specific to the object type.
     */
    public void register(String type, Function<P, T> creator) {
        creators.put(type, creator);
    }

    /**
     * Create a new object of the specified type
     * @param type The type of object to create
     * @param params The parameters to pass to the object's constructor.
     */
    public abstract T create(String type, P params);
}

