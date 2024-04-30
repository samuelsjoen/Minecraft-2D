package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.minecraft.game.model.GameState;
import com.minecraft.game.view.MinecraftView;

public class HomeScreenController {
    
    private MinecraftController minecraftController;
    private MinecraftView view;
    private Stage stage;

    public HomeScreenController(MinecraftView view, MinecraftController minecraftController) {
        this.view = view;
        this.minecraftController = minecraftController;
        this.stage = this.view.getMenuScreenStage();

        // iterate through actors within stage and set input processor
        this.stage.getActors().forEach(actor -> {
            if (actor != null && actor instanceof Button) {
                if (actor.getName() == "startButton") {
                    actor.addListener(createClickListener(this.minecraftController, GameState.GAME_ACTIVE));
                } 
                if (actor.getName() == "helpButton") {
                    actor.addListener(createClickListener(this.minecraftController, GameState.HELP_SCREEN));    
                } 
                if (actor.getName() == "quitButton") {
                    actor.addListener(createClickListener(minecraftController, null));    
                } 
            }
        });
    }

    private ClickListener createClickListener(MinecraftController minecraftController, GameState gameState) {
        return new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (gameState == GameState.GAME_ACTIVE) { // if button changes gamestate to Active:
                    minecraftController.setGameStateAndUpdateScreen(gameState);
                    Gdx.input.setInputProcessor(minecraftController);
                    return;
                }

                if (gameState == GameState.HELP_SCREEN) { // if button changes gamestate to helpscreen
                    minecraftController.setGameStateAndUpdateScreen(gameState);
                    Gdx.input.setInputProcessor(view.getHelpScreenStage());
                    return;
                }

                if (gameState == null) { // if button is quit button
                    Gdx.app.exit();
                    return;
                }
            }
        };
    }

}
