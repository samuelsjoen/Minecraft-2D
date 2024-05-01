package com.minecraft.game.model.entities;

import com.minecraft.game.view.entities.ViewableEntityModel;
import com.minecraft.game.model.EnemyManager;
import java.util.List;

/**
 * Implements the {@link ViewableEntityModel} interface to provide access to
 * lists of game entities.
 * This class serves as a bridge to the {@link EnemyManager}, allowing view
 * components to access
 * game entities for rendering and interaction purposes without directly
 * handling game logic.
 */
public class EntityModel implements ViewableEntityModel {
    /**
     * Retrieves the list of all Knight entities currently managed by the
     * {@link EnemyManager}.
     *
     * @return a list of {@link Knight} instances.
     */

    @Override
    public List<Knight> getKnights() {
        return EnemyManager.getEnemies();
    }

    /**
     * Retrieves the list of all Slime entities currently managed by the
     * {@link EnemyManager}.
     *
     * @return a list of {@link Slime} instances.
     */

    @Override
    public List<Slime> getSlimes() {
        return EnemyManager.getSlimes();
    }

    /**
     * Retrieves the list of all PinkMonster entities currently managed by the
     * {@link EnemyManager}.
     *
     * @return a list of {@link PinkMonster} instances.
     */

    @Override
    public List<PinkMonster> getPinkMonsters() {
        return EnemyManager.getPinkMonsters();
    }

    /**
     * Retrieves the list of all Projectile entities currently managed by the
     * {@link EnemyManager}.
     *
     * @return a list of {@link Projectile} instances.
     */

    @Override
    public List<Projectile> getProjectiles() {
        return EnemyManager.getProjectiles();
    }

}
