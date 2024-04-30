package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * The HelpScreen class represents the screen displayed when the user accesses the help section of the game.
 * It provides functionality for rendering help information and navigating back to the main menu.
 */
public class HelpScreen extends ScreenAdapter {

    private final SpriteBatch batch;
    private Texture backgroundTexture;
    private Button button;
    private Stage stage;

    /**
     * Constructs a new HelpScreen with the specified SpriteBatch.
     * This screen is displayed when the user accesses the help section of the game,
     * providing functionality for rendering help information and navigating back to the main menu.
     *
     * @param batch The SpriteBatch used for rendering graphics.
     */
    public HelpScreen(SpriteBatch batch) {
        this.stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        int padding = 20;
        float x = Gdx.graphics.getWidth() - Gdx.graphics.getWidth() + padding;
        float y = Gdx.graphics.getHeight() - padding;

        this.button = new Button("helpScreen/back_button.png", stage, x, y, "backButton");
        this.button.createButton();

        this.batch = batch;
        backgroundTexture = new Texture(Gdx.files.internal("helpScreen/help_background.png"));
    }

    @Override
    public void render(float delta) {
        clearScreen();
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        this.backgroundTexture.dispose();
        this.batch.dispose();
        this.stage.dispose();
        this.button = null;
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


    // For testing:

    /**
     * Sets the stage of the HelpScreen.
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Returns the stage of the HelpScreen.
     * @return The stage.
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Returns the background texture of the HelpScreen.
     * @return The background texture.
     */
    public Texture getBackgroundTexture() {
        return this.backgroundTexture;
    }
}