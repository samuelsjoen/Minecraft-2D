package com.minecraft.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.model.MinecraftModel;
import com.minecraft.game.view.MinecraftView;
import com.minecraft.game.controller.MinecraftController;

/**
 * The `Minecraft` class is responsible for creating and managing the game instance.
 * It serves as the entry point where essential elements are initialized.
 */
public class Minecraft extends Game {

	public static Minecraft INSTANCE;
	private int widthScreen, heightScreen;
	public OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private BitmapFont font;

	public Minecraft() {
		INSTANCE = this;
	}

	@Override
	public void create() {

		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, widthScreen, heightScreen);
		this.spriteBatch = new SpriteBatch();
        this.font = new BitmapFont();
		
		MinecraftModel model = new MinecraftModel();
		MinecraftView view = new MinecraftView(this, model, spriteBatch, font);
		new MinecraftController(model, view);		
	}

	/**
	 * Gets the camera used in the game.
	 * 
	 * @return The OrthographicCamera object representing the camera.
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}

	@Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
		spriteBatch.dispose();
		font.dispose();
    }
}
