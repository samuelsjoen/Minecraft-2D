package com.minecraft.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.minecraft.game.Minecraft;
import com.minecraft.game.model.GameState;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.CursorUtils;
import com.minecraft.game.view.screens.GameOverScreen;
import com.minecraft.game.view.screens.GameScreen;
import com.minecraft.game.view.screens.GameWonScreen;
import com.minecraft.game.view.screens.HelpScreen;
import com.minecraft.game.view.screens.MenuScreen;
import com.minecraft.game.view.screens.PausedScreen;
import com.minecraft.game.view.sound.MineBlockSoundManager;
import com.minecraft.game.view.sound.SoundManager;

/**
 * The MinecraftView class represents the view component of the Minecraft game.
 * It is responsible for changing the screens and managing the user interface.
 */
public class MinecraftView implements Disposable {

    private Minecraft game;
    private ViewableMinecraftModel viewableMinecraftModel;

    private MenuScreen menuScreen;
    private HelpScreen helpScreen;
    private PausedScreen pausedScreen;
    private GameScreen gameScreen;
    private GameOverScreen gameOverScreen;
    private GameWonScreen gameWonScreen;
    
    private SpriteBatch spriteBatch;
    private BitmapFont font;

    private SoundManager mineBlockSoundManager;

    /**
     * Constructs a new MinecraftView object.
     *
     * @param game The Minecraft game instance.
     * @param viewableMinecraftModel The viewable Minecraft model.
     * @param spriteBatch The sprite batch used for rendering.
     * @param font The font used for rendering text.
     */
    public MinecraftView(Minecraft game, ViewableMinecraftModel viewableMinecraftModel, SpriteBatch spriteBatch, BitmapFont font) {

        this.game = game;
        this.viewableMinecraftModel = viewableMinecraftModel;

        this.spriteBatch = spriteBatch;
        this.font = font;

        this.mineBlockSoundManager = new MineBlockSoundManager("sound/mineSound.wav");

        this.menuScreen = new MenuScreen(spriteBatch);
        this.helpScreen = new HelpScreen(spriteBatch);
        this.gameScreen = new GameScreen(game.camera, viewableMinecraftModel, this, spriteBatch);
        this.pausedScreen = new PausedScreen(spriteBatch, font);
        this.gameOverScreen = new GameOverScreen(spriteBatch, font);
        this.gameWonScreen = new GameWonScreen(spriteBatch, font, viewableMinecraftModel); 

        updateScreen();

    }

    /**
     * Returns the menu screen stage.
     *
     * @return the menu screen stage
     */
    public Stage getMenuScreenStage() {
		return menuScreen.getStage();
	}

    /**
     * Returns the help screen stage.
     *
     * @return the help screen stage
     */
    public Stage getHelpScreenStage() {
        return helpScreen.getStage();
    }

    /**
     * Creates a new game screen and updates the view.
     */
    public void newGameScreen() {
        gameScreen.dispose();
        gameScreen = new GameScreen(game.camera, viewableMinecraftModel, this, spriteBatch);
        updateScreen();
    }

    /**
     * Updates the screen based on the current game state in the viewable Minecraft model.
     * Sets the appropriate screen in the game based on the game state.
     */
    public void updateScreen() {
        CursorUtils.setCursorPixmap("default_cursor.png");
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
        } else if (viewableMinecraftModel.getGameState() == GameState.GAME_WON){
            game.setScreen(gameWonScreen);        
        }
    }

    /**
     * Toggles the fullscreen mode.
     */
    public void toggleFullscreen() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
    }

    /**
     * Gets the camera used in the game.
     * @return The OrthographicCamera object representing the camera.
     */
    public OrthographicCamera getCamera() {
        return game.camera;
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

    @Override
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
        mineBlockSoundManager.dispose();
        menuScreen.dispose();
        helpScreen.dispose();
        pausedScreen.dispose();
        gameScreen.dispose();
        gameOverScreen.dispose();
    }

    // Getters used for testing only:

    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    public HelpScreen getHelpScreen() {
        return helpScreen;
    }

    public PausedScreen getPausedScreen() {
        return pausedScreen;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public GameOverScreen getGameOverScreen() {
        return gameOverScreen;
    }

    public GameWonScreen getGameWonScreen() {
        return gameWonScreen;
    }    

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public void setMenuScreen(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
    }

    public void setHelpScreen(HelpScreen helpScreen) {
        this.helpScreen = helpScreen;
    }

    public void setPausedScreen(PausedScreen pausedScreen) {
        this.pausedScreen = pausedScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void setGameOverScreen(GameOverScreen gameOverScreen) {
        this.gameOverScreen = gameOverScreen;
    }

    public void setGameWonScreen(GameWonScreen gameWonScreen) {
        this.gameWonScreen = gameWonScreen;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
    
    public BitmapFont getFont() {
        return font;
    }

    public SoundManager getMineBlockSoundManager() {
        return mineBlockSoundManager;
    }
    
}
