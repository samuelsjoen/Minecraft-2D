package com.mygdx.game.utils;

import com.badlogic.gdx.Input.Keys;

public class Constants {
    // Screen dimensions
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    // player
    public static final float PLAYER_MOVE_SPEED = 200;
    public static final float PLAYER_JUMP_VELOCITY = 500;
    public static final float GRAVITY = -999.8f;
    // Animation frame durations and counts for the player character
    public static final float IDLE_FRAME_DURATION = 0.1f;
    public static final float RUN_FRAME_DURATION = 0.1f;
    public static final float JUMP_FRAME_DURATION = 0.1f;
    public static final int FRAME_COLS = 10; // Number of columns in the sprite sheet
    public static final int FRAME_ROWS = 4; // Number of rows in the sprite sheet

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
}
