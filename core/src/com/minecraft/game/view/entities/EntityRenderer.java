package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Projectile;
import com.minecraft.game.model.entities.Slime;

public class EntityRenderer implements Disposable {

    private KnightRenderer knightRenderer;
    private SlimeRenderer slimeRenderer;
    private PinkMonsterRenderer pinkMonsterRenderer;
    private ProjectileRenderer projectileRenderer;
    private ViewableEntityModel entityModel;
    private SpriteBatch batch;

    public EntityRenderer(ViewableEntityModel entityModel, SpriteBatch batch) {
        // Initialize renderers
        this.batch = batch;
        this.entityModel = entityModel;
        knightRenderer = new KnightRenderer();
        slimeRenderer = new SlimeRenderer();
        pinkMonsterRenderer = new PinkMonsterRenderer();
        projectileRenderer = new ProjectileRenderer();
    }

    public void renderAllEntities() {

        for (Projectile projectile : entityModel.getProjectiles()) {
            projectileRenderer.render(projectile, batch);
        }

        for (Knight knight : entityModel.getKnights()) {
            knightRenderer.render(knight, batch);
        }
        for (Slime slime : entityModel.getSlimes()) {
            slimeRenderer.render(slime, batch);
        }
        for (PinkMonster pinkMonster : entityModel.getPinkMonsters()) {
            pinkMonsterRenderer.render(pinkMonster, batch);
        }
    }

    @Override
    public void dispose() {
        knightRenderer.dispose();
        slimeRenderer.dispose();
        pinkMonsterRenderer.dispose();
        projectileRenderer.dispose();
    }

}
