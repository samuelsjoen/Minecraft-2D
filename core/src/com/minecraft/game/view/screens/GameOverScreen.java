package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

/**
 * The screen displayed when the game is over.
 * It prompts the user to restart the game.
 */
public class GameOverScreen extends ScreenAdapter {

    final BitmapFont font;
    final SpriteBatch batch;
    private Texture backgroundImage;

    /**
     * Constructs a new GameOverScreen.
     *
     * @param batch The SpriteBatch used for rendering.
     * @param font  The BitmapFont used for text rendering.
     */
    public GameOverScreen(SpriteBatch batch, BitmapFont font) {
        this.batch = batch;
        this.font = font;
        this.font.getData().setScale(2); // increasing font size
        this.backgroundImage = new Texture(Gdx.files.internal("background/gameOverScreen.jpg"));
    }

    @Override
    public void render(float delta) {
        clearScreen();    
        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        String message = "Press any button to restart.";
        font.draw(batch, message, (Gdx.graphics.getWidth() / 2), (Gdx.graphics.getHeight() / 2), 0, Align.center, false);
        batch.end();
    }

    /**
     * Clears the screen with a black color.
     */
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
