package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.minecraft.game.view.ViewableMinecraftModel;

/**
 * The screen displayed when the game is won.
 * It prompts the user to restart the game.
 */
public class GameWonScreen extends ScreenAdapter {

    private final BitmapFont font;
    private final SpriteBatch batch;
    private ViewableMinecraftModel model;
    private Texture backgroundImage;

    /**
     * Constructs a new GameWonScreen.
     *
     * @param batch The SpriteBatch used for rendering.
     * @param font  The BitmapFont used for text rendering.
     */
    public GameWonScreen(SpriteBatch batch, BitmapFont font, ViewableMinecraftModel model) {
        this.batch = batch;
        this.font = font;
        this.model = model;
        this.font.getData().setScale(2); // increasing font size
        backgroundImage = new Texture(Gdx.files.internal("background/winScreen.png"));
    }

    @Override
    public void render(float delta) {
        clearScreen();    
        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        int score = this.model.getScore().getScore();
        String message = "Press any button to restart.\nScore : " + score;
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
        // Update the projection matrix
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

    /**
     * Clears the screen with a black color.
     */
    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
