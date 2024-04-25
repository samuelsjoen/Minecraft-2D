package com.minecraft.game.model.entities;

import com.minecraft.game.view.entities.ViewableEntityModel;
import com.minecraft.game.model.EnemyManager;
import java.util.List;

public class EntityModel implements ViewableEntityModel {

    @Override
    public List<Knight> getKnights() {
        return EnemyManager.getEnemies();
    }

    @Override
    public List<Slime> getSlimes() {
        return EnemyManager.getSlimes();
    }

    @Override
    public List<PinkMonster> getPinkMonsters() {
        return EnemyManager.getPinkMonsters();
    }

    @Override
    public List<Projectile> getProjectiles() {
        return EnemyManager.getProjectiles();
    }
    
}
