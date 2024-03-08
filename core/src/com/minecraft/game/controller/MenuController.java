package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.minecraft.game.Minecraft;
import com.minecraft.game.view.GameScreen;
import com.minecraft.game.view.MenuScreen;
import com.minecraft.game.view.OptionsScreen;

public class MenuController {
    private final Minecraft game;
    private final MenuScreen menuScreen;

    public MenuController(Minecraft game, MenuScreen menuScreen) {
        this.game = game;
        this.menuScreen = menuScreen;
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            float touchX = Gdx.input.getX();

            if (menuScreen.isStartButtonClicked(touchX, touchY)) {
                game.setScreen(new GameScreen(game.orthographicCamera));
            } else if (menuScreen.isOptionsButtonClicked(touchX, touchY)) {
                game.setScreen(new OptionsScreen(game));
            } else if (menuScreen.isQuitButtonClicked(touchX, touchY)) {
                Gdx.app.exit();
            }
        }
    }
}
