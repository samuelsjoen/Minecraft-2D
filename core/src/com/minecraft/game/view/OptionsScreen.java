package com.minecraft.game.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.Minecraft;

public class OptionsScreen implements Screen {
    
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
        
        // Tegn bakgrunnen
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        batch.end();
    }
    
    // Tøm skjermen
    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    
    // Remaining required methods of the Screen interface
    @Override
    public void show() { }
    
    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        batch.dispose();
    }
}