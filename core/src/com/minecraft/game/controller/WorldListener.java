package com.minecraft.game.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldListener implements ContactListener {

    public boolean inWater;

    @Override
    public void beginContact(Contact contact) {
        System.out.println(contact.getFixtureA().getUserData());
        System.out.println(contact.getFixtureB().getUserData());
        /*
         * if (contact.getFixtureA().getUserData().equals("player") &&
         * contact.getFixtureB().getUserData().equals("water")) {
         * inWater = true;
         * }
         */
    }

    @Override
    public void endContact(Contact contact) {
        /*
         * if (contact.getFixtureA().getUserData().equals("player") &&
         * contact.getFixtureB().getUserData().equals("water")) {
         * inWater = false;
         * }
         */
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
