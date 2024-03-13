package com.minecraft.game.utils;

import com.badlogic.gdx.physics.box2d.*;

// This class is used to create bodies for the game
public class BodyHelperService {

    public static Body createBody(float x, float y, float width, float height, Shape shape, boolean isStatic,
            World world,
            short categoryBits, short maskBits, String userData, boolean isSensor) {
        BodyDef bodyDef = new BodyDef();
        // if true, will be a static body, in else case it will be dynamic
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

        // if the body is dynamic, position and rotation will be set
        if (!isStatic) {
            bodyDef.position.set(x / Constants.PPM, y / Constants.PPM);
            bodyDef.fixedRotation = true;
            bodyDef.angle = 0f; // Set the initial angle to 0 radians
        }

        Body body = world.createBody(bodyDef);

        // if no shape is provided, it will be created based on width and height
        if (shape == null) {
            shape = new PolygonShape();
            ((PolygonShape) shape).setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);
        }

        FixtureDef fixtureDef = createFixtureDef(shape, isSensor, categoryBits, maskBits);

        body.createFixture(fixtureDef).setUserData(userData);
        shape.dispose();
        return body;
    }

    // if coordinates and width/height is not needed (aka static), they are set to 0
    // (and won't be used)
    public static Body createBody(Shape shape, boolean isStatic, World world,
            short categoryBits, short maskBits, String userData, boolean isSensor) {
        return createBody(0, 0, 0, 0, shape, isStatic, world, categoryBits, maskBits, userData, isSensor);
    }

    private static FixtureDef createFixtureDef(Shape shape, boolean isSensor, short categoryBits, short maskBits) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0; // Default friction value
        fixtureDef.density = 1f; // Default density value
        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;
        fixtureDef.isSensor = isSensor;
        return fixtureDef;
    }

}
