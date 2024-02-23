package com.mygdx.game.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.game.Minecraft2D;
import com.mygdx.game.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.Minecraft2D;
import com.mygdx.game.utils.BodyHelperService;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.utils.TileMapHelper;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class GameScreen extends ScreenAdapter {
    private Minecraft2D game;
    private SpriteBatch batch;
    private Player player;
    private Texture backgroundImage; // Background image

    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap tiledMap;
    private World world;
    private Box2DDebugRenderer debugRenderer;

    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;

        this.batch = new SpriteBatch();
        this.backgroundImage = new Texture(Gdx.files.internal("assets/backgrd1.png")); // Loads the background img
        this.world = new World(new Vector2(0, -25f), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();

    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = Math.round(player.getBody().getPosition().x * Constants.PPM * 10) / 10f;
        position.y = Math.round(player.getBody().getPosition().y * Constants.PPM * 10) / 10f;
        camera.position.set(position);
        camera.update();

    }

    @Override
    public void render(float delta) {
        this.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();
        batch.begin();
        // render objects
        // batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(),
        // Gdx.graphics.getHeight());

        if (player != null) {
            player.render(batch);
        }
        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(Constants.PPM));
    }

    public World getWorld() {
        return world;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
