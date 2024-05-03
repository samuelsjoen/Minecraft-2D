package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
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
import com.minecraft.game.view.entities.EntityRenderer;
import com.minecraft.game.view.overlay.OverlayView;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

/**
 * The main game screen where the game is played.
 */
public class GameScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private OverlayView overlayView;
    private Texture backgroundImage;

    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    protected EnemyManager enemyManager;

    private SpriteManager spriteManager;
    private ViewableMinecraftModel viewableMinecraftModel;

    private DayNightCycle dayNightCycle;
    private boolean isNightBackground;
    private Texture backgroundNight;
    private Texture backgroundDay;

    protected EntityRenderer entityRenderer;
    private MinecraftView minecraftView;

    /**
     * Constructs a new GameScreen.
     *
     * @param camera                The OrthographicCamera used for rendering.
     * @param viewableMinecraftModel The model containing game data.
     * @param minecraftView         The main view of the game.
     * @param batch                 The SpriteBatch used for rendering.
     */
    public GameScreen(OrthographicCamera camera, ViewableMinecraftModel viewableMinecraftModel,
            MinecraftView minecraftView, SpriteBatch batch) {
        this.camera = camera;
        this.batch = batch;

        this.isNightBackground = false;
        this.backgroundNight = new Texture(Gdx.files.internal("background/backgroundNight.png"));
        this.backgroundDay = new Texture(Gdx.files.internal("background/backgroundDay.png"));
        this.backgroundImage = backgroundDay;

        this.box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawBodies(Constants.DEBUG_MODE);

        this.viewableMinecraftModel = viewableMinecraftModel;
        this.minecraftView = minecraftView;

        this.orthogonalTiledMapRenderer = viewableMinecraftModel.getMapRenderer();

        enemyManager = new EnemyManager(viewableMinecraftModel.getWorld(), viewableMinecraftModel.getPlayer(),
                viewableMinecraftModel.getTiledMap(), viewableMinecraftModel.getDayNightCycle(), viewableMinecraftModel.getScore());

        viewableMinecraftModel.getScore().resetScore();
        // Disse er greie å ha i view - de handler om view
        this.spriteManager = new SpriteManager(viewableMinecraftModel.getPlayer(),
                viewableMinecraftModel.getInventory(), viewableMinecraftModel.getArmorInventory());
        this.overlayView = new OverlayView(viewableMinecraftModel.getInventory(), Player.getHealth(),
                viewableMinecraftModel.getCrafting(), viewableMinecraftModel.getScore(), batch, minecraftView.getFont());

        this.dayNightCycle = viewableMinecraftModel.getDayNightCycle();

        // Initialize renderer
        this.entityRenderer = new EntityRenderer(viewableMinecraftModel.getEntityModel(), batch);
    }

    /**
     * Updates the game logic.
     */
    public void update() {

        viewableMinecraftModel.checkAndUpdateGameState();

        if (viewableMinecraftModel.getGameState() == GameState.GAME_OVER
                || viewableMinecraftModel.getGameState() == GameState.GAME_WON) {
            // If the game is over, call the method in MinecraftView to update the screen
            minecraftView.updateScreen();
            return;
        }

        viewableMinecraftModel.getWorld().step(1 / 60f, 6, 2);

        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        viewableMinecraftModel.getPlayer().update(Gdx.graphics.getDeltaTime());

        spriteManager.update();

        overlayView.update(getLowerLeftCorner());

        enemyManager.update(0.01f);

    }

    /**
     * Updates the camera position based on the player's position.
     */
    private void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = Math.round(viewableMinecraftModel.getPlayer().getBody().getPosition().x * Constants.PPM * 10)
                / 10f;
        position.y = Math.round(viewableMinecraftModel.getPlayer().getBody().getPosition().y * Constants.PPM * 10)
                / 10f;

        camera.position.set(position);
        camera.update();
    }

    /**
     * Retrieves the lower-left corner position of the camera viewport.
     *
     * @return The lower-left corner position.
     */
    Vector2 getLowerLeftCorner() {
        float cameraX = camera.position.x - camera.viewportWidth / 2;
        float cameraY = camera.position.y - camera.viewportHeight / 2;
        return new Vector2(cameraX, cameraY);
    }

    /**
     * Sets the background to day mode.
     */
    public void setDay() {
        backgroundImage = backgroundDay;
    }

    /**
     * Sets the background to night mode.
     */
    public void setNight() {
        backgroundImage = backgroundNight;
    }

    /**
     * Updates the background based on the day/night cycle.
     */
    void updateBackground() {
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

        entityRenderer.renderAllEntities();

        if (overlayView != null) {
            overlayView.render();
        }

        batch.end();

        box2DDebugRenderer.render(viewableMinecraftModel.getWorld(), camera.combined.scl(Constants.PPM));
    }


    @Override
    public void dispose() {
        //batch.dispose();
        backgroundImage.dispose();
        box2DDebugRenderer.dispose();
        orthogonalTiledMapRenderer.dispose();
        spriteManager.dispose();
        entityRenderer.dispose();
    }

    // for testing - only used in gamescreen: 

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
        return backgroundImage;
    }

    public Texture getBackgroundDay() {
        return backgroundDay;
    }

    public Texture getBackgroundNight() {
        return backgroundNight;
    }

    public void setBackgroundImage(Texture backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setDayNightCycle(DayNightCycle dayNightCycle) {
        this.dayNightCycle = dayNightCycle;
    }

}
