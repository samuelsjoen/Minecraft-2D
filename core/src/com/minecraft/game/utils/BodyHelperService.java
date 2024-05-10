package com.minecraft.game.utils;

import com.badlogic.gdx.physics.box2d.*;

// This class is used to create bodies for the game
/**
 * The BodyHelperService class provides utility methods for creating Box2D bodies in a game world.
 * It includes methods for creating dynamic and static bodies with various shapes and properties.
 */
public class BodyHelperService {

    /**
     * Creates a new body in the given world with the specified parameters.
     *
     * @param x             The x-coordinate of the body's position.
     * @param y             The y-coordinate of the body's position.
     * @param width         The width of the body.
     * @param height        The height of the body.
     * @param shape         The shape of the body. If null, a default shape will be created based on the width and height.
     * @param isStatic      Determines whether the body is static (true) or dynamic (false).
     * @param world         The world in which the body will be created.
     * @param categoryBits  The category bits for collision filtering.
     * @param maskBits      The mask bits for collision filtering.
     * @param userData      The user data associated with the body.
     * @param isSensor      Determines whether the body is a sensor (true) or not (false).
     * @return The created body.
     */
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
    /**
     * Creates a new body with the specified parameters.
     *
     * @param shape        the shape of the body
     * @param isStatic     true if the body is static, false otherwise
     * @param world        the world in which the body will be created
     * @param categoryBits the category bits for collision filtering
     * @param maskBits     the mask bits for collision filtering
     * @param userData     the user data associated with the body
     * @param isSensor     true if the body is a sensor, false otherwise
     * @return the created body
     */
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
