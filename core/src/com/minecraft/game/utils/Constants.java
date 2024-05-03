package com.minecraft.game.utils;

import com.badlogic.gdx.Input.Keys;
import com.minecraft.game.model.items.Item;

public class Constants {
    // Screen dimensions
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    // player
    public static final float PLAYER_MOVE_SPEED = 15f;
    public static final float PLAYER_JUMP_VELOCITY = 15;
    public static final int PLAYER_MAX_HEALTH = 5;

    // Input key constants
    public static final int ESCAPE_KEY = Keys.ESCAPE;
    public static final int TOGGLE_PAUSE_KEY = Keys.P;
    public static final int TOGGLE_FULLSCREEN_KEY = Keys.F;
    public static final int ANY_KEY = Keys.ANY_KEY;

    // Movement & player keys
    public static final int MOVE_LEFT_KEY = Keys.A;
    public static final int MOVE_RIGHT_KEY = Keys.D;
    public static final int JUMP_KEY = Keys.SPACE;
    public static final int ATTACK_KEY = Keys.TAB;

    public static final int REVIVE_KEY = Keys.R;

    // Crafting & inventory keys
    public static final int CHANGE_INVENTORY_LEFT_KEY = Keys.LEFT;
    public static final int CHANGE_INVENTORY_RIGHT_KEY = Keys.RIGHT;
    public static final int DROP_INVENTORY_ITEM_KEY = Keys.Q;
    public static final int TOGGLE_CRAFTING_KEY = Keys.E;

    // Character scale (1.0f for original size, adjust as needed)
    public static final float CHARACTER_SCALE = 2.5f; // Example scale to make character bigger

    // Debug mode for drawing hitbox around the character
    public static final boolean DEBUG_MODE = false; // Set to true or false to show or hide the world tiles !

    // PPM (pixels per meter) for Box2D
    public static final float PPM = 32.0f;

    // Tile size
    public static final int TILE_SIZE = 64;

    // Enemy Constants
    public static final float ENEMY_SPEED = 1.2f;
    public static final int ENEMY_MAX_HEALTH = 1;

    // Collision Constants
    public static final short CATEGORY_PLAYER = 0x0001;
    public static final short CATEGORY_ENEMY = 0x0002;
    // public static final short CATEGORY_SLIME = 0x0003;
    public static final short CATEGORY_WORLD = 0x0004;
    public static final short MASK_PLAYER = CATEGORY_WORLD; // Player collides with the world
    public static final short MASK_ENEMY = CATEGORY_WORLD; // Enemy collides with the world
    // public static final short MASK_SLIME = CATEGORY_WORLD; // Slimes collides
    // with the world
    public static final short MASK_WORLD = CATEGORY_PLAYER | CATEGORY_ENEMY; // World collides with
                                                                             // player and enemy

    // Inventory
    public static final Item[] EMPTY_INVENTORY = new Item[] {
    };

    public static final Item[] DEMO_ITEMS = new Item[] {
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.WOOD,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
        Item.IRON_ORE,
    };
}
