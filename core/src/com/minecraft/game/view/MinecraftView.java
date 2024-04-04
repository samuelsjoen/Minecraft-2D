package com.minecraft.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.Minecraft;
import com.minecraft.game.model.GameState;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.view.screens.GameOverScreen;
import com.minecraft.game.view.screens.GameScreen;
import com.minecraft.game.view.screens.HelpScreen;
import com.minecraft.game.view.screens.MenuScreen;
import com.minecraft.game.view.screens.PausedScreen;

public class MinecraftView {

    private Minecraft game;
    private ViewableMinecraftModel viewableMinecraftModel;

    private MenuScreen menuScreen;
    private HelpScreen helpScreen;
    private PausedScreen pausedScreen;
    private GameScreen gameScreen;
    private GameOverScreen gameOverScreen;
    
    private SpriteBatch spriteBatch;
    private BitmapFont font;

    public MinecraftView(Minecraft game, ViewableMinecraftModel viewableMinecraftModel) {

        this.game = game;
        this.viewableMinecraftModel = viewableMinecraftModel;

        this.spriteBatch = new SpriteBatch();
        this.font = new BitmapFont();

        this.menuScreen = new MenuScreen(game);
        this.helpScreen = new HelpScreen(game, spriteBatch);
        this.gameScreen = new GameScreen(game.camera, viewableMinecraftModel, this);
        this.pausedScreen = new PausedScreen(game, spriteBatch, font);
        this.gameOverScreen = new GameOverScreen(game, spriteBatch, font);

        updateScreen();

    }

    public void newGameScreen() {
        gameScreen = new GameScreen(game.camera, viewableMinecraftModel, this);
        updateScreen();
    }

    public void updateScreen() {
        if (viewableMinecraftModel.getGameState() == GameState.WELCOME_SCREEN){
            game.setScreen(menuScreen);
        } else if (viewableMinecraftModel.getGameState() == GameState.HELP_SCREEN){
            game.setScreen(helpScreen);
        } else if (viewableMinecraftModel.getGameState() == GameState.GAME_ACTIVE){
            game.setScreen(gameScreen);        
        } else if (viewableMinecraftModel.getGameState() == GameState.GAME_PAUSED){
                game.setScreen(pausedScreen);
        } else if (viewableMinecraftModel.getGameState() == GameState.GAME_OVER){
            game.setScreen(gameOverScreen);
        }
    }

    public void toggleFullscreen() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
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

    public boolean isHelpButtonClicked(float touchX, float touchY) {
        return menuScreen.isHelpButtonClicked(touchX, touchY);
    }

    public boolean isQuitButtonClicked(float touchX, float touchY) {
        return menuScreen.isQuitButtonClicked(touchX, touchY);
    }
    
}
