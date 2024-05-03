package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.minecraft.game.model.entities.IViewableEntityModel;
import com.minecraft.game.utils.Constants;

/**
 * Handles rendering of projectiles within the game.
 * This renderer is responsible for drawing the projectile's texture at the
 * correct position
 * and with the appropriate dimensions on the screen.
 */
public class ProjectileRenderer {
    private Texture sheet;
    private SpriteBatch batch;

    /**
     * Creates a ProjectileRenderer instance.
     *
     * @param batch The SpriteBatch used to draw the texture of the projectile.
     */
    public ProjectileRenderer(SpriteBatch batch) {
        this.batch = batch;
        sheet = new Texture("entities/Rock2.png");
    }

    /**
     * Renders a projectile on the screen using its current position and dimensions.
     *
     * @param projectile The projectile to be rendered, which should implement
     *                   IViewableEntityModel.
     */
    public void render(IViewableEntityModel projectile) {
        if (sheet != null) {
            batch.draw(sheet,
                    (projectile.getPosition().x * Constants.PPM) - 25,
                    (projectile.getPosition().y * Constants.PPM) - 25,
                    projectile.getWidth(),
                    projectile.getHeight());
        }
    }

    /**
     * Disposes of resources used by this renderer.
     * This method should be called to free up resources when they are no longer
     * needed.
     */
    public void dispose() {
        sheet.dispose();
    }
}
