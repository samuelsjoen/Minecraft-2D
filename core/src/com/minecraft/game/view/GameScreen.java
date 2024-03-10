package com.minecraft.game.view;

//import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
//import com.minecraft.game.Minecraft;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.controller.CharacterController;
import com.minecraft.game.controller.WorldInputProcessor;
import com.minecraft.game.controller.WorldListener;
import com.minecraft.game.controller.InventoryController;
import com.minecraft.game.model.EnemyManager;
import com.minecraft.game.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.Input.Keys;
//import com.minecraft.game.utils.BodyHelperService;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.SpriteManager;
import com.minecraft.game.utils.TileMapHelper;
import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
//import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import com.minecraft.game.model.Projectile;
import java.util.Iterator;
import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    // private Minecraft game;
    private SpriteBatch batch;
    private Player player;
    private Health playerHealth;
    private HealthView healthView;
    private Inventory inventory;
    private InventoryView inventoryView;
    private Texture backgroundImage; // Background image

    private World world;

    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;

    private EnemyManager enemyManager;
    private CharacterController characterController;
    private static ArrayList<Projectile> projectiles = new ArrayList<>();
    private InventoryController inventoryController;

    private WorldListener contactListener;
    private WorldInputProcessor inputProcessor;
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
        this.characterController = new CharacterController(player, inventory);
        this.inventoryController = new InventoryController(inventory);

        // controller
        this.contactListener = new WorldListener();
        this.world.setContactListener(contactListener);
        this.inputProcessor = new WorldInputProcessor(this);
        Gdx.input.setInputProcessor(inputProcessor);

        enemyManager = new EnemyManager(world, player, getTiledMap());

    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();
        characterController.update();
        inventoryController.update();

        spriteManager.update();

        healthView.update();
        inventoryView.update();
        enemyManager.update(0.01f);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

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

        if (healthView != null) {
            healthView.render(batch);
        }

        if (inventoryView != null) {
            inventoryView.render(batch);
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

    public void setPlayer(Player player) {
        this.player = player;
        this.healthView = new HealthView(2000, 2000, player.getBody(), playerHealth);
        this.inventoryView = new InventoryView(200, 200, player.getBody(), inventory);
        this.spriteManager = new SpriteManager(player, inventory);
    }

    public static void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

}

/*
 * package com.mygdx.game.view;
 * 
 * import com.badlogic.gdx.Screen;
 * import com.mygdx.game.Minecraft2D;
 * import com.mygdx.game.model.Player;
 * import com.badlogic.gdx.Gdx;
 * import com.badlogic.gdx.Screen;
 * import com.badlogic.gdx.graphics.GL20;
 * import com.badlogic.gdx.graphics.Texture;
 * import com.badlogic.gdx.graphics.g2d.Sprite;
 * import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 * import com.badlogic.gdx.Input.Keys;
 * import com.mygdx.game.Minecraft2D;
 * import com.mygdx.game.utils.Constants;
 * 
 * public class GameScreen implements Screen {
 * private Minecraft2D game;
 * private SpriteBatch batch;
 * private Player player;
 * private Texture backgroundImage; // Background image
 * 
 * public GameScreen(Minecraft2D game) {
 * this.game = game;
 * this.batch = new SpriteBatch();
 * this.player = new Player();
 * this.backgroundImage = new
 * Texture(Gdx.files.internal("assets/backgrd1.png")); // Loads the background
 * img
 * }
 * 
 * @Override
 * public void render(float delta) {
 * Gdx.gl.glClearColor(0, 0, 0, 1);
 * Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
 * 
 * batch.begin();
 * 
 * batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(),
 * Gdx.graphics.getHeight());
 * player.update(delta);
 * player.draw(batch);
 * batch.end();
 * }
 * 
 * // Implement other necessary methods for the Screen interface
 * 
 * @Override
 * public void show() {
 * }
 * 
 * @Override
 * public void resize(int width, int height) {
 * }
 * 
 * @Override
 * public void pause() {
 * }
 * 
 * @Override
 * public void resume() {
 * }
 * 
 * @Override
 * public void hide() {
 * }
 * 
 * @Override
 * public void dispose() {
 * batch.dispose();
 * player.dispose();
 * backgroundImage.dispose();
 * }
 * }
 */