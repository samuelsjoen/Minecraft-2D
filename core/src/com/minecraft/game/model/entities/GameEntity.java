package com.minecraft.game.model.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;

public abstract class GameEntity implements Disposable {

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

    public abstract void update(float deltaTime);

    public Body getBody() {
        return body;
    }

    @Override
    public void dispose () {
        body.getWorld().destroyBody(body);
    }

}
