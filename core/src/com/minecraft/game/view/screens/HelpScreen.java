package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class HelpScreen extends ScreenAdapter {

    private final SpriteBatch batch;
    private Texture backgroundTexture;
    private Button button;
    private Stage stage;

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

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    // For testing:

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    public Texture getBackgroundTexture() {
        return this.backgroundTexture;
    }
}