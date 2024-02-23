package com.minecraft.game.utils;

import com.badlogic.gdx.Input.Keys;

public class Constants {
    // Screen dimensions
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    
    // player
    public static final float PLAYER_MOVE_SPEED = 1.5f;
    public static final float PLAYER_JUMP_VELOCITY = 15;
    // Animation frame durations and counts for the player character

    // Input key constants
    public static final int MOVE_LEFT_KEY = Keys.A;
    public static final int MOVE_RIGHT_KEY = Keys.D;
    public static final int JUMP_KEY = Keys.SPACE;

    // Character scale (1.0f for original size, adjust as needed)
    public static final float CHARACTER_SCALE = 2.5f; // Example scale to make character bigger

    // Debug mode for drawing hitbox around the character
    public static final boolean DEBUG_MODE = true; // Set to false to disable by default

    // Key to toggle debug mode if needed dynamically
    public static final int TOGGLE_DEBUG_KEY = Keys.F1;

    public static final float PPM = 32.0f;

}
