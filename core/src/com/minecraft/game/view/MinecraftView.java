package com.minecraft.game.view;

import com.minecraft.game.Minecraft;
import com.minecraft.game.model.GameState;

public class MinecraftView {

    @SuppressWarnings("unused")
    private Minecraft game;
    private MenuScreen menuScreen;
    @SuppressWarnings("unused")
    private ViewableMinecraftModel viewableMinecraftModel;
    private OptionsScreen optionsScreen;
    private GameScreen gameScreen;

    public MinecraftView(Minecraft game, ViewableMinecraftModel viewableMinecraftModel) {

        this.game = game;
        this.viewableMinecraftModel = viewableMinecraftModel;

        this.menuScreen = new MenuScreen(game);
        this.optionsScreen = new OptionsScreen(game);
        this.gameScreen = new GameScreen(game.camera);

        updateScreen();

        //game.setScreen(menuScreen);

        //game.setScreen(new GameScreen(game.camera));
    }

    public void updateScreen() {
        if (viewableMinecraftModel.getGameState() == GameState.WELCOME_SCREEN){
            System.out.println("MenuScreen is started");
            game.setScreen(menuScreen);
        } else if (viewableMinecraftModel.getGameState() == GameState.PAUSED_SCREEN){
            System.out.println("PausedScreen is started");
            game.setScreen(optionsScreen);
        } else if (viewableMinecraftModel.getGameState() == GameState.GAME_ACTIVE){
            System.out.println("GameScreen is started");
            game.setScreen(gameScreen);
        } else if (viewableMinecraftModel.getGameState() == GameState.GAME_OVER){
            //game.setScreen(new GameOverScreen(game));
        }
    }

    public void dispose() {
        // Dispose of resources when the game is closing
    }

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
