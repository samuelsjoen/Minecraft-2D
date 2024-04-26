package com.minecraft.game.view.overlay;

import com.badlogic.gdx.math.Vector2;

public interface OverlayInterface {
    public void render();
    public void update(Vector2 lowerLeftCorner);
    public void dispose();
}
