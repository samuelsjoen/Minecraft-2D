package com.minecraft.game.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.Minecraft;

public class MenuScreen implements Screen {
    private final Minecraft game;
    private final SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture titleTexture;
    private Texture startButtonTexture;
    private Texture optionsButtonTexture;
    private Texture quitButtonTexture;
    private final float scaleFactor = 0.25f; // Adjusting the scale factor as needed
    private final float buttonSpacing = 5; // Adjusting the spacing between buttons as needed

    public MenuScreen(Minecraft game) {
        this.game = game;
        this.batch = new SpriteBatch();
        loadTextures();
    }

    // Last inn bilder
    private void loadTextures() {
        backgroundTexture = new Texture(Gdx.files.internal("assets/menu_background.png"));
        titleTexture = new Texture(Gdx.files.internal("assets/minecraft_logo.png"));
        startButtonTexture = new Texture(Gdx.files.internal("assets/start_button.png"));
        optionsButtonTexture = new Texture(Gdx.files.internal("assets/options_button.png"));
        quitButtonTexture = new Texture(Gdx.files.internal("assets/quit_button.png"));
    }

    @Override
    public void render(float delta) {
        clearScreen();
        batch.begin();
        drawMenu();
        batch.end();
        handleInput();
    }
    
    // Tøm skjermen
    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    
    // Tegn menyelementene
    private void drawMenu() {
        drawBackground();
        drawTitle();
        drawButtons();
    }
    
    // Tegn bakgrunnen
    private void drawBackground() {
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    
    // Tegn tittelen
    private void drawTitle() {
        // Beregn posisjon og størrelse for tittelen
        int titleWidth = (int)(titleTexture.getWidth() * scaleFactor * 3);
        int titleHeight = (int)(titleTexture.getHeight() * scaleFactor * 3);
        int titleX = (Gdx.graphics.getWidth() - titleWidth) / 2;
        int titleY = Gdx.graphics.getHeight() - titleHeight - 50;
        batch.draw(titleTexture, titleX, titleY, titleWidth, titleHeight);
    }
    
    // Tegn knappene
    private void drawButtons() {
        float buttonY = 300;
        buttonY = drawButton(startButtonTexture, buttonY);
        buttonY -= buttonSpacing;
        buttonY = drawButton(optionsButtonTexture, buttonY);
        buttonY -= buttonSpacing;
        drawButton(quitButtonTexture, buttonY);
    }
    
    // Tegn en enkelt knapp og returner den nye Y-posisjonen
    private float drawButton(Texture buttonTexture, float buttonY) {
        int buttonWidth = (int)(buttonTexture.getWidth() * scaleFactor);
        int buttonHeight = (int)(buttonTexture.getHeight() * scaleFactor);
        int buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        batch.draw(buttonTexture, buttonX, buttonY, buttonWidth, buttonHeight);
        return buttonY - buttonHeight;
    }
    
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            float touchX = Gdx.input.getX();
            
            // Calculate the button positions
            int buttonWidth = (int)(startButtonTexture.getWidth() * scaleFactor);
            int buttonHeight = (int)(startButtonTexture.getHeight() * scaleFactor);
            float startButtonY = 300; // Y-coordinate for the start button
            float optionsButtonY = startButtonY - buttonHeight - buttonSpacing; // Y-coordinate for the options button
            float quitButtonY = optionsButtonY - buttonHeight - buttonSpacing; // Y-coordinate for the quit button
            float buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
            
            // Check if start button is clicked
            if (isButtonClicked(buttonX, startButtonY, touchX, touchY)) {
                //game.setScreen(new GameScreen(game)); // Start the game
                //return;
            }
    
            // Check if options button is clicked
            if (isButtonClicked(buttonX, optionsButtonY, touchX, touchY)) {
                // Handle options button click
                //game.setScreen(new OptionsScreen(game)); 
                //return;
            }
            
            // Check if quit button is clicked
            if (isButtonClicked(buttonX, quitButtonY, touchX, touchY)) {
                Gdx.app.exit(); // Quit the window
                return;
            }
        }
    }
    
    private boolean isButtonClicked(float buttonX, float buttonY, float touchX, float touchY) {
        return (touchX >= buttonX &&
                touchX <= buttonX + startButtonTexture.getWidth() * scaleFactor &&
                touchY >= buttonY &&
                touchY <= buttonY + startButtonTexture.getHeight() * scaleFactor);
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        titleTexture.dispose();
        startButtonTexture.dispose();
        optionsButtonTexture.dispose();
        quitButtonTexture.dispose();
    }
}
