package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.minecraft.game.model.Player;
import com.minecraft.game.model.EnemyManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.SpriteManager;
import com.minecraft.game.view.OverlayView;
import com.minecraft.game.view.ViewableMinecraftModel;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import com.minecraft.game.model.Projectile;
import java.util.Iterator;
import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private OverlayView overlayView;
    private Texture backgroundImage; 

    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    private EnemyManager enemyManager;
    private static ArrayList<Projectile> projectiles = new ArrayList<>();

    private SpriteManager spriteManager;
    private ViewableMinecraftModel viewableMinecraftModel;

    //private boolean isNight;

    public GameScreen(OrthographicCamera camera, ViewableMinecraftModel viewableMinecraftModel) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.backgroundImage = new Texture(Gdx.files.internal("assets/background.png")); // Loads the background img
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawBodies(Constants.DEBUG_MODE);

        this.viewableMinecraftModel = viewableMinecraftModel;

        this.orthogonalTiledMapRenderer = viewableMinecraftModel.getMapRenderer();

        // TODO: Should not initialize EnemyManager here, should be initialized in model, and use getter for getViewableEnemies() or something?
        enemyManager = new EnemyManager(viewableMinecraftModel.getWorld(), viewableMinecraftModel.getPlayer(), viewableMinecraftModel.getTiledMap());

        // Disse er greie å ha i view - de handler om view
        this.spriteManager = new SpriteManager(viewableMinecraftModel.getPlayer(), viewableMinecraftModel.getInventory()); 
        this.overlayView = new OverlayView(viewableMinecraftModel.getInventory(), Player.getHealth(), camera); 

        // Starts with day
        //isNight = false;
    }

    // TODO: world.step() should be called in model?
    private void update() {
        viewableMinecraftModel.getWorld().step(1 / 60f, 6, 2);
        
        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        viewableMinecraftModel.getPlayer().update();

        spriteManager.update();

        overlayView.update();
        enemyManager.update(0.01f);

        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update();
            projectile.checkCollisionWithPlayer(viewableMinecraftModel.getPlayer());

            if (projectile.isMarkedForRemoval()) {
                viewableMinecraftModel.getWorld().destroyBody(projectile.getBody());

                iterator.remove();
            }
        }
    }

    private void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = Math.round(viewableMinecraftModel.getPlayer().getBody().getPosition().x * Constants.PPM * 10) / 10f;
        position.y = Math.round(viewableMinecraftModel.getPlayer().getBody().getPosition().y * Constants.PPM * 10) / 10f;

        camera.position.set(position);
        camera.update();
    }

    /*private void backgroundUpdate() {
        if ()
    }*/


/*
    public void setDay() {
        isNight = false;
    }

    public void setNight() {
        isNight = true;
    }

    public boolean getNight() {
        return this.isNight;
        return viewableMinecraftModel.getIsNight();
    }*/

    private Vector2 getLowerLeftCorner() {
        float cameraX = camera.position.x - camera.viewportWidth / 2;
        float cameraY = camera.position.y - camera.viewportHeight / 2;
        return new Vector2(cameraX, cameraY);
    }

    @Override
    public void render(float delta) {
        this.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        Vector2 lowerLeftCorner = getLowerLeftCorner();
        batch.draw(backgroundImage, lowerLeftCorner.x, lowerLeftCorner.y, camera.viewportWidth, camera.viewportHeight);
        batch.end();

        orthogonalTiledMapRenderer.render();
        batch.begin();

        enemyManager.render(batch);

        if (viewableMinecraftModel.getPlayer() != null) {
            viewableMinecraftModel.getPlayer().render(batch);
            spriteManager.render(batch, viewableMinecraftModel.getPlayer().getX(), viewableMinecraftModel.getPlayer().getY());
        }

        if (overlayView != null) {
            overlayView.render(batch);
        }

        // TODO: Should be in model - if projectiles should be drawn use getVisibleProjectiles() or something
        for (Projectile projectile : projectiles) {
            projectile.render(batch);
        }

        /*if (isNight) {
            Gdx.gl.glClearColor(0, 0, 0, (float) 0.5);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }*/

        batch.end();

        box2DDebugRenderer.render(viewableMinecraftModel.getWorld(), camera.combined.scl(Constants.PPM));
    }

    // TODO: Should definitely be in model
    public static void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        box2DDebugRenderer.dispose();
        orthogonalTiledMapRenderer.dispose();
        spriteManager.dispose();
    }

}
