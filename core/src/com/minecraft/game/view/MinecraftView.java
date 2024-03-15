package com.minecraft.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.Minecraft;

public class MinecraftView extends Game {

    private Minecraft game;
    private SpriteBatch batch;

    public MinecraftView(Minecraft game) {

        this.game = game;
        this.batch = new SpriteBatch();

        game.setScreen(new MenuScreen(game));

        //game.setScreen(new GameScreen(game.camera));
    }

    public void dispose() {
        // Dispose of resources when the game is closing
    }

    @Override
    public void create() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

}
