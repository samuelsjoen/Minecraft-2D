package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.minecraft.game.Minecraft;

public class GameWonScreen extends ScreenAdapter {

    @SuppressWarnings("unused")
    private final Minecraft game;
    final BitmapFont font;
    final SpriteBatch batch;

    public GameWonScreen(Minecraft game, SpriteBatch batch, BitmapFont font) {
        this.game = game;
        this.batch = batch;
        this.font = font;
        this.font.getData().setScale(2); // increasing font size
    }

    @Override
    public void render(float delta) {
        clearScreen();    
        batch.begin();
        String message = "Game Won\nPress 'R' to continue playing. \nPress any other button to restart.";
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