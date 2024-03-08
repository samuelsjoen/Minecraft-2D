package com.minecraft.game.utils;

import com.badlogic.gdx.physics.box2d.*;

public class BodyHelperService {

    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world,
            short categoryBits, short maskBits, String userData) {
        BodyDef bodyDef = new BodyDef();
        // if true, will be a static body, in else case it will be dynamic
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / Constants.PPM, y / Constants.PPM);
        bodyDef.fixedRotation = true;
        bodyDef.angle = 0f; // Set the initial angle to 0 radians
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        // no sticking to the wall
        fixtureDef.friction = 0;
        fixtureDef.filter.categoryBits = categoryBits; 
        fixtureDef.filter.maskBits = maskBits; 

        body.createFixture(fixtureDef).setUserData(userData);
        shape.dispose();
        return body;
    }

}
