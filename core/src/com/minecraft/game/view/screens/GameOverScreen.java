package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.minecraft.game.Minecraft;

public class GameOverScreen extends ScreenAdapter {

    @SuppressWarnings("unused")
    private final Minecraft game;
    private BitmapFont font;
    private SpriteBatch batch;

    public GameOverScreen(Minecraft game) {
        this.game = game;
        this.font = new BitmapFont();
        this.font.getData().setScale(2); // increasing font size
        this.batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        clearScreen();    
        batch.begin();
        String message = "Game Over\nPress any button to restart";
        font.draw(batch, message, (Gdx.graphics.getWidth() / 2), (Gdx.graphics.getHeight() / 2), 0, Align.center, false);
        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        // Update the projection matrix
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }
}
