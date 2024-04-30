package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.minecraft.game.model.GameState;
import com.minecraft.game.view.MinecraftView;

/**
 * The HelpScreenController class is responsible for controlling the help screen in the Minecraft game.
 * It handles user interactions with the help screen buttons and updates the game state accordingly.
 */
public class HelpScreenController {
    
    private MinecraftController minecraftController;
    private MinecraftView view;
    private Stage stage;

    /**
     * Constructs a new HelpScreenController with the specified MinecraftView and MinecraftController.
     * Initializes the stage and adds a click listener to the help screen buttons.
     * @param view The MinecraftView containing the help screen stage.
     * @param minecraftController The MinecraftController for handling game state changes.
     */
    public HelpScreenController(MinecraftView view, MinecraftController minecraftController) {
        this.view = view;
        this.minecraftController = minecraftController;
        this.stage = this.view.getHelpScreenStage();

        // iterate through actors within stage and set input processor
        this.stage.getActors().forEach(actor -> {
            if (actor != null && actor instanceof Button) {
                actor.addListener(createClickListener(this.minecraftController));
            }
        });
    }

    /**
     * A listener that handles click events.
     */
    private ClickListener createClickListener(MinecraftController minecraftController) {
        return new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Your logic when the button is clicked
                GameState lastGameState = minecraftController.getLastGameState(); // either WELCOME_SCREEN or GAME_ACTIVE
                minecraftController.setGameStateAndUpdateScreen(lastGameState);
                if (lastGameState == GameState.WELCOME_SCREEN) {
                    Gdx.input.setInputProcessor(view.getMenuScreenStage());
                } else {
                Gdx.input.setInputProcessor(minecraftController);
                }
            }
        };
    }
}
