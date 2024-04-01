package com.minecraft.game.model.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameEntity {

    public float x;
    public float y;
    protected float velX;
    protected float velY;
    protected float speed;
    public float width;
    public float height;
    protected Body body;

    public GameEntity(float width, float height, Body body) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.body = body;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
    }

    public abstract void update();

    // public abstract void render(SpriteBatch batch);

    public Body getBody() {
        return body;
    }
}
