package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

/**
 * The PausedScreen class represents the screen displayed when the game is paused.
 * It provides functionality for rendering text and graphics relevant to the paused state.
 */
public class PausedScreen extends ScreenAdapter {

    private BitmapFont font;
    private SpriteBatch batch;

    /**
     * Constructs a new PausedScreen with the specified SpriteBatch and BitmapFont.
     * This screen is displayed when the game is paused and allows rendering text and graphics.
     *
     * @param batch The SpriteBatch used for rendering graphics.
     * @param font The BitmapFont used for rendering text.
     */
    public PausedScreen(SpriteBatch batch, BitmapFont font) {
        this.batch = batch;
        this.font = font;
        this.font.getData().setScale(2); // increasing font size
    }

    @Override
    public void render(float delta) {
        clearScreen();    
        batch.begin();
        String message = "The game is paused. Press 'P' to resume game.";
        font.draw(batch, message, (Gdx.graphics.getWidth() / 2), (Gdx.graphics.getHeight() / 2), 0, Align.center, false);
        batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

    /**
     * Clears the screen by setting the background color to black.
     */
    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
