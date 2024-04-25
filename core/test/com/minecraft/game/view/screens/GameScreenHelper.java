package com.minecraft.game.view.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
//import com.minecraft.game.model.DayNightCycle;
//import com.minecraft.game.model.EnemyManager;
//import com.minecraft.game.utils.SpriteManager;
import com.minecraft.game.view.MinecraftView;
import com.minecraft.game.view.ViewableMinecraftModel;
//import com.minecraft.game.view.entities.EntityRenderer;
//import com.minecraft.game.view.overlay.OverlayView;

public class GameScreenHelper extends GameScreen {

    public GameScreenHelper(OrthographicCamera camera, ViewableMinecraftModel viewableMinecraftModel,
            MinecraftView minecraftView, SpriteBatch batch) {
        super(camera, viewableMinecraftModel, minecraftView, batch);
    }
    /* 
    public void setMapRenderer(OrthogonalTiledMapRenderer mapRenderer) {
        this.orthogonalTiledMapRenderer = mapRenderer;
    }

    public void setSpriteManager(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
    }

    public void setOverlayView(OverlayView overlayView) {
        this.overlayView = overlayView;
    }

    public void setEnemyManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }

    public void setEntityRenderer(EntityRenderer entityRenderer) {
        this.entityRenderer = entityRenderer;
    }

    public Texture getBackgroundImage() {
        return this.backgroundImage;
    }

    public Texture getBackgroundDay() {
        return this.backgroundDay;
    }

    public Texture getBackgroundNight() {
        return this.backgroundNight;
    }

    public void setBackgroundImage(Texture backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setDayNightCycle(DayNightCycle dayNightCycle) {
        this.dayNightCycle = dayNightCycle;
    } 
    */
}
