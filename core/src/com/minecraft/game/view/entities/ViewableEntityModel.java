package com.minecraft.game.view.entities;

import java.util.List;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Projectile;
import com.minecraft.game.model.entities.Slime;

/**
 * Interface defining methods for retrieving lists of different entity types.
 * This interface allows for accessing collections of knights, slimes, pink
 * monsters,
 * and projectiles that are currently active within the game environment.
 */
public interface ViewableEntityModel {
    /**
     * Retrieves a list of all knight entities.
     * 
     * @return List of knights currently active in the game.
     */
    List<Knight> getKnights();

    /**
     * Retrieves a list of all slime entities.
     * 
     * @return List of slimes currently active in the game.
     */
    List<Slime> getSlimes();

    /**
     * Retrieves a list of all pink monster entities.
     * 
     * @return List of pink monsters currently active in the game.
     */
    List<PinkMonster> getPinkMonsters();

    /**
     * Retrieves a list of all projectile entities.
     * 
     * @return List of projectiles currently active in the game.
     */
    List<Projectile> getProjectiles();

}