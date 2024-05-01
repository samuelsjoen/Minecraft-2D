package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Projectile;
import com.minecraft.game.model.entities.Slime;

/**
 * Manages and coordinates the rendering of all game entities on the screen.
 * This class holds individual renderers for different entity types and handles
 * the lifecycle of these renderers.
 */
public class EntityRenderer implements Disposable {

    private KnightRenderer knightRenderer;
    private SlimeRenderer slimeRenderer;
    private PinkMonsterRenderer pinkMonsterRenderer;
    private ProjectileRenderer projectileRenderer;
    private ViewableEntityModel entityModel;
    private SpriteBatch batch;

    /**
     * Constructs an EntityRenderer which sets up individual renderers for
     * different entity types.
     *
     * @param entityModel The model providing access to the entities that need to be
     *                    rendered.
     * @param batch       The SpriteBatch used to draw all entities on the screen.
     */
    public EntityRenderer(ViewableEntityModel entityModel, SpriteBatch batch) {
        // Initialize renderers
        this.batch = batch;
        this.entityModel = entityModel;
        knightRenderer = new KnightRenderer(batch);
        slimeRenderer = new SlimeRenderer(batch);
        pinkMonsterRenderer = new PinkMonsterRenderer(batch);
        projectileRenderer = new ProjectileRenderer(batch);
    }

    /**
     * Renders all entities managed by this renderer. It delegates the actual
     * rendering to the specific entity renderers for each type of entity.
     */
    public void renderAllEntities() {

        for (Projectile projectile : entityModel.getProjectiles()) {
            projectileRenderer.render(projectile);
        }

        for (Knight knight : entityModel.getKnights()) {
            knightRenderer.render(knight);
        }
        for (Slime slime : entityModel.getSlimes()) {
            slimeRenderer.render(slime);
        }
        for (PinkMonster pinkMonster : entityModel.getPinkMonsters()) {
            pinkMonsterRenderer.render(pinkMonster);
        }
    }

    /**
     * Disposes of resources used by this renderer. It ensures all individual
     * entity renderers are also disposed of properly, freeing up graphic resources.
     */
    @Override
    public void dispose() {
        knightRenderer.dispose();
        slimeRenderer.dispose();
        pinkMonsterRenderer.dispose();
        projectileRenderer.dispose();
    }

}
