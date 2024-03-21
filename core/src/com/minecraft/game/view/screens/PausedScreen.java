package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.minecraft.game.Minecraft;

public class PausedScreen extends ScreenAdapter {

    @SuppressWarnings("unused")
    private final Minecraft game;

    public PausedScreen(Minecraft game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        clearScreen();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
    }
}