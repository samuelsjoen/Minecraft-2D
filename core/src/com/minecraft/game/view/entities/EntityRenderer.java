package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Projectile;
import com.minecraft.game.model.entities.Slime;

/**
 * The EntityRenderer class is responsible for rendering all entities in the
 * game.
 */
public class EntityRenderer implements Disposable {

    private KnightRenderer knightRenderer;
    private SlimeRenderer slimeRenderer;
    private PinkMonsterRenderer pinkMonsterRenderer;
    private ProjectileRenderer projectileRenderer;
    private ViewableEntityModel entityModel;
    private SpriteBatch batch;

    /**
     * Constructs an EntityRenderer with the given ViewableEntityModel and
     * SpriteBatch.
     *
     * @param entityModel The ViewableEntityModel containing entities to render
     * @param batch       The SpriteBatch used for rendering
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
     * Iterates over all Knights, Slimes, PinkMonsters, and Projectiles in the
     * entity model, and renders them using their respective renderers.
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
