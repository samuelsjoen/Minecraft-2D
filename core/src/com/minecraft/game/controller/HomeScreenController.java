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

    /**
     * The HomeScreenController class is responsible for controlling the home screen of the Minecraft game.
     * It handles the initialization of the view, the Minecraft controller, and the stage.
     * It also sets up the input processors for the buttons on the home screen.
     */
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

    /**
     * A listener that handles click events.
     *
     * @param minecraftController The main controller
     * @param gameState     The gameState that the click event will change to.
     */
    private ClickListener createClickListener(MinecraftController minecraftController, GameState gameState) {
        return new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (gameState == GameState.GAME_ACTIVE) { // if button changes gamestate to Active:
                    minecraftController.setLastGameState(GameState.GAME_ACTIVE);
                    minecraftController.setGameStateAndUpdateScreen(gameState);
                    Gdx.input.setInputProcessor(minecraftController);
                    return;
                }

                if (gameState == GameState.HELP_SCREEN) { // if button changes gamestate to helpscreen
                    minecraftController.setLastGameState(GameState.WELCOME_SCREEN);
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
