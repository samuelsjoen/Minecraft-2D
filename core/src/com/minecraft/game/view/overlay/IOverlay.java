package com.minecraft.game.view.overlay;

import com.badlogic.gdx.math.Vector2;

public interface IOverlay {
    /** Renders the overlay */
    public void render();

    /**
     * Updates the position of the overlay based on the lower left corner of the
     * screen
     * 
     * @param lowerLeftCorner lower left corner of the screen
     */
    public void update(Vector2 lowerLeftCorner);

    /** Disposes of the overlay textures */
    public void dispose();
}
