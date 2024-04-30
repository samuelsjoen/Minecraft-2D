package com.minecraft.game.view.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MenuScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private Texture backgroundTexture;
    private Stage stage;
    private Button startButton;
    private Button helpButton;
    private Button quitButton;
    private Button title;

    public MenuScreen(SpriteBatch batch) {
        this.batch = batch;
        loadTextures();

        this.stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        int space = 100;

        float x = Gdx.graphics.getWidth() / 2 - (375/2); // 375 is the width of the button
        float y = Gdx.graphics.getHeight() / 2 ; // the middle of the screen
        
        float yStart = y ;
        float yHelp = y - space;
        float yQuit = y - space * 2;

        float xTitle = Gdx.graphics.getWidth() / 2 - (800 / 2); // width of title is 800
        float yTitle = y + space * 2.5f;

        this.startButton = new Button("homeScreen/start_button.png", stage, x, yStart, "startButton");
        this.startButton.createButton();

        this.helpButton = new Button("homeScreen/help_button.png", stage, x, yHelp, "helpButton");
        this.helpButton.createButton();

        this.quitButton = new Button("homeScreen/quit_button.png", stage, x, yQuit, "quitButton");
        this.quitButton.createButton();

        this.title = new Button("homeScreen/minecraft_logo.png", stage, xTitle, yTitle, "title");
        this.title.createButton();

    }

    private void loadTextures() {
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
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

    void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    void drawBackground() {
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        stage.getViewport().update(width, height, true);
    }

    public Stage getStage() {
        return this.stage;
    }

    // Getters used for testing only: 

    public void setBackgroundTexture(Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
