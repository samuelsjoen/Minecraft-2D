package com.minecraft.game.view;

import com.badlogic.gdx.ScreenAdapter;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.controller.WorldController;
import com.minecraft.game.model.EnemyManager;
import com.minecraft.game.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.SpriteManager;
import com.minecraft.game.utils.TileMapHelper;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import com.minecraft.game.model.Projectile;
import java.util.Iterator;
import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    // private Minecraft game;
    private SpriteBatch batch;
    private Player player;
    private Health playerHealth;
    private Inventory inventory;
    private OverlayView overlayView;
    private Texture backgroundImage; // Background image

    private World world;

    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;

    private EnemyManager enemyManager;
    private static ArrayList<Projectile> projectiles = new ArrayList<>();
    private WorldController worldController;

    private SpriteManager spriteManager;

    public GameScreen(OrthographicCamera camera) {
        this.playerHealth = new Health(Constants.PLAYER_MAX_HEALTH, Constants.PLAYER_MAX_HEALTH);
        this.inventory = new Inventory(Constants.DEFAULT_ITEMS);
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.backgroundImage = new Texture(Gdx.files.internal("assets/background.png")); // Loads the background img
        this.world = new World(new Vector2(0, -25f), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawBodies(Constants.DEBUG_MODE);
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();

        // controller
        this.worldController = new WorldController(player, inventory, this);
        Gdx.input.setInputProcessor(worldController);

        enemyManager = new EnemyManager(world, player, getTiledMap());

    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();
        worldController.handleGameInput();

        spriteManager.update();

        overlayView.update();
        enemyManager.update(0.01f);

        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update();
            projectile.checkCollisionWithPlayer(player);

            if (projectile.isMarkedForRemoval()) {
                world.destroyBody(projectile.getBody());
                iterator.remove();
            }
        }

    }

    private void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = Math.round(player.getBody().getPosition().x * Constants.PPM * 10) / 10f;
        position.y = Math.round(player.getBody().getPosition().y * Constants.PPM * 10) / 10f;
        camera.position.set(position);
        camera.update();

    }

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
        // Added temporary background based on the lower left corner of the screen
        // window
        // Should be changed so that one cannot see clouds behind tiles, when player is
        // "in the ground".
        Vector2 lowerLeftCorner = getLowerLeftCorner();
        batch.draw(backgroundImage, lowerLeftCorner.x, lowerLeftCorner.y, camera.viewportWidth, camera.viewportHeight);
        batch.end();

        orthogonalTiledMapRenderer.render();
        batch.begin();

        enemyManager.render(batch);

        if (player != null) {
            player.render(batch);
            spriteManager.render(batch, player.getX(), player.getY());

        }

        if (overlayView != null) {
            overlayView.render(batch);
        }

        for (Projectile projectile : projectiles) {
            projectile.render(batch);
        }

        batch.end();

        // rendering the liquid layer in the end, so it looks like the player is behind
        // the liquid
        orthogonalTiledMapRenderer.render(new int[] { 2 });

        box2DDebugRenderer.render(world, camera.combined.scl(Constants.PPM));
    }

    public World getWorld() {
        return world;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public TiledMap getTiledMap() {
        return tileMapHelper.getTiledMap();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }  

    public void setPlayer(Player player) {
        this.player = player;
        this.overlayView = new OverlayView(200, 200, player.getBody(), inventory, playerHealth);
        this.spriteManager = new SpriteManager(player, inventory);
    }

    public static void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
        orthogonalTiledMapRenderer.dispose();
        // dispose the map
        getTiledMap().dispose();
        // dispose the sprite texture
        spriteManager.dispose();
    }

}
