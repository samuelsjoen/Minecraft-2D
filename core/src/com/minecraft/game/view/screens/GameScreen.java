package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.minecraft.game.model.entities.Knight;
import com.minecraft.game.model.entities.PinkMonster;
import com.minecraft.game.model.entities.Projectile;
import com.minecraft.game.model.entities.Slime;
import com.minecraft.game.model.DayNightCycle;
import com.minecraft.game.model.EnemyManager;
import com.minecraft.game.model.GameState;
import com.minecraft.game.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.SpriteManager;
import com.minecraft.game.view.MinecraftView;
import com.minecraft.game.view.ViewableMinecraftModel;
import com.minecraft.game.view.entities.KnightRenderer;
import com.minecraft.game.view.entities.PinkMonsterRenderer;
import com.minecraft.game.view.entities.ProjectileRenderer;
import com.minecraft.game.view.entities.SlimeRenderer;
import com.minecraft.game.view.overlay.OverlayView;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

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

    private DayNightCycle dayNightCycle;
    private boolean isNightBackground;
    private Texture backgroundNight;
    private Texture backgroundDay;

    private KnightRenderer knightRenderer;
    private SlimeRenderer slimeRenderer;
    private PinkMonsterRenderer pinkMonsterRenderer;
    private ProjectileRenderer projectileRenderer;
    private MinecraftView minecraftView;

    public GameScreen(OrthographicCamera camera, ViewableMinecraftModel viewableMinecraftModel,
            MinecraftView minecraftView) {
        this.camera = camera;
        this.batch = new SpriteBatch();

        this.isNightBackground = false;
        this.backgroundNight = new Texture(Gdx.files.internal("assets/backgroundNight.png"));
        this.backgroundDay = new Texture(Gdx.files.internal("assets/background.png"));
        this.backgroundImage = backgroundDay;

        this.box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawBodies(Constants.DEBUG_MODE);

        this.viewableMinecraftModel = viewableMinecraftModel;
        this.minecraftView = minecraftView;

        this.orthogonalTiledMapRenderer = viewableMinecraftModel.getMapRenderer();

        // TODO: Should not initialize EnemyManager here, should be initialized in
        // model, and use getter for getViewableEnemies() or something?
        enemyManager = new EnemyManager(viewableMinecraftModel.getWorld(), viewableMinecraftModel.getPlayer(),
                viewableMinecraftModel.getTiledMap(), viewableMinecraftModel.getDayNightCycle());

        // Disse er greie å ha i view - de handler om view
        this.spriteManager = new SpriteManager(viewableMinecraftModel.getPlayer(),
                viewableMinecraftModel.getInventory(), viewableMinecraftModel.getArmorInventory());
        this.overlayView = new OverlayView(viewableMinecraftModel.getInventory(), Player.getHealth(),
                viewableMinecraftModel.getCrafting());

        this.dayNightCycle = viewableMinecraftModel.getDayNightCycle();

        // Initialize renderers
        knightRenderer = new KnightRenderer();
        slimeRenderer = new SlimeRenderer();
        pinkMonsterRenderer = new PinkMonsterRenderer();
        projectileRenderer = new ProjectileRenderer();

    }

    private void update() {

        viewableMinecraftModel.checkAndUpdateGameState();

        if (viewableMinecraftModel.getGameState() == GameState.GAME_OVER || viewableMinecraftModel.getGameState() == GameState.GAME_WON) {
            // If the game is over, call the method in MinecraftView to update the screen
            minecraftView.updateScreen();
        }

        // TODO: world.step() should be called in model?
        viewableMinecraftModel.getWorld().step(1 / 60f, 6, 2);

        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        viewableMinecraftModel.getPlayer().update(Gdx.graphics.getDeltaTime());

        spriteManager.update();

        overlayView.update(getLowerLeftCorner());
        enemyManager.update(0.01f);

        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update(Gdx.graphics.getDeltaTime());
            projectile.checkCollisionWithPlayer(viewableMinecraftModel.getPlayer());

            if (projectile.isMarkedForRemoval()) {
                viewableMinecraftModel.getWorld().destroyBody(projectile.getBody());

                iterator.remove();
            }
        }
    }

    private void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = Math.round(viewableMinecraftModel.getPlayer().getBody().getPosition().x * Constants.PPM * 10)
                / 10f;
        position.y = Math.round(viewableMinecraftModel.getPlayer().getBody().getPosition().y * Constants.PPM * 10)
                / 10f;

        camera.position.set(position);
        camera.update();
    }

    private Vector2 getLowerLeftCorner() {
        float cameraX = camera.position.x - camera.viewportWidth / 2;
        float cameraY = camera.position.y - camera.viewportHeight / 2;
        return new Vector2(cameraX, cameraY);
    }

    public void setDay() {
        // System.out.println("setDay() gets called from " + this.getClass().getName() +
        // " class");
        backgroundImage = backgroundDay;
    }

    public void setNight() {
        // System.out.println("setNight() gets called from " + this.getClass().getName()
        // + " class");
        backgroundImage = backgroundNight;
    }

    private void updateBackground() {
        boolean isNight = dayNightCycle.getIsNight();
        if (isNight != isNightBackground) {
            isNightBackground = isNight;
            if (isNight)
                setNight();
            else
                setDay();
        }
    }

    @Override
    public void render(float delta) {
        this.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateBackground();

        batch.begin();
        Vector2 lowerLeftCorner = getLowerLeftCorner();
        batch.draw(backgroundImage, lowerLeftCorner.x, lowerLeftCorner.y, camera.viewportWidth, camera.viewportHeight);
        batch.end();

        orthogonalTiledMapRenderer.render();
        batch.begin();

        if (viewableMinecraftModel.getPlayer() != null) {
            spriteManager.render(batch, viewableMinecraftModel.getPlayer().getX(),
                    viewableMinecraftModel.getPlayer().getY());
        }

        // TODO: Should be in model - if projectiles should be drawn use
        // getVisibleProjectiles() or something
        for (Projectile projectile : projectiles) {
            projectileRenderer.render(projectile, batch);
        }

        for (Knight knight : EnemyManager.getEnemies()) {
            knightRenderer.render(knight, batch);
        }
        for (Slime slime : EnemyManager.getSlimes()) {
            slimeRenderer.render(slime, batch);
        }
        for (PinkMonster pinkMonster : EnemyManager.getPinkMonsters()) {
            pinkMonsterRenderer.render(pinkMonster, batch);
        }

        if (overlayView != null) {
            overlayView.render(batch);
        }

        batch.end();

        box2DDebugRenderer.render(viewableMinecraftModel.getWorld(), camera.combined.scl(Constants.PPM));
    }

    // TODO: Should be in model
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
