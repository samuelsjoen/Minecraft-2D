package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.model.entities.GameEntity;

/**
 * Defines a generic interface for rendering entities in the game.
 * This interface ensures that all entity renderers implement a common method
 * to handle the rendering of entities using a SpriteBatch.
 *
 * @param <T> The type of GameEntity this renderer can handle.
 */
public interface IEntityRenderer<T extends GameEntity> {
    /**
     * Renders the specified entity using the provided SpriteBatch.
     * 
     * @param entity The entity to be rendered.
     * @param batch  The SpriteBatch used to draw the entity on the screen.
     */
    void render(T entity, SpriteBatch batch);

}
