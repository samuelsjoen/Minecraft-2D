package com.minecraft.game.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.minecraft.game.Minecraft;
import com.minecraft.game.model.GameState;
import com.minecraft.game.view.screens.GameOverScreen;
import com.minecraft.game.view.screens.GameScreen;
import com.minecraft.game.view.screens.MenuScreen;
import com.minecraft.game.view.screens.OptionsScreen;
import com.minecraft.game.view.screens.PausedScreen;
import com.minecraft.game.view.sound.MineBlockSoundManager;
import com.minecraft.game.view.sound.SoundManager;

public class MinecraftView {

    private Minecraft game;
    private ViewableMinecraftModel viewableMinecraftModel;

    private MenuScreen menuScreen;
    private OptionsScreen optionsScreen;
    private PausedScreen pausedScreen;
    private GameScreen gameScreen;
    private GameOverScreen gameOverScreen;

    private SoundManager mineBlockSoundManager;

    public MinecraftView(Minecraft game, ViewableMinecraftModel viewableMinecraftModel) {

        this.game = game;
        this.viewableMinecraftModel = viewableMinecraftModel;

        // Create the sound manager for the mine block sound
        this.mineBlockSoundManager = new MineBlockSoundManager();

        this.menuScreen = new MenuScreen(game);
        this.optionsScreen = new OptionsScreen(game);
        this.gameScreen = new GameScreen(game.camera, viewableMinecraftModel);
        this.pausedScreen = new PausedScreen(game);
        this.gameOverScreen = new GameOverScreen(game);

        updateScreen();

    }

    public void newGameScreen() {
        gameScreen = new GameScreen(game.camera, viewableMinecraftModel);
    }

    public void updateScreen() {
        if (viewableMinecraftModel.getGameState() == GameState.WELCOME_SCREEN){
            game.setScreen(menuScreen);
        } else if (viewableMinecraftModel.getGameState() == GameState.OPTIONS_SCREEN){
            game.setScreen(optionsScreen);
        } else if (viewableMinecraftModel.getGameState() == GameState.GAME_ACTIVE){
            game.setScreen(gameScreen);        
        } else if (viewableMinecraftModel.getGameState() == GameState.GAME_PAUSED){
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
    
    /**
     * Plays the mine block sound
     */
    public void playMineBlockSound() {
        mineBlockSoundManager.playSound();
    }

    /**
     * Stops the mine block sound
     */
    public void stopMineBlockSound() {
        mineBlockSoundManager.stopSound();
    }

    // a general dispose class for all view elements that needs to get disposed??

}
