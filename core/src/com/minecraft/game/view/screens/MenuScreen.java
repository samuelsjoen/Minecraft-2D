package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.Minecraft;

public class MenuScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture titleTexture;
    private Texture startButtonTexture;
    private Texture helpButtonTexture;
    private Texture quitButtonTexture;
    private final float buttonSpacing = 5;

    public MenuScreen(Minecraft game, SpriteBatch batch) {
        this.batch = batch;
        loadTextures();
    }

    private void loadTextures() {
        backgroundTexture = new Texture(Gdx.files.internal("home/menu_background.png"));
        titleTexture = new Texture(Gdx.files.internal("home/minecraft_logo.png"));

        startButtonTexture = new Texture(Gdx.files.internal("home/start_button.png"));
        helpButtonTexture = new Texture(Gdx.files.internal("home/help_button.png"));
        quitButtonTexture = new Texture(Gdx.files.internal("home/quit_button.png"));
    }

    @Override
    public void render(float delta) {
        clearScreen();
        batch.begin();
        drawMenu();
        batch.end();
    }

    void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    void drawMenu() {
        drawBackground();
        drawTitle();
        drawButtons();
    }

    void drawBackground() {
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    void drawTitle() {
        int titleWidth = (int) (titleTexture.getWidth() * 0.6f);
        int titleHeight = (int) (titleTexture.getHeight() * 0.6f);
        int titleX = (Gdx.graphics.getWidth() - titleWidth) / 2;
        int titleY = Gdx.graphics.getHeight() - titleHeight - 100;
        batch.draw(titleTexture, titleX, titleY, titleWidth, titleHeight);
    }

    void drawButtons() {
        float buttonY = 300;
        buttonY = drawButton(startButtonTexture, buttonY);
        buttonY -= buttonSpacing;
        buttonY = drawButton(helpButtonTexture, buttonY);
        buttonY -= buttonSpacing;
        drawButton(quitButtonTexture, buttonY);
    }

    float drawButton(Texture buttonTexture, float buttonY) {
        int buttonWidth = (int) (buttonTexture.getWidth());
        int buttonHeight = (int) (buttonTexture.getHeight());
        int buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        batch.draw(buttonTexture, buttonX, buttonY, buttonWidth, buttonHeight);
        return buttonY - buttonHeight;
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        titleTexture.dispose();
        startButtonTexture.dispose();
        helpButtonTexture.dispose();
        quitButtonTexture.dispose();
    }

    // Methods to check if buttons are clicked
    public boolean isStartButtonClicked(float touchX, float touchY) {
        return isButtonClicked(startButtonTexture, touchX, touchY);
    }

    public boolean isHelpButtonClicked(float touchX, float touchY) {
        return isButtonClicked(helpButtonTexture, touchX, touchY);
    }

    public boolean isQuitButtonClicked(float touchX, float touchY) {
        return isButtonClicked(quitButtonTexture, touchX, touchY);
    }

    boolean isButtonClicked(Texture buttonTexture, float touchX, float touchY) {
        int buttonWidth = buttonTexture.getWidth();
        int buttonHeight = buttonTexture.getHeight();
        int buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        float buttonY;
    
        // Determine the Y position based on the button texture being checked
        if (buttonTexture == startButtonTexture) {
            buttonY = 300;
        } else if (buttonTexture == helpButtonTexture) {
            buttonY = 300 - buttonHeight - buttonSpacing;
        } else { // quitButtonTexture
            buttonY = 300 - 2 * (buttonHeight + buttonSpacing);
        }
    
        return (touchX >= buttonX &&
                touchX <= buttonX + buttonWidth &&
                touchY >= buttonY &&
                touchY <= buttonY + buttonHeight);
    }

    @Override
    public void resize(int width, int height) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

    public void setBackgroundTexture(Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public void setTitleTexture(Texture titleTexture) {
        this.titleTexture = titleTexture;
    }

    public void setHelpButtonTexture(Texture helpButtonTexture) {
        this.helpButtonTexture = helpButtonTexture;
    }

    public void setStartButtonTexture(Texture startButtonTexture) {
        this.startButtonTexture = startButtonTexture;
    }

    public void setQuitButtonTexture(Texture quitButtonTexture) {
        this.quitButtonTexture = quitButtonTexture;
    }
}
