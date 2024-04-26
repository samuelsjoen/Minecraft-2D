package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.model.entities.GameEntity;

public interface IEntityRenderer<T extends GameEntity> {
    void render(T entity, SpriteBatch batch);
}
