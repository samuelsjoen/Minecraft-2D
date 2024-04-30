package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.minecraft.game.model.GameState;
import com.minecraft.game.view.MinecraftView;

public class HelpScreenController {
    
    private MinecraftController minecraftController;
    private MinecraftView view;
    private Stage stage;

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

    private ClickListener createClickListener(MinecraftController minecraftController) {
        return new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Your logic when the button is clicked
                GameState lastGameState = minecraftController.getLastGameState();
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
