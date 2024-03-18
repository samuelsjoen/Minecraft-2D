package com.minecraft.game.controller;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldListener implements ContactListener {

    public boolean inWater;
    private TiledMap map;

    public WorldListener(TiledMap map) {
        this.map = map;
    }

    @Override
    public void beginContact(Contact contact) {
            Object userDataA = contact.getFixtureA().getUserData();
    Object userDataB = contact.getFixtureB().getUserData();

    if (userDataA != null && userDataA.equals("player")) {
        Object otherUserData = contact.getFixtureB().getUserData();
        if (otherUserData != null) {
            // get the name of the mapobject that the player is touching
            System.out.println("Player touched object with user data: " + otherUserData);
        }
    } else if (userDataB != null && userDataB.equals("player")) {
        Object otherUserData = contact.getFixtureA().getUserData();
        if (otherUserData != null) {
            System.out.println("Player touched object with user data: " + otherUserData);
        }
    }
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
