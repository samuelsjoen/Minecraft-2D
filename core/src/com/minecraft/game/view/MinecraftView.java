package com.minecraft.game.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.minecraft.game.Minecraft;
import com.minecraft.game.model.GameState;
import com.minecraft.game.view.screens.GameOverScreen;
import com.minecraft.game.view.screens.GameScreen;
import com.minecraft.game.view.screens.MenuScreen;
import com.minecraft.game.view.screens.OptionsScreen;
import com.minecraft.game.view.screens.PausedScreen;

public class MinecraftView {

    private Minecraft game;
    private ViewableMinecraftModel viewableMinecraftModel;

    private MenuScreen menuScreen;
    private OptionsScreen optionsScreen;
    private PausedScreen pausedScreen;
    private GameScreen gameScreen;
    private GameOverScreen gameOverScreen;

    public MinecraftView(Minecraft game, ViewableMinecraftModel viewableMinecraftModel) {

        this.game = game;
        this.viewableMinecraftModel = viewableMinecraftModel;

        this.menuScreen = new MenuScreen(game);
        this.optionsScreen = new OptionsScreen(game);
        this.pausedScreen = new PausedScreen(game);
        // this.gameScreen = new GameScreen(game.camera);
        this.gameScreen = new GameScreen(game.camera, viewableMinecraftModel);
        this.gameOverScreen = new GameOverScreen(game);

        updateScreen();

    }

    public void updateScreen() {
        if (viewableMinecraftModel.getGameState() == GameState.WELCOME_SCREEN){
            System.out.println("MenuScreen is started");
            game.setScreen(menuScreen);
        } else if (viewableMinecraftModel.getGameState() == GameState.OPTIONS_SCREEN){
            System.out.println("OptionsScreen is started");
            game.setScreen(optionsScreen);
        } else if (viewableMinecraftModel.getGameState() == GameState.GAME_ACTIVE){
            System.out.println("GameScreen is started");
            game.setScreen(gameScreen);        
        } else if (viewableMinecraftModel.getGameState() == GameState.GAME_PAUSED){
                System.out.println("PausedScreen is started");
                game.setScreen(pausedScreen);
        } else if (viewableMinecraftModel.getGameState() == GameState.GAME_OVER){
            game.setScreen(gameOverScreen);
        }
    }

    public void dispose() {
        // Dispose of resources when the game is closing
    }

    public OrthographicCamera getCamera() {
        return game.camera;
    }

    // Used for checking if menuScreen buttons are clicked or not. 
    public boolean isStartButtonClicked(float touchX, float touchY) {
        return menuScreen.isStartButtonClicked(touchX, touchY);
    }

    public boolean isOptionsButtonClicked(float touchX, float touchY) {
        return menuScreen.isOptionsButtonClicked(touchX, touchY);
    }

    public boolean isQuitButtonClicked(float touchX, float touchY) {
        return menuScreen.isQuitButtonClicked(touchX, touchY);
    }
    

}
