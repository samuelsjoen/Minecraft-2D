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
    private SpriteBatch batch;
    private Player player;
    private Health playerHealth;
    private Inventory inventory;
    private OverlayView overlayView;
    private Texture backgroundImage; 
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
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.backgroundImage = new Texture(Gdx.files.internal("assets/background.png")); // Loads the background img
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawBodies(Constants.DEBUG_MODE);

        // TODO: Should be initialized in model, and use getter for getPlayerHealth() and getInventory()
        this.playerHealth = new Health(Constants.PLAYER_MAX_HEALTH, Constants.PLAYER_MAX_HEALTH);
        this.inventory = new Inventory(Constants.DEFAULT_ITEMS);
        
        // TODO: Should be initialized in model, and use getter for getOrthogonalTiledMapRenderer() & getWorld()
        this.world = new World(new Vector2(0, -25f), false);
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();

        // TODO: Should not initialize EnemyManager here, should be initialized in model, and use getter for getViewableEnemies() or something
        enemyManager = new EnemyManager(world, player, getTiledMap());

        // TODO: Should rather use MinecraftController which is initialized in Minecraft.java instead of initializing a controller here. 
        this.worldController = new WorldController(player, inventory, this);
        Gdx.input.setInputProcessor(worldController);

    }

    // TODO: world.step() should be called in model - 
    private void update() {
        world.step(1 / 60f, 6, 2);

        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();
        worldController.handleGameInput();
        this.inventory = this.getInventory();

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
    // TODO: instead of using player.getBody() directly here, we should use a getter
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

        // TODO: Should be in model - if projectiles should be drawn use getVisibleProjectiles() or something
        for (Projectile projectile : projectiles) {
            projectile.render(batch);
        }

        batch.end();

        // rendering the liquid layer in the end, so it looks like the player is behind
        // the liquid
        orthogonalTiledMapRenderer.render(new int[] { 2 });

        // TODO: World used directly - should use getter
        box2DDebugRenderer.render(world, camera.combined.scl(Constants.PPM));
    }

    // TODO: Is maybe not needed here - when we have getCamera in MinecraftView?
    public OrthographicCamera getCamera() {
        return camera;
    }

    // TODO: This should be in model - and use getter the other way around from model to view instead
    public World getWorld() {
        return world;
    }

    // TODO: Should be in model
    public TiledMap getTiledMap() {
        return tileMapHelper.getTiledMap();
    }

    // TODO: Should be in model
    public Inventory getInventory() {
        return inventory;
    }

    // TODO: Should be in model, and use getter from model to view through viewableminecraftmodel which we have in MinecraftView.java
    public Player getPlayer() {
        return player;
    }  

    // TODO: instead of using player directly here, we should use a getter or setter
    public void setPlayer(Player player) {
        this.player = player;
        this.overlayView = new OverlayView(inventory, playerHealth, camera);
        this.spriteManager = new SpriteManager(player, inventory);
    }

    // TODO: Should definitely be in model
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
        getTiledMap().dispose();
        spriteManager.dispose();
    }

}
