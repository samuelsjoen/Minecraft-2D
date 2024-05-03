package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * The MenuScreen class represents the main menu screen of the Minecraft game.
 * It provides functionality for rendering the main menu interface and handling user input.
 */
public class MenuScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture buttonTexture;
    private Button startButton;
    private Button helpButton;
    private Button quitButton;
    private Texture titleTexture;

    /**
     * Constructs a new MenuScreen with the specified SpriteBatch.
     * This screen represents the main menu of the Minecraft game, providing functionality
     * for rendering the main menu interface and handling user input.
     *
     * @param batch The SpriteBatch used for rendering graphics.
     */
    public MenuScreen(SpriteBatch batch) {
        this.batch = batch;
        loadTextures();

        this.stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        drawButtons();
        drawTitle();
    }

    @Override
    public void render(float delta) {
        clearScreen();
        batch.begin();
        drawBackground();
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        buttonTexture.dispose();
        titleTexture.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        stage.getViewport().update(width, height, true);
    }

    /**
     * Returns the stage connected to the menu screen.
     * @return The stage.
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Loads textures used in menu screen.
     */
    private void loadTextures() {
        backgroundTexture = new Texture(Gdx.files.internal("background/backgroundDay.png"));
    }

    /**
     * Clears the screen.
     */
    void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Draws the background of the menu screen.
     */
    void drawBackground() {
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Draws the buttons on the menu screen.
     */
    private void drawButtons() {
        int space = 100;

        buttonTexture = new Texture(Gdx.files.internal("homeScreen/start_button.png"));
        float buttonHeight = buttonTexture.getWidth();

        float x = (Gdx.graphics.getWidth() - (buttonHeight)) / 2; // buttonWidth is the width of the button
        float y = Gdx.graphics.getHeight() / 2 ; // the middle of the screen
        
        float yStart = y ;
        float yHelp = y - space;
        float yQuit = y - space * 2;

        this.startButton = new Button("homeScreen/start_button.png", stage, x, yStart, "startButton");
        this.startButton.createButton();

        this.helpButton = new Button("homeScreen/help_button.png", stage, x, yHelp, "helpButton");
        this.helpButton.createButton();

        this.quitButton = new Button("homeScreen/quit_button.png", stage, x, yQuit, "quitButton");
        this.quitButton.createButton();
    }

    /**
     * Draws the title of the menu screen.
     */
    private void drawTitle() {
        int space = 100;

        titleTexture = new Texture(Gdx.files.internal("homeScreen/minecraft_logo.png"));
        float titleHeight = titleTexture.getHeight();
        float titleWidth = titleTexture.getWidth();

        float xTitle = (Gdx.graphics.getWidth() - titleWidth)/ 2; 
        float yTitle = Gdx.graphics.getHeight() / 2 + space * 2.5f;

        Image titleImage = new Image(new TextureRegionDrawable(new TextureRegion(titleTexture)));
        titleImage.setPosition(xTitle, yTitle - titleHeight);
        stage.addActor(titleImage);   
    }


    // Setters below used for testing only: 
    /**
     * Sets the background texture of the menu screen.
     * @param backgroundTexture The background texture to set.
     */
    public void setBackgroundTexture(Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    /**
     * Sets the stage of the menu screen.
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
