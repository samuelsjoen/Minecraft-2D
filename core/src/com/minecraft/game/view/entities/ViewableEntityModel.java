package com.minecraft.game.view.entities;

import java.util.List;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Projectile;
import com.minecraft.game.model.entities.Slime;

public interface ViewableEntityModel {

    List<Knight> getKnights();

    List<Slime> getSlimes();

    List<PinkMonster> getPinkMonsters();

    List<Projectile> getProjectiles();

    
}