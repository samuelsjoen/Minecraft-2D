package com.mygdx.game.view;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Minecraft2D;
import com.mygdx.game.model.Health;
import com.mygdx.game.model.Inventory;
import com.mygdx.game.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.Minecraft2D;
import com.mygdx.game.utils.Constants;

public class GameScreen implements Screen {
    private Minecraft2D game;
    private SpriteBatch batch;
    private Player player;
    private Health health;
    private Inventory inventory;
    private Texture backgroundImage; // Background image

    public GameScreen(Minecraft2D game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.player = new Player();
        this.health = new Health(batch);
        this.inventory = new Inventory(batch);
        this.backgroundImage = new Texture(Gdx.files.internal("assets/backgrd1.png")); // Loads the background img
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.update(delta);
        player.draw(batch);
        health.renderHealthBar();
        inventory.renderInventory();
        batch.end();
    }

    // Implement other necessary methods for the Screen interface
    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        backgroundImage.dispose();
    }
}
