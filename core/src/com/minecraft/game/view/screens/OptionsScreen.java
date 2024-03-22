package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.Minecraft;

public class OptionsScreen extends ScreenAdapter {
    // TODO: find out what should be displayed on this screen?

    @SuppressWarnings("unused")
    private final Minecraft game;
    private final SpriteBatch batch;
    private Texture backgroundTexture;

    public OptionsScreen(Minecraft game) {
        this.game = game;
        this.batch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("assets/home/menu_background.png"));
    }

    @Override
    public void render(float delta) {
        clearScreen();
        batch.begin();
        // Draw background
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        batch.dispose();
    }
}